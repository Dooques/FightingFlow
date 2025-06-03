package com.example.fightingflow.ui.characterScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.datastore.Game
import com.example.fightingflow.data.datastore.SF6ControlType
import com.example.fightingflow.data.datastore.SettingsDsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class CharacterScreenViewModel(private val settingsDsRepository: SettingsDsRepository): ViewModel() {

    companion object {
        const val TIME_MILLIS = 5_000L
    }

    val gameSelected = settingsDsRepository.getGame()
        .map { game ->
            Timber.d("Getting game from DS...")
            Timber.d("Game: $game")
            when (game) {
                Game.T8.title -> Game.T8
                Game.MK1.title -> Game.MK1
                Game.SF6.title -> Game.SF6
                else -> null
            }
        }
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
}