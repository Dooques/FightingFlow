package com.example.fightingflow.data

import com.example.fightingflow.util.characterAndMoveData.CharacterAndMoveData
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.ImmutableList
import com.example.fightingflow.util.emptyComboDisplay
import kotlin.random.Random

class TestData(moveData: CharacterAndMoveData) {
    val comboItem = ComboDisplayUiState(
        emptyComboDisplay.copy(
            character = moveData.characterEntries[0][Random.nextInt(
                0, until = moveData.characterEntries.size)].name,
            moves = ImmutableList(
                listOf(
                    moveData.moveEntries[0][1],
                    moveData.moveEntries[0][2],
                    moveData.moveEntries[0][3],
                    moveData.moveEntries[0][4],
                    moveData.moveEntries[0][0],
                    moveData.moveEntries[0][11],
                    moveData.moveEntries[0][12],
                    moveData.moveEntries[0][13],
                    moveData.moveEntries[0][14],
                    moveData.moveEntries[0][0],
                    moveData.moveEntries[0][1],
                    moveData.moveEntries[0][2],
                    moveData.moveEntries[0][3],
                    moveData.moveEntries[0][4],
                )
            ),
            damage = 40,
            createdBy = "Sam"
        )
    )
}