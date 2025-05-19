package com.example.fightingflow.ui.comboDisplayScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.FlowRepository
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.getMoveEntryDataForComboDisplay
import com.example.fightingflow.model.toDisplay
import com.example.fightingflow.model.toEntry
import com.example.fightingflow.util.CharImageUiState
import com.example.fightingflow.util.CharNameUiState
import com.example.fightingflow.util.CharacterEntryListUiState
import com.example.fightingflow.util.CharacterUiState
import com.example.fightingflow.util.ComboDisplayListUiState
import com.example.fightingflow.util.ComboEntryListUiState
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.emptyCharacter
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

    private val _characterState = MutableStateFlow(CharacterUiState())
    val characterState: StateFlow<CharacterUiState> = _characterState

    init {
        Timber.d("Initializing Combo Display View Model...")
        Timber.d("Getting move entry list...")
        getAllMoveEntries()
        Timber.d("Getting Combo Display List")
    }


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
        viewModelScope.launch {
            Timber.d("Getting character from Database")
            flowRepository.getCharacter(name)
                .map { it ?: emptyCharacter }
                .collect { characterEntry ->
                    Timber.d("Character: $characterEntry")
                    _characterState.update { CharacterUiState(characterEntry) }
                }
        }
    }

    fun updateCharacterInDS(character: CharacterEntry) {
        viewModelScope.launch {
            Timber.d("Setting ${character.name} to Character Datastore")
            characterDsRepository.updateCharacter(character)
        }
    }

    suspend fun saveComboIdToDs(comboDisplay: ComboDisplay) {
        comboDsRepository.setCombo(comboDisplay)
        setEditingState(true)
    }

    suspend fun setEditingState(editingStateValue: Boolean) = comboDsRepository.setEditingState(editingStateValue)

    // Database Functions
    private fun getAllMoveEntries() = viewModelScope.launch {
        _moveEntryListState.update { MoveEntryListUiState(flowRepository.getAllMoves().first()) }
    }

    fun getCharacterEntryListByGame(game: String) = viewModelScope.launch {
        Timber.d("Updating character entry list state")
        Timber.d("Game selected: $game")
            flowRepository.getCharactersByGame(game)
                .map { characterList ->
                    Timber.d("CharacterList: $characterList")
                    CharacterEntryListUiState(characterList)
                }
                .collect { characterList ->
                    _characterEntryListState.update { characterList }
                }

    }

    fun getComboDisplayListByCharacter() = viewModelScope.launch {
        Timber.d("Getting combos from database...")
        flowRepository.getAllCombosByCharacter(characterState.value.character)
            .map { comboEntryList ->
                Timber.d("Combo List: $comboEntryList")
                ComboDisplayListUiState(comboDisplayList = comboEntryList?.map { combo ->
                    getMoveEntryDataForComboDisplay(combo.toDisplay(), moveEntryListUiState.value)
                } ?: emptyList())
            }
            .collect { comboDisplayList ->
                _comboDisplayListState.update { comboDisplayList }
            }
    }

    suspend fun deleteCombo(combo: ComboDisplay) {
        Timber.d("Starting delete process...")
        Timber.d("Deleting: $combo")
        flowRepository.deleteCombo(combo.toEntry(characterEntryListState.value.characterList.first {it.name == combo.character}))
        Timber.d("Combo Deleted.")
    }


}
