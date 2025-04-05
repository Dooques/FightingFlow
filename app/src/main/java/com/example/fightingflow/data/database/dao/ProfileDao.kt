package com.example.fightingflow.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.fightingflow.model.ProfileEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao:BaseDao<ProfileEntry> {
    @Query("SELECT * FROM profile")
    fun getProfile(): Flow<ProfileEntry>
}