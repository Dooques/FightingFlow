package com.example.fightingflow.util.characterAndMoveData.customInputLayouts

import com.example.fightingflow.model.Game
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.ImmutableList

val numpadNotation = ImmutableList<MoveEntry>(
    listOf(
        MoveEntry(moveName = "1", notation = "1", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "2", notation = "2", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "3", notation = "3", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "4", notation = "4", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "5", notation = "5", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "6", notation = "6", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "7", notation = "7", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "8", notation = "8", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "9", notation = "9", moveType = "Movement", character = "Generic", game = Game.CUSTOM.title),

        MoveEntry(moveName = "A", notation = "a", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "B", notation = "b", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "C", notation = "c", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "D", notation = "d", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
    )
)