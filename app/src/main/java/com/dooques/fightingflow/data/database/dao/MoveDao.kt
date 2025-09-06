package com.dooques.fightingflow.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dooques.fightingflow.model.MoveEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface MoveDao: BaseDao<MoveEntry> {
    @Query("select * from move_table where move_name = :name")
    fun getMove(name: String): Flow<MoveEntry?>

    @Query("select * from move_table")
    fun getAllMoves(): Flow<List<MoveEntry>>

    @Query("Select * from move_table where character = :character")
    fun getAllMovesByCharacter(character: String): Flow<List<MoveEntry>>

    @Query("Select * from move_table where game = :game")
    fun getAllMovesByGame(game: String): Flow<List<MoveEntry>>
}