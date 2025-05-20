package com.example.fightingflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.String

@Entity(tableName = "character_table")
data class CharacterEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val imageId: Int,
    val fightingStyle: String,
    val combosById: String = "",
    val game: String,
)
