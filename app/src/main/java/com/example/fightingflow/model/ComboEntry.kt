package com.example.fightingflow.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.fightingflow.util.CharacterUiState
import com.example.fightingflow.util.emptyMove
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
    val id: Int,
    val comboId: String,
    val character: String,
    val damage: Int,
    val createdBy: String,
    val areOptionsRevealed: Boolean = false,
    val moves: List<MoveEntry>
)

fun ComboEntry.toDisplay(): ComboDisplay =
    ComboDisplay(
        id = id,
        comboId = comboId,
        character = character.name,
        damage = damage,
        createdBy = createdBy,
        areOptionsRevealed = false,
        moves = moveListToMoveEntry(moves)
    )

fun ComboDisplay.toEntry(character: CharacterEntry): ComboEntry =
    ComboEntry(
        id = 0,
        comboId = comboId.let { UUID.randomUUID().toString() },
        character = character,
        damage = damage,
        createdBy = createdBy,
        moves = moveEntryToMoveList(moves)
    )

fun moveListToMoveEntry(moveList: String): List<MoveEntry> {
    val moveEntryList = mutableListOf<MoveEntry>()
    moveList.split(",").map { it.trimIndent() }.forEach { moveEntryList.add(emptyMove.copy(moveName = it)) }
    return moveEntryList
}

fun moveEntryToMoveList(moveList: List<MoveEntry>): String {
    var moveString = ""
    val listIterator = moveList.iterator()
    while (listIterator.hasNext()) {
        val nextMove = listIterator.next().moveName
        moveString += nextMove
        if (listIterator.hasNext()) {
            moveString += ", "
        }
    }
    return moveString
}

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