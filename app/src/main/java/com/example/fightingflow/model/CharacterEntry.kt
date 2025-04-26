package com.example.fightingflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.String

@Entity(tableName = "character_table")
data class CharacterEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val imageId: Int,
    val fightingStyle: String,
    val uniqueMoves: String,
    val combosById: String = "",
    val gameFranchise: String,
    val gameEntry: String
)
