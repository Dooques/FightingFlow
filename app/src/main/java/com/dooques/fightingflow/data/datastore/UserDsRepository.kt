package com.dooques.fightingflow.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dooques.fightingflow.data.datastore.UserDsRepository.Companion.IS_USER_LOGGED_IN
import com.dooques.fightingflow.data.datastore.UserDsRepository.Companion.USERNAME
import com.dooques.fightingflow.data.datastore.UserDsRepository.Companion.emailSignInError
import com.dooques.fightingflow.data.datastore.UserDsRepository.Companion.googleSignInError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException

interface UserDsRepository {

    fun profileLoggedInState(): Flow<Boolean>
    fun getUsername(): Flow<String>
    suspend fun updateEmailErrorState(boolean: Boolean)
    suspend fun updateGoogleErrorState(boolean: Boolean)

    suspend fun updateLoggedInState(isUserLoggedIn: Boolean)
    suspend fun updateUsername(username: String?)
    fun getEmailErrorState(): Flow<Boolean>
    fun getGoogleErrorState(): Flow<Boolean>

    companion object {
        val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
        val USERNAME = stringPreferencesKey("username")
        val googleSignInError = booleanPreferencesKey("google_sign_in_error")
        val emailSignInError = booleanPreferencesKey("email_sign_in_error")
    }
}

class ProfileDatastoreRepository(private val dataStore: DataStore<Preferences>): UserDsRepository {

    override fun profileLoggedInState(): Flow<Boolean> = dataStore.data
        .catch { e ->
            if (e is IOException) {
                Timber.e(e, " Error reading preferences")
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
                Timber.e(it, " Error reading preferences")
            } else {
                throw it
            }
        }
        .map { preference ->
            Timber.d(" Returning username from datastore \nUsername: %s", preference[USERNAME])
            preference[USERNAME] ?: "Invalid User"
    }

    override suspend fun updateEmailErrorState(boolean: Boolean) {
        dataStore.edit { preference ->
            preference[emailSignInError] = boolean
        }
    }

    override suspend fun updateGoogleErrorState(boolean: Boolean) {
        dataStore.edit { preference ->
            preference[googleSignInError] = boolean
        }
    }

    override suspend fun updateUsername(username: String?) {
        Timber.d( " Saving username: $username")
        dataStore.edit { preference ->
            preference[USERNAME] = username ?: ""
        }
        Timber.d("Username stored in datastore.")
    }

    override suspend fun updateLoggedInState (isUserLoggedIn: Boolean) {
        Timber.d(" Updating logged in state from datastore...")
        dataStore.edit { preferences ->
            preferences[IS_USER_LOGGED_IN] = isUserLoggedIn
        }
    }

    override fun getEmailErrorState() = dataStore.data
        .map { preference ->
            preference[emailSignInError] ?: false
        }

    override fun getGoogleErrorState() = dataStore.data
        .map { preference ->
            preference[googleSignInError] ?: false
        }
}