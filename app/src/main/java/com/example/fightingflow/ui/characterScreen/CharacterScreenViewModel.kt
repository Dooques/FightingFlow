package com.example.fightingflow.ui.characterScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.datastore.GameDsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CharacterScreenViewModel(private val gameDsRepository: GameDsRepository): ViewModel() {

    val gameSelected = gameDsRepository.getGame()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ""
        )

    suspend fun updateGameInDs(selectedGame: String) = gameDsRepository.updateGame(selectedGame)
}