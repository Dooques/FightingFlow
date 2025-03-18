package com.example.fightingflow.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.fightingflow.model.UserEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao:BaseDao<UserEntry> {
    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun getUser(username: String, password:String): Flow<UserEntry>

    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<UserEntry>>
}