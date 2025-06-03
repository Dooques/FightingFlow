package com.example.fightingflow.ui.comboCreationScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.FlowRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.data.datastore.Console
import com.example.fightingflow.data.datastore.Game
import com.example.fightingflow.data.datastore.ProfileDsRepository
import com.example.fightingflow.data.datastore.SF6ControlType
import com.example.fightingflow.data.datastore.SettingsDsRepository
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.getMoveEntryDataForComboDisplay
import com.example.fightingflow.model.toDisplay
import com.example.fightingflow.model.toEntry
import com.example.fightingflow.ui.comboDisplayScreen.inputConverter.convertInputToStandard
import com.example.fightingflow.util.CharacterUiState
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.ComboEntryUiState
import com.example.fightingflow.util.ImmutableList
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.characterAndMoveData.consoleInputs
import com.example.fightingflow.util.emptyComboDisplay
import com.example.fightingflow.util.emptyComboEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate


class ComboCreationViewModel(
    private val flowRepository: FlowRepository,
    private val comboDsRepository: ComboDsRepository,
    private val profileDsRepository: ProfileDsRepository,
    private val settingsDsRepository: SettingsDsRepository
): ViewModel() {

    companion object {
        const val TIME_MILLIS = 5_000L
    }

    val editingState = mutableStateOf(false)
    val comboIdState = mutableStateOf("")

    val characterState = MutableStateFlow(CharacterUiState())
    val originalCombo = MutableStateFlow(ComboDisplayUiState())
    val comboDisplayState = MutableStateFlow(ComboDisplayUiState(emptyComboDisplay.copy(dateCreated = LocalDate.now().toString())))
    val comboAsStringState = MutableStateFlow(processComboAsString())
    val characterMoveEntryList = MutableStateFlow(MoveEntryListUiState())
    val gameMoveEntryList = MutableStateFlow(MoveEntryListUiState())
    val itemIndexState = MutableStateFlow(comboDisplayState.value.comboDisplay.moves.size)
    private val moveEntryListUiState = MutableStateFlow(MoveEntryListUiState())
    private val profileNameState = MutableStateFlow("")

    init {
        getComboIdFromDs()
        getEditingState()
        getMoveEntryList()
        getProfileName()
    }

    val gameState = settingsDsRepository.getGame()
        .map {
            when (it) {
                Game.MK1.title -> Game.MK1
                Game.SF6.title -> Game.SF6
                Game.T8.title -> Game.T8
                else -> null
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = null
        )

    private val controlTypeState = settingsDsRepository.getConsoleType()
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

    private val sF6ControlType = settingsDsRepository.getSF6ControlType()
        .map { if (it == 0) SF6ControlType.Classic else SF6ControlType.Modern }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = SF6ControlType.Classic
        )

    fun updateItemIndex(index: Int) {
        itemIndexState.update { index }
    }

    fun resetItemIndex() {
        itemIndexState.update { comboDisplayState.value.comboDisplay.moves.size }
    }

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
        profileDsRepository.getUsername()
            .map { it }
            .collect { profileName ->
                profileNameState.update { profileName }
            }
    }

    fun getExistingCombo() {
        viewModelScope.launch {
            Timber.d("Getting existing combo from DB...")
            flowRepository.getCombo(comboIdState.value)
                .map { combo -> ComboEntryUiState(combo ?: emptyComboEntry) }
                .collect { combo ->
                    val existingCombo = ComboDisplayUiState(
                        getMoveEntryDataForComboDisplay(
                            combo = combo.comboEntry.toDisplay(moveEntryListUiState.value),
                            moveEntryList = moveEntryListUiState.value,
                            controlType = controlTypeState.value.let { Console.STANDARD }
                        )
                    )
                    comboDisplayState.update { existingCombo }
                    originalCombo.update { existingCombo }
                }
        }
    }

    private fun getMoveEntryList() {
        viewModelScope.launch {
            flowRepository.getAllMoves()
                .map { it }
                .collect { moveList ->
                    moveEntryListUiState.update { MoveEntryListUiState(moveList) }
                }
        }
    }

    fun getCharacterMoveEntryList(character: String) {
        viewModelScope.launch {
            flowRepository.getAllMovesByCharacter(character)
                .map {moveList -> moveList}
                .collect { characterMoveList ->
                   characterMoveEntryList.update { MoveEntryListUiState(characterMoveList) }
                }
        }
    }

    fun getGameMoveEntryList(game: String) {
        viewModelScope.launch {
            flowRepository.getAllMovesByGame(game)
                .map { it }
                .collect { gameMoves ->
                    gameMoveEntryList.update { MoveEntryListUiState(gameMoves) }
                }
        }
    }

    // Combo Processing Functions
    private fun processComboAsString(): String {
        Timber.d("Processing combo as string...")
        val comboIterator = comboDisplayState.value.comboDisplay.moves.iterator()
        var moveAsString = ""
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
        Timber.d("Processed combo: $moveAsString")
        return moveAsString
    }

    fun updateComboDetails(comboDisplay: ComboDisplayUiState) {
        Timber.d("Updating combo details...")
        val comboDisplayWithCharacterData = comboDisplay.comboDisplay.copy(
                character = characterState.value.character.name)

        Timber.d("Combo Display with Character: $comboDisplayWithCharacterData")
        comboDisplayState.update { ComboDisplayUiState(comboDisplayWithCharacterData) }

        Timber.d("Combo Display Ui State: ")
        comboAsStringState.update { processComboAsString() }

        Timber.d("Saving updated combo to datastore...")
        saveComboDetailsToDs(comboDisplayState.value)
    }

    fun updateMoveList(
        moveName: String,
        moveListUiState: MoveEntryListUiState,
        game: Game? = null,
        console: Console? = null
    ) {
        Timber.d("Adding $moveName to combo...")
        Timber.d("Move List: ${moveListUiState.moveList.size} moves")
        Timber.d("Game: ${gameState.value}, Console: ${controlTypeState.value}")
        var moveToAdd = moveEntryListUiState.value.moveList.first { it.moveName == moveName}
        Timber.d("MoveToAdd: $moveToAdd")
        if (moveToAdd.moveName in consoleInputs) {
            Timber.d("Converting console input to standard...")
            moveToAdd = convertInputToStandard(
                move = moveToAdd,
                game = game,
                console = console,
                classic = sF6ControlType.value == SF6ControlType.Classic,
            )
        } else {
            moveListUiState.moveList.first { it.moveName == moveName }
        }
        Timber.d("${moveToAdd.moveName} found.")
        val updatedList = comboDisplayState.value.comboDisplay.moves.toMutableList()
        Timber.d("Index: $itemIndexState")
        val index =
            if (itemIndexState.value == comboDisplayState.value.comboDisplay.moves.size)
                itemIndexState.value else itemIndexState.value + 1
        updatedList.add(index, moveToAdd)
        val updatedCombo = comboDisplayState.value.comboDisplay.copy(moves = ImmutableList(list = updatedList))
        Timber.d("Updated Combo: $updatedCombo")
        updateComboDetails(ComboDisplayUiState(updatedCombo))
        itemIndexState.update { itemIndexState.value + 1 }
        Timber.d("Index: ${itemIndexState.value}")
    }

    fun deleteMove() {
        Timber.d("Deleting last move...")
        if (itemIndexState.value > 0) {
            val currentMoves: MutableList<MoveEntry> =
                comboDisplayState.value.comboDisplay.moves.toMutableList()
            Timber.d("Temporary move list created, move to delete: ${currentMoves.last()}")
            val index =
                if (itemIndexState.value >= comboDisplayState.value.comboDisplay.moves.size)
                    comboDisplayState.value.comboDisplay.moves.size - 1
                else itemIndexState.value
            currentMoves.removeAt(index)
            Timber.d("Move removed from temp list, preparing to copy and update.")
            val updatedCombo =
                comboDisplayState.value.comboDisplay.copy(moves = ImmutableList(currentMoves.toList()))
            updateComboDetails(ComboDisplayUiState(updatedCombo))
            itemIndexState.update { if (itemIndexState.value != 0) itemIndexState.value - 1 else itemIndexState.value }
            Timber.d("Index: ${itemIndexState.value}")
        } else {
            Timber.d("No moves found, cannot delete move.")
        }
    }

    fun clearMoveList() {
        Timber.d("Clearing move list...")
        comboDisplayState.update { ComboDisplayUiState() }
        comboAsStringState.update { "" }
        Timber.d("Cleared move list: ${comboAsStringState.value}")
    }

    suspend fun saveCombo() {
        Timber.d("Checking if combo details valid")
        Timber.d("ComboDisplay Character: ${comboDisplayState.value.comboDisplay.character}")
        Timber.d("ComboDisplay Moves: ${comboDisplayState.value.comboDisplay.moves}")
        if (
        comboDisplayState.value.comboDisplay.character.isNotEmpty() &&
            comboDisplayState.value.comboDisplay.moves.isNotEmpty()
            ) {
            Timber.d("Preparing to add combo to Db, checking if edit mode...")
            if (editingState.value) {
                Timber.d("Edit mode true, updating existing combo...")
                updateComboInDb()
            } else {
                Timber.d("Edit mode false, adding new combo...")
                insertComboToDb()
            }
        } else {
            Timber.d("Cannot save Combo, please make sure a Character is selected and the Move List is not empty.")
        }
    }

    // Datastore Functions
    private fun saveComboDetailsToDs(comboDisplay: ComboDisplayUiState = ComboDisplayUiState()) {
        Timber.d("Saving combo details to datastore...")
        Timber.d("Combo to save: ${comboDisplay.comboDisplay}")
        viewModelScope.launch {
            Timber.d("Checking if combo empty...")
            if (comboDisplay.comboDisplay == emptyComboDisplay) {
                Timber.d("Combo empty, adding empty combo to datastore...")
                comboDsRepository.updateComboState(comboDisplay.comboDisplay)
            } else {
                Timber.d("Combo details found, saving them to datastore...")
                comboDsRepository.updateComboState(comboDisplayState.value.comboDisplay)
            }
        }
        Timber.d("Combo saved to datastore.")
    }

    suspend fun updateControlType(controlType: Int) = settingsDsRepository.updateConsoleType(controlType)

    // Room Db Functions
    private suspend fun insertComboToDb() {
        val comboEntry = comboDisplayState.value.comboDisplay.toEntry(characterState.value.character)
            .copy(createdBy = profileNameState.value)
        flowRepository.insertCombo(comboEntry)
        Timber.d("Combo added to Db.")
        Timber.d("Clearing move list...")
        clearMoveList()
        Timber.d("Move list cleared.")
    }

    private suspend fun updateComboInDb() {
        Timber.d("Preparing to update combo in database...")
        val updatedCombo = comboDisplayState.value.comboDisplay.toEntry(characterState.value.character)
        Timber.d("Move List: ${updatedCombo.moves}")
        flowRepository.updateCombo(updatedCombo)
        Timber.d("Existing combo updated in Db.")
    }
}