package com.example.fightingflow.ui.comboScreen

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
import com.example.fightingflow.util.CharacterEntryListUiState
import com.example.fightingflow.util.CharacterUiState
import com.example.fightingflow.util.ComboDisplayListUiState
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.ComboEntryListUiState
import com.example.fightingflow.util.ImmutableList
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.emptyComboDisplay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class ComboDisplayViewModel(
    private val tekkenDataRepository: TekkenDbRepository,
    private val selectedCharacterRepository: CharacterDsRepository,
    private val comboDsRepository: ComboDsRepository
): ViewModel() {

    companion object {
        const val TIME_MILLIS = 5_000L
    }

    // Flows
    private val _moveEntryListState = MutableStateFlow(MoveEntryListUiState())
    val moveEntryListUiState: StateFlow<MoveEntryListUiState> = _moveEntryListState

    private val _characterEntryListState = MutableStateFlow(CharacterEntryListUiState())
    val characterEntryListState: StateFlow<CharacterEntryListUiState> = _characterEntryListState

    init {
        getCharacterEntryList()
        getAllMoveEntries()
    }

    val characterState = MutableStateFlow(CharacterUiState())

    val comboEntryListSate = tekkenDataRepository.getAllCombos()
            .mapNotNull { ComboEntryListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ComboEntryListUiState()
            )

    val comboDisplayListState = tekkenDataRepository.getAllCombos()
            .mapNotNull { ComboDisplayListUiState(it.map { it.toDisplay() }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ComboDisplayListUiState()
            )

    val characterNameState = selectedCharacterRepository.getName()
            .map { CharNameUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = CharNameUiState()
            )

    val characterImageState = selectedCharacterRepository.getImage()
            .map { CharImageUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = CharImageUiState()
            )

    // Datastore
    fun updateCharacterState(name: String) {
        Timber.d("Getting character from Character List")
        val character = characterEntryListState.value.characterList.first { it.name == name }
        characterState.update { CharacterUiState(character) }
        Timber.d("Found $character and updated CharacterState")
    }

    fun setCharacterToDS(character: CharacterEntry) {
        viewModelScope.launch {
            Timber.d("Setting ${character.name} to Character Datastore")
            selectedCharacterRepository.updateCharacter(character)
        }
    }

    suspend fun saveComboIdToDs(comboDisplay: ComboDisplay) {
        comboDsRepository.setCombo(comboDisplay)
    }

    // Database Functions
    private fun getAllMoveEntries() = tekkenDataRepository.getAllMoves()
        .map { MoveEntryListUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = MoveEntryListUiState()
        )

    private fun getCharacterEntryList() =
        tekkenDataRepository.getAllCharacters()
            .map { CharacterEntryListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = CharacterEntryListUiState()
            )

    suspend fun deleteCombo(combo: ComboDisplay, comboEntries: List<ComboEntry>) {
        Timber.d("Starting delete process...")
        Timber.d("Existing combo list: $comboEntries")
        val comboToDelete = comboEntries.first {it.comboId == combo.comboId}
        Timber.d("Deleting: $comboToDelete")
        tekkenDataRepository.deleteCombo(comboToDelete)
        Timber.d("Combo Deleted.")
    }

    // Object Conversion
    fun getMoveEntryData(moveData: List<MoveEntry>, combo: ComboDisplay): ComboDisplay {
        Timber.d("Processing moveList for ${combo.comboId}")
        val updatedCombo = combo.copy(
            moves = ImmutableList(
                combo.moves.map { move ->
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
        )
        Timber.d("Move List completed for ${combo.comboId} and returning to UI")
        return updatedCombo
    }
}
