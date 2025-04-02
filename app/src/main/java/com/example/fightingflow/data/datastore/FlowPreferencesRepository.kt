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
import com.example.fightingflow.data.datastore.PreferencesRepository.Companion.IS_USER_LOGGED_IN
import com.example.fightingflow.data.datastore.PreferencesRepository.Companion.TAG
import com.example.fightingflow.data.datastore.PreferencesRepository.Companion.USERNAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

interface PreferencesRepository {

    fun isUserLoggedIn(): Flow<Boolean>
    fun getUsername(): Flow<String>
    suspend fun loginUser(isUserLoggedIn: Boolean)
    suspend fun setUsername(username: String)

    companion object {
        const val TAG = "FlowPreferencesRepo"
        val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
        val USERNAME = stringPreferencesKey("username")
    }

}

class FlowPreferencesRepository(private val dataStore: DataStore<Preferences>): PreferencesRepository {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun isUserLoggedIn(): Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_USER_LOGGED_IN] != false
        }

    override fun getUsername(): Flow<String> = dataStore.data.map { preference ->
        preference[USERNAME] ?: "Invalid User"
    }

    override suspend fun setUsername(username: String) {
        dataStore.edit { preference ->
            preference[USERNAME] = username
        }
    }

    override suspend fun loginUser (isUserLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_USER_LOGGED_IN] = isUserLoggedIn
        }
    }
}