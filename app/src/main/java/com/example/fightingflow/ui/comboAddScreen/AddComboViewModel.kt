package com.example.fightingflow.ui.comboAddScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.TekkenDataRepository
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.moveEntryToMoveList
import com.example.fightingflow.model.toEntry
import com.example.fightingflow.util.emptyCharacter
import com.example.fightingflow.util.emptyCombo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddComboViewModel(
    private val tekkenDataRepository: TekkenDataRepository
): ViewModel() {

    val characterState = MutableStateFlow(emptyCharacter)
    val comboState = MutableStateFlow(emptyCombo)
    val comboAsStringState = MutableStateFlow(processComboAsString())
    val allMoves = MutableStateFlow<List<MoveEntry>>(listOf())
    val existingCombos = MutableStateFlow<List<ComboEntry>>(listOf())
    val editing = mutableStateOf(false)

    fun processComboAsString(): String {
        val comboIterator = comboState.value.moves.iterator()
        var moveAsString = ""
        while (comboIterator.hasNext()) {
            val move = comboIterator.next().notation
            if (move == "Break") {
                moveAsString += " $move "
            }
            moveAsString += move
            if (comboIterator.hasNext()) {
                moveAsString += ", "
            }
        }
        return moveAsString
    }

    fun updateComboData(comboDisplay: ComboDisplay) {
        comboState.update { comboDisplay }
        comboAsStringState.value = processComboAsString()
    }

    fun updateMoveList(moveName: String) {
        val moveToAdd = allMoves.value.first { it.moveName == moveName }
        val updatedCombo = comboState.value.copy(moves = comboState.value.moves + moveToAdd)
        updateComboData(updatedCombo)
    }

    fun deleteLastMove() {
        val currentMoves: MutableList<MoveEntry> = comboState.value.moves.toMutableList()
        currentMoves.removeAt(currentMoves.size - 1)
        val updatedCombo = comboState.value.copy(moves = currentMoves.toList())
        updateComboData(updatedCombo)
    }

    fun clearMoveList() {
        comboState.value = comboState.value.copy(moves = mutableListOf<MoveEntry>())
        comboAsStringState.value = ""
    }

    fun saveCombo() {
        if (editing.value) {
            updateComboInDb()
        } else {
            insertComboToDb()
        }
    }

    fun insertComboToDb() {
        viewModelScope.launch {
            val comboEntry = comboState.value.toEntry(characterState.value)
            tekkenDataRepository.insertCombo(comboEntry)
            characterState.update { currentState ->
                currentState.copy(combosById = currentState.combosById + "${comboEntry.comboId}, ")
            }
            clearMoveList()
            tekkenDataRepository.updateCharacter(characterState.value)
        }
    }

     fun updateComboInDb() {
         viewModelScope.launch {
             val existingCombo = existingCombos.value.first {it.comboId == comboState.value.comboId}
             val newCombo = comboState.value

             tekkenDataRepository.updateCombo(existingCombo.copy(
                 id = newCombo.id,
                 comboId = newCombo.comboId,
                 character = characterState.value,
                 damage = newCombo.damage,
                 createdBy = newCombo.createdBy,
                 moves = moveEntryToMoveList(newCombo.moves)
             ))
             clearMoveList()
         }
    }
}