package com.example.fightingflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val dob: String,
    val tos: Boolean,
    val marketing: Boolean,
    val loggedIn: Boolean
)
