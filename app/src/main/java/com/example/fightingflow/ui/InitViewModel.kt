package com.example.fightingflow.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.TAG
import com.example.fightingflow.data.database.TekkenDbRepository
import com.example.fightingflow.util.CharacterAndMoveData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

class InitViewModel(
    private val tekkenDataRepository: TekkenDbRepository
): ViewModel() {
    private val dataFile = CharacterAndMoveData()
    private val charactersToAdd = dataFile.tekkenCharacterEntries
    private val movesToAdd = dataFile.moveEntries

    fun addDataToDb() {
        viewModelScope.launch {
            val existingCharacterData = tekkenDataRepository.getAllCharacters().first()
            val existingMoveData = tekkenDataRepository.getAllMoves().first()
            Timber.d("Checking for existing data...")
            if (existingCharacterData.isEmpty() && existingMoveData.isEmpty()) {
                Timber.d("Data not found, adding moves & characters to Db")
                tekkenDataRepository.insertAllCharacters(charactersToAdd)
                tekkenDataRepository.insertMoves(movesToAdd)
            } else {
                Timber.d("Database exists, starting app...")
            }
        }
    }
}