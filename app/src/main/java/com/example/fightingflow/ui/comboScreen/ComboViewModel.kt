package com.example.fightingflow.ui.comboScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.TekkenDataRepository
import com.example.fightingflow.data.database.initData.DataToAdd
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.MoveEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class ComboViewModel(private val tekkenDataRepository: TekkenDataRepository): ViewModel() {
    val data = DataToAdd().moveEntries

    val emptyCharacter = CharacterEntry(
        id = 0,
        name = "",
        imageId = 0,
        fightingStyle = "",
        uniqueMoves = "",
        combosById = ""
    )

    val emptyCombo = ComboDisplay(
        comboId = "",
        character = "",
        damage = 0,
        createdBy = "",
        moves = mutableListOf()
    )

    val emptyMove = MoveEntry(
        id = 0,
        moveName = "",
        notation = "",
        moveType = "",
        counterHit = false,
        hold = false,
        justFrame = false,
        associatedCharacter = ""
    )

    val allCharacters: StateFlow<List<CharacterEntry>> = getAllCharacterEntries()

    val allMoves: StateFlow<List<MoveEntry>> = getAllMovesEntries()

    private val _characterState = MutableStateFlow<CharacterEntry>(emptyCharacter)
    val characterState: StateFlow<CharacterEntry> = _characterState


    private val _comboState = MutableStateFlow<ComboDisplay>(emptyCombo)
    val comboState: StateFlow<ComboDisplay> = _comboState

    val comboAsStringState = MutableStateFlow(processComboAsString())

    init {
        getAllCharacterEntries()
    }

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
        _comboState.update { comboDisplay }
    }

    fun updateMoveList(moveName: String) {
        val moveList = comboState.value.moves
        val moveToAdd = allMoves.value.first {it.moveName == moveName}
        moveList.add(moveToAdd)
        _comboState.update { currentState -> currentState.copy(moves = moveList) }
        comboAsStringState.value = processComboAsString()
        moveList.forEach { Log.d("", it.toString() + "\n") }
    }

    fun saveComboToDb() {
        viewModelScope.launch {
            tekkenDataRepository.insertCombo(comboState.value.toEntry())
        }
    }

    fun clearMoveList() {
        _comboState.value= comboState.value.copy(moves = mutableListOf<MoveEntry>())
        comboAsStringState.value = ""
    }

    fun getAllMovesEntries() =
        tekkenDataRepository.getAllMoves()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )

    fun getCombosByCharacter(): List<ComboDisplay> {
        val comboEntriesByCharacter =
            tekkenDataRepository.getAllCombosByCharacter(characterState.value)
            .mapNotNull { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf<ComboEntry>()
            )
        val comboDisplayByCharacter = comboEntriesByCharacter.value.map { it.toDisplay() }
        return comboDisplayByCharacter
    }

    fun getCharacterEntry(name: String) {
        _characterState.value = allCharacters.value.first {it.name == name}
        Log.d("", characterState.value.toString())
    }

    fun getAllCharacterEntries() =
        tekkenDataRepository.getAllCharacters()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )
    fun processMoveList(moves: String): MutableList<MoveEntry> {
        val movesList = moves.split(",")
        val moveEntryList = movesList.map {
            tekkenDataRepository.getMove(it)
                .map { it }
                .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = emptyMove
            ).value
        }
        return moveEntryList as MutableList<MoveEntry>
    }

    fun ComboEntry.toDisplay(): ComboDisplay =
        ComboDisplay(
            comboId =comboId,
            character = character.name,
            damage = damage,
            createdBy = createdBy,
            moves = processMoveList(moves)
        )
    fun ComboDisplay.toEntry(): ComboEntry =
        ComboEntry(
            id = 0,
            comboId = comboId.let { UUID.randomUUID().toString()},
            character = characterState.value,
            damage = damage,
            createdBy = createdBy,
            moves = moves.toString()
        )
}
