package com.example.fightingflow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.datastore.UserDsRepository
import com.example.fightingflow.data.firebase.FirebaseRepository
import com.example.fightingflow.data.firebase.UserFbResult
import com.example.fightingflow.model.UserEntry
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import timber.log.Timber

class UserViewModel(
    private val userDsRepository: UserDsRepository,
    private val firebaseRepository: FirebaseRepository,
): ViewModel() {

    companion object {
        private const val TIME_MILLIS = 5_000L
    }

    val username = userDsRepository.getUsername()
        .mapNotNull { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = ""
        )

    // Update Profile State
    val userDetailsState = MutableStateFlow<UserDetailsState>(UserDetailsState.Idle)
    val newUserState = MutableStateFlow<UserEntry?>(null)

    fun updateNewUserState(user: UserEntry) {
        newUserState.update { user }
    }

    suspend fun getUserFromFb(userId: String) {
        Timber.d("--VM: Getting user details from firestore...--")
        userDetailsState.update { UserDetailsState.Loading }
        try {
            Timber.d("Attempting to get user details from firestore...")
            firebaseRepository.getUserDetailsById(userId)
                .collect { userEntry ->
                    userDetailsState.update {
                        Timber.d("Updating userState: ${userDetailsState.value}")
                        if (userEntry != null) {
                            UserDetailsState.Loaded(userEntry)
                        } else {
                            UserDetailsState.NotFound
                        }
                    }
                }
            Timber.d("User details updated.")
        } catch (e: Exception) {
            if (e is CancellationException) {
                Timber.w("Collection was cancelled.")
                throw e
            }
            Timber.e(e, "Error occurred during state collection.")
            userDetailsState.update { UserDetailsState.Error(e) }
        }
    }

    suspend fun createUser(): UserSaveResult {
        Timber.d("-- Preparing to save profile to database --")
        Timber.d("User Entry: $userDetailsState")
        if (newUserState.value?.username?.isBlank() == true) {
            return UserSaveResult.Error(Exception("No username entered."))
        }

        try {
            Timber.d("Creating profile in firestore")
            firebaseRepository.addUserToStore(newUserState.value!!)

            Timber.d("Updating data in datastore from ViewModel...")
            Timber.d("UserState: ${newUserState.value}")

            updateUsernameInDs(newUserState.value!!.username)
            Timber.d("Profile added to datastore.")

            newUserState.update { UserEntry() }

            return UserSaveResult.Success
        } catch (e: Exception) {
            return UserSaveResult.Error(e)
        }
    }

    fun deleteUser(userId: String): UserFbResult {
        Timber.d("--Preparing to delete user details--")
        try {
            val result = firebaseRepository.deleteUser(userId)
            return when (result) {
                is UserFbResult.Success -> {
                    result
                }
                is UserFbResult.Error -> {
                    result
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error deleting user details.")
            return UserFbResult.Error(e)
        }
    }

    // Datastore Functions
    suspend fun updateUsernameInDs(username: String?) { userDsRepository.updateUsername(username) }
}

sealed class UserSaveResult {
    data object Success: UserSaveResult()
    data class Error(val e: Exception): UserSaveResult()
}

sealed class UserDetailsState {
    object Idle: UserDetailsState()
    object Loading: UserDetailsState()
    data class Loaded(val user: UserEntry): UserDetailsState()
    object NotFound: UserDetailsState()
    data class Error(val e: Exception): UserDetailsState()
}