package com.example.fightingflow.ui

import androidx.lifecycle.ViewModel
import com.example.fightingflow.data.database.TekkenDbRepository
import com.example.fightingflow.util.CharacterAndMoveData
import kotlinx.coroutines.flow.first
import timber.log.Timber

class InitViewModel(
    private val tekkenDataRepository: TekkenDbRepository
): ViewModel() {
    private val dataFile = CharacterAndMoveData()
    private val charactersToAdd = dataFile.tekkenCharacterEntries
    private val movesToAdd = dataFile.moveEntries

    suspend fun addDataToDb(): Boolean {
        Timber.d("")
        Timber.d("Launching coroutine to check database...")

        Timber.d("Getting existing data...")
        val existingCharacterData = tekkenDataRepository.getAllCharacters().first()
        Timber.d("Character List: $existingCharacterData")
        val existingMoveList = tekkenDataRepository.getAllMoves().first()
        Timber.d("Move List: $existingMoveList")

        Timber.d("Checking existing data...")
        if (existingCharacterData.isEmpty() && existingMoveList.isEmpty()) {
            Timber.d("Data not found, adding moves & characters to Db")
            tekkenDataRepository.insertAllCharacters(charactersToAdd)
            tekkenDataRepository.insertMoves(movesToAdd)
        } else {
            Timber.d("Database exists, starting app...")
        }
        return true
    }
}