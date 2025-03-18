package com.example.fightingflow.data.database

import com.example.fightingflow.data.database.dao.UserDao
import com.example.fightingflow.model.UserEntry
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    fun getUser(username: String, password: String): Flow<UserEntry>

    suspend fun insert(user: UserEntry)

    suspend fun update(user: UserEntry)

    suspend fun delete(user: UserEntry)
}

class OfflineUserDataRepository(private val userDao: UserDao): UserDataRepository {
    override fun getUser(username: String, password: String): Flow<UserEntry> =
        userDao.getUser(username, password)

    override suspend fun insert(user: UserEntry) =
        userDao.insert(user)

    override suspend fun update(user: UserEntry) =
        userDao.update(user)

    override suspend fun delete(user: UserEntry) =
        userDao.delete(user)

}