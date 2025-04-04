package com.example.fightingflow.util

import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.MoveEntry

val emptyCharacter = CharacterEntry(
    id = 0,
    name = "",
    imageId = 0,
    fightingStyle = "",
    uniqueMoves = "",
    combosById = ""
)

val emptyCombo = ComboDisplay(
    id = 0,
    comboId = "",
    character = "",
    damage = 0,
    createdBy = "",
    moves = mutableListOf()
)

val emptyComboEntry = ComboEntry(
    id = 0,
    comboId = "",
    character = emptyCharacter,
    damage = 0,
    createdBy = "",
    moves = ""
)

val emptyMove = MoveEntry(
    id = 0,
    moveName = "",
    notation = "",
    moveType = "",
    counterHit = false,
    hold = false,
    justFrame = false,
    associatedCharacter = ""
)