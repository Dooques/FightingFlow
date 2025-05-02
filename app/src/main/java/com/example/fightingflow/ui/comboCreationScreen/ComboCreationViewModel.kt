package com.example.fightingflow.ui.comboCreationScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.FlowRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.moveEntryToMoveList
import com.example.fightingflow.model.toDisplay
import com.example.fightingflow.model.toEntry
import com.example.fightingflow.util.CharacterUiState
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.ComboEntryListUiState
import com.example.fightingflow.util.ImmutableList
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.emptyComboDisplay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber


class ComboCreationViewModel(
    private val flowRepository: FlowRepository,
    private val comboDsRepository: ComboDsRepository,
    private val characterDsRepository: CharacterDsRepository
): ViewModel() {

    companion object {
        const val TIME_MILLIS = 2_000L
    }

    val editingState = mutableStateOf(false)
    val characterState = MutableStateFlow(CharacterUiState())
    val comboDisplayState = MutableStateFlow(ComboDisplayUiState())
    val comboAsStringState = MutableStateFlow(processComboAsString())

    private val _comboEntryListState = MutableStateFlow(ComboEntryListUiState())
    val comboEntryListState: StateFlow<ComboEntryListUiState> = _comboEntryListState


    val comboIdFromDs = comboDsRepository.getComboId()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = ""
        )

    val existingCombos = flowRepository.getAllCombos()
        .map { ComboEntryListUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = ComboEntryListUiState()
        )

    // Combo Processing Functions
    private fun processComboAsString(): String {
        Timber.d("")
        Timber.d("Processing combo as string...")
        val comboIterator = comboDisplayState.value.comboDisplay.moves.iterator()
        var moveAsString = ""
        while (comboIterator.hasNext()) {
            val move = comboIterator.next().notation
            if (move == "Break") {
                moveAsString += " $move "
            }
            moveAsString += move
            if (comboIterator.hasNext()) {
                moveAsString += ", "
            }
        }
        Timber.d("Processed combo: $moveAsString")
        return moveAsString
    }

    fun updateComboDetails(comboDisplay: ComboDisplayUiState) {
        Timber.d("")
        Timber.d("Updating combo details...")
        val comboDisplayWithCharacterData = comboDisplay.comboDisplay.copy(
                character = characterState.value.character.name)

        Timber.d("Combo Display with Character: $comboDisplayWithCharacterData")
        comboDisplayState.update { ComboDisplayUiState(comboDisplayWithCharacterData) }

        Timber.d("Combo Display Ui State: ")
        comboAsStringState.update { processComboAsString() }

        Timber.d("")
//        Timber.d("Updating combo details for ${characterState.value.character.name}")
//        Timber.d("Updated Combo Details: ")
//        Timber.d("ComboId: ${comboDisplayState.value.comboDisplay.comboId}")
//        Timber.d("Character: ${comboDisplayState.value.comboDisplay.character}")
//        Timber.d("Damage: ${comboDisplayState.value.comboDisplay.damage}")
//        Timber.d("Created by: ${comboDisplayState.value.comboDisplay.createdBy}")
//        Timber.d("Moves: ${comboAsStringState.value}")
        Timber.d("Saving updated combo to datastore...")
        saveComboDetailsToDs(comboDisplayState.value)
    }

    fun updateMoveList(moveName: String, moveListUiState: MoveEntryListUiState) {
        Timber.d("")
        Timber.d("Adding $moveName to combo...")
        Timber.d("Move List: ${moveListUiState.moveList.size} moves")
        val moveToAdd = moveListUiState.moveList.first { it.moveName == moveName }
        Timber.d("${moveToAdd.moveName} found.")
        val updatedCombo = comboDisplayState.value.comboDisplay.copy(moves = ImmutableList(list =comboDisplayState.value.comboDisplay.moves + moveToAdd))
        Timber.d("Updated Combo: $updatedCombo")
        updateComboDetails(ComboDisplayUiState(updatedCombo))
    }

    fun deleteLastMove() {
        Timber.d("")
        Timber.d("Deleting last move...")
        val currentMoves: MutableList<MoveEntry> = comboDisplayState.value.comboDisplay.moves.toMutableList()
        Timber.d("Temporary move list created, move to delete: ${currentMoves.last()}")
        currentMoves.removeAt(currentMoves.size - 1)
        Timber.d("Move removed from temp list, preparing to copy and update.")
        val updatedCombo = comboDisplayState.value.comboDisplay.copy(moves = ImmutableList(currentMoves.toList()))
        updateComboDetails(ComboDisplayUiState(updatedCombo))
    }

    fun clearMoveList() {
        Timber.d("")
        Timber.d("Clearing move list...")
        comboDisplayState.update { ComboDisplayUiState() }
        comboAsStringState.update { "" }
        Timber.d("Cleared move list: ${comboAsStringState.value}")
    }

    fun saveCombo() {
        Timber.d("")
        Timber.d("Checking if combo details valid")
        Timber.d("ComboDisplay Character: ${comboDisplayState.value.comboDisplay.character}")
        Timber.d("ComboDisplay Moves: ${comboDisplayState.value.comboDisplay.moves}")
        if (
        comboDisplayState.value.comboDisplay.character.isNotEmpty() &&
            comboDisplayState.value.comboDisplay.moves.isNotEmpty()
            ) {
            Timber.d("Preparing to add combo to Db, checking if edit mode...")
            if (editingState.value) {
                Timber.d("Edit mode true, updating existing combo...")
                updateComboInDb()
            } else {
                Timber.d("Edit mode false, adding new combo...")
                insertComboToDb()
            }
        } else {
            Timber.d("Cannot save Combo, please make sure a Character is selected and the Move List is not empty.")
        }
    }

    fun getExistingCombo() {
        viewModelScope.launch {
            _comboEntryListState.update { ComboEntryListUiState(flowRepository.getAllCombos().first()) }
            comboDisplayState.update {
                ComboDisplayUiState(comboEntryListState.value.comboEntryList.first {combo -> combo.comboId == comboIdFromDs.value }.toDisplay())
            }
        }
    }

    // Datastore Functions
    fun saveComboDetailsToDs(comboDisplay: ComboDisplayUiState = ComboDisplayUiState()) {
        Timber.d("")
        Timber.d("Saving combo details to datastore...")
        Timber.d("Combo to save: ${comboDisplay.comboDisplay}")
        viewModelScope.launch {
            Timber.d("Checking if combo empty...")
            if (comboDisplay.comboDisplay == emptyComboDisplay) {
                Timber.d("Combo empty, adding empty combo to datastore...")
                comboDsRepository.setCombo(comboDisplay.comboDisplay)
            } else {
                Timber.d("Combo details found, saving them to datastore...")
                comboDsRepository.setCombo(comboDisplayState.value.comboDisplay)
            }
        }
        Timber.d("Combo saved to datastore.")
    }

    // Room Db Functions
    private fun insertComboToDb() {
        viewModelScope.launch {
            Timber.d("")
            val comboEntry = comboDisplayState.value.comboDisplay.toEntry(characterState.value.character)
            flowRepository.insertCombo(comboEntry)
            Timber.d("Combo added to Db.")
            Timber.d("Clearing move list...")
            clearMoveList()
            Timber.d("Move list cleared.")
        }
    }

     private fun updateComboInDb() {
         viewModelScope.launch {
             Timber.d("")
             val existingCombo = comboEntryListState.value.comboEntryList.first { combo ->
                 combo.comboId == comboDisplayState.value.comboDisplay.comboId
             }
             val newCombo = comboDisplayState.value.comboDisplay

             flowRepository.updateCombo(
                 existingCombo.copy(
                     id = newCombo.id,
                     comboId = newCombo.comboId,
                     character = characterState.value.character,
                     damage = newCombo.damage,
                     createdBy = newCombo.createdBy,
                     moves = moveEntryToMoveList(newCombo.moves)
                 )
             )
             Timber.d("Existing combo updated in Db.")
             clearMoveList()
             Timber.d("Move list cleared.")
         }
    }
}