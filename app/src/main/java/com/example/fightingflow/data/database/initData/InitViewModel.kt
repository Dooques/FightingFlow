package com.example.fightingflow.data.database.initData

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.TAG
import com.example.fightingflow.data.database.TekkenDataRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class InitViewModel(
    private val tekkenDataRepository: TekkenDataRepository
): ViewModel() {
    private val dataFile = DataToAdd()
    private val charactersToAdd = dataFile.tekkenCharacterEntries
    private val movesToAdd = dataFile.moveEntries

    fun addDataToDb() {
        viewModelScope.launch {
            val existingCharacterData = tekkenDataRepository.getAllCharacters().first()
            val existingMoveData = tekkenDataRepository.getAllMoves().first()
            Log.d(TAG,"Checking for existing data...")
            if (existingCharacterData.isEmpty() && existingMoveData.isEmpty()) {
                Log.d(TAG, "Data not found, adding moves & characters to Db")
                tekkenDataRepository.insertAllCharacters(charactersToAdd)
                tekkenDataRepository.insertMoves(movesToAdd)
            } else {
                Log.d(TAG, "Database exists, starting app...")
            }
        }
    }
}