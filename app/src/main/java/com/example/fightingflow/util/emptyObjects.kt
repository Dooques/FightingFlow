package com.example.fightingflow.util

import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.ProfileCreation
import com.example.fightingflow.model.ProfileEntry

val emptyCharacter = CharacterEntry(
    id = 0,
    name = "",
    imageId = R.drawable.mokujin,
    fightingStyle = "",
    combosById = "",
    game = "Custom",
)

val emptyComboDisplay = ComboDisplay(
    id = 0,
    title = "",
    character = "",
    damage = 0,
    createdBy = "",
    dateCreated = "",
    moves = ImmutableList(emptyList())
)

val emptyComboEntry = ComboEntry(
    id = 0,
    title = "",
    character = "",
    damage = 0,
    createdBy = "",
    dateCreated = "",
    moves = ""
)

val emptyMove = MoveEntry(
    moveName = "",
    notation = "",
    moveType = "",
    character = ""
)

val emptyProfile = ProfileEntry(
    id = 0,
    username = "",
    password = "",
    profilePic = "",
    loggedIn = false
)

val emptyProfileCreation = ProfileCreation(
    username = "",
    password = "",
    confirmPassword = "",
    profilePic = ""
)