package com.example.fightingflow.model

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.fightingflow.util.ImmutableList
import com.example.fightingflow.util.MoveEntryListUiState
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber

@Entity(tableName = "combo_table")
data class ComboEntry (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    val description: String,
    val character: CharacterEntry,
    val damage: Int,
    @ColumnInfo(name = "created_by")
    val createdBy: String,
    val dateCreated: String,
    val moves: String,
)

@Immutable
data class ComboDisplay(
    val id: Int,
    val description: String,
    val character: String,
    val damage: Int,
    val createdBy: String,
    val dateCreated: String,
    val areOptionsRevealed: Boolean = false,
    val pinned: Boolean = false,
    val moves: ImmutableList<MoveEntry>
)

fun ComboEntry.toDisplay(moveEntryList: MoveEntryListUiState): ComboDisplay =
    ComboDisplay(
        id = id,
        description = description,
        character = character.name,
        damage = damage,
        createdBy = createdBy,
        dateCreated = dateCreated,
        areOptionsRevealed = false,
        moves = ImmutableList(moveListStringToMoveEntryList(moves, moveEntryList))
    )

fun ComboDisplay.toEntry(character: CharacterEntry): ComboEntry =
    ComboEntry(
        id = id,
        description = description,
        character = character,
        damage = damage,
        createdBy = createdBy,
        dateCreated = dateCreated,
        moves = moveEntryListToMoveListString(moves)
    )

fun moveListStringToMoveEntryList(moveList: String, moveListEntries: MoveEntryListUiState): List<MoveEntry> {
    val moveEntryList = mutableListOf<MoveEntry>()
    moveList
        .split(",")
        .map { move -> move.trimIndent() }
        .forEach { move -> moveEntryList.add(moveListEntries.moveList.first { it.notation == move }) }
    return moveEntryList
}

fun moveEntryListToMoveListString(moveList: List<MoveEntry>): String {
    var moveString = ""
    val listIterator = moveList.iterator()
    while (listIterator.hasNext()) {
        val nextMove = listIterator.next().notation
        moveString += nextMove
        if (listIterator.hasNext()) {
            moveString += ", "
        }
    }
    return moveString
}
fun getMoveEntryDataForComboDisplay(
    combo: ComboDisplay,
    moveEntryList: MoveEntryListUiState,
    controlType: Console
): ComboDisplay {
    Timber.d("Processing moveList for ${combo.id}")
    val updatedCombo = combo.copy(
        moves = ImmutableList(
            combo.moves.map { move ->
                val updateData = moveEntryList.moveList.first { it.moveName == move.moveName }
                MoveEntry(
                    id = updateData.id,
                    moveName = updateData.moveName,
                    notation = updateData.notation,
                    moveType = updateData.moveType,
                    counterHit = updateData.counterHit,
                    hold = updateData.hold,
                    justFrame = updateData.justFrame,
                    character = updateData.character,
                    game = updateData.game
                )
            }
        )
    )
    Timber.d("Move List completed for ${combo.id} and returning to UI")
    return updatedCombo
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
    fun stringToCharacter(json: kotlin.String?) =
        json?.let { jsonAdapter.fromJson(json) }
}