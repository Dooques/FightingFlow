package com.example.fightingflow.ui.comboDisplayScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.FlowRepository
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.data.datastore.ProfileDatastoreRepository
import com.example.fightingflow.data.datastore.ProfileDsRepository
import com.example.fightingflow.data.datastore.SettingsDsRepository
import com.example.fightingflow.data.firebase.FirebaseRepository
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.Console
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.model.toDisplay
import com.example.fightingflow.model.toEntry
import com.example.fightingflow.ui.comboCreationScreen.ComboCreationViewModel
import com.example.fightingflow.util.CharImageUiState
import com.example.fightingflow.util.CharNameUiState
import com.example.fightingflow.util.CharacterEntryListUiState
import com.example.fightingflow.util.CharacterEntryUiState
import com.example.fightingflow.util.ComboDisplayListUiState
import com.example.fightingflow.util.ComboEntryListUiState
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.emptyCharacter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.collections.emptyList

class ComboDisplayViewModel(
    private val flowRepository: FlowRepository,
    private val characterDsRepository: CharacterDsRepository,
    private val comboDsRepository: ComboDsRepository,
    private val settingsDsRepository: SettingsDsRepository,
    private val firebaseRepository: FirebaseRepository,
    private val profileDsRepository: ProfileDsRepository
): ViewModel() {

    companion object {
        const val TIME_MILLIS = 5_000L
    }

    // Flows
    private val _moveEntryListState = MutableStateFlow(MoveEntryListUiState())
    val moveEntryListUiState: StateFlow<MoveEntryListUiState> = _moveEntryListState

    private val _characterEntryListState = MutableStateFlow(CharacterEntryListUiState())
    val characterEntryListState: StateFlow<CharacterEntryListUiState> = _characterEntryListState

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

    // Firebase Flows
    @OptIn(ExperimentalCoroutinesApi::class)
    val fireStoreComboDisplayFlow: StateFlow<ComboDisplayListUiState> = characterNameState
        .flatMapLatest { characterName ->
            user.flatMapLatest { currentUser ->
                publicCombosDisplayState.flatMapLatest { publicCombosDisplayState ->
                    if (characterName.name.isNotBlank() && currentUser.isNotBlank()) {
                        firebaseRepository.getComboList(
                            characterName.name,
                            publicCombosDisplayState,
                            currentUser
                        )
                            .map { entryList ->
                                ComboDisplayListUiState(entryList.map { entry ->
                                    entry.toDisplay(moveEntryListUiState.value)
                                })
                            }
                            .catch { exception ->
                                Timber.e(exception, "Error collecting firestore combo flow: ")
                                emit(ComboDisplayListUiState())
                            }
                    } else {
                        Timber.d("Character name is blank, emitting empty list.")
                        flowOf(ComboDisplayListUiState())
                    }
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(ComboCreationViewModel.Companion.TIME_MILLIS * 6),
                initialValue = ComboDisplayListUiState()
            )

    @OptIn(ExperimentalCoroutinesApi::class)
    val fireStoreComboEntryFlow: StateFlow<ComboEntryListUiState> = characterNameState
        .flatMapLatest { characterName ->
            user.flatMapLatest { currentUser ->
                publicCombosDisplayState.flatMapLatest { publicCombosDisplayState ->
                    if (characterName.name.isNotBlank() && currentUser.isNotBlank()) {
                        firebaseRepository.getComboList(
                            characterName.name,
                            publicCombosDisplayState,
                            currentUser
                        )
                            .map { entryList -> ComboEntryListUiState(entryList) }
                            .catch { exception ->
                                Timber.e(exception, "Error collecting firestore combo flow: ")
                                emit(ComboEntryListUiState())
                            }
                    } else {
                        Timber.d("Character name is blank, emitting empty list.")
                        flowOf(ComboEntryListUiState())
                    }
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(ComboCreationViewModel.Companion.TIME_MILLIS * 6),
            initialValue = ComboEntryListUiState()
        )

    //Database Flows
    @OptIn(ExperimentalCoroutinesApi::class)
    val databaseComboEntryFlow: StateFlow<ComboEntryListUiState> = characterNameState
        .flatMapLatest { characterName ->
                if (characterName.name.isNotBlank()) {
                    flowRepository.getAllCombosByCharacter(characterName.name)
                        .map { entryList -> ComboEntryListUiState(entryList ?: emptyList()) }
                        .catch { exception ->
                            Timber.e(exception, "Error collecting combo entry list from database.")
                            emit(ComboEntryListUiState())
                        }
                } else {
                    Timber.d("Character name is blank, emitting empty list.")
                    flowOf(ComboEntryListUiState())
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ComboEntryListUiState()
            )

    @OptIn(ExperimentalCoroutinesApi::class)
    val databaseComboDisplayFlow: StateFlow<ComboDisplayListUiState> = characterNameState
        .flatMapLatest { characterName ->
            if (characterName.name.isNotBlank()) {
                flowRepository.getAllCombosByCharacter(characterName.name)
                    .map { entryList -> ComboDisplayListUiState(entryList?.map { entry ->
                        entry.toDisplay(moveEntryListUiState.value) } ?: emptyList()) }
                    .catch { exception ->
                        Timber.e(exception, "Error collecting combo entry list from database.")
                        emit(ComboDisplayListUiState())
                    }
            } else {
                Timber.d("Character name is blank, emitting empty list.")
                flowOf(ComboDisplayListUiState())
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ComboDisplayListUiState()
            )

    // Datastore Flows
    val user = profileDsRepository.getUsername()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = ""
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
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = SF6ControlType.Invalid
        )

    val publicCombosDisplayState =  settingsDsRepository.getPublicComboDisplayState()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = false
        )

    // Datastore Functions
    fun updateCharacterState(name: String, game: String) {
        viewModelScope.launch {
            Timber.d("-- Getting character from Database -- \n Character: %s \n Game: %s",
                name, game)
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

    suspend fun updateShowComboDisplayState(boolean: Boolean) {
        settingsDsRepository.updatePublicComboDisplayState(boolean)
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

    suspend fun deleteCombo(combo: ComboDisplay) {
        Timber.d("Deleting: $combo...")
        flowRepository.deleteCombo(combo.toEntry(characterEntryListState.value.characterList.first {it.name == combo.character}))
        Timber.d("Combo deleted from database.")
        firebaseRepository.deleteCombo(combo.character, combo.id)
        Timber.d("Combo deleted from firestore.")
        Timber.d("Combo Deleted.")
    }


}
