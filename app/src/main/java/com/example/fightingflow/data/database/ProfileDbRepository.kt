package com.example.fightingflow.data.database

import com.example.fightingflow.data.database.dao.ProfileDao
import com.example.fightingflow.model.ProfileEntry
import kotlinx.coroutines.flow.Flow

interface ProfileDbRepository {

    fun getAllProfiles(): Flow<ProfileEntry>

    suspend fun insert(profile: ProfileEntry)

    suspend fun update(profile: ProfileEntry)

    suspend fun delete(profile: ProfileEntry)
}

class ProfileDatabaseRepository(private val profileDao: ProfileDao): ProfileDbRepository {
    override fun getAllProfiles(): Flow<ProfileEntry> =
        profileDao.getProfile()

    override suspend fun insert(profile: ProfileEntry) =
        profileDao.insert(profile)

    override suspend fun update(profile: ProfileEntry) =
        profileDao.update(profile)

    override suspend fun delete(profile: ProfileEntry) =
        profileDao.delete(profile)

}