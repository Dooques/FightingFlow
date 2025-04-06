package com.example.fightingflow.data.database

import com.example.fightingflow.data.database.dao.ProfileDao
import com.example.fightingflow.model.ProfileEntry
import kotlinx.coroutines.flow.Flow

interface ProfileDbRepository {

    fun getProfile(username: String): Flow<ProfileEntry>

    fun getAllProfiles(): Flow<List<ProfileEntry>>

    suspend fun insert(profile: ProfileEntry)

    suspend fun update(profile: ProfileEntry)

    suspend fun delete(profile: ProfileEntry)
}

class ProfileDatabaseRepository(private val profileDao: ProfileDao): ProfileDbRepository {
    override fun getProfile(username: String): Flow<ProfileEntry> =
        profileDao.getProfile(username)

    override fun getAllProfiles(): Flow<List<ProfileEntry>> =
        profileDao.getAllProfiles()


    override suspend fun insert(profile: ProfileEntry) =
        profileDao.insert(profile)

    override suspend fun update(profile: ProfileEntry) =
        profileDao.update(profile)

    override suspend fun delete(profile: ProfileEntry) =
        profileDao.delete(profile)

}