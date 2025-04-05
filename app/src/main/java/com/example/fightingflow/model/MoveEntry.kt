package com.example.fightingflow.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.String

@Entity("move_table")
data class MoveEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "move_name")
    val moveName: kotlin.String,
    val notation: kotlin.String,
    @ColumnInfo(name = "move_type")
    val moveType: String,
    @ColumnInfo(name = "counter_hit")
    val counterHit: Boolean = false,
    val hold: Boolean = false,
    @ColumnInfo(name = "just_frame")
    val justFrame: Boolean = false,
    @ColumnInfo(name = "associated_character")
    val associatedCharacter: kotlin.String
)

data class MoveString(
    val name: kotlin.String,
    val moveString: List<kotlin.String>,
    val counterHit: Boolean = false,
    val moveType: kotlin.String
)