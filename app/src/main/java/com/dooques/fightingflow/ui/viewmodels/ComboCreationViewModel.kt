package com.dooques.fightingflow.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dooques.fightingflow.data.database.FlowRepository
import com.dooques.fightingflow.data.datastore.ComboDsRepository
import com.dooques.fightingflow.data.datastore.SettingsDsRepository
import com.dooques.fightingflow.data.datastore.UserDsRepository
import com.dooques.fightingflow.data.firebase.FirebaseRepository
import com.dooques.fightingflow.data.firebase.GoogleAuthService
import com.dooques.fightingflow.model.CharacterEntry
import com.dooques.fightingflow.model.Console
import com.dooques.fightingflow.model.Game
import com.dooques.fightingflow.model.MoveEntry
import com.dooques.fightingflow.model.SF6ControlType
import com.dooques.fightingflow.model.getMoveEntryDataForComboDisplay
import com.dooques.fightingflow.model.toDisplay
import com.dooques.fightingflow.model.toEntry
import com.dooques.fightingflow.model.toFbEntry
import com.dooques.fightingflow.ui.comboCreationScreen.processComboAsStringAbstract
import com.dooques.fightingflow.ui.comboCreationScreen.updateMoveListAbstract
import com.dooques.fightingflow.util.CharacterEntryUiState
import com.dooques.fightingflow.util.ComboDisplayUiState
import com.dooques.fightingflow.util.ComboEntryUiState
import com.dooques.fightingflow.util.DataProcessor
import com.dooques.fightingflow.util.ImmutableList
import com.dooques.fightingflow.util.MoveEntryListUiState
import com.dooques.fightingflow.util.emptyComboDisplay
import com.dooques.fightingflow.util.emptyComboEntry
import com.dooques.fightingflow.util.emptyComboEntryFb
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

class ComboCreationViewModel(
    private val flowRepository: FlowRepository,
    private val comboDsRepository: ComboDsRepository,
    private val profileDsRepository: UserDsRepository,
    private val settingsDsRepository: SettingsDsRepository,
    private val firebaseRepository: FirebaseRepository,
): ViewModel() {

    companion object {
        const val TIME_MILLIS = 5_000L
    }

    val dataProcessor = DataProcessor()

    // Mutable State
    val editingState = mutableStateOf(false)
    var comboIdState = MutableStateFlow("")

    // State Flow
    val comboDisplayState = MutableStateFlow(
        ComboDisplayUiState(
            emptyComboDisplay.copy(
                dateCreated = LocalDate.now().toString()
            )
        )
    )

    val characterState = MutableStateFlow(CharacterEntryUiState())
    val originalComboState = MutableStateFlow(ComboDisplayUiState())

    val comboAsStringState = MutableStateFlow(processComboAsString())
    private val profileNameState = MutableStateFlow("")

    val itemIndexState = MutableStateFlow(comboDisplayState.value.comboDisplay.moves.size)
    val gameTypeState = getGameState()

    private val controlTypeState = getConsoleTypeState()
    private val sf6ControlTypeState = getSF6ControlTypeState()

    // State Flow Lists
    val moveEntryList = MutableStateFlow(MoveEntryListUiState())

    init {
        getComboIdFromDs()
        getEditingState()
        getProfileName()
    }

    // Combo Processing Functions
    fun updateMoveList(
        moveName: String,
        moveListUiState: MoveEntryListUiState,
        console: Console? = null
    ) {
        Timber.d("Adding $moveName to combo...")
        Timber.d("Move List: ${moveListUiState.moveList.size} moves")
        Timber.d("Game: ${gameTypeState.value}, Console: $console")

        val updatedCombo = updateMoveListAbstract(
            moveName = moveName,
            comboDisplayState = comboDisplayState.value,
            game = characterState.value.character.game,
            console = console,
            sf6ControlTypeState = sf6ControlTypeState.value,
            itemIndexState = itemIndexState.value,
            moveList = moveListUiState.moveList
        )

        Timber.d("Updated Combo: $updatedCombo")
        try {
            updateComboDetails(ComboDisplayUiState(updatedCombo))
            itemIndexState.update { itemIndexState.value + 1 }
            Timber.d("Index: ${itemIndexState.value}")
        } catch (e: Exception) {
            Timber.e(e, "Error occurred updating combo details.")
        }
    }

    fun updateComboDetails(comboDisplay: ComboDisplayUiState) {
        Timber.d("Updating combo details...")
        val comboDisplayWithCharacterData =
            comboDisplay.comboDisplay.copy(character = characterState.value.character.name)

        Timber.d("Combo Display with Character: $comboDisplayWithCharacterData")
        comboDisplayState.update { ComboDisplayUiState(comboDisplayWithCharacterData) }

        Timber.d("Combo Display Ui State: ")
        comboAsStringState.update { processComboAsString() }

        Timber.d("Saving updated combo to datastore...")
        updateComboIdInDs(comboDisplayState.value)
    }

    private fun processComboAsString(): String {
        Timber.d("Processing combo as string...")
        val moveList = comboDisplayState.value.comboDisplay.moves
        return processComboAsStringAbstract(moveList)
    }

    fun deleteMove() {
        Timber.d("Deleting last move...")
        if (itemIndexState.value >= 0) {
            val currentMoves: MutableList<MoveEntry> = comboDisplayState.value.comboDisplay.moves.toMutableList()
            Timber.d("Temporary move list created, move to delete: ${currentMoves.last()}")

            val index =
                if (itemIndexState.value >= comboDisplayState.value.comboDisplay.moves.size)
                    comboDisplayState.value.comboDisplay.moves.size - 1
                else itemIndexState.value
            currentMoves.removeAt(index)

            Timber.d("Move removed from temp list, preparing to copy and update.")
            val updatedCombo = comboDisplayState.value.comboDisplay.copy(moves = ImmutableList(currentMoves.toList()))
            try {
                updateComboDetails(ComboDisplayUiState(updatedCombo))
            } catch (e: Exception) {
                Timber.e(e, "An error occurred trying to delete the combo.")
            }

            itemIndexState.update { if (itemIndexState.value != 0) itemIndexState.value - 1 else itemIndexState.value }
            Timber.d("Index: ${itemIndexState.value}")
        } else {
            Timber.d("No moves found, cannot delete move.")
        }
    }

    fun clearMoveList() {
        Timber.d("Clearing move list...")
        comboDisplayState.update { ComboDisplayUiState(comboDisplayState.value.comboDisplay.copy(moves = emptyList())) }
        comboAsStringState.update { "" }
        Timber.d("Cleared move list: ${comboAsStringState.value}")
        itemIndexState.update { 0 }
    }

    suspend fun saveCombo(
        currentUser: GoogleAuthService.SignInState
    ): ComboResult {
        Timber.d("Checking if combo details valid")
        Timber.d("ComboDisplay Character: ${comboDisplayState.value.comboDisplay.character}")
        Timber.d("ComboDisplay Moves: ${comboDisplayState.value.comboDisplay.moves}")
        if (comboDisplayState.value.comboDisplay.character.isNotEmpty() &&
            comboDisplayState.value.comboDisplay.moves.isNotEmpty()) {
            Timber.d("Preparing to add combo to Db, checking if edit mode...")
            try {
                if (editingState.value) {
                    Timber.d("Edit mode true, updating existing combo...")
                    return updateCombo()
                } else {
                    Timber.d("Edit mode false, adding new combo...")
                    return insertCombo(currentUser as GoogleAuthService.SignInState.Success)
                }
            } catch (e: Exception) {
                return ComboResult.Error(e = e)
            }
        } else {
            return ComboResult.Error(
                Exception("Cannot save Combo, please make sure a Character is selected and the Move List is not empty."))
        }
    }

    // Datastore Functions
    private fun updateComboIdInDs(
        comboDisplay: ComboDisplayUiState = ComboDisplayUiState()
    ) {
        Timber.d("Saving combo details to datastore...")
        Timber.d("Combo to save: ${comboDisplay.comboDisplay}")
        viewModelScope.launch {
            Timber.d("Checking if combo empty...")
            if (comboDisplay.comboDisplay == emptyComboDisplay) {
                Timber.d("Combo empty, adding empty combo to datastore...")
                try {
                    comboDsRepository.updateComboIdState(comboDisplay.comboDisplay)
                } catch (e: Exception) {
                    Timber.e(e, "An error occurred trying to update the datastore with Combo ID: ${comboDisplay.comboDisplay.id}.")
                }
            } else {
                Timber.d("Combo details found, saving them to datastore...")
                try {
                    comboDsRepository.updateComboIdState(comboDisplayState.value.comboDisplay)
                } catch (e: Exception) {
                    Timber.e(e, "An error occurred trying to update the datastore with Combo ID: ${comboDisplayState.value.comboDisplay.id}.")
                }
            }
        }
        Timber.d("Combo saved to datastore.")
    }

    // Room Db Functions
    private suspend fun insertCombo(
        currentUser: GoogleAuthService.SignInState.Success
    ): ComboResult {
        val userId = currentUser.user.userId
        val comboEntry =
            comboDisplayState.value.comboDisplay.toEntry(characterState.value.character)
                .copy(createdBy = userId)

        if (!characterState.value.character.mutable) {
            try {
                var comboIdResult = ""
                val firestoreRequest = viewModelScope.launch {
                    comboIdResult = firebaseRepository.addCombo(
                        comboEntry
                            .toDisplay(moveEntryList.value)
                            .toFbEntry(characterState.value.character)
                    )
                }
                firestoreRequest.join()

                flowRepository.insertCombo(comboEntry.copy(id = comboIdResult))

                Timber.d("Combo added to Db.")
                Timber.d("Clearing move list...")
                resetCombo()
                resetComboId()
                resetItemIndex()
                updateComboIdInDs(ComboDisplayUiState())
                Timber.d("Move list cleared.")
                return ComboResult.Success
            } catch (e: Exception) {
                return ComboResult.Error(e)
            }
        } else {
            try {
                flowRepository.insertCombo(comboEntry)

                Timber.d("Combo added to Db.")
                Timber.d("Clearing move list...")
                resetCombo()
                resetComboId()
                resetItemIndex()
                updateComboIdInDs(ComboDisplayUiState())
                Timber.d("Move list cleared.")
                return ComboResult.Success
            } catch (e: Exception) {
                return ComboResult.Error(e)
            }
        }
    }

    private suspend fun updateCombo(): ComboResult {
        Timber.d("--Preparing to update combo in database...--")
        val updatedCombo = comboDisplayState.value.comboDisplay.toEntry(characterState.value.character)
        Timber.d(" Move List: ${updatedCombo.moves}")
        if (!characterState.value.character.mutable) {
            try {
                firebaseRepository.updateCombo(
                    updatedCombo
                        .toDisplay(moveEntryList.value)
                        .toFbEntry(characterState.value.character)
                )
                flowRepository.updateCombo(updatedCombo)

                resetCombo()
                resetComboId()
                resetItemIndex()
                updateComboIdInDs(ComboDisplayUiState())
                Timber.d(" ComboState: ${comboDisplayState.value.comboDisplay}\n OriginalComboState: ${originalComboState.value.comboDisplay}")
                Timber.d(" Existing combo updated in Db.")

                return ComboResult.Success
            } catch (e: Exception) {
                return ComboResult.Error(e)
            }
        } else {
           try {
               flowRepository.updateCombo(updatedCombo)

               resetCombo()
               resetComboId()
               resetItemIndex()
               updateComboIdInDs(ComboDisplayUiState())
               Timber.d(" Existing combo updated in Db.")

               return ComboResult.Success
           } catch (e: Exception) {
               return ComboResult.Error(e)
           }
        }
    }

    fun updateItemIndex(index: Int) {
        itemIndexState.update { index }
    }

    fun resetItemIndex() {
        itemIndexState.update { comboDisplayState.value.comboDisplay.moves.size }
    }

    fun resetComboId() {
        comboIdState.value = ""
    }

    private fun resetCombo() {
        Timber.d("--Resetting combo--")
        comboDisplayState.update {
            ComboDisplayUiState(emptyComboDisplay.copy(dateCreated = LocalDate.now().toString()))
        }
        originalComboState.update {
            ComboDisplayUiState(emptyComboDisplay)
        }
        Timber.d(" Combo State: ${comboDisplayState.value.comboDisplay}")
        Timber.d(" Original Combo State: ${originalComboState.value.comboDisplay}")
    }

    // State Functions
    private fun getGameState() = settingsDsRepository.getGame()
        .map { when (it) {
            Game.MK1.title -> Game.MK1
            Game.SF6.title -> Game.SF6
            Game.T8.title -> Game.T8
            else -> Game.CUSTOM
        } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = Game.CUSTOM
        )

    private fun getConsoleTypeState() = settingsDsRepository.getConsoleType()
        .map { when (it) {
            1 -> Console.STANDARD
            2 -> Console.PLAYSTATION
            3 -> Console.XBOX
            4 -> Console.NINTENDO
            else -> null
        } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = Console.STANDARD
        )

    private fun getSF6ControlTypeState() = settingsDsRepository.getSF6ControlType()
        .map { if (it == 0) SF6ControlType.Classic else SF6ControlType.Modern }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = SF6ControlType.Classic
        )

    private fun getComboIdFromDs() = viewModelScope.launch {
        comboDsRepository.getComboId()
            .map { comboId -> comboId }
            .collect { comboId ->
                comboIdState.value = comboId
            }
    }

    private fun getEditingState() = viewModelScope.launch {
        comboDsRepository.getEditingState()
            .map {editingStateValue -> editingStateValue }
            .collect { editingStateValue ->
                editingState.value = editingStateValue
            }
    }

    private fun getProfileName() = viewModelScope.launch {
        try {
            profileDsRepository.getUsername()
                .map { it }
                .collect { profileName ->
                    profileNameState.update { profileName }
                }
        } catch (e: Exception) {
            Timber.e(e, "An error occurred getting profile name from database.")
        }
    }

    fun getExistingComboFromDb() {
        viewModelScope.launch {
            Timber.d("Getting existing combo from DB...")
            flowRepository.getCombo(comboIdState.value)
                .map { combo -> ComboEntryUiState(combo ?: emptyComboEntry) }
                .collect { combo ->
                    val existingCombo = ComboDisplayUiState(
                        getMoveEntryDataForComboDisplay(
                            combo = combo.comboEntry.toDisplay(moveEntryList.value),
                            moveEntryList = moveEntryList.value,
                        )
                    )
                    comboDisplayState.update { existingCombo }
                    originalComboState.update { existingCombo }
                }
        }
    }

    fun getExistingComboFromFirestore() {
        viewModelScope.launch {
            if (characterState.value != CharacterEntryUiState() && comboIdState.value.isNotBlank()) {
                firebaseRepository.getCombo(characterState.value.character.name, comboIdState.value)
                    .map { combo -> (combo ?: emptyComboEntryFb) }
                    .collect { combo ->
                        val existingCombo = ComboDisplayUiState(
                            combo.toDisplay(moveEntryList.value)
                        )
                        comboDisplayState.update { existingCombo }
                        originalComboState.update { existingCombo }
                    }
            }
        }
    }

    fun getMoveEntryList(character: CharacterEntry) {
        viewModelScope.launch {
            Timber.d("Getting move list for ${character.name}")
            val moveList = if (!character.mutable) {
                dataProcessor.getMoveListForCharacter(character)
            } else {
                val moveList: MutableList<MoveEntry> = mutableListOf()
                moveList.addAll(dataProcessor.getMoveListForCharacter(character).moveList)
                moveList.addAll(
                    flowRepository.getAllMovesByCharacter(character.name)
                        .map { moveList -> MoveEntryListUiState(moveList) }
                        .first()
                        .moveList
                )
                MoveEntryListUiState(moveList)
            }

            Timber.d("MoveList: $moveList")
            moveEntryList.update { moveList }
        }
    }
}

sealed class ComboResult {
    data object Success: ComboResult()
    data class Error(val e: Exception): ComboResult()
}