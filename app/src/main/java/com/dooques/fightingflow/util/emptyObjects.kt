package com.dooques.fightingflow.util

import android.R
import com.dooques.fightingflow.model.CharacterEntry
import com.dooques.fightingflow.model.ComboDisplay
import com.dooques.fightingflow.model.ComboEntry
import com.dooques.fightingflow.model.ComboEntryFb
import com.dooques.fightingflow.model.MoveEntry
import com.dooques.fightingflow.model.UserEntry

val emptyCharacter = CharacterEntry(
    id = 0,
    name = "",
    imageId = R.drawable,
    fightingStyle = "",
    combosById = "",
    game = "Custom",
    numpadNotation = false,
    controlType = "Tekken"
)

val emptyComboDisplay = ComboDisplay(
    id = "",
    title = "",
    character = "",
    damage = 0,
    createdBy = "",
    dateCreated = "",
    moves = ImmutableList(emptyList())
)

val emptyComboEntry = ComboEntry(
    id = "",
    title = "",
    character = "",
    damage = 0,
    createdBy = "",
    dateCreated = "",
    moves = ""
)

val emptyComboEntryFb = ComboEntryFb(
    comboId = "",
    title = "",
    character = "",
    damage = 0,
    createdBy = "",
    dateCreated = "",
    difficulty = 0f,
    likes = 0,
    tags = "",
    private = false,
    moves = ImmutableList(listOf()),
)

val emptyMove = MoveEntry(
    moveName = "",
    notation = "",
    moveType = "",
    character = ""
)

val emptyUser = UserEntry(
    userId = "",
    username = "",
    profilePic = "",
)