package com.example.fightingflow.util.characterAndMoveData.customInputLayouts

import com.example.fightingflow.model.Game
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.ImmutableList

val ArcSysMoves = ImmutableList<MoveEntry>(
    listOf(
        MoveEntry(moveName = "L", notation = "l", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "M", notation = "m", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "H", notation = "h", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "S", notation = "s", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "E", notation = "e", moveType = "Input", character = "Generic", game = Game.CUSTOM.title)
    )
)