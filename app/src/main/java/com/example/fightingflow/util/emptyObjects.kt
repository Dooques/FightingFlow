package com.example.fightingflow.util

import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.ComboEntryFb
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.UserEntry

val emptyCharacter = CharacterEntry(
    id = 0,
    name = "",
    imageId = R.drawable.mokujin,
    fightingStyle = "",
    combosById = "",
    game = "Custom",
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