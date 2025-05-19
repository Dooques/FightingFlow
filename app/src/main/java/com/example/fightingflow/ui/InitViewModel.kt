package com.example.fightingflow.ui

import androidx.lifecycle.ViewModel
import com.example.fightingflow.data.database.FlowRepository
import com.example.fightingflow.util.CharacterAndMoveData
import com.example.fightingflow.util.emptyCharacter
import kotlinx.coroutines.flow.first
import timber.log.Timber

class InitViewModel(
    private val tekkenDataRepository: FlowRepository
): ViewModel() {
    private val dataFile = CharacterAndMoveData()
    private val movesToAdd = dataFile.moveEntries

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
            dataFile.characterEntries.forEach { tekkenDataRepository.insertAllCharacters(it) }
            tekkenDataRepository.insertMoves(movesToAdd)
        } else {
            Timber.d("Database exists, starting app...")
        }
        return true
    }
}