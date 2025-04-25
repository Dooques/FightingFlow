package com.example.fightingflow.ui.comboAddScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.TekkenDbRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.moveEntryToMoveList
import com.example.fightingflow.model.toDisplay
import com.example.fightingflow.model.toEntry
import com.example.fightingflow.util.ADD_COMBO_VM_TAG
import com.example.fightingflow.util.CharacterUiState
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.ComboEntryListUiState
import com.example.fightingflow.util.MoveListUiState
import com.example.fightingflow.util.emptyComboDisplay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AddComboViewModel(
    private val tekkenDataRepository: TekkenDbRepository,
    private val comboDsRepository: ComboDsRepository,
    private val characterDsRepository: CharacterDsRepository
): ViewModel() {

    companion object {
        const val TIME_MILLIS = 2_000L
    }

    val editingState = mutableStateOf(false)

    val characterState = MutableStateFlow(CharacterUiState())

    val comboIdFromDs = comboDsRepository.getComboId()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = ""
        )

    val existingCombos = tekkenDataRepository.getAllCombos()
        .map { ComboEntryListUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = ComboEntryListUiState()
        )

    val comboDisplayState = MutableStateFlow(ComboDisplayUiState())

    val comboAsStringState = MutableStateFlow(processComboAsString())

    val comboEntryListState = MutableStateFlow<ComboEntryListUiState>(ComboEntryListUiState())

    // Combo Processing Functions
    fun processComboAsString(): String {
        Log.d(ADD_COMBO_VM_TAG, "")
        Log.d(ADD_COMBO_VM_TAG, "Processing combo as string...")
        Log.d(ADD_COMBO_VM_TAG, "Getting iterator...")
        val comboIterator = comboDisplayState.value.comboDisplay.moves.iterator()
        var moveAsString = ""
        Log.d(ADD_COMBO_VM_TAG, "Starting while loop to process combo")
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
        Log.d(ADD_COMBO_VM_TAG, "Processed combo: $moveAsString")
        return moveAsString
    }

    fun updateComboDetails(comboDisplay: ComboDisplayUiState) {
        Log.d(ADD_COMBO_VM_TAG, "")
        Log.d(ADD_COMBO_VM_TAG, "Updating combo details...")
        val comboDisplayWithCharacterData =
            comboDisplay.comboDisplay.copy(character = characterState.value.character.name)

        Log.d(ADD_COMBO_VM_TAG, "Combo Display with Character: $comboDisplayWithCharacterData")
        comboDisplayState.update { ComboDisplayUiState(comboDisplayWithCharacterData) }

        Log.d(ADD_COMBO_VM_TAG, "Combo Display Ui State: ")
        comboAsStringState.update { processComboAsString() }

        Log.d(ADD_COMBO_VM_TAG, "")
        Log.d(ADD_COMBO_VM_TAG, "Updating combo details for ${characterState.value.character.name}")
        Log.d(
            ADD_COMBO_VM_TAG, "Updated Combo Details: " +
                    "\nComboId: ${comboDisplayState.value.comboDisplay.comboId}" +
                    "\nCharacter: ${comboDisplayState.value.comboDisplay.character}" +
                    "\nDamage: ${comboDisplayState.value.comboDisplay.damage}" +
                    "\nCreated by: ${comboDisplayState.value.comboDisplay.createdBy}" +
                    "\nmoves: ${comboAsStringState.value}"
        )
        Log.d(ADD_COMBO_VM_TAG, "Saving updated combo to datastore...")
        saveComboDetailsToDs(comboDisplayState.value)
    }

    fun updateMoveList(moveName: String, moveListUiState: MoveListUiState) {
        Log.d(ADD_COMBO_VM_TAG, "")
        Log.d(ADD_COMBO_VM_TAG, "Adding $moveName to combo...")
        Log.d(ADD_COMBO_VM_TAG, "Move List: ${moveListUiState.moveList.size} moves")
        val moveToAdd = moveListUiState.moveList.first { it.moveName == moveName }
        Log.d(ADD_COMBO_VM_TAG, "${moveToAdd.moveName} found.")
        val updatedCombo = comboDisplayState.value.comboDisplay.copy(moves = comboDisplayState.value.comboDisplay.moves + moveToAdd)
        Log.d(ADD_COMBO_VM_TAG, "Updated Combo: $updatedCombo")
        updateComboDetails(ComboDisplayUiState(updatedCombo))
    }

    fun deleteLastMove() {
        Log.d(ADD_COMBO_VM_TAG, "")
        Log.d(ADD_COMBO_VM_TAG, "Deleting last move...")
        val currentMoves: MutableList<MoveEntry> = comboDisplayState.value.comboDisplay.moves.toMutableList()
        Log.d(ADD_COMBO_VM_TAG, "Temporary move list created, move to delete: ${currentMoves.last()}")
        currentMoves.removeAt(currentMoves.size - 1)
        Log.d(ADD_COMBO_VM_TAG, "Move removed from temp list, preparing to copy and update.")
        val updatedCombo = comboDisplayState.value.comboDisplay.copy(moves = currentMoves.toList())
        updateComboDetails(ComboDisplayUiState(updatedCombo))
    }

    fun clearMoveList() {
        Log.d(ADD_COMBO_VM_TAG, "")
        Log.d(ADD_COMBO_VM_TAG, "Clearing move list...")
        comboDisplayState.update { ComboDisplayUiState() }
        comboAsStringState.update { "" }
        Log.d(ADD_COMBO_VM_TAG, "Cleared move list: ${comboAsStringState.value}")
    }

    fun saveCombo() {
        Log.d(ADD_COMBO_VM_TAG, "")
        Log.d(ADD_COMBO_VM_TAG, "Checking if combo details valid")
        Log.d(ADD_COMBO_VM_TAG, "ComboDisplay Character: ${comboDisplayState.value.comboDisplay.character}")
        Log.d(ADD_COMBO_VM_TAG, "ComboDisplay Moves: ${comboDisplayState.value.comboDisplay.moves}")
        if (
            comboDisplayState.value.comboDisplay.character.isNotEmpty() &&
            comboDisplayState.value.comboDisplay.moves.isNotEmpty()
            ) {
            Log.d(ADD_COMBO_VM_TAG, "Preparing to add combo to Db, checking if edit mode...")
            if (editingState.value) {
                Log.d(ADD_COMBO_VM_TAG, "Edit mode true, updating existing combo...")
                updateComboInDb()
            } else {
                Log.d(ADD_COMBO_VM_TAG, "Edit mode false, adding new combo...")
                insertComboToDb()
            }
        } else {
            Log.d(ADD_COMBO_VM_TAG, "Cannot save Combo, please make sure a Character is selected and the Move List is not empty.")
        }
    }

    fun getExistingComboFromList() {
        Log.d(ADD_COMBO_VM_TAG, "")
        Log.d(ADD_COMBO_VM_TAG, "Getting Combo from existing combo list...")
        val combo = ComboDisplayUiState(
                existingCombos.value.comboEntryList.first {it.comboId == comboIdFromDs.value}.toDisplay()
            )
        Log.d(ADD_COMBO_VM_TAG, "Combo found: ${combo.comboDisplay}")
        comboDisplayState.update { combo }
        Log.d(ADD_COMBO_VM_TAG, "ComboDisplayState updated.")
    }

    // Datastore Functions
    fun saveComboDetailsToDs(comboDisplay: ComboDisplayUiState = ComboDisplayUiState()) {
        Log.d(ADD_COMBO_VM_TAG, "")
        Log.d(ADD_COMBO_VM_TAG, "Saving combo details to datastore...")
        Log.d(ADD_COMBO_VM_TAG, "Combo to save: ${comboDisplay.comboDisplay}")
        viewModelScope.launch {
            Log.d(ADD_COMBO_VM_TAG, "Checking if combo empty...")
            if (comboDisplay.comboDisplay == emptyComboDisplay) {
                Log.d(ADD_COMBO_VM_TAG, "Combo empty, adding empty combo to datastore...")
                comboDsRepository.setCombo(comboDisplay.comboDisplay)
            } else {
                Log.d(ADD_COMBO_VM_TAG, "Combo details found, saving them to datastore...")
                comboDsRepository.setCombo(comboDisplayState.value.comboDisplay)
            }
        }
        Log.d(ADD_COMBO_VM_TAG, "Combo saved to datastore.")
    }

    // Room Db Functions
    fun insertComboToDb() {
        viewModelScope.launch {
            Log.d(ADD_COMBO_VM_TAG, "")
            val comboEntry = comboDisplayState.value.comboDisplay.toEntry(characterState.value.character)
            tekkenDataRepository.insertCombo(comboEntry)
            Log.d(ADD_COMBO_VM_TAG, "Combo added to Db.")

            clearMoveList()
            Log.d(ADD_COMBO_VM_TAG, "Move list cleared.")
        }
    }

     fun updateComboInDb() {
         viewModelScope.launch {
             Log.d(ADD_COMBO_VM_TAG, "")
             val existingCombo = comboEntryListState.value.comboEntryList.first {it.comboId == comboDisplayState.value.comboDisplay.comboId}
             val newCombo = comboDisplayState.value.comboDisplay

             tekkenDataRepository.updateCombo(
                 existingCombo.copy(
                     id = newCombo.id,
                     comboId = newCombo.comboId,
                     character = characterState.value.character,
                     damage = newCombo.damage,
                     createdBy = newCombo.createdBy,
                     moves = moveEntryToMoveList(newCombo.moves)
                 )
             )
             Log.d(ADD_COMBO_VM_TAG, "Existing combo updated in Db.")
             clearMoveList()
             Log.d(ADD_COMBO_VM_TAG, "Move list cleared.")
         }
    }
}