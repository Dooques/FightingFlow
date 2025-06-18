package com.example.fightingflow.ui.characterScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.FlowRepository
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.data.datastore.SettingsDsRepository
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.SF6ControlType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class CharacterViewModel(
    private val flowRepository: FlowRepository,
    private val settingsDsRepository: SettingsDsRepository,
    private val characterDsRepository: CharacterDsRepository
): ViewModel() {

    companion object {
        const val TIME_MILLIS = 5_000L
    }

    val gameSelected = settingsDsRepository.getGame()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = null
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

    val customGameList = characterDsRepository.getCustomGameList()
        .map { it.split(", ") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = listOf()
        )

    suspend fun updateGameInDs(selectedGame: String) {
            Timber.d("Updating game in DS...")
            Timber.d("GameSelected: $selectedGame")
            settingsDsRepository.updateGameSelected(selectedGame)
    }

    suspend fun updateSF6ControlType(controlType: SF6ControlType) {
        Timber.d("Updating SF6 Control Type...")
        Timber.d("Type: ${controlType.name}")
        settingsDsRepository.updateSf6ControlType(controlType.type)
    }
    private suspend fun cleanupCustomGames(characterEntry: CharacterEntry) {
        Timber.d("Checking for other characters in changed game...")
        val charactersByGame = flowRepository.getCharactersByGame(characterEntry.game)
            .map { it }
            .first()
        Timber.d("Characters in related game: $charactersByGame")
        if (charactersByGame.isEmpty()) {
            Timber.d("No characters found for ${characterEntry.game}, deleting game from DS")
            val currentGameList = customGameList.value.toMutableList()
            Timber.d("Current List: $currentGameList")
            currentGameList.remove(characterEntry.game)
            val currentGameListString = if (currentGameList.isNotEmpty()) currentGameList.toList().joinToString() else ""
            Timber.d("Current List as String: $currentGameListString")
            characterDsRepository.updateCustomGameList(currentGameListString)
        }
    }

    suspend fun deleteCustomCharacter(characterEntry: CharacterEntry) {
        Timber.d("-- Deleting character Entry --")
        var moveEntryList = listOf<MoveEntry>()
        try {
            moveEntryList = flowRepository.getAllMovesByCharacter(characterEntry.name).first()
        } catch (e: Exception) {
            Timber.e(e, message = "Error accessing Moves for ${characterEntry.name}")
        }
        if (characterEntry.mutable) {
            Timber.d("Attempting to delete character...")
            try {
                flowRepository.deleteCharacter(characterEntry)
                if (moveEntryList.isNotEmpty()) {
                    moveEntryList.forEach { move ->
                        flowRepository.deleteMove(move)
                    }
                }
                Timber.d("Character deleted, cleaning up related data...")
                val charactersByGame = flowRepository.getCharactersByGame(characterEntry.game)
                    .map { it }
                    .first()
                Timber.d("Characters in related game: $charactersByGame")
                if (charactersByGame.isEmpty()) {
                    cleanupCustomGames(characterEntry)
                    settingsDsRepository.updateGameSelected("Tekken 8")
                }
            } catch (e: Exception) {
                Timber.e(e,"Error deleting character from database.")
            }
        } else {
            Timber.d("This character cannot be deleted.")
        }
    }
}