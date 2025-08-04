package com.example.fightingflow.util.characterAndMoveData.customInputLayouts

import com.example.fightingflow.model.Game
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.util.ImmutableList

val virtuaFighterMoves = ImmutableList(
    listOf(
        MoveEntry(moveName = "p", notation = "p", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "k", notation = "k", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "g", notation = "g", moveType = "Input", character = "Generic", game = Game.CUSTOM.title)
    )
)