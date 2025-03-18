package com.example.fightingflow.model

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("move_table")
data class MoveEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val moveName: String,
    val moveType: String,
    val associatedCharacter: String
)

data class MoveString(
    val name: String,
    val moveString: List<String>,
    val counterHit: Boolean = false,
    val moveType: String
)