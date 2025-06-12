package com.example.fightingflow.ui.characterScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.data.datastore.SettingsDsRepository
import com.example.fightingflow.model.Game
import com.example.fightingflow.model.SF6ControlType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class CharacterScreenViewModel(
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
        settingsDsRepository.updateGame(selectedGame)
    }

    suspend fun updateSF6ControlType(controlType: SF6ControlType) {
        Timber.d("Updating SF6 Control Type...")
        Timber.d("Type: ${controlType.name}")
        settingsDsRepository.updateSf6ControlType(controlType.type)
    }

    suspend fun updateCustomGameList(gameList: String) {
        Timber.d("Updating list of custom games...")
        Timber.d("Game List: $gameList")
        characterDsRepository.updateCustomGameList(gameList)
    }
}