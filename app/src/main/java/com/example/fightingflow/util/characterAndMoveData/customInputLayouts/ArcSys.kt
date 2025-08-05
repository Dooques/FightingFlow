package com.example.fightingflow.util.characterAndMoveData.customInputLayouts

import com.example.fightingflow.model.Game
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.ImmutableList

val arcSysMoves = ImmutableList(
    listOf(
        MoveEntry(moveName = "light", notation = "l", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "medium", notation = "m", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "heavy", notation = "h", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "special", notation = "s", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "L", notation = "L", moveType = "Text Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "R", notation = "R", moveType = "Text Input", character = "Generic", game = Game.CUSTOM.title),
    )
)