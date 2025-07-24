package com.example.fightingflow.ui.userScreen.abstractedFunctions

import androidx.compose.material3.SnackbarHostState
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.example.fightingflow.model.UserEntry
import com.example.fightingflow.viewmodels.AuthViewModel
import com.example.fightingflow.viewmodels.UserSaveResult
import com.example.fightingflow.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

fun signInOrCreateUserByEmail(
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    createAccount: Boolean,
    username: String,
    name: String,
    dob: String,
    email: String,
    password: String,
    confirmPassword: String,
    showUsernameError: () -> Unit,
    showEmailError: () -> Unit,
    showPasswordError: () -> Unit,
    showConfirmPasswordError: () -> Unit,
    showShortPasswordError: () -> Unit,
    onDismissRequest: () -> Unit,
    showUserTooYoung: () -> Unit
) {
    Timber.d("Confirming details and checking sign in state")
    scope.launch {
        if (createAccount) {
           createAccountByEmail(
               authViewModel = authViewModel,
               userViewModel = userViewModel,
               snackbarHostState = snackbarHostState,
               username = username,
               name = name,
               dob = dob,
               email = email,
               password = password,
               confirmPassword = confirmPassword,
               showUsernameError = showUsernameError,
               showEmailError = showEmailError,
               showPasswordError = showPasswordError,
               showShortPasswordError = showShortPasswordError,
               showConfirmPasswordError = showConfirmPasswordError,
               onDismissRequest = onDismissRequest,
               showUserTooYoung = showUserTooYoung,
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
    name: String,
    dob: String,
    email: String,
    password: String,
    confirmPassword: String,
    showUsernameError: () -> Unit,
    showEmailError: () -> Unit,
    showPasswordError: () -> Unit,
    showShortPasswordError: () -> Unit,
    showConfirmPasswordError: () -> Unit,
    onDismissRequest: () -> Unit,
    showUserTooYoung: () -> Unit,
) {

    val currentUserState = userViewModel.newUserState.value
    Timber.d("Account creation process launched")
    Timber.d("Checking fields aren't blank")
    if (username.isBlank()) { showUsernameError() }
    if (email.isBlank()) { showEmailError() }
    if (password.isBlank()) { showPasswordError() }
    if (confirmPassword.isBlank()) { showConfirmPasswordError() }
    if (!checkUserAge(dob)) {
        showUserTooYoung()
        return
    }

    if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
        Timber.d("Checking password is valid.")
        if (password.length >= 6) {
            if (password == confirmPassword) {
                Timber.d("Passwords match, beginning sign-in attempt")
                authViewModel.createAccountWithEmailAndPassword(email, password)
                    .onSuccess {
                        onDismissRequest()
                        Timber.d("Successfully created account, saving user details to firestore.")
                        val user = (authViewModel.signInState.value as GoogleAuthService.SignInState.Success).user
                        userViewModel.updateNewUserState(
                            currentUserState?.copy(
                                userId = user.userId,
                                name = name,
                                dob = dob,
                                dateCreated = LocalDate.now().toString()
                            )
                        )
                        val result = userViewModel.createUser()
                        Timber.d("Details result: $result")
                        when (result) {
                            is UserSaveResult.Success -> {
                                Timber.d("User $username has been created.")
                                snackbarHostState.showSnackbar(message = "User $username has been created")
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
        } else {
            showShortPasswordError()
            showPasswordError()
            return
        }
    } else {
        snackbarHostState.showSnackbar("Please enter valid information.")
        return
    }
}

fun checkUserAge(dob: String): Boolean {
    val currentDate = LocalDate.now().toString().split("-").reversed()
    val dobSplit = dob.split("/")
    Timber.d("Current Date: $currentDate")
    Timber.d("Birth Date: $dobSplit")
    val currentAge = {
        Timber.d("Month Now: ${currentDate[1]} Month Dob: ${dobSplit[0]}")
        if (dobSplit[0].toInt() >= currentDate[1].toInt()) {
            Timber.d("Day Now: ${currentDate[0]} Month Dob: ${dobSplit[1]}")
            if (dobSplit[1].toInt() >= dobSplit[0].toInt()) {
                (currentDate[2].toInt() - dobSplit[2].toInt())
            } else {
                (currentDate[2].toInt() - dobSplit[2].toInt()) - 1
            }
        } else {
            (currentDate[2].toInt() - dobSplit[2].toInt()) - 1
        }
    }
    Timber.d("Current Age: ${currentAge()}")
    return currentAge() >= 16
}