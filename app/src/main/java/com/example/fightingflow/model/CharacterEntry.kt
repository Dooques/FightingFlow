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
    val imageUri: String? = null,
    val fightingStyle: String,
    val combosById: String = "",
    val game: String,
    val controlType: String,
    val uniqueMoves: String? = null,
    val mutable: Boolean = false
)

enum class Console {
    STANDARD, XBOX, PLAYSTATION, NINTENDO
}

enum class Game(val title: String) {
    T8(title = "Tekken 8"),
    MK1(title = "Mortal Kombat 1"),
    SF6(title = "Street Fighter VI"),
    CUSTOM(title = "Custom")
}

enum class SF6ControlType(val type: Int) {
    Modern(type = 0), Classic(type = 1), Invalid(type = 2)
}

enum class ControlType(val type: String) {
    ArcSys("ArcSys"),
    MortalKombat("Mortal Kombat"),
    NumpadNotation("Numpad Notation"),
    StreetFighter("Street Fighter"),
    TagFighter("Tag Fighter"),
    Tekken("Tekken"),
    VirtuaFighter("Virtua Fighter"),
}
