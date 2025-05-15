package com.example.fightingflow.model

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.fightingflow.util.ImmutableList
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.emptyMove
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber
import java.time.LocalDate
import java.util.UUID
import kotlin.String

@Entity(tableName = "combo_table")
data class ComboEntry (
    @PrimaryKey
    @ColumnInfo(name = "combo_id")
    val comboId: String = UUID.randomUUID().toString(),
    val character: CharacterEntry,
    val damage: Int,
    @ColumnInfo(name = "created_by")
    val createdBy: String,
    val dateCreated: String,
    val moves: String
)

@Immutable
data class ComboDisplay(
    val comboId: String,
    val character: String,
    val damage: Int,
    val createdBy: String,
    val dateCreated: String,
    val areOptionsRevealed: Boolean = false,
    val moves: ImmutableList<MoveEntry>
)

fun ComboEntry.toDisplay(): ComboDisplay =
    ComboDisplay(
        comboId = comboId,
        character = character.name,
        damage = damage,
        createdBy = createdBy,
        dateCreated = dateCreated,
        areOptionsRevealed = false,
        moves = ImmutableList(moveListStringToMoveEntryList(moves))
    )

fun ComboDisplay.toEntry(character: CharacterEntry): ComboEntry =
    ComboEntry(
        comboId = comboId,
        character = character,
        damage = damage,
        createdBy = createdBy,
        dateCreated = dateCreated,
        moves = moveEntryToMoveList(moves)
    )

fun moveListStringToMoveEntryList(moveList: String): List<MoveEntry> {
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
fun getMoveEntryDataForComboDisplay(combo: ComboDisplay, moveEntryList: MoveEntryListUiState): ComboDisplay {
    Timber.d("Processing moveList for ${combo.comboId}")
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
                    associatedCharacter = updateData.associatedCharacter
                )
            }
        )
    )
    Timber.d("Move List completed for ${combo.comboId} and returning to UI")
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