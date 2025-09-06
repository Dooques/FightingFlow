package com.dooques.fightingflow.util.characterAndMoveData.customInputLayouts

import com.dooques.fightingflow.model.Game
import com.dooques.fightingflow.model.MoveEntry
import com.dooques.fightingflow.util.ImmutableList

val virtuaFighterMoves = ImmutableList(
    listOf(
        MoveEntry(moveName = "p", notation = "p", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "k", notation = "k", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "g", notation = "g", moveType = "Input", character = "Generic", game = Game.CUSTOM.title)
    )
)