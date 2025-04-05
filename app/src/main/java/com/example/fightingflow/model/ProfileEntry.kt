package com.example.fightingflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: kotlin.String,
    val profilePic: kotlin.String,
    val password: kotlin.String,
    val loggedIn: Boolean
)

data class ProfileCreation(
    val username: kotlin.String,
    val profilePic: kotlin.String,
    val password: kotlin.String,
    val confirmPassword: kotlin.String
)

fun ProfileCreation.toEntry(): ProfileEntry =
    ProfileEntry(
        id = 0,
        username = username,
        profilePic = profilePic,
        password = password,
        loggedIn = false
    )