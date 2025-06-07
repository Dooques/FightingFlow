package com.example.fightingflow.util

import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.ProfileCreation
import com.example.fightingflow.model.ProfileEntry
import java.util.UUID

val emptyCharacter = CharacterEntry(
    id = 0,
    name = "",
    imageId = R.drawable.mokujin,
    fightingStyle = "",
    combosById = "",
    game = "Custom",
)

val emptyComboDisplay = ComboDisplay(
    comboId = UUID.randomUUID().toString(),
    description = "",
    character = "",
    damage = 0,
    createdBy = "",
    dateCreated = "",
    moves = ImmutableList(emptyList())
)

val emptyComboEntry = ComboEntry(
    comboId = UUID.randomUUID().toString(),
    description = "",
    character = emptyCharacter,
    damage = 0,
    createdBy = "",
    dateCreated = "",
    moves = ""
)

val emptyMove = MoveEntry(
    moveName = "",
    notation = "",
    moveType = "",
    counterHit = false,
    hold = false,
    justFrame = false,
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