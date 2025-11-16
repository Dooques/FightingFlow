package com.dooques.fightingflow.ui.userScreen.abstractedFunctions

import androidx.compose.material3.SnackbarHostState
import com.dooques.fightingflow.data.firebase.GoogleAuthService
import com.dooques.fightingflow.model.UserEntry
import com.dooques.fightingflow.ui.viewmodels.AuthViewModel
import com.dooques.fightingflow.ui.viewmodels.UserViewModel
import com.dooques.fightingflow.util.emptyUser
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import timber.log.Timber
import java.time.LocalDate

suspend fun signInByEmail(
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    email: String,
    password: String,
    showEmailError: () -> Unit,
    showPasswordError: () -> Unit,
    onDismissRequest: () -> Unit,
): Result<Unit> {
    Timber.d("--Signing in user--")
    if (email.isBlank()) { showEmailError() }
    if (password.isBlank()) { showPasswordError() }
    if (email.isBlank() && password.isBlank()) {
        return Result.failure(Exception("User Sign in Failed"))
    }

    return authViewModel.signInWithEmailAndPassword(email, password)
        .onSuccess {
            val username = userViewModel.username.value
            Timber.d(" $username is logged in.")
            userViewModel.updateNewUserState(UserEntry())
            onDismissRequest()
        }
        .onFailure { result ->
            Timber.e(result, " Error trying to sign in.")
            userViewModel.updateEmailErrorState(true)
            onDismissRequest()
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
    showDobError: () -> Unit,
    showUserTooYoung: () -> Unit,
): Result<Unit> {

    val currentUserDetailsState = userViewModel.newUserDetailsState
    Timber.d("--User Details creation--")
    Timber.d(" Checking fields aren't blank")
    if (username.isBlank()) { showUsernameError() }
    if (email.isBlank()) { showEmailError() }
    if (password.isBlank()) { showPasswordError() }
    if (confirmPassword.isBlank()) { showConfirmPasswordError() }

    if (username.isBlank() && email.isBlank() && password.isBlank() && confirmPassword.isBlank()) {
        snackbarHostState.showSnackbar("Please enter valid information.")
        return Result.failure(Exception("One or more values are blank"))
    }

    if (dob.isNotBlank()) {
        if (!checkUserAge(dob)) {
            showUserTooYoung()
            return Result.failure(Exception("User must be at least 16 years old"))
        }
    } else {
        showDobError()
        return Result.failure(Exception("DOB value is blank."))
    }

    if (password.length < 6) {
        showShortPasswordError()
        showPasswordError()
        return Result.failure(Exception("Password must be at least 6 characters"))
    }

    if (password != confirmPassword) {
        showPasswordError()
        showConfirmPasswordError()
        snackbarHostState.showSnackbar(message = "Passwords do not match.")
        return Result.failure(Exception("Passwords must match."))
    }

    Timber.d(" User Details:\n Username: %s\n Email: %s\n Password: %s\n Confirm Password: %s\n DOB: %s" +
            "\n Current Details: %s",
        username, email, password, confirmPassword, dob, currentUserDetailsState)

    Timber.d(" Passwords match, beginning sign-in attempt")
    try {
        authViewModel.createAccountWithEmailAndPassword(email, password)
        Timber.d(" Successfully created account, saving user details to firestore.")
        val user = (authViewModel.signInState.value as GoogleAuthService.SignInState.Success).user

        Timber.d(" Current User Details: ${currentUserDetailsState.value}")
        userViewModel.createUser(
            emptyUser.copy(
                userId = user.userId,
                email = user.email ?: "Invalid Email",
                username = username,
                name = name,
                dob = dob,
                dateCreated = LocalDate.now().toString()
            ))
        return Result.success(Unit)
    } catch (e: Exception) {
        if (e is FirebaseAuthInvalidCredentialsException) {
            Timber.e(e, " Invalid Credentials Exception: ")
            snackbarHostState.showSnackbar(e.message.toString())
            return Result.failure(e)
        } else {
            Timber.e(e)
            return Result.failure(e)
        }
    }
}

fun checkUserAge(dob: String): Boolean {
    val currentDate = LocalDate.now().toString().split("-").reversed()
    val dobSplit = dob.split("-")
//    Timber.d(" Current Date: $currentDate")
//    Timber.d(" Birth Date: $dobSplit")
    val currentAge = {
//        Timber.d(" Month Now: ${currentDate[1]} Month Dob: ${dobSplit[0]}")
        if (dobSplit[0].toInt() >= currentDate[1].toInt()) {
//            Timber.d(" Day Now: ${currentDate[0]} Month Dob: ${dobSplit[1]}")
            if (dobSplit[1].toInt() >= dobSplit[0].toInt()) {
                (currentDate[2].toInt() - dobSplit[2].toInt())
            } else {
                (currentDate[2].toInt() - dobSplit[2].toInt()) - 1
            }
        } else {
            (currentDate[2].toInt() - dobSplit[2].toInt()) - 1
        }
    }
//    Timber.d(" Current Age: ${currentAge()}")
    return currentAge() >= 16
}