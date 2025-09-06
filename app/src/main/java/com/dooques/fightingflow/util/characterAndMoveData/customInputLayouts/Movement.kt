package com.dooques.fightingflow.util.characterAndMoveData.customInputLayouts

import com.dooques.fightingflow.model.MoveEntry
import com.dooques.fightingflow.util.ImmutableList

val movement = ImmutableList (
    listOf(
        MoveEntry(moveName = "forward", notation = "f", moveType = "Movement", character = "Generic", game = "Generic"),
        MoveEntry(moveName = "up", notation = "u", moveType = "Movement", character = "Generic", game = "Generic"),
        MoveEntry(moveName = "up_forward", notation = "u/f", moveType = "Movement", character = "Generic", game = "Generic"),
        MoveEntry(moveName = "down_forward", notation = "d/f", moveType = "Movement", character = "Generic", game = "Generic"),
        MoveEntry(moveName = "forward_dash", notation = "F", moveType = "Movement", character = "Generic", game = "Generic"),
        MoveEntry(moveName = "back", notation = "b", moveType = "Movement", character = "Generic", game = "Generic"),
        MoveEntry(moveName = "down", notation = "d", moveType = "Movement", character = "Generic", game = "Generic"),
        MoveEntry(moveName = "up_back", notation = "u/b", moveType = "Movement", character = "Generic", game = "Generic"),
        MoveEntry(moveName = "down_back", notation = "d/b", moveType = "Movement", character = "Generic", game = "Generic"),
        MoveEntry(moveName = "neutral", notation = "n", moveType = "Movement", character = "Generic", game = "Generic"),
    )
)