package com.example.fightingflow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.datastore.UserDsRepository
import com.example.fightingflow.data.firebase.FirebaseRepository
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class AuthViewModel(
    private val authRepository: GoogleAuthService,
    private val profileDsRepository: UserDsRepository,
    private val firebaseRepository: FirebaseRepository
): ViewModel() {
    val signInState = MutableStateFlow<GoogleAuthService.SignInState>(GoogleAuthService.SignInState.Idle)

    init {
        Timber.d("--Checking if user logged in--")
        viewModelScope.launch { checkCurrentUser() }
    }

    suspend fun checkCurrentUser() {
        val currentUser = authRepository.getCurrentUser()
        Timber.d("Current User: %s", currentUser)
        if (currentUser != null) {
            signInState.update { GoogleAuthService.SignInState.Success(currentUser) }
        } else {
            signInState.update { GoogleAuthService.SignInState.Idle }
            profileDsRepository.updateUsername("")
            profileDsRepository.updateLoggedInState(false)
        }
    }

    fun initiateGoogleSignIn() {
        viewModelScope.launch {
            signInState.update { GoogleAuthService.SignInState.Loading }
            val resultState = authRepository.signInWithGoogle()
            signInState.update { resultState }
            if (resultState is GoogleAuthService.SignInState.Success) {
                Timber.i("Google Sign-In Successful: ${resultState.user.displayName} is signed in.")
            } else if (resultState is GoogleAuthService.SignInState.Error) {
                Timber.w(resultState.exception, "Google Sign-In Failed. Message: ${resultState.message}")
            }
        }
    }

    suspend fun createAccountWithEmailAndPassword(email: String, password: String): Result<Unit> {
        signInState.update { GoogleAuthService.SignInState.Loading }
        return authRepository.createAccountWithEmailAndPassword(email, password)
            .onSuccess {
                signInState.update { GoogleAuthService.SignInState.Success(authRepository.getCurrentUser() as GoogleAuthService.GoogleSignInResult) }
                Result.success(Unit)
                return@onSuccess
            }
            .onFailure { result ->
                Timber.e(result, "An error occurred during account creation.")
                Result.failure<Exception>(result)
                return@onFailure
            }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit> {
        Timber.d("--Signing in user with Email--")
        signInState.update { GoogleAuthService.SignInState.Loading }
        return authRepository.signInWithEmailAndPassword(email, password)
            .onSuccess { result ->
                Timber.d("Sign in successful")
                signInState.update {
                    Timber.d("Updating sign-in state")
                    val currentUser = authRepository.getCurrentUser()
                    Timber.d("Current User: $currentUser")
                    if (currentUser != null) {
                        Timber.d("User is not null")
                        GoogleAuthService.SignInState.Success(currentUser)
                    } else  {
                        GoogleAuthService.SignInState.Error("User does not exist.")
                    }
                }
                Result.success(Unit)
                return@onSuccess
            }
            .onFailure { result ->
                Timber.e(result, "An error occurred during sign in.")
                signInState.update {
                    GoogleAuthService.SignInState.Error("Error signing in:", result as Exception
                    ) }
                Result.failure<Exception>(result)
                return@onFailure
            }
    }

    fun signOut() {
        viewModelScope.launch {
            signInState.update { GoogleAuthService.SignInState.Loading }
            authRepository.signOut()
                .onSuccess {
                    signInState.update { GoogleAuthService.SignInState.Idle }
                    Timber.Forest.i("Sign out successful.")
                }
                .onFailure { error ->
                    signInState.update {
                        GoogleAuthService.SignInState.Error(
                            "Sign out failed: ${error.message}",
                            error as? Exception
                        )
                    }
                    Timber.e(error, "Sign out failed.")
                }
        }
    }

    suspend fun deleteUser(): Result<Unit>? {
        if (signInState.value is GoogleAuthService.SignInState.Success) {
            val currentUser = signInState.value as GoogleAuthService.SignInState.Success
            val result = authRepository.deleteCurrentUser()
            Timber.d("Attempting to delete: ${currentUser.user}")

            when {
                result.isSuccess -> {
                    profileDsRepository.updateUsername("")
                    profileDsRepository.updateLoggedInState(false)
                    firebaseRepository.deleteUser(currentUser.user.userId)
                    return Result.success(Unit)
                }

                result.isFailure -> {
                    val e = result.exceptionOrNull()
                    return if (e is FirebaseAuthRecentLoginRequiredException) {
                        Timber.e("User needs to re-authenticate.")
                        result
                    } else {
                        Timber.e(e, "Error deleting account.")
                        result
                    }
                }
            }
        }
        return null
    }
}