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
    val moveName: String,
    val notation: String,
    @ColumnInfo(name = "move_type")
    val moveType: String,
    @ColumnInfo(name = "counter_hit")
    val counterHit: Boolean = false,
    val hold: Boolean = false,
    @ColumnInfo(name = "just_frame")
    val justFrame: Boolean = false,
    @ColumnInfo(name = "character")
    val character: String,
    val game: String? = null,
    val controlType: SF6ControlType? = SF6ControlType.Invalid
)

data class MoveString(
    val name: String,
    val moveString: List<String>,
    val counterHit: Boolean = false,
    val moveType: String
)