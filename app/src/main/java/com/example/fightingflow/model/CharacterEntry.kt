package com.example.fightingflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.String

@Entity(tableName = "character_table")
data class CharacterEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: kotlin.String,
    val imageId: Int,
    val fightingStyle: kotlin.String,
    val uniqueMoves: kotlin.String,
    val combosById: String = ""
)
