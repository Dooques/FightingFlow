package com.example.fightingflow.util.characterAndMoveData

import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.ImmutableList

val commonMoves = ImmutableList(
    listOf(
        MoveEntry(moveName = "break", notation = "►", moveType = "Break", character = "Generic"),
        MoveEntry(moveName = "o_parenthesis", notation = "{", moveType = "Misc", character = "Generic"),
        MoveEntry(moveName = "c_parenthesis", notation = "}", moveType = "Misc", character = "Generic"),
        MoveEntry(moveName = "o_hold", notation = "[", moveType = "Misc", character = "Generic"),
        MoveEntry(moveName = "c_hold", notation = "]", moveType = "Misc", character = "Generic"),
        MoveEntry(moveName = "plus", notation = "+", moveType = "Misc", character = "Generic"),
        MoveEntry(moveName = "slide", notation = "/", moveType = "Misc", character = "Generic"),
    )
)