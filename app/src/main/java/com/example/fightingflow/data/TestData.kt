package com.example.fightingflow.data

import com.example.fightingflow.util.CharacterAndMoveData
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.ImmutableList
import com.example.fightingflow.util.emptyComboDisplay
import kotlin.random.Random

class TestData(moveData: CharacterAndMoveData) {
    val comboItem = ComboDisplayUiState(
        emptyComboDisplay.copy(
            character = moveData.tekkenCharacterEntries[Random.nextInt(
                0, until = moveData.tekkenCharacterEntries.size)].name,
            moves = ImmutableList(list = listOf(
                moveData.moveEntries[1],
                moveData.moveEntries[2],
                moveData.moveEntries[3],
                moveData.moveEntries[4],
                moveData.moveEntries[0],
                moveData.moveEntries[11],
                moveData.moveEntries[12],
                moveData.moveEntries[13],
                moveData.moveEntries[14],
                moveData.moveEntries[0],
                moveData.moveEntries[1],
                moveData.moveEntries[2],
                moveData.moveEntries[3],
                moveData.moveEntries[4],
            )),
            damage = 40,
            createdBy = "Sam"
        )
    )
}