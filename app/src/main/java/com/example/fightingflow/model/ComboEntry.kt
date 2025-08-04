package com.example.fightingflow.model

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.fightingflow.util.ImmutableList
import com.example.fightingflow.util.MoveEntryListUiState
import com.google.firebase.firestore.PropertyName
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber
import java.util.UUID

@Entity(tableName = "combo_table")
data class ComboEntry (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val character: String = "",
    val damage: Int = 0,
    @ColumnInfo(name = "created_by")
    val createdBy: String = "",
    @ColumnInfo(name = "date_created")
    val dateCreated: String = "",
    val difficulty: Float = 0f,
    val likes: Int = 0,
    val tags: String? = null,
    val private: Boolean = false,
    val moves: String = "",
)

data class ComboEntryFb(
    val comboId: String = "",
    val title: String = "",
    val character: String = "",
    val damage: Int = 0,
    @get:PropertyName("created_by")
    @set:PropertyName("created_by")
    var createdBy: String = "",
    @get:PropertyName("date_created")
    @set:PropertyName("date_created")
    var dateCreated: String = "",
    val difficulty: Float = 0f,
    val likes: Int = 0,
    val tags: String? = null,
    val private: Boolean = false,
    val moves: List<String> = listOf(),
)

@Immutable
data class ComboDisplay(
    val id: String = "",
    val title: String = "",
    val character: String = "",
    val damage: Int = 0,
    val createdBy: String = "",
    val dateCreated: String = "",
    val areOptionsRevealed: Boolean = false,
    val pinned: Boolean = false,
    val difficulty: Float = 0f,
    val likes: Int = 0,
    val tags: String? = null,
    val private: Boolean = false,
    val moves: List<MoveEntry> = listOf()
)

fun ComboEntry.toDisplay(moveEntryList: MoveEntryListUiState): ComboDisplay =
    ComboDisplay(
        id = id,
        title = title,
        character = character,
        damage = damage,
        createdBy = createdBy,
        dateCreated = dateCreated,
        difficulty = difficulty,
        likes = likes,
        tags = tags,
        moves = ImmutableList(moveListStringToMoveEntryList(moves, moveEntryList))
    )

fun ComboDisplay.toEntry(character: CharacterEntry): ComboEntry =
    ComboEntry(
        id = id,
        title = title,
        character = character.name,
        damage = damage,
        createdBy = createdBy,
        dateCreated = dateCreated,
        difficulty = difficulty,
        likes = likes,
        tags = tags,
        moves = moveEntryListToMoveListString(moves)
    )

fun ComboDisplay.toFbEntry(character: CharacterEntry): ComboEntryFb {
    val movesList = mutableListOf<String>()
    moves.forEach { movesList.add(it.notation) }
    return ComboEntryFb(
        comboId = id,
        title = title,
        character = character.name,
        damage = damage,
        createdBy = createdBy,
        dateCreated = dateCreated,
        difficulty = difficulty,
        likes = likes,
        tags = tags,
        private = private,
        moves = ImmutableList(movesList.toList())
    )
}

fun ComboEntryFb.toDisplay(moveEntryListUiState: MoveEntryListUiState): ComboDisplay =
    ComboDisplay(
        id = comboId,
        title = title,
        character = character,
        damage = damage,
        createdBy = createdBy,
        dateCreated = dateCreated,
        areOptionsRevealed = false,
        pinned = false,
        difficulty = difficulty,
        likes = likes,
        tags = tags,
        private = private,
        moves = ImmutableList(moves.map { move ->
            moveEntryListUiState.moveList.first { it.notation == move }
        })
    )

fun ComboEntryFb.toHashMap(): HashMap<String, Any> =
    hashMapOf(
        "title" to title,
        "character" to character,
        "damage" to damage,
        "created_by" to createdBy,
        "date_created" to dateCreated,
        "difficulty" to difficulty,
        "likes" to likes,
        "tags" to tags.let { "" },
        "moves" to moves
    )

fun moveListStringToMoveEntryList(moveList: String, moveListEntries: MoveEntryListUiState): List<MoveEntry> {
    val moveEntryList = mutableListOf<MoveEntry>()
    moveList
        .split(",")
        .map { move -> move.trimIndent() }
        .forEach { move -> moveEntryList.add(moveListEntries.moveList.first { it.moveName == move }) }
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
): ComboDisplay {
    Timber.d("Processing moveList for ${combo.id}")
    val updatedCombo = combo.copy(
        moves = ImmutableList(
            combo.moves.map { move ->
                Timber.d("Move: $move")
                val updateData = moveEntryList.moveList.first { it.moveName == move.moveName }
                Timber.d("MoveToAdd: ${moveEntryList.moveList.first { it.moveName == move.moveName }}")
                MoveEntry(
                    id = updateData.id,
                    moveName = updateData.moveName,
                    notation = updateData.notation,
                    moveType = updateData.moveType,
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
    fun stringToCharacter(json: String?) =
        json?.let { jsonAdapter.fromJson(json) }
}