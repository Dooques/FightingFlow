package com.example.fightingflow.data.database.initData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

            if (existingCharacterData.isEmpty() && existingMoveData.isEmpty()) {
                tekkenDataRepository.insertAllCharacters(charactersToAdd)
                tekkenDataRepository.insertMoves(movesToAdd)
            }
        }
    }
}