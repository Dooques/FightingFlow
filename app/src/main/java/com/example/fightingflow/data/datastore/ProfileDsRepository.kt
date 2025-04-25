package com.example.fightingflow.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fightingflow.data.datastore.ProfileDsRepository.Companion.IS_USER_LOGGED_IN
import com.example.fightingflow.data.datastore.ProfileDsRepository.Companion.USERNAME
import com.example.fightingflow.util.PROFILE_DS_REPO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

interface ProfileDsRepository {

    fun profileLoggedInState(): Flow<Boolean>
    fun getUsername(): Flow<String>

    suspend fun updateLoggedInState(isUserLoggedIn: Boolean)
    suspend fun updateUsername(username: String)

    companion object {
        val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
        val USERNAME = stringPreferencesKey("username")
    }
}

class ProfileDatastoreRepository(private val dataStore: DataStore<Preferences>): ProfileDsRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun profileLoggedInState(): Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(PROFILE_DS_REPO, "Error reading preferences", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_USER_LOGGED_IN] == true
        }

    override fun getUsername(): Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(PROFILE_DS_REPO, "Error reading preferences", it)
            } else {
                throw it
            }
        }
        .map { preference ->
            Log.d(PROFILE_DS_REPO, "")
            Log.d(PROFILE_DS_REPO, "Returning username from datastore...")
            Log.d(PROFILE_DS_REPO, "Username: ${preference[USERNAME]}")
            preference[USERNAME] ?: "Invalid User"
    }

    override suspend fun updateUsername(username: String) {
        Log.d(PROFILE_DS_REPO, "")
        Log.d(PROFILE_DS_REPO, "Saving username: $username")
        dataStore.edit { preference ->
            preference[USERNAME] = username
        }
        Log.d(PROFILE_DS_REPO, "Username stored in datastore.")
    }

    override suspend fun updateLoggedInState (isUserLoggedIn: Boolean) {
        Log.d(PROFILE_DS_REPO, "")
        Log.d(PROFILE_DS_REPO, "Updating logged in state from datastore...")
        dataStore.edit { preferences ->
            preferences[IS_USER_LOGGED_IN] = isUserLoggedIn
        }
    }
}