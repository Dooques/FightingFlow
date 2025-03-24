package com.example.fightingflow.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.fightingflow.model.MoveEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface MoveDao: BaseDao<MoveEntry> {
    @Query("select * from move_table where move_name = :name")
    fun getMove(name: String): Flow<MoveEntry>

    @Query("select * from move_table")
    fun getAllMoves(): Flow<List<MoveEntry>>
}