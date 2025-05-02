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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
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
        .catch { e ->
            if (e is IOException) {
                Timber.e(e, "Error reading preferences")
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { preferences ->
            preferences[IS_USER_LOGGED_IN] == true
        }

    override fun getUsername(): Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Timber.e(it, "Error reading preferences")
            } else {
                throw it
            }
        }
        .map { preference ->
            Timber.d("")
            Timber.d("Returning username from datastore...")
            Timber.d("Username: ${preference[USERNAME]}")
            preference[USERNAME] ?: "Invalid User"
    }

    override suspend fun updateUsername(username: String) {
        Timber.d("")
        Timber.d( "Saving username: $username")
        dataStore.edit { preference ->
            preference[USERNAME] = username
        }
        Timber.d("Username stored in datastore.")
    }

    override suspend fun updateLoggedInState (isUserLoggedIn: Boolean) {
        Timber.d( "")
        Timber.d("Updating logged in state from datastore...")
        dataStore.edit { preferences ->
            preferences[IS_USER_LOGGED_IN] = isUserLoggedIn
        }
    }
}