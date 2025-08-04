package com.example.fightingflow.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.FlowRepository
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.data.datastore.SettingsDsRepository
import com.example.fightingflow.data.datastore.UserDsRepository
import com.example.fightingflow.data.firebase.FirebaseRepository
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.Console
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.model.UserEntry
import com.example.fightingflow.model.toDisplay
import com.example.fightingflow.model.toEntry
import com.example.fightingflow.model.toFbEntry
import com.example.fightingflow.util.CharImageUiState
import com.example.fightingflow.util.CharNameUiState
import com.example.fightingflow.util.CharacterEntryListUiState
import com.example.fightingflow.util.CharacterEntryUiState
import com.example.fightingflow.util.ComboDisplayListUiState
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.ComboEntryFbListUiState
import com.example.fightingflow.util.ComboEntryListUiState
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.characterAndMoveData.moveMap
import com.example.fightingflow.util.emptyCharacter
import com.example.fightingflow.util.emptyComboDisplay
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

class ComboDisplayViewModel(
    private val flowRepository: FlowRepository,
    private val characterDsRepository: CharacterDsRepository,
    private val comboDsRepository: ComboDsRepository,
    private val settingsDsRepository: SettingsDsRepository,
    private val firebaseRepository: FirebaseRepository,
    profileDsRepository: UserDsRepository,
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
        Timber.Forest.d("Initializing Combo Display View Model...")
        Timber.Forest.d("Getting move entry list...")
        getAllMoveEntries()
        Timber.Forest.d("Getting Combo Display List")
    }

    val characterNameState = characterDsRepository.getName()
            .map { CharNameUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Companion.WhileSubscribed(TIME_MILLIS),
                initialValue = CharNameUiState()
            )

    // Firebase Flows
    private val _currentUserState = MutableStateFlow<GoogleAuthService.SignInState>(GoogleAuthService.SignInState.Idle)
    val currentUserState:StateFlow<GoogleAuthService.SignInState> = _currentUserState

    fun updateUserState(userState: GoogleAuthService.SignInState) {
        Timber.d("Updating User ID to $userState")
        _currentUserState.update { userState }
        Timber.d("Updated ID: ${currentUserState.value}")
    }

    val fireStoreComboDisplayFlow = MutableStateFlow(ComboDisplayListUiState())

    @OptIn(ExperimentalCoroutinesApi::class)
    val fireStoreComboEntryFlow: StateFlow<ComboEntryFbListUiState> =
        characterNameState.flatMapLatest { characterName ->
            if (characterName.name.isBlank()) {
                Timber.Forest.d("Character name is blank, emitting empty list.")
                flowOf(ComboEntryFbListUiState())
            } else {
                Timber.d("Checking if character is Mutable: ${characterState.value}")
                if (characterState.value.character.mutable) {
                Timber.d("Character is mutable, returning empty list")
                flowOf(ComboEntryFbListUiState())
                } else {
                    Timber.d("--Getting Combo Display List from Firestore--\nCharacter: %s",
                        characterName)
                    currentUserState.flatMapLatest { currentUser ->
                        Timber.d("User: %s", currentUser)
                        when (currentUser) {
                            is GoogleAuthService.SignInState.Success -> {
                                moveEntryListUiState.flatMapLatest { moveList ->
                                    publicCombosDisplayState.flatMapLatest { publicCombosDisplayState ->
                                        Timber.d("Show Public Combos: $publicCombosDisplayState")
                                        firebaseRepository.getComboList(
                                            character = characterName.name,
                                            publicComboDisplayState = publicCombosDisplayState,
                                            user = currentUser.user.userId
                                        )
                                            .map { entryList ->
                                                Timber.d("ComboList Found: ${entryList}\nCreating Display List")
                                                fireStoreComboDisplayFlow.update {
                                                    ComboDisplayListUiState(
                                                        entryList.map { entry ->
                                                            entry.toDisplay(moveList)
                                                        }
                                                    )
                                                }
                                                Timber.d("Returning Fb Entry List: $entryList")
                                                ComboEntryFbListUiState(entryList)
                                            }
                                            .catch { e ->
                                                Timber.Forest.e(e, "Error collecting firestore combo flow: ")
                                                emit(ComboEntryFbListUiState())
                                            }
                                    }
                                }
                            }

                            else -> {
                                Timber.d("")
                                flowOf(ComboEntryFbListUiState())
                            }
                        }
                    }
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Companion.WhileSubscribed(TIME_MILLIS * 6),
                initialValue = ComboEntryFbListUiState()
            )

    fun updateCombo(combo: ComboDisplay, user: UserEntry) {
        Timber.d("Updating combo ${combo.id} in firestore database")
        firebaseRepository.updateCombo(
            combo.toFbEntry(characterEntryListState.value.characterList.first { it.name == combo.character })
        )
        Timber.d("Updating user details to add/remove combo from likes")
        Timber.d("User Details: $user")
        firebaseRepository.updateUser(user)
    }

    //Database Flows
    val comboDisplayListRoom = MutableStateFlow(ComboDisplayListUiState())

    @OptIn(ExperimentalCoroutinesApi::class)
    val comboEntryListRoom = characterNameState
        .flatMapLatest { characterName ->
            Timber.d("--Getting Combos from Room Database--\nCharacter: $characterName")
                if (characterName.name.isNotBlank()) {
                    Timber.d("Character name is not blank, getting combo list")
                    flowRepository.getAllCombosByCharacter(characterName.name)
                        .mapNotNull { entryList ->
                            Timber.d("List: $entryList")
                            comboDisplayListRoom.update {
                                ComboDisplayListUiState(
                                    entryList?.map { comboEntry ->
                                        comboEntry.toDisplay(moveEntryListUiState.value)
                                    } ?: listOf()
                                )
                            }
                            ComboEntryListUiState(entryList ?: emptyList())
                        }
                        .catch { exception ->
                            Timber.Forest.e(exception, "Error collecting combo entry list from database.")
                            emit(ComboEntryListUiState())
                        }
                } else {
                    Timber.Forest.d("Character name is blank, emitting empty list.")
                    flowOf(ComboEntryListUiState())
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Companion.WhileSubscribed(TIME_MILLIS),
                initialValue = ComboEntryListUiState()
            )


    // Datastore Flows
    val user = profileDsRepository.getUsername()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(TIME_MILLIS),
            initialValue = ""
        )

    val characterImageState = characterDsRepository.getImage()
        .map { CharImageUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(TIME_MILLIS),
            initialValue = CharImageUiState()
        )

    val gameSelectedState = settingsDsRepository.getGame()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(TIME_MILLIS),
            initialValue = ""
        )

    val showIconState = settingsDsRepository.getIconDisplayState()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(TIME_MILLIS),
            initialValue = true
        )

    val textComboState = settingsDsRepository.getComboTextState()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(TIME_MILLIS),
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
            started = SharingStarted.Companion.WhileSubscribed(TIME_MILLIS),
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
            started = SharingStarted.Companion.WhileSubscribed(TIME_MILLIS),
            initialValue = SF6ControlType.Invalid
        )

    val publicCombosDisplayState =  settingsDsRepository.getPublicComboDisplayState()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(TIME_MILLIS),
            initialValue = false
        )

    // Datastore Functions
    fun updateCharacterState(name: String, game: String) {
        viewModelScope.launch {
            Timber.Forest.d("-- Getting character from Database -- \n Character: %s \n Game: %s",
                name, game)
            flowRepository.getCharacterByNameAndGame(name, game)
                .map { it ?: emptyCharacter }
                .collect { characterEntry ->
                    Timber.Forest.d("Character: $characterEntry")
                    _characterState.update { CharacterEntryUiState(characterEntry) }
                }
        }
    }

    suspend fun updateCharacterInDS(character: CharacterEntry) {
            Timber.Forest.d("Setting ${character.name} to Character Datastore")
            characterDsRepository.updateCharacter(character)
    }

    fun updateShowIconDisplayState(iconState: Boolean) = viewModelScope.launch {
        Timber.Forest.d("Updating Icon Display State...")
        settingsDsRepository.updateIconDisplayState(iconState)
    }

    fun updateShowComboTextState(textCombo: Boolean) = viewModelScope.launch {
        Timber.Forest.d("Updating Combo Text State...")
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
        Timber.Forest.d("Updating character entry list state")
        Timber.Forest.d("Game selected: $game")
        flowRepository.getCharactersByGame(game)
            .map { characterList ->
                Timber.Forest.d("CharacterList: $characterList")
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
        Timber.Forest.d("Deleting: $combo...")
        if (!characterState.value.character.mutable) {
            try {
                flowRepository.deleteCombo(combo.toEntry(characterEntryListState.value.characterList.first { it.name == combo.character }))
                Timber.Forest.d("Combo deleted from database.")
                firebaseRepository.deleteCombo(combo.character, combo.id)
                Timber.Forest.d("Combo deleted from firestore.")
                Timber.Forest.d("Combo Deleted.")
            } catch (e: Exception) {
                Timber.e(e, "Error deleting combo.")
            }
        } else {
            try {
                flowRepository.deleteCombo(combo.toEntry(characterEntryListState.value.characterList.first { it.name == combo.character }))
                Timber.Forest.d("Combo deleted from database.")
            } catch (e: Exception) {
                Timber.e(e, "Error deleting combo.")
            }
        }
    }


}