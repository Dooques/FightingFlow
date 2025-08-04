package com.example.fightingflow.util.characterAndMoveData.customInputLayouts

import com.example.fightingflow.model.Game
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.ImmutableList

val arcSysMoves = ImmutableList(
    listOf(
        MoveEntry(moveName = "light", notation = "a", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "medium", notation = "b", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "heavy", notation = "c", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "special", notation = "d", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "L", notation = "L", moveType = "Text Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "R", notation = "R", moveType = "Text Input", character = "Generic", game = Game.CUSTOM.title),
    )
)