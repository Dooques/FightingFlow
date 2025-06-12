package com.example.fightingflow.ui.addCharacterScreen

import com.example.fightingflow.data.database.FlowRepository
import com.example.fightingflow.util.CharacterEntryUiState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.util.emptyCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class AddCharacterViewModel(
    private val flowRepository: FlowRepository,
    private val characterDsRepository: CharacterDsRepository,
) : ViewModel() {
    companion object {
        const val TIME_MILLIS = 5_000L
    }

    private var characterUiState by mutableStateOf(CharacterEntryUiState())

    private var existingCharacterState by mutableStateOf(CharacterEntryUiState())

    val customGameList = characterDsRepository.getCustomGameList()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = ""
        )

    private suspend fun getExistingCharacterEntry() {
        Timber.d("Name: ${characterUiState.character.name}, Game: ${characterUiState.character.game}")
        val foundCharacter = flowRepository.getCharacterByGameAndName(
            name = characterUiState.character.name,
            game = characterUiState.character.game
        ).firstOrNull()

        Timber.d("Character: $foundCharacter")
        existingCharacterState = CharacterEntryUiState(foundCharacter ?: emptyCharacter)
    }

    fun updateCharacterUiState(character: CharacterEntry) {
        Timber.d("Updating character: $character")
        characterUiState = CharacterEntryUiState(character)
    }

    suspend fun updateCustomGameList(customGame: String) =
        characterDsRepository.updateCustomGameList(
            gameList =
                if (customGameList.value.isNotEmpty()) "${customGameList.value}, $customGame"
                else customGame
        )

    suspend fun saveCharacter(updateCharacter: Boolean): String {
        var result: String
        Timber.d("Saving character to database...")
        Timber.d("Update mode: $updateCharacter")
        if (characterUiState == CharacterEntryUiState()) {
            Timber.d("No character data found.")
            result = "Empty Character Error"
            return result
        }

        Timber.d("Searching for existing characters...")
        val searching = viewModelScope.launch {
            Timber.d("Coroutine launched...")
            getExistingCharacterEntry()
            Timber.d("Coroutine completed.")
        }
        searching.join()
        Timber.d("Existing Character: $existingCharacterState")

        Timber.d("Checking if existing character matches new one...")
        if (
            existingCharacterState.character.name == characterUiState.character.name &&
            existingCharacterState.character.game == characterUiState.character.game
        ) {
            if (updateCharacter) {
                try {
                    if (!existingCharacterState.character.mutable) {
                        result = "Character Immutable"
                        return result
                    }

                    characterUiState = CharacterEntryUiState(
                        characterUiState.character.copy(id = existingCharacterState.character.id)
                    )
                    flowRepository.updateCharacter(characterUiState.character)
                    Timber.d("Character saved: ${characterUiState.character}")

                    result = "Success"
                    return result
                } catch (e: Exception) {
                    Timber.e(e, "Error updating character in database.")
                    result = "Error inserting character"
                    return result
                }
            }

            if (existingCharacterState.character.mutable) {
                Timber.d("Character Entry found, can't add duplicate characters")
                result = "Character Exists"
                return result
            } else {
                Timber.d("Character Entry found, can't edit this character")
                result = "Character Immutable"
                return result
            }
        }

        Timber.d("Character found, preparing to save character...")
        try {
            Timber.d("Saving character to database...")
            flowRepository.insertCharacter(characterUiState.character)
            Timber.d("Character saved: ${characterUiState.character}")
            result = "Success"
        } catch (e: Exception) {
            Timber.e(e, "Error saving character")
            result = "Error Saving Character"
        }
        return result
    }
}