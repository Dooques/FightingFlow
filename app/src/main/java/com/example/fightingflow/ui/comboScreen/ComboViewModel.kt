package com.example.fightingflow.ui.comboScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.TekkenDbRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.toDisplay
import com.example.fightingflow.util.CharImageUiState
import com.example.fightingflow.util.CharNameUiState
import com.example.fightingflow.util.CharacterListUiState
import com.example.fightingflow.util.CharacterUiState
import com.example.fightingflow.util.ComboDisplayListUiState
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.ComboEntryListUiState
import com.example.fightingflow.util.MoveListUiState
import com.example.fightingflow.util.emptyComboDisplay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ComboViewModel(
    private val tekkenDataRepository: TekkenDbRepository,
    private val selectedCharacterRepository: CharacterDsRepository,
    private val comboDsRepository: ComboDsRepository
): ViewModel() {

    companion object {
        const val TAG = "ComboViewModel"
        const val TIME_MILLIS = 5_000L
    }

    // Flows
    val moveEntryListState =
        tekkenDataRepository.getAllMoves()
            .map { MoveListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = MoveListUiState()
            )
    val characterEntryListState =
        tekkenDataRepository.getAllCharacters()
            .map { CharacterListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = CharacterListUiState()
            )

    val comboEntryListSate =
        tekkenDataRepository.getAllCombos()
            .mapNotNull { ComboEntryListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ComboEntryListUiState()
            )

    val comboDisplayListState =
        tekkenDataRepository.getAllCombos()
            .mapNotNull { ComboDisplayListUiState(it.map { it.toDisplay() }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ComboDisplayListUiState()
            )

    val comboDisplayState = MutableStateFlow(ComboDisplayUiState(emptyComboDisplay))
    val characterState = MutableStateFlow(CharacterUiState())

    val characterNameState = getCharacterNameFromDS()
    val characterImageState = getCharacterImageFromDS()


    // Datastore
    fun updateCharacterState(name: String) {
        Log.d(TAG, "Getting character from Character List")
        val character = characterEntryListState.value.characterList.first { it.name == name }
        characterState.update { CharacterUiState(character) }
        Log.d(TAG, "Found $character and updated CharacterState")
    }

    fun getCharacterNameFromDS() =
        selectedCharacterRepository.getName()
            .map { CharNameUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = CharNameUiState()
            )

    fun getCharacterImageFromDS() =
        selectedCharacterRepository.getImage()
            .map { CharImageUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = CharImageUiState()
            )


    fun setCharacterToDS(character: CharacterEntry) {
        viewModelScope.launch {
            Log.d(TAG, "Setting ${character.name} to Character Datastore")
            selectedCharacterRepository.updateCharacter(character)
        }
    }

    suspend fun saveComboIdToDs(comboDisplay: ComboDisplay) {
        comboDsRepository.setCombo(comboDisplay)
    }


    // Repository Functions
    suspend fun deleteCombo(combo: ComboDisplay, comboEntries: List<ComboEntry>) {
        Log.d(TAG, "Starting Delete process.")
        Log.d(TAG, "Existing combo list: $comboEntries")
        val comboToDelete = comboEntries.first {it.comboId == combo.comboId}
        Log.d(TAG, "Deleting: $comboToDelete")
        tekkenDataRepository.deleteCombo(comboToDelete)
        Log.d(TAG, "Combo Deleted.")
    }

    // Object Conversion

    fun getMoveEntryData(moveData: List<MoveEntry>, combo: ComboDisplay): ComboDisplay {
        Log.d(TAG, "Processing moveList for ${combo.comboId}")
        val updatedCombo = combo.copy(
            moves = combo.moves.map {  move ->
                val updateData = moveData.first { it.moveName == move.moveName }
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
        )
        Log.d(TAG, "Move List completed for ${combo.comboId} and returning to UI")
        return updatedCombo
    }
}
