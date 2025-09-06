package com.example.fightingflow.ui

import androidx.lifecycle.ViewModel
import com.dooques.fightingflow.data.database.FlowRepository
import com.dooques.fightingflow.util.emptyCharacter
import kotlinx.coroutines.flow.first
import timber.log.Timber

class InitViewModel(
    private val tekkenDataRepository: FlowRepository
): ViewModel() {

    suspend fun addDataToDb(): Boolean {
        Timber.d("Launching coroutine to check database...")
        Timber.d("Getting existing data...")

        val existingCharacterData = tekkenDataRepository.getCharacter("Kazuya").first() ?: emptyCharacter
        Timber.d("Character: $existingCharacterData")

        val existingMoveList = tekkenDataRepository.getAllMoves().first()
        Timber.d("Move List: $existingMoveList")

        Timber.d("Checking existing data...")
        if (existingCharacterData == emptyCharacter && existingMoveList.isEmpty()) {
            Timber.d("Data not found, adding moves & characters to Db")
        } else {
            Timber.d("Database exists, starting app...")
        }
        return true
    }
}