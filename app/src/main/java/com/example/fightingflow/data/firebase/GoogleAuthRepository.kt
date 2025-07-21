package com.example.fightingflow.data.firebase

import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.os.registerForAllProfilingResults
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.example.fightingflow.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

interface GoogleAuthService {
    data class GoogleSignInResult(
        val userId : String,
        val displayName: String?,
        val email: String?,
        val photo: String?
    )
    sealed class SignInState {
        data object Idle: SignInState()
        data object Loading: SignInState()
        data class Success(val user: GoogleSignInResult): SignInState()
        data class Error(val message: String, val exception: Exception? = null): SignInState()
    }

    fun getCurrentUser(): GoogleSignInResult?
    suspend fun signInWithGoogle(): SignInState
    suspend fun signOut(): Result<Unit>
    suspend fun deleteCurrentUser(): Result<Unit>
    suspend fun createAccountWithEmailAndPassword(email: String, password: String): Result<Unit>
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit>
}

class GoogleAuthRepository(
    private val context: Context,
    private val firebaseAuth: FirebaseAuth = Firebase.auth,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GoogleAuthService {

    var auth: FirebaseAuth = Firebase.auth
    private val credentialManager: CredentialManager by lazy { CredentialManager.create(context) }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override suspend fun signInWithGoogle(): GoogleAuthService.SignInState {
        Timber.d("ClientId: ${BuildConfig.WEB_CLIENT_ID}")
        return try {
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                .build()

            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            Timber.d("Requesting Google ID Token Credential")
            val result: GetCredentialResponse = credentialManager.getCredential(
                request = request,
                context = context
            )

            val credential = result.credential
            if (credential is GoogleIdTokenCredential) {
                val googleIdToken = credential.idToken
                Timber.d("Successfully obtained Google ID Token")
                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                val authResult = firebaseAuth.signInWithCredential(firebaseCredential).await()
                val firebaseUser = authResult.user

                if (firebaseUser != null) {
                    Timber.i("Successfully signed in with Firebase. User: ${firebaseUser.displayName}")
                    GoogleAuthService.SignInState.Success(
                        GoogleAuthService.GoogleSignInResult(
                            userId = firebaseUser.uid,
                            displayName = firebaseUser.displayName ?: credential.displayName,
                            email = firebaseUser.email ?: credential.givenName,
                            photo = firebaseUser.photoUrl?.toString() ?: credential.profilePictureUri?.toString()
                        )
                    )
                } else {
                    Timber.e("Firebase user is null after successful credential sign-in.")
                    GoogleAuthService.SignInState.Error("Firebase user is null after sign-in.")
                }
            } else if (
                credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                try {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val googleIdToken = googleIdTokenCredential.idToken
                    Timber.d("Successfully obtained Google ID token from CustomCredential.")
                    val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                    val authResult = firebaseAuth.signInWithCredential(firebaseCredential).await()
                    val firebaseUser = authResult.user

                    if (firebaseUser != null) {
                        Timber.i("Successfully signed in with Firebase. User: ${firebaseUser.displayName}")
                        GoogleAuthService.SignInState.Success(
                            GoogleAuthService.GoogleSignInResult(
                                userId = firebaseUser.uid,
                                displayName = firebaseUser.displayName ?: googleIdTokenCredential.displayName,
                                email = firebaseUser.email ?: googleIdTokenCredential.givenName,
                                photo = firebaseUser.photoUrl?.toString() ?: googleIdTokenCredential.profilePictureUri?.toString()
                            )
                        )
                    } else {
                        GoogleAuthService.SignInState.Error("Firebase user is null (custom flow).")
                    }
                } catch (e: GoogleIdTokenParsingException) {
                    Timber.e(e, "Failed to parse Google ID token from CustomCredential bundle.")
                    GoogleAuthService.SignInState.Error("Failed to parse Google ID Token")
                }
            } else {
                Timber.e("Unexpected credential type received ${credential.type}")
                GoogleAuthService.SignInState.Error("Unexpected Credential Type.")
            }
        } catch (e: GetCredentialException) {
            Timber.e(e, "Google Sign-In failed with GetCredentialException")
            GoogleAuthService.SignInState.Error("Google Sign-In failed: ${e.message}", e)
        } catch (e: Exception) {
            Timber.e("Google Sign-In: General Exception.")
            GoogleAuthService.SignInState.Error("An unexpected error occurred: ${e.message}", e)
        }
    }

    override suspend fun createAccountWithEmailAndPassword(email: String, password: String): Result<Unit> {
        Timber.d("Creating account for $email")
        if (email.isEmpty()|| password.isEmpty()) {
            Timber.e("Email is empty.")
            return Result.failure(Exception("Missing Info"))
        } else {
            try {
                Timber.d("Details seem correct, creating account.")
                val result = auth.createUserWithEmailAndPassword(email, password)
                result.await()

                if (result.isSuccessful) {
                    Timber.d("Successfully created User with Email/Password")
                    return Result.success(Unit)
                } else {
                    if (result.exception != null) {
                        Timber.e(result.exception, "An error occurred during account creation.")
                        return Result.failure(result.exception as Exception)
                    }
                }
            } catch (e: Exception) {
                return Result.failure(e)
            }
        }
        return Result.failure(Exception("An unknown error occurred."))
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String):Result<Unit> {
        Timber.d("--Signing in with Email/Password--")
        return if (email.isEmpty()|| password.isEmpty()) {
            Timber.e("Email is empty.")
            Result.failure(Exception("Missing username or password"))
        } else {
            try {
                Timber.d("Attempting to sign in")
                val result = auth.signInWithEmailAndPassword(email, password)
                if (result.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Timber.e(result.exception, "Error signing in.")
                    if (result.exception != null) {
                        Result.failure(result.exception as Exception)
                    } else {
                        Timber.d("Result: ${result.result}")
                        Result.failure(Exception("Exception result is null"))
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error attempting to sign in.")
                Result.failure(e)
            }
        }
    }

    override fun getCurrentUser(): GoogleAuthService.GoogleSignInResult? {
        Timber.d("--Getting current User--")
        val firebaseUser = auth.currentUser
        Timber.d("User: $firebaseUser")
        return firebaseUser?.let {
            GoogleAuthService.GoogleSignInResult(
                userId = it.uid,
                displayName = it.displayName,
                email = it.email,
                photo = it.photoUrl?.toString()
            )
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            withContext(ioDispatcher) {
                firebaseAuth.signOut()
                Timber.i("Successfully signed out from Firebase.")
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Timber.e(e, "Error during Firebase sign out.")
            Result.failure(e)
        }
    }

    override suspend fun deleteCurrentUser(): Result<Unit> {
        val user = auth.currentUser
        Timber.d("User: ${user?.uid}")

        return if (user == null) {
            Timber.e("User is null")
            Result.failure(IllegalStateException("No current user to delete"))
        }
        else {
            try {
                Timber.d("User found: ${user.uid}")
                user.delete().await()
                Timber.d("User deleted")
                Result.success(Unit)
            } catch (e: Exception) {
                if (e is FirebaseAuthRecentLoginRequiredException) {
                    Timber.e(e, "User needs to reauthenticate.")
                    Result.failure(e)
                } else {
                    Timber.e(e, "Error deleting User")
                    Result.failure(e)
                }
            }
        }
    }
}