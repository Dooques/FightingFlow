package com.example.fightingflow.model

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Entity(tableName = "character_table")
data class CharacterEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val imageId: Int,
    val fightingStyle: String,
    val uniqueMoves: String,
    val combosById: String = ""
)
