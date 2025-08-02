package com.example.fightingflow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.datastore.UserDsRepository
import com.example.fightingflow.data.firebase.FirebaseRepository
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.example.fightingflow.data.firebase.UserFbResult
import com.example.fightingflow.model.UserDataForCombos
import com.example.fightingflow.model.UserEntry
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    val userDataMap = firebaseRepository.getUserMap()
        .mapNotNull { userData ->
            Timber.d("UserData: $userData")
            userData
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(30_000L),
            initialValue = UserDataForCombos(emptyMap())
        )

    // Update Profile State
    var userIdState = MutableStateFlow("")
    val newUserState = MutableStateFlow<UserEntry?>(null)
    val currentUserState = MutableStateFlow<GoogleAuthService.SignInState>(GoogleAuthService.SignInState.Idle)

    fun updateNewUserState(user: UserEntry?) { newUserState.update { user } }
    fun updateCurrentUser(user: GoogleAuthService.SignInState) { currentUserState.update { user } }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userDetailsState = currentUserState.flatMapLatest { currentUser ->
        Timber.d("--Getting User Details from Firestore Database")
        Timber.d("Current User: $currentUser")
        when (currentUser) {
            is GoogleAuthService.SignInState.Success -> {
                userIdState.flatMapLatest { userId ->
                    Timber.d("Valid User ID detected, creating User Details flow.")
                    try {
                        firebaseRepository.getUserDetailsById(userIdState.value)
                            .map { userEntry ->
                                if (userEntry != null) {
                                    Timber.d("User Entry: $userEntry")
                                    updateUsernameInDs(userEntry.username)
                                    UserDetailsState.Loaded(userEntry)
                                } else {
                                    Timber.d("Mapping null entry to Not Found State")
                                    UserDetailsState.NotFound
                                }
                            }
                            .onStart {
                                Timber.d("Flow initiated, mapping Loading State"); emit(
                                UserDetailsState.Loading
                            )
                            }
                    } catch (e: Exception) {
                        if (e is CancellationException) {
                            Timber.w("User Details flow cancelled for ${userIdState.value}")
                            throw e
                        }
                        flowOf(UserDetailsState.Error(e))
                    }
                }
            }
            else -> {
                Timber.d("User not signed in, returning Idle state")
                flowOf(UserDetailsState.Idle)
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = UserDetailsState.Idle
        )

    fun updateUserId(userId: String) {
        Timber.d("--Updating user ID--")
        userIdState.update { userId }
        Timber.d("UserId: ${userIdState.value}")
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