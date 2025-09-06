package com.dooques.fightingflow.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dooques.fightingflow.data.database.FlowRepository
import com.dooques.fightingflow.data.datastore.CharacterDsRepository
import com.dooques.fightingflow.data.datastore.ComboDsRepository
import com.dooques.fightingflow.data.datastore.SettingsDsRepository
import com.dooques.fightingflow.data.datastore.UserDsRepository
import com.dooques.fightingflow.data.firebase.FirebaseRepository
import com.dooques.fightingflow.data.firebase.GoogleAuthService
import com.dooques.fightingflow.model.CharacterEntry
import com.dooques.fightingflow.model.ComboDisplay
import com.dooques.fightingflow.model.Console
import com.dooques.fightingflow.model.MoveEntry
import com.dooques.fightingflow.model.SF6ControlType
import com.dooques.fightingflow.model.UserEntry
import com.dooques.fightingflow.model.toDisplay
import com.dooques.fightingflow.model.toEntry
import com.dooques.fightingflow.model.toFbEntry
import com.dooques.fightingflow.util.CharImageUiState
import com.dooques.fightingflow.util.CharNameUiState
import com.dooques.fightingflow.util.CharacterEntryListUiState
import com.dooques.fightingflow.util.CharacterEntryUiState
import com.dooques.fightingflow.util.ComboDisplayListUiState
import com.dooques.fightingflow.util.ComboEntryFbListUiState
import com.dooques.fightingflow.util.ComboEntryListUiState
import com.dooques.fightingflow.util.DataProcessor
import com.dooques.fightingflow.util.MoveEntryListUiState
import com.dooques.fightingflow.util.characterAndMoveData.characterMap
import com.dooques.fightingflow.util.emptyCharacter
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

    val comboDisplayListFb = MutableStateFlow(ComboDisplayListUiState())

    val dataProcessor = DataProcessor()

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
            initialValue = null
        )

    // Firebase Flows
    private val _currentUserState = MutableStateFlow<GoogleAuthService.SignInState>(GoogleAuthService.SignInState.Idle)
    val currentUserState: StateFlow<GoogleAuthService.SignInState> = _currentUserState

    fun updateUserState(userState: GoogleAuthService.SignInState) {
        Timber.d("Updating User ID to $userState")
        _currentUserState.update { userState }
        Timber.d("Updated ID: ${currentUserState.value}")
    }

    fun processMoveList(character: CharacterEntryUiState): MoveEntryListUiState {
        Timber.d("Processing character move data")
        var moveList = dataProcessor.getMoveListForCharacter(character.character)
        Timber.d("MoveList: $moveList")
        val customMoveList = moveEntryListUiState.value.moveList.filter {
            it.character == character.character.name
        } as MutableList<MoveEntry>
        if (character.character.mutable) {
            Timber.d("Custom moves: $customMoveList")
            customMoveList.addAll(moveList.moveList)
            moveList = MoveEntryListUiState(customMoveList)
        }

        Timber.d("Processed MoveList: $moveList")

        return moveList
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val comboEntryListFb =
        characterState.flatMapLatest { character ->
            publicCombosDisplayState.flatMapLatest { publicCombosDisplayState ->
                currentUserState.flatMapLatest { currentUser ->
                    Timber.d(
                        "--Getting Combo Display List from Firestore--\nCharacter: %s\nUser: %s\nShow Public Combos: %s",
                        character, currentUserState, publicCombosDisplayState
                    )

                    val moveList = processMoveList(character)

                    if (moveList.moveList.isNotEmpty()) {
                        when (currentUser) {
                            is GoogleAuthService.SignInState.Success -> {
                                firebaseRepository.getComboList(
                                    character = character.character.name,
                                    publicComboDisplayState = publicCombosDisplayState,
                                    user = (currentUserState.value as GoogleAuthService.SignInState.Success).user.userId
                                )
                                    .map { entryList ->
                                        Timber.d("ComboList Found: ${entryList}\nCreating Display List")
                                        comboDisplayListFb.update {
                                            ComboDisplayListUiState(
                                                entryList.map { entry ->
                                                    Timber.d("Mapping ${entry.comboId} list")
                                                    entry.toDisplay(moveList)
                                                }
                                            )
                                        }
                                        Timber.d("Returning Fb Entry List: $entryList")
                                        ComboEntryFbListUiState(entryList)
                                    }
                                    .catch { e ->
                                        Timber.Forest.e(
                                            e,
                                            "Error collecting firestore combo flow: "
                                        )
                                        emit(ComboEntryFbListUiState())
                                    }
                            }

                            else -> {
                                Timber.d("User not signed in, emitting empty list.")
                                flowOf(ComboEntryFbListUiState())
                            }
                        }
                    } else {
                        Timber.d("No moves found, emitting empty list.")
                        flowOf(ComboEntryFbListUiState())
                    }
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
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
            if (characterState.value.character.name == characterName?.name && characterState.value.character.mutable) {
                Timber.d("--Getting Combos from Room Database--\nCharacter: $characterName")
                if (characterName.name.isNotBlank()) {
                    Timber.d("Character name is not blank, getting combo list")

                    val moveList = processMoveList(characterState.value)

                    flowRepository.getAllCombosByCharacter(characterName.name)
                        .mapNotNull { entryList ->
                            Timber.d("List: $entryList")
                            comboDisplayListRoom.update {
                                ComboDisplayListUiState(
                                    entryList?.map { comboEntry ->
                                        comboEntry.toDisplay(moveList)
                                    } ?: listOf()
                                )
                            }
                            ComboEntryListUiState(entryList ?: emptyList())
                        }
                        .catch { exception ->
                            Timber.Forest.e(
                                exception,
                                "Error collecting combo entry list from database."
                            )
                            emit(ComboEntryListUiState())
                        }
                } else {
                    Timber.Forest.d("Character name is blank, emitting empty list.")
                    flowOf(ComboEntryListUiState())
                }
            }
            else {
                Timber.d("")
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
            initialValue = null
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
    fun updateCharacterState(name: String, game: String?) {
        Timber.d("--Updating character state--")
        if (game != null) {
            Timber.d(" Game is not null: $game, getting character")
            val characterFromMap = characterMap[game]?.first { it.name == name }
            Timber.d(" Character From Map: $characterFromMap")
            if (characterFromMap != null ) {
                _characterState.update { CharacterEntryUiState(characterFromMap) }
            } else {
                Timber.d(" Character not in map, getting custom character.")
                viewModelScope.launch {
                    Timber.Forest.d(
                        "-- Getting character from Database --\n Character: %s\n Game: %s",
                        name, game
                    )
                    flowRepository.getCharacterByNameAndGame(name, game)
                        .map { it ?: emptyCharacter }
                        .collect { characterEntry ->
                            Timber.Forest.d(" Character: $characterEntry")
                            _characterState.update { CharacterEntryUiState(characterEntry) }
                        }
                }
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
                flowRepository.deleteCombo(combo.toEntry(characterState.value.character))
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