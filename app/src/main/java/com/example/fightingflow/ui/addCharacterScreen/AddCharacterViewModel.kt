package com.example.fightingflow.ui.addCharacterScreen

import com.example.fightingflow.data.database.FlowRepository
import com.example.fightingflow.util.CharacterEntryUiState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.data.datastore.SettingsDsRepository
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.ui.characterScreen.CharacterViewModel
import com.example.fightingflow.ui.comboDisplayScreen.ComboDisplayViewModel
import com.example.fightingflow.util.emptyCharacter
import com.example.fightingflow.util.emptyMove
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class AddCharacterViewModel(
    private val flowRepository: FlowRepository,
    private val characterDsRepository: CharacterDsRepository,
    private val settingsDsRepository: SettingsDsRepository,
) : ViewModel() {

    companion object {
        const val TIME_MILLIS = 5_000L
    }

    var characterUiState = MutableStateFlow(CharacterEntryUiState())
    private var existingCharacterState = MutableStateFlow(CharacterEntryUiState())
    var editState by mutableStateOf(false)

    val customGameList = characterDsRepository.getCustomGameList()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = ""
        )

    val gameSelectedState = settingsDsRepository.getGame()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(CharacterViewModel.TIME_MILLIS),
            initialValue = ""
        )

    val characterNameState = characterDsRepository.getName()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(ComboDisplayViewModel.TIME_MILLIS),
            initialValue = ""
        )

    private suspend fun getExistingMove(moveName: String) = flowRepository.getMove(moveName)
        .map { it ?: emptyMove }
        .first()

    fun getCharacterToEdit() { viewModelScope.launch {
        Timber.d("-- Getting character from DB --")
        flowRepository.getCharacterByNameAndGame(characterNameState.value, gameSelectedState.value)
            .map { character -> CharacterEntryUiState(character ?: emptyCharacter) }
            .collect { existingCharacter -> characterUiState.update { existingCharacter } }
        }
    }
    fun clearCharacterState() {
        characterUiState.update { CharacterEntryUiState() }
    }

    private fun getExistingCharacterEntry(character: CharacterEntry) { viewModelScope.launch {
        Timber.d("Name: ${character.name} \n Game: ${character.game}")

        flowRepository.getCharacterByNameAndGame(character.name, character.game)
            .mapNotNull { CharacterEntryUiState(it ?: emptyCharacter) }
            .collect { characterState -> existingCharacterState.update { characterState } }

        Timber.d("Character: ${existingCharacterState.value.character}") }
    }

    suspend fun updateCustomGameList(customGame: String) {
        Timber.d("New Game: $customGame")
        Timber.d("Existing list: ${customGameList.value}")
        characterDsRepository.updateCustomGameList(gameList =
            if (customGameList.value.isEmpty()) { customGame }
            else {
                if (!customGameList.value.lowercase().contains(customGame.lowercase())) {
                    "${customGameList.value}, $customGame"
                } else { customGameList.value }
            }) }

    private suspend fun cleanupCustomGames(character: CharacterEntry) {
        Timber.d("Checking for other characters in changed game...")
        val gameList = customGameList.value.split(", ").toMutableList()
        gameList.forEach { game ->
            val charactersByGame = flowRepository.getCharactersByGame(game).map { it }.first()
            Timber.d("Characters in related game: $charactersByGame")

            if (charactersByGame.isEmpty()) {
                Timber.d("No characters found for ${character.game}, deleting game from DS" +
                        "\n Current List: $gameList")
                gameList.remove(game)
                val  gameListString =
                    if (gameList.isNotEmpty()) gameList.toList().joinToString()
                    else ""
                Timber.d("Current List as String: $gameListString")
                characterDsRepository.updateCustomGameList(gameListString)
            }
        }
    }

    private suspend fun addUniqueMovesToDb(character: CharacterEntry) {
        Timber.d("-- Converting Unique Moves --")
        val uniqueMoves = character.uniqueMoves?.split(", ")?.map {
            MoveEntry(
                moveName = it,
                notation = it.lowercase(),
                moveType = "Unique Move",
                character = character.name,
                game = character.game,
            ) }

        val movesToAdd = mutableListOf<MoveEntry>()

        uniqueMoves?.forEach {
            val existingMove = getExistingMove(it.moveName)
            if (existingMove.moveName != it.moveName && existingMove.game != it.game)
              movesToAdd.add(it)
            }

        if (movesToAdd.isNotEmpty()) {
            try {
                flowRepository.insertMoves(movesToAdd)
            } catch (e: Exception) {
                Timber.e(e, message = "Error inserting Unique character moves into database.")
            }
        } else {
            Timber.d("Unique move list is empty.")
        }
    }

    suspend fun saveCharacter(character: CharacterEntry): CharacterDbResult {
        Timber.d("-- Saving/Updating character to database --")
        Timber.d("Edit mode: $editState")

        var result = checkCharacterBeforeSaving(character)

        Timber.d("Checking Result")
        if (result is CharacterDbResult.Error) {
            Timber.e(result.e, "Character Error, returning.")
            return result
        } else { Timber.d("No errors found, preparing to save/update") }

        Timber.d("Character found, preparing to save/update character...")
        result = if (editState) {
            Timber.d("Updating character...")
            updateCharacter(character)
        } else {
            Timber.d("Inserting character...")
            insertCharacter(character)
        }
        return result
    }

    private fun checkCharacterBeforeSaving(character: CharacterEntry): CharacterDbResult {
        Timber.d("-- Checking Character before saving/updating database --")
        Timber.d("Update mode: $editState")

        if (character == CharacterEntryUiState().character) {
            Timber.d("No character data found.")
            return CharacterDbResult.Error(Exception("Character Error: Empty character"))
        } else { Timber.d("Character is valid.") }

        if (editState) {
            Timber.d("Checking character does not match existing character")
            if (
                character.name == existingCharacterState.value.character.name &&
                character.game == existingCharacterState.value.character.game
                ) {
                return CharacterDbResult.Error(
                    Exception("Character Error: Character matches existing character"))
            }
        } else {
            Timber.d("Checking for existing character")
            getExistingCharacterEntry(character)
            Timber.d("Existing character: ${existingCharacterState.value}")
            if (character == existingCharacterState.value.character) {
                Timber.d("Character exists and matches new character.")
                return CharacterDbResult.Error(Exception("Character Error: Character already exists."))
            } else { Timber.d("No matching characters, moving to save/update.") }
        }
        return CharacterDbResult.Loading
    }

    private suspend fun updateCharacter(character: CharacterEntry): CharacterDbResult {
        Timber.d("Updating existing character in database")
        Timber.d("Character: $character" +
                "\n Existing Character: ${existingCharacterState.value}")
        try {
            if (existingCharacterState.value != CharacterEntryUiState()) {
                Timber.d("Copying id of existing character to update database value.")
                characterUiState.update { CharacterEntryUiState(characterUiState.value.character.copy(
                    id = existingCharacterState.value.character.id)) } }

            flowRepository.updateCharacter(character)
            addUniqueMovesToDb(character)
            Timber.d("Character saved: $character")

            if (!customGameList.value.contains(existingCharacterState.value.character.game)) {
                updateCustomGameList(character.game)
                cleanupCustomGames(existingCharacterState.value.character)
            }

            Timber.d("Updating datastore with Character and Game")
            characterDsRepository.updateCharacter(character)
            settingsDsRepository.updateGameSelected(character.game)
            clearCharacterState()
            editState = false
            Timber.d("Datastores updated.")

            return CharacterDbResult.Success
        } catch (e: Exception) {
            Timber.e(e, "Error updating character in database.")
            return CharacterDbResult.Error(e)
        }
    }

    private suspend fun insertCharacter(character: CharacterEntry): CharacterDbResult {
        try {
            Timber.d("Saving character to database...")
            flowRepository.insertCharacter(character)
            addUniqueMovesToDb(character)
            Timber.d("Character saved: $character")

            Timber.d("Updating datastore with Character and Game")
            characterDsRepository.updateCharacter(character)
            settingsDsRepository.updateGameSelected(character.game)
            clearCharacterState()
            editState = false
            Timber.d("Datastores updated.")

            return CharacterDbResult.Success
        } catch (e: Exception) {
            Timber.e(e, "Error saving character")
            return CharacterDbResult.Error(e)
        }
    }
}

sealed class CharacterDbResult {
    data object Loading : CharacterDbResult()
    data object Success : CharacterDbResult()
    data class Error(val e: Exception) : CharacterDbResult()
}