package com.example.fightingflow.ui.userScreen

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.example.fightingflow.model.UserEntry
import com.example.fightingflow.viewmodels.AuthViewModel
import com.example.fightingflow.viewmodels.UserSaveResult
import com.example.fightingflow.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

fun signInOrCreateUserByEmail(
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    createAccount: Boolean,
    username: String,
    email: String,
    password: String,
    confirmPassword: String,
    showUsernameError: () -> Unit,
    showEmailError: () -> Unit,
    showPasswordError: () -> Unit,
    showConfirmPasswordError: () -> Unit,
    showShortPasswordError: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Timber.d("Confirming details and checking sign in state")
    scope.launch {
        if (createAccount) {
           createAccountByEmail(
               authViewModel = authViewModel,
               userViewModel = userViewModel,
               snackbarHostState = snackbarHostState,
               username = username,
               email = email,
               password = password,
               confirmPassword = confirmPassword,
               showUsernameError = showUsernameError,
               showEmailError = showEmailError,
               showPasswordError = showPasswordError,
               showShortPasswordError = showShortPasswordError,
               showConfirmPasswordError = showConfirmPasswordError,
               onDismissRequest = onDismissRequest
           ) 
        } else {
            signInByEmail(
                authViewModel = authViewModel,
                userViewModel = userViewModel,
                email = email,
                password = password,
                showEmailError = showEmailError,
                showPasswordError = showPasswordError,
                onDismissRequest = onDismissRequest
            )
        }
    }
}

suspend fun signInByEmail(
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    email: String,
    password: String,
    showEmailError: () -> Unit,
    showPasswordError: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Timber.d("Signing in user.")
    if (email.isBlank()) { showEmailError() }
    if (password.isBlank()) { showPasswordError() }
    if (email.isNotBlank() && password.isNotBlank()) {
        authViewModel.signInWithEmailAndPassword(email, password)
            .onSuccess { result ->
                val username = userViewModel.username.value
                Timber.d("$username is logged in.")
                userViewModel.updateNewUserState(UserEntry())
                onDismissRequest()
            }
            .onFailure { result ->
                Timber.e(result, "Error trying to sign in.")

            }

    } else {
        return
    }
}

suspend fun createAccountByEmail(
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    snackbarHostState: SnackbarHostState,
    username: String,
    email: String,
    password: String,
    confirmPassword: String,
    showUsernameError: () -> Unit,
    showEmailError: () -> Unit,
    showPasswordError: () -> Unit,
    showShortPasswordError: () -> Unit,
    showConfirmPasswordError: () -> Unit,
    onDismissRequest: () -> Unit,
) {

    val currentUserState = userViewModel.newUserState.value
    Timber.d("Account creation process launched")
    Timber.d("Checking fields aren't blank")
    if (username.isBlank()) { showUsernameError() }
    if (email.isBlank()) { showEmailError() }
    if (password.isBlank()) { showPasswordError() }
    if (confirmPassword.isBlank()) { showConfirmPasswordError() }

    if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
        Timber.d("Checking password is valid.")
        if (password.length < 6) {
            showShortPasswordError()
            showPasswordError()
            return
        } else {
            if (password == confirmPassword) {
                Timber.d("Passwords match, beginning sign-in attempt")
                authViewModel.createAccountWithEmailAndPassword(email, password)
                    .onSuccess {
                        Timber.d("Successfully created account, saving user details to firestore.")
                        val userId = (authViewModel.signInState.value as GoogleAuthService.SignInState.Success).user.userId
                        userViewModel.updateNewUserState(currentUserState?.copy(userId = userId))
                        val result = userViewModel.createUser()
                        Timber.d("Details result: $result")
                        when (result) {
                            is UserSaveResult.Success -> {
                                Timber.d("User $username has been created.")
                                snackbarHostState.showSnackbar(message = "User $username has been created")
                                onDismissRequest()
                            }

                            is UserSaveResult.Error -> {
                                Timber.e(result.e, "Error saving user.")
                            }
                        }
                    }
                    .onFailure { result ->
                        if (result is FirebaseAuthInvalidCredentialsException) {
                            Timber.e(result, "Error creating account.")
                            snackbarHostState.showSnackbar(result.message.toString())
                            return
                        }
                    }
            } else {
                showPasswordError()
                showConfirmPasswordError()
                snackbarHostState.showSnackbar(message = "Passwords do not match.")
                return
            }
        }
    } else {
        snackbarHostState.showSnackbar("Please enter valid information.")
        return
    }
}