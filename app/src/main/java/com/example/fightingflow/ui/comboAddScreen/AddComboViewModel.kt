package com.example.fightingflow.ui.comboAddScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.TekkenDbRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.moveEntryToMoveList
import com.example.fightingflow.model.toEntry
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

const val VM_TAG = "AddComboViewModel"

class AddComboViewModel(
    private val tekkenDataRepository: TekkenDbRepository,
    private val comboRepository: ComboDsRepository,
): ViewModel() {

    companion object {
        const val TIME_MILLIS = 2_000L
    }

    val characterState = MutableStateFlow(CharacterUiState())
    val comboState = MutableStateFlow(ComboDisplayUiState())
    val comboAsStringState = MutableStateFlow(processComboAsString())
    val moveListState = getAllMovesEntries()
    val comboEntryListState = MutableStateFlow<ComboEntryListUiState>(ComboEntryListUiState())
    val editingState = mutableStateOf(false)

    // Combo Processing Functions
    fun processComboAsString(): String {
        Log.d(VM_TAG, "")
        Log.d(VM_TAG, "Processing combo as string...")
        Log.d(VM_TAG, "Getting iterator...")
        val comboIterator = comboState.value.comboDisplay.moves.iterator()
        var moveAsString = ""
        Log.d(VM_TAG, "Starting while loop to process combo")
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
        Log.d(VM_TAG, "Processed combo: $moveAsString")
        return moveAsString
    }

    fun updateComboDetails(comboDisplayState: ComboDisplayUiState) {
        Log.d(VM_TAG, "")
        Log.d(VM_TAG, "Updating combo details...")
        val comboDisplayWithCharacterData = comboDisplayState.comboDisplay.copy(character = characterState.value.character.name)

        Log.d(VM_TAG, "Combo Display with Character: $comboDisplayWithCharacterData")
        comboState.update { ComboDisplayUiState(comboDisplayWithCharacterData) }

        Log.d(VM_TAG, "Combo Display Ui State: ")
        comboAsStringState.update { processComboAsString() }

        Log.d(VM_TAG, "")
        Log.d(VM_TAG, "Updating combo details for ${characterState.value.character.name}")
        Log.d(
            VM_TAG, "Updated Combo Details: " +
                    "\nComboId: ${comboState.value.comboDisplay.comboId}" +
                    "\nCharacter: ${comboState.value.comboDisplay.character}" +
                    "\nDamage: ${comboState.value.comboDisplay.damage}" +
                    "\nCreated by: ${comboState.value.comboDisplay.createdBy}" +
                    "\nmoves: ${comboAsStringState.value}"
        )

        Log.d(VM_TAG, "Saving updated combo to datastore...")
        saveComboDetailsToDs(comboState.value)
    }

    fun updateMoveList(moveName: String, moveListUiState: MoveListUiState) {
        Log.d(VM_TAG, "")
        Log.d(VM_TAG, "Adding $moveName to combo...")
        Log.d(VM_TAG, "Move List: ${moveListUiState.moveList.size} moves")
        val moveToAdd = moveListUiState.moveList.first { it.moveName == moveName }
        Log.d(VM_TAG, "${moveToAdd.moveName} found.")
        val updatedCombo = comboState.value.comboDisplay.copy(moves = comboState.value.comboDisplay.moves + moveToAdd)
        Log.d(VM_TAG, "Updated Combo: $updatedCombo")
        updateComboDetails(ComboDisplayUiState(updatedCombo))
    }

    fun deleteLastMove() {
        Log.d(VM_TAG, "")
        Log.d(VM_TAG, "Deleting last move...")
        val currentMoves: MutableList<MoveEntry> = comboState.value.comboDisplay.moves.toMutableList()
        Log.d(VM_TAG, "Temporary move list created, move to delete: ${currentMoves.last()}")
        currentMoves.removeAt(currentMoves.size - 1)
        Log.d(VM_TAG, "Move removed from temp list, preparing to copy and update.")
        val updatedCombo = comboState.value.comboDisplay.copy(moves = currentMoves.toList())
        updateComboDetails(ComboDisplayUiState(updatedCombo))
    }

    fun clearMoveList() {
        Log.d(VM_TAG, "")
        Log.d(VM_TAG, "Clearing move list...")
        comboState.update { ComboDisplayUiState() }
        comboAsStringState.update { "" }
        Log.d(VM_TAG, "Cleared move list: ${comboAsStringState.value}")
    }

    fun saveCombo() {
        Log.d(VM_TAG, "")
        Log.d(VM_TAG, "Checking if combo details valid")
        if (
            comboState.value.comboDisplay.character.isNotEmpty() &&
            comboState.value.comboDisplay.moves.isNotEmpty()
            ) {
            Log.d(VM_TAG, "Preparing to add combo to Db, checking if edit mode...")
            if (editingState.value) {
                Log.d(VM_TAG, "Edit mode true, updating existing combo...")
                updateComboInDb()
            } else {
                Log.d(VM_TAG, "Edit mode false, adding new combo...")
                insertComboToDb()
            }
        } else {
            Log.d(VM_TAG, "Cannot save Combo, please make sure a Character is selected and the Move List is not empty.")
        }
    }

    fun getMoveEntryData(comboString: String, moveList: MoveListUiState): List<MoveEntry> {
        Log.d(VM_TAG, "")
        Log.d(VM_TAG, "Combo String: $comboString")
        Log.d(VM_TAG, "Move List: ${moveList.moveList.size} moves")
        if (comboString != "") {
            val moveEntrySplit = comboString.split(",")
            Log.d(VM_TAG, "Split List: $moveEntrySplit")
            val moveEntryList = moveEntrySplit.map { move ->
                val updateData = moveList.moveList.first { it.moveName == move }
                MoveEntry(
                    id = updateData.id,
                    moveName = updateData.moveName,
                    notation = updateData.notation,
                    moveType = updateData.moveType,
                    counterHit = updateData.counterHit,
                    hold = updateData.hold,
                    justFrame = updateData.justFrame,
                    associatedCharacter = updateData.associatedCharacter
                )
            }
            Log.d(VM_TAG, "Move Entry List: $moveEntryList")
            return moveEntryList
        } else {
            return listOf()
        }
    }

    // Datastore Functions
    fun saveComboDetailsToDs(comboDisplay: ComboDisplayUiState = ComboDisplayUiState()) {
        Log.d(VM_TAG, "")
        Log.d(VM_TAG, "Saving combo details to datastore...")
        Log.d(VM_TAG, "Combo to save: ${comboDisplay.comboDisplay}")
        viewModelScope.launch {
            Log.d(VM_TAG, "Checking if combo empty...")
            if (comboDisplay.comboDisplay == emptyComboDisplay) {
                Log.d(VM_TAG, "Combo empty, adding empty combo to datastore...")
                comboRepository.setCombo(comboDisplay.comboDisplay, "")
            } else {
                Log.d(VM_TAG, "Combo details found, saving them to datastore...")
                comboRepository.setCombo(comboState.value.comboDisplay, comboAsStringState.value)
            }
        }
        Log.d(VM_TAG, "Combo saved to datastore.")
    }

    fun getComboDetailsFromDs(moveList: MoveListUiState) {
        Log.d(VM_TAG, "")
        Log.d(VM_TAG, "Getting comboId from datastore...")
        val comboId = comboRepository.getComboId()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ""
            )
        Log.d(VM_TAG, "comboId: ${comboId.value}")
        Log.d(VM_TAG, "Getting character from datastore...")
        val character = comboRepository.getCharacter()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ""
            )
        Log.d(VM_TAG, "Character: ${character.value}")
        Log.d(VM_TAG, "Loading damage from datastore...")
        val damage= comboRepository.getDamage()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = 0
            )
        Log.d(VM_TAG, "Damage: ${damage.value}")
        Log.d(VM_TAG, "Loading createdBy from datastore...")
        val createdBy = comboRepository.getCreatedBy()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ""
            )
        Log.d(VM_TAG, "createdBy: ${createdBy.value}")
        Log.d(VM_TAG, "Loading moves from Datastore...")
        val moves = comboRepository.getCreatedBy()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ""
            )
        Log.d(VM_TAG, "Moves: ${moves.value}")
        Log.d(VM_TAG, "Updating combo state...")
        comboState.update {
            ComboDisplayUiState(
                comboDisplay = ComboDisplay(
                    id = 0,
                    comboId = comboId.value,
                    character = character.value,
                    damage = damage.value,
                    createdBy = createdBy.value,
                    moves = getMoveEntryData(moves.value, moveList)
                )
            )
        }
        Log.d(VM_TAG, "Combo: ${comboState.value.comboDisplay}")
    }

    fun getMoveListFromDs() =
        comboRepository.getMoveList()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ""
            )

    // Room Db Functions
    fun getAllMovesEntries() =
        tekkenDataRepository.getAllMoves()
            .map { MoveListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = MoveListUiState()
            )

    fun insertComboToDb() {
        viewModelScope.launch {
            Log.d(VM_TAG, "")
            val comboEntry = comboState.value.comboDisplay.toEntry(characterState.value.character)
            tekkenDataRepository.insertCombo(comboEntry)
            Log.d(VM_TAG, "Combo added to Db.")

            characterState.update { currentState ->
                CharacterUiState(
                    currentState.character.copy(
                        combosById = currentState.character.combosById + "${comboEntry.comboId}, "
                    )
                )
            }
            tekkenDataRepository.updateCharacter(characterState.value.character)
            Log.d(VM_TAG, "ComboId added to character entry in Db.")

            clearMoveList()
            Log.d(VM_TAG, "Move list cleared.")
        }
    }

     fun updateComboInDb() {
         viewModelScope.launch {
             Log.d(VM_TAG, "")
             val existingCombo = comboEntryListState.value.comboEntryList.first {it.comboId == comboState.value.comboDisplay.comboId}
             val newCombo = comboState.value.comboDisplay

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
             Log.d(VM_TAG, "Existing combo updated in Db.")
             clearMoveList()
             Log.d(VM_TAG, "Move list cleared.")
         }
    }
}