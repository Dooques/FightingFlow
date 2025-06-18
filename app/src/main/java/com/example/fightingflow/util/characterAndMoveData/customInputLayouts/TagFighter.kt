package com.example.fightingflow.util.characterAndMoveData.customInputLayouts

import com.example.fightingflow.model.Game
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.ImmutableList

val tagFighterMoves = ImmutableList(
    listOf(
        MoveEntry(moveName = "a", notation = "a", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "b", notation = "b", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "c", notation = "c", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "d", notation = "d", moveType = "Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "Tag 1", notation = "Tag 1", moveType = "Text Input", character = "Generic", game = Game.CUSTOM.title),
        MoveEntry(moveName = "Tag 2", notation = "Tag 2", moveType = "Text Input", character = "Generic", game = Game.CUSTOM.title),
    )
)