package com.example.fightingflow.util.characterAndMoveData.customInputLayouts

import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.ImmutableList

val movement = ImmutableList<MoveEntry> (
    listOf(
        MoveEntry(moveName = "forward", notation = "f", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "up", notation = "u", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "up_forward", notation = "u/f", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "down_forward", notation = "d/f", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "forward_dash", notation = "F", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "back", notation = "b", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "down", notation = "d", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "up_back", notation = "u/b", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "down_back", notation = "d/b", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "neutral", notation = "n", moveType = "Movement", character = "Generic", game = "Tekken 8"),
    )
)