package com.example.fightingflow.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert
    suspend fun insert(input: T)

    @Insert
    suspend fun insertAll(inputList: List<T>)

    @Update
    suspend fun update(input: T)

    @Delete
    suspend fun delete(input: T)
}