package com.dooques.fightingflow.util.characterAndMoveData.customInputLayouts

import com.dooques.fightingflow.model.Game
import com.dooques.fightingflow.model.MoveEntry
import com.dooques.fightingflow.util.ImmutableList

val tagFighterMoves = ImmutableList(
    listOf(
        MoveEntry(moveName = "light", notation = "l", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "medium", notation = "m", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "heavy", notation = "h", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "special", notation = "s", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "Tag 1", notation = "Tag 1", moveType = "Text Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "Tag 2", notation = "Tag 2", moveType = "Text Input", character = "Generic", game = Game.CUSTOM.title),
    )
)