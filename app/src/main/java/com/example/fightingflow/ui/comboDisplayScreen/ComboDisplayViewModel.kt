package com.example.fightingflow.ui.comboDisplayScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.FlowRepository
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.data.datastore.SettingsDsRepository
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.Console
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.model.toDisplay
import com.example.fightingflow.model.toEntry
import com.example.fightingflow.util.CharImageUiState
import com.example.fightingflow.util.CharNameUiState
import com.example.fightingflow.util.CharacterEntryListUiState
import com.example.fightingflow.util.CharacterEntryUiState
import com.example.fightingflow.util.ComboDisplayListUiState
import com.example.fightingflow.util.ComboEntryListUiState
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.emptyCharacter
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
    private val flowRepository: FlowRepository,
    private val characterDsRepository: CharacterDsRepository,
    private val comboDsRepository: ComboDsRepository,
    private val settingsDsRepository: SettingsDsRepository
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
    val comboEntryListUiState: StateFlow<ComboEntryListUiState> = _comboEntryListState

    private val _characterState = MutableStateFlow(CharacterEntryUiState())
    val characterState: StateFlow<CharacterEntryUiState> = _characterState

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

    val gameSelectedState = settingsDsRepository.getGame()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = ""
        )

    val showIconState = settingsDsRepository.getIconDisplayState()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = true
        )

    val textComboState = settingsDsRepository.getComboTextState()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = false
        )

    val consoleTypeState = settingsDsRepository.getConsoleType()
        .map { when (it) {
            1 -> Console.PLAYSTATION
            2 -> Console.XBOX
            3 -> Console.NINTENDO
            else -> Console.STANDARD
        } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = Console.STANDARD
        )

    val modernOrClassicState = settingsDsRepository.getSF6ControlType()
        .map { type ->
            when (type) {
                0 -> SF6ControlType.Classic
                1 -> SF6ControlType.Modern
                else -> SF6ControlType.Invalid
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SF6ControlType.Invalid
        )

    // Datastore
    fun updateCharacterState(name: String, game: String) {
        viewModelScope.launch {
            Timber.d("-- Getting character from Database --" +
                    "\n Character: $name" +
                    "\n Game: $game")
            flowRepository.getCharacterByNameAndGame(name, game)
                .map { it ?: emptyCharacter }
                .collect { characterEntry ->
                    Timber.d("Character: $characterEntry")
                    _characterState.update { CharacterEntryUiState(characterEntry) }
                }
        }
    }

    suspend fun updateCharacterInDS(character: CharacterEntry) {
            Timber.d("Setting ${character.name} to Character Datastore")
            characterDsRepository.updateCharacter(character)
    }

    fun updateShowIconDisplayState(iconState: Boolean) = viewModelScope.launch {
        Timber.d("Updating Icon Display State...")
        settingsDsRepository.updateIconDisplayState(iconState)
    }

    fun updateShowComboTextState(textCombo: Boolean) = viewModelScope.launch {
        Timber.d("Updating Combo Text State...")
        settingsDsRepository.updateShowComboTextState(textCombo)
    }

    suspend fun updateComboStateInDs(comboDisplay: ComboDisplay) {
        comboDsRepository.updateComboIdState(comboDisplay)
        updateEditingState(true)
    }

    suspend fun updateEditingState(editingStateValue: Boolean) =
        comboDsRepository.updateEditingState(editingStateValue)

    fun updateConsoleType(console: Console) = viewModelScope.launch {
        settingsDsRepository.updateConsoleType(
            when (console) {
                Console.STANDARD -> 0
                Console.PLAYSTATION -> 1
                Console.XBOX -> 2
                Console.NINTENDO -> 3
            }
        )
    }

    // Database Functions
    private fun getAllMoveEntries() = viewModelScope.launch {
        flowRepository.getAllMoves()
            .map { MoveEntryListUiState(it) }
            .collect { moveList ->
                _moveEntryListState.update { moveList }
            }
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
            } }

    fun getCustomCharacters() = viewModelScope.launch {
        flowRepository.getCustomCharacters()
            .mapNotNull { characterList ->
                CharacterEntryListUiState(characterList ?: emptyList())
            }
            .collect { characterList ->
                _characterEntryListState.update { characterList }
            }
    }

    fun getComboDisplayListByCharacter() = viewModelScope.launch {
        Timber.d("-- Getting combos from database... --")
        flowRepository.getAllCombosByCharacter(characterState.value.character.name)
            .map { comboEntryList ->
                Timber.d("Combo List: $comboEntryList")
                ComboEntryListUiState(comboEntryList ?: emptyList())
            }
            .collect { comboEntryList ->
                _comboEntryListState.update {
                    Timber.d("Collecting Combo Entry List: ${comboEntryList.comboEntryList}")
                    comboEntryList
                }
                try {
                    _comboDisplayListState.update {
                        Timber.d("Updating comboDisplayListState: $comboEntryList")
                        val comboDisplay = ComboDisplayListUiState(
                            comboDisplayList = comboEntryList.comboEntryList.map { combo ->
                                Timber.d("Combo: $combo")
                                combo.toDisplay(
                                    MoveEntryListUiState(moveEntryListUiState.value.moveList)) })

                        Timber.d("Combo Display List: ${comboDisplay.comboDisplayList}")
                        comboDisplay
                    }
                } catch (e: Exception) {
                    Timber.e(e, "An error occurred converting move list to move entry list.")
                }
            }
    }

    suspend fun deleteCombo(combo: ComboDisplay) {
        Timber.d("Starting delete process...")
        Timber.d("Deleting: $combo...")
        flowRepository.deleteCombo(combo.toEntry(characterEntryListState.value.characterList.first {it.name == combo.character}))
        Timber.d("Combo Deleted.")
    }


}
