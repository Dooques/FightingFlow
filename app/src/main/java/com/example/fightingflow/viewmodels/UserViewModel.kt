package com.example.fightingflow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fightingflow.data.datastore.UserDsRepository
import com.example.fightingflow.data.firebase.FirebaseRepository
import com.example.fightingflow.data.firebase.UserFbResult
import com.example.fightingflow.model.UserEntry
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart
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
    var userIdState = MutableStateFlow<String?>(null)
    val newUserState = MutableStateFlow<UserEntry?>(null)

    fun updateNewUserState(user: UserEntry?) {
        newUserState.update { user }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userDetailsState = userIdState.flatMapLatest { userId ->
        if (userId.isNullOrBlank()) {
            Timber.d("User ID is null. Emiting idle state")
            flowOf(UserDetailsState.Idle)
        } else {
            Timber.d("Valid User ID detected, creating User Details flow.")
            firebaseRepository.getUserDetailsById(userId)
                .map { userEntry ->
                    if (userEntry != null) {
                        Timber.d("Mapping user entry to Loaded State")
                        UserDetailsState.Loaded(userEntry)
                    } else {
                        Timber.d("Mapping null entry to Not Found State")
                        UserDetailsState.NotFound
                    }
                }
                .onStart { Timber.d("Flow initiated, mapping Loading State"); emit(UserDetailsState.Loading) }
                .catch { e ->
                    if (e is CancellationException) {
                        Timber.w("User Details flow cancelled for $userId")
                        throw e
                    }
                    emit(UserDetailsState.Error(e as Exception))
                }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = UserDetailsState.Idle
    )

    fun getUserDetailsFromFb(userId: String) {
        Timber.d("--VM: Getting user details from firestore...--")
        Timber.d("Attempting to get user details from firestore...")
        userIdState.value = userId
    }

    suspend fun createUser(): UserSaveResult {
        Timber.d("-- Preparing to save profile to database --")
        Timber.d("User Entry: $newUserState")
        if (newUserState.value?.username?.isBlank() == true) {
            return UserSaveResult.Error(Exception("No username entered."))
        }

        try {
            Timber.d("Creating profile in firestore")
            Timber.d("User Details: ${newUserState.value}")
            firebaseRepository.addUserToStore(newUserState.value!!)

            Timber.d("Updating data in datastore from ViewModel...\nUserState: %s", newUserState.value)

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
            return firebaseRepository.deleteUser(userId)
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