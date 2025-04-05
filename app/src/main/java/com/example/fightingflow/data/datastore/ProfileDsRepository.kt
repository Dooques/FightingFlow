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
import com.example.fightingflow.data.datastore.ProfileDsRepository.Companion.PASSWORD
import com.example.fightingflow.data.datastore.ProfileDsRepository.Companion.PROFILE_PIC
import com.example.fightingflow.data.datastore.ProfileDsRepository.Companion.TAG
import com.example.fightingflow.data.datastore.ProfileDsRepository.Companion.USERNAME
import com.example.fightingflow.util.ProfileUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

interface ProfileDsRepository {

    fun isProfileLoggedIn(): Flow<Boolean>
    fun getUsername(): Flow<String>
    fun getPassword(): Flow<String>
    fun getProfilePic(): Flow<String>

    suspend fun updateLoggedInState(isUserLoggedIn: Boolean)
    suspend fun setProfileDetails(profileUiState: ProfileUiState)

    companion object {
        const val TAG = "FlowPreferencesRepo"
        val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
        val USERNAME = stringPreferencesKey("username")
        val PASSWORD = stringPreferencesKey("password")
        val PROFILE_PIC = stringPreferencesKey("profile_pic")
    }

}

class ProfileDatastoreRepository(private val dataStore: DataStore<Preferences>): ProfileDsRepository {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun isProfileLoggedIn(): Flow<Boolean> = dataStore.data
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

    override fun getPassword(): Flow<String> = dataStore.data.map { preference ->
        preference[PASSWORD] ?: "Invalid Password"
    }

    override fun getProfilePic(): Flow<String> = dataStore.data.map { preference ->
        preference[PROFILE_PIC] ?: "Invalid Profile Pic"
    }

    override suspend fun setProfileDetails(profileUiState: ProfileUiState) {
        Log.d(TAG, "")
        Log.d(TAG, "Preparing to save data to the datastore...")
        Log.d(TAG, "Saving username: ${profileUiState.profile.username}")
        dataStore.edit { preference ->
            preference[USERNAME] = profileUiState.profile.username
        }
        Log.d(TAG, "Saving password: ${profileUiState.profile.password}")
        dataStore.edit { preference ->
            preference[PASSWORD] = profileUiState.profile.password
        }
        Log.d(TAG, "Saving profile image: ${profileUiState.profile.profilePic}")
        dataStore.edit { preference ->
            preference[PROFILE_PIC] = profileUiState.profile.profilePic
        }
        Log.d(TAG, "Profile stored in datastore.")
    }

    override suspend fun updateLoggedInState (isUserLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_USER_LOGGED_IN] = isUserLoggedIn
        }
    }
}