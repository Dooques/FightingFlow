package com.example.fightingflow.util.characterAndMoveData.customInputLayouts

import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.util.ImmutableList

val capcomTagFighterMoves = ImmutableList<MoveEntry>(
    listOf(
        MoveEntry(moveName = "LP", notation = "lp", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "MP", notation = "mp", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "HP", notation = "hp", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "LK", notation = "lk", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "MK", notation = "mk", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "HK", notation = "hk", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
    )
)