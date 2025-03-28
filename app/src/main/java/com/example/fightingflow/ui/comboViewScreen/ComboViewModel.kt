package com.example.fightingflow.ui.comboViewScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.TekkenDataRepository
import com.example.fightingflow.data.database.initData.DataToAdd
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.toDisplay
import com.example.fightingflow.util.emptyCharacter
import com.example.fightingflow.util.emptyCombo
import com.example.fightingflow.util.emptyComboEntry
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ComboViewModel(
    private val tekkenDataRepository: TekkenDataRepository
): ViewModel() {

    val data = DataToAdd().moveEntries
    val allMoves = getAllMovesEntries()
    val allCharacters = getAllCharacterEntries()

    val comboEntries = getAllComboEntries()
    val allCombos = getAllCombosDisplay()

    val comboState = MutableStateFlow(emptyCombo)
    val characterState = MutableStateFlow(emptyCharacter)

    fun getCharacterEntry(name: String) {
        characterState.value = allCharacters.value.first { it.name == name }
    }

    // Repository Functions

    fun getAllCharacterEntries() =
        tekkenDataRepository.getAllCharacters()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )

    fun getCharacter(characterName: String) {
        viewModelScope.launch {
            characterState.value = tekkenDataRepository.getCharacter(characterName)
        }
    }

    fun getAllMovesEntries() =
        tekkenDataRepository.getAllMoves()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )

    fun getAllCombosDisplay() =
        tekkenDataRepository.getAllCombos()
            .mapNotNull { it.map { it.toDisplay()} }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )

    fun getAllComboEntries() =
        tekkenDataRepository.getAllCombos()
            .mapNotNull {it}
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )

    suspend fun deleteCombo(combo: ComboDisplay, comboEntries: List<ComboEntry>) {
        Log.d("", "existing combos: $comboEntries")
        val comboToDelete = comboEntries.first {it.comboId == combo.comboId}
        Log.d("", "VM deleting:$comboToDelete")
        tekkenDataRepository.deleteCombo(comboToDelete)
    }

    // Object Conversion

    fun getMoveEntryData(moveData: List<MoveEntry>, combo: ComboDisplay): ComboDisplay {
        val updatedCombo = combo.copy(
            moves = combo.moves.map {  move ->
                val updateData = moveData.first { it.moveName == move.moveName }
                MoveEntry(
                    id = updateData.id,
                    moveName = updateData.moveName,
                    notation = updateData.notation,
                    moveType = updateData.moveType,
                    counterHit = updateData.counterHit,
                    hold = updateData.hold,
                    justFrame = updateData.justFrame,
                    associatedCharacter = updateData.associatedCharacter
                )
            }
        )
        return updatedCombo
    }
}
