package com.dooques.fightingflow.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dooques.fightingflow.data.datastore.UserDsRepository
import com.dooques.fightingflow.data.firebase.FirebaseUserRepository
import com.dooques.fightingflow.data.firebase.GoogleAuthService
import com.dooques.fightingflow.model.UserDataForCombos
import com.dooques.fightingflow.model.UserEntry
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
    private val firebaseUserRepository: FirebaseUserRepository,
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

    val userDataMap = firebaseUserRepository.getUserMap()
        .mapNotNull { userData ->
            Timber.d(" UserData: $userData")
            userData
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = UserDataForCombos(emptyMap())
        )

    val emailSignInErrorState = getEmailErrorState()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = false
        )

    val googleSignInErrorState = getGoogleErrorState()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = false
        )

    // Update Profile State
    var userIdState = MutableStateFlow("")
    val newUserDetailsState = MutableStateFlow<UserEntry?>(null)
    val currentUserState = MutableStateFlow<GoogleAuthService.SignInState>(GoogleAuthService.SignInState.Idle)

    fun updateNewUserState(user: UserEntry?) { newUserDetailsState.update { user } }
    fun updateCurrentUser(user: GoogleAuthService.SignInState) { currentUserState.update { user } }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userDetailsState = currentUserState.flatMapLatest { currentUser ->
        Timber.d("--Getting User Details from Firestore Database--")
        Timber.d(" Current User: $currentUser")
        when (currentUser) {
            is GoogleAuthService.SignInState.Success -> {
                userIdState.flatMapLatest {
                    Timber.d(" Valid User ID detected, creating User Details flow.")
                    try {
                        this@UserViewModel.firebaseUserRepository.getUserDetailsById(userIdState.value)
                            .map { userEntry ->
                                if (userEntry != null) {
                                    Timber.d(" User Entry: $userEntry")
                                    updateUsernameInDs(userEntry.username)
                                    UserDetailsState.Loaded(userEntry)
                                } else {
                                    Timber.d(" Mapping null entry to Not Found State")
                                    UserDetailsState.NotFound
                                }
                            }
                            .onStart {
                                Timber.d(" Flow initiated, mapping Loading State"); emit(
                                UserDetailsState.Loading
                            )
                            }
                    } catch (e: Exception) {
                        if (e is CancellationException) {
                            Timber.w(" User Details flow cancelled for ${userIdState.value}")
                            throw e
                        }
                        flowOf(UserDetailsState.Error(e))
                    }
                }
            }
            else -> {
                Timber.d(" User not signed in, returning Idle state")
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
        Timber.d(" UserId: ${userIdState.value}")
    }

    suspend fun createUser(userDataToSave: UserEntry?): Result<Unit> {
        Timber.d("--Preparing to save profile to database--")
        Timber.d(" User Entry: $userDataToSave")

        if (userDataToSave != null && userDataToSave.username.isBlank()) {
            return Result.failure(Exception("No username entered."))
        }

        return try {
            Timber.d(" Creating profile in firestore")
            if (userDataToSave != null) {
                firebaseUserRepository.addUserToStore(userDataToSave)

                Timber.d(" Updating data in datastore from ViewModel...\n UserState: %s", userDataToSave)

                updateUsernameInDs(userDataToSave.username)
                Timber.d(" Profile added to datastore.")

               Result.success(Unit)
            } else {
                throw Exception("User Details State is null")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteUser(userId: String): Result<Unit> {
        Timber.d("--Preparing to delete user details--")
        try {
            return firebaseUserRepository.deleteUser(userId)
        } catch (e: Exception) {
            Timber.e(e, " Error deleting user details.")
            return Result.failure(e)
        }
    }

    // Datastore Functions
    suspend fun updateUsernameInDs(username: String?) { userDsRepository.updateUsername(username) }

    suspend fun updateGoogleErrorState(boolean: Boolean) { userDsRepository.updateGoogleErrorState(boolean) }

    suspend fun updateEmailErrorState(boolean: Boolean) { userDsRepository.updateEmailErrorState(boolean) }

    fun getEmailErrorState() = userDsRepository.getEmailErrorState().map { it }

    fun getGoogleErrorState() = userDsRepository.getGoogleErrorState().map { it }

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