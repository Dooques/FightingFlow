package com.dooques.fightingflow.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dooques.fightingflow.data.datastore.UserDsRepository
import com.dooques.fightingflow.data.firebase.FirebaseUserRepository
import com.dooques.fightingflow.data.firebase.GoogleAuthService
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class AuthViewModel(
    private val authRepository: GoogleAuthService,
    private val profileDsRepository: UserDsRepository,
): ViewModel() {
    val signInState =
        MutableStateFlow<GoogleAuthService.SignInState>(GoogleAuthService.SignInState.Idle)

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

    suspend fun initiateGoogleSignIn(): Result<Unit> {
        signInState.update { GoogleAuthService.SignInState.Loading }
        val resultState = authRepository.signInWithGoogle()
        signInState.update { resultState }
        return when (resultState) {

            is GoogleAuthService.SignInState.Success -> {
                Timber.i(" Google Sign-In Successful: ${resultState.user.displayName} is signed in.")
                Result.success(Unit)
            }

            is GoogleAuthService.SignInState.Error -> {
                if (resultState.exception != null) {
                    Timber.w(
                        resultState.exception,
                        " Google Sign-In Failed. Message: ${resultState.message}"
                    )
                    Result.failure(resultState.exception)
                } else {
                    Result.failure(Exception("Exception state is null"))
                }
            }

            else -> {
                Result.failure(Exception("Unknown Error occurred"))
            }
        }
    }

    suspend fun createAccountWithEmailAndPassword(email: String, password: String): Result<Unit> {
        signInState.update { GoogleAuthService.SignInState.Loading }
        return authRepository.createAccountWithEmailAndPassword(email, password)
            .onSuccess {
                Timber.d(" User creation successful, updating SignInState to success")
                signInState.update { GoogleAuthService.SignInState.Success(authRepository.getCurrentUser() as GoogleAuthService.GoogleSignInResult) }
                Result.success(Unit)
                return@onSuccess
            }
            .onFailure { result ->
                Timber.e(result, " An error occurred during account creation.")
                signInState.update {
                    GoogleAuthService.SignInState.Error(
                        result.message.toString(),
                        result as Exception
                    )
                }
                Result.failure<Exception>(result)
                return@onFailure
            }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit> {
        Timber.d("--Signing in user with Email--")
        signInState.update { GoogleAuthService.SignInState.Loading }
        return authRepository.signInWithEmailAndPassword(email, password)
            .onSuccess {
                Timber.d(" Sign in successful")
                signInState.update {
                    Timber.d(" Updating sign-in state")
                    val currentUser = authRepository.getCurrentUser()
                    Timber.d(" Current User: $currentUser")
                    if (currentUser != null) {
                        Timber.d(" User is not null")
                        GoogleAuthService.SignInState.Success(currentUser)
                    } else {
                        GoogleAuthService.SignInState.Error("User does not exist.")
                    }
                }
                Result.success(Unit)
                return@onSuccess
            }
            .onFailure { result ->
                Timber.e(result, " An error occurred during sign in.")
                signInState.update {
                    GoogleAuthService.SignInState.Error(
                        "Error signing in:", result as Exception
                    )
                }
                Result.failure<Exception>(result)
                return@onFailure
            }
    }

    suspend fun signOut(): Result<Unit> {
        signInState.update { GoogleAuthService.SignInState.Loading }
        return authRepository.signOut()
            .onSuccess {
                signInState.update { GoogleAuthService.SignInState.Idle }
                Timber.i(" Sign out successful.")
                Result.success(Unit)
                return@onSuccess
            }
            .onFailure { error ->
                signInState.update {
                    GoogleAuthService.SignInState.Error(
                        "Sign out failed: ${error.message}",
                        error as? Exception
                    )
                }
                Timber.e(error, " Sign out failed.")
                Result.failure<Exception>(error as Exception)
                return@onFailure
            }
    }

    suspend fun deleteAuthUser(): Result<Unit> {
        when (signInState.value) {
            is GoogleAuthService.SignInState.Success -> {
                val currentUser = signInState.value as GoogleAuthService.SignInState.Success
                Timber.d("--Attempting to delete: ${currentUser.user}--")
                Timber.d("User is signed in")
                try {
                    val authDeleteResult = authRepository.deleteCurrentUser()
                    authDeleteResult
                        .onSuccess {
                            Timber.d("Successfully deleted auth User, signing out.")
                            val signOutResult = signOut()
                            signOutResult
                                .onSuccess {
                                    Timber.d("Successfully signed out, removing user details from user datastore")
                                    profileDsRepository.updateUsername("")
                                    profileDsRepository.updateLoggedInState(false)
                                    return Result.success(Unit)
                                }
                                .onFailure {
                                    throw signOutResult.exceptionOrNull()
                                        ?: Exception("Sign out result is null")
                                }
                        }
                        .onFailure {
                            val exception = authDeleteResult.exceptionOrNull()
                                ?: Exception("Auth deletion result is null")
                            if (exception is FirebaseAuthRecentLoginRequiredException) {
                                Timber.e(" User needs to re-authenticate.")
                                throw exception
                            } else {
                                Timber.e(exception, " Error deleting account.")
                                throw exception
                            }
                        }
                } catch (e: Exception) {
                    Timber.e(" Error occurred during auth deletion process")
                    return Result.failure(e)
                }
            }

            else -> {
                return Result.failure(Exception("User is not signed in."))
            }
        }
        return Result.failure(Exception("Unknown error occurred during auth deletion"))
    }
}