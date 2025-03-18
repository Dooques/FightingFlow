package com.example.fightingflow.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.UUID

@Entity(tableName = "combo_table")
data class ComboEntry (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "combo_id")
    val comboId: String = UUID.randomUUID().toString(),
    val character: CharacterEntry,
    val damage: Int,
    @ColumnInfo(name = "created_by")
    val createdBy: String,
    val moves: String
)

data class ComboDisplay(
    val comboId: String,
    val character: String,
    val damage: Int,
    val createdBy: String,
    val moves: List<MoveString>
)

class CharacterConverter {
    private val jsonAdapter = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
        .adapter(CharacterEntry::class.java)

    @TypeConverter
    fun characterToString(character: CharacterEntry?) =
        character?.let { jsonAdapter.toJson(it) } ?: ""

    @TypeConverter
    fun stringToCharacter(json: String?) =
        json?.let { jsonAdapter.fromJson(json) }
}