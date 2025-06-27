package com.example.fightingflow.util.characterAndMoveData.customInputLayouts

import com.example.fightingflow.model.Game
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.ImmutableList

val numpadNotationMoves = ImmutableList(
    listOf(
        MoveEntry(moveName = "one_m", notation = "1", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "two_m", notation = "2", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "three_m", notation = "3", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "four_m", notation = "4", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "five_m", notation = "5", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "six_m", notation = "6", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "seven_m", notation = "7", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "eight_m", notation = "8", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "nine_m", notation = "9", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),

        MoveEntry(moveName = "a", notation = "a", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "b", notation = "b", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "c", notation = "c", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "d", notation = "d", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "L", notation = "l", moveType = "Text Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "R", notation = "r", moveType = "Text Input", character = "Generic", game = Game.CUSTOM.title),
    )
)