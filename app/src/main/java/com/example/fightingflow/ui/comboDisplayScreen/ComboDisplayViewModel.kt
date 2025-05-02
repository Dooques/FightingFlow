package com.example.fightingflow.ui.comboDisplayScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.FlowRepository
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
import com.example.fightingflow.util.ComboEntryListUiState
import com.example.fightingflow.util.ImmutableList
import com.example.fightingflow.util.MoveEntryListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class ComboDisplayViewModel(
    private val flowRepository: FlowRepository,
    private val characterDsRepository: CharacterDsRepository,
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

    private val _comboDisplayListState = MutableStateFlow(ComboDisplayListUiState())
    val comboDisplayListState: StateFlow<ComboDisplayListUiState> = _comboDisplayListState

    private val _comboEntryListState = MutableStateFlow(ComboEntryListUiState())
    val comboEntryListState: StateFlow<ComboEntryListUiState> = _comboEntryListState

    init {
        Timber.d("")
        Timber.d("Initializing Combo Display View Model...")
        Timber.d("Getting character entry list...")
        getCharacterEntryList()
        Timber.d("Getting move entry list...")
        getAllMoveEntries()
        getAllCombos()
    }

    val characterState = MutableStateFlow(CharacterUiState())

    val characterNameState = characterDsRepository.getName()
            .map { CharNameUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = CharNameUiState()
            )

    val characterImageState = characterDsRepository.getImage()
            .map { CharImageUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = CharImageUiState()
            )

    // Datastore
    fun updateCharacterState(name: String) {
        Timber.d("")
        Timber.d("Getting character from Character List")
        val character = characterEntryListState.value.characterList.first { it.name == name }
        characterState.update { CharacterUiState(character) }
        Timber.d("Found $character and updated CharacterState")
    }

    fun updateCharacterInDS(character: CharacterEntry) {
        viewModelScope.launch {
            Timber.d("")
            Timber.d("Setting ${character.name} to Character Datastore")
            characterDsRepository.updateCharacter(character)
        }
    }

    suspend fun saveComboIdToDs(comboDisplay: ComboDisplay) {
        comboDsRepository.setCombo(comboDisplay)
    }

    // Database Functions
    private fun getAllMoveEntries() = viewModelScope.launch {
        _moveEntryListState.update { MoveEntryListUiState(flowRepository.getAllMoves().first()) }
    }

    private fun getCharacterEntryList() = viewModelScope.launch {
        Timber.d("Updating character entry list state")
        _characterEntryListState.update {
            CharacterEntryListUiState(flowRepository.getAllCharacters().first())
        }
    }

    private fun getAllCombos() = viewModelScope.launch {
        Timber.d("")
        Timber.d("Getting combos from database...")
        val comboEntryList = flowRepository.getAllCombos()
            .map { ComboEntryListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ComboEntryListUiState()
            )
        Timber.d("Combo Entry List: ${comboEntryList.value.comboEntryList}")
        _comboEntryListState.update { comboEntryList.value }
        _comboDisplayListState.update {
            ComboDisplayListUiState(
                comboEntryList.value.comboEntryList.map {
                        combo -> combo.toDisplay()
                }
            )
        }
    }

    suspend fun deleteCombo(combo: ComboDisplay, comboEntries: List<ComboEntry>) {
        Timber.d("Starting delete process...")
        Timber.d("Existing combo list: $comboEntries")
        val comboToDelete = comboEntries.first {it.comboId == combo.comboId}
        Timber.d("Deleting: $comboToDelete")
        flowRepository.deleteCombo(comboToDelete)
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
