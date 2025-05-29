package com.example.fightingflow.ui.comboCreationScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.FlowRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.data.datastore.GameDsRepository
import com.example.fightingflow.data.datastore.ProfileDsRepository
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.getMoveEntryDataForComboDisplay
import com.example.fightingflow.model.toDisplay
import com.example.fightingflow.model.toEntry
import com.example.fightingflow.util.CharacterEntryListUiState
import com.example.fightingflow.util.CharacterUiState
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.ComboEntryUiState
import com.example.fightingflow.util.ImmutableList
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.emptyComboDisplay
import com.example.fightingflow.util.emptyComboEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
    private val gameDsRepository: GameDsRepository
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
    private val moveEntryListUiState = MutableStateFlow(MoveEntryListUiState())
    private val profileNameState = MutableStateFlow("")

    init {
        getComboIdFromDs()
        getEditingState()
        getMoveEntryList()
        getProfileName()
    }

    val gameSelected = gameDsRepository.getGame()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ""
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
                .collect { comboEntryState ->
                    val existingCombo = ComboDisplayUiState(
                        getMoveEntryDataForComboDisplay(
                            comboEntryState.comboEntry.toDisplay(),
                            moveEntryListUiState.value
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

    fun updateMoveList(moveName: String, moveListUiState: MoveEntryListUiState) {
        Timber.d("Adding $moveName to combo...")
        Timber.d("Move List: ${moveListUiState.moveList.size} moves")
        val moveToAdd = moveListUiState.moveList.first { it.moveName == moveName }
        Timber.d("${moveToAdd.moveName} found.")
        val updatedCombo = comboDisplayState.value.comboDisplay.copy(moves = ImmutableList(list =comboDisplayState.value.comboDisplay.moves + moveToAdd))
        Timber.d("Updated Combo: $updatedCombo")
        updateComboDetails(ComboDisplayUiState(updatedCombo))
    }

    fun deleteLastMove() {
        Timber.d("Deleting last move...")
        val currentMoves: MutableList<MoveEntry> = comboDisplayState.value.comboDisplay.moves.toMutableList()
        Timber.d("Temporary move list created, move to delete: ${currentMoves.last()}")
        currentMoves.removeAt(currentMoves.size - 1)
        Timber.d("Move removed from temp list, preparing to copy and update.")
        val updatedCombo = comboDisplayState.value.comboDisplay.copy(moves = ImmutableList(currentMoves.toList()))
        updateComboDetails(ComboDisplayUiState(updatedCombo))
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
                comboDsRepository.setCombo(comboDisplay.comboDisplay)
            } else {
                Timber.d("Combo details found, saving them to datastore...")
                comboDsRepository.setCombo(comboDisplayState.value.comboDisplay)
            }
        }
        Timber.d("Combo saved to datastore.")
    }

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