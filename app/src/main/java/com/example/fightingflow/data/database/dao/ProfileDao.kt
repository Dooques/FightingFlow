package com.example.fightingflow.data.database.dao

import android.provider.ContactsContract
import androidx.room.Dao
import androidx.room.Query
import com.example.fightingflow.model.ProfileEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao:BaseDao<ProfileEntry> {
    @Query("SELECT * FROM profile where username = :username")
    fun getProfile(username: String): Flow<ProfileEntry?>

    @Query("select * from profile")
    fun getAllProfiles(): Flow<List<ProfileEntry>>
}