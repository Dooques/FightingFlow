package com.example.fightingflow.ui.userScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.example.fightingflow.model.UserEntry
import com.example.fightingflow.ui.components.convertMillisToDate
import com.example.fightingflow.viewmodels.AuthViewModel
import com.example.fightingflow.viewmodels.UserViewModel
import timber.log.Timber

@Composable
fun UserCreationForm(
    authViewModel: AuthViewModel,
    showEmailPasswordDialog: () -> Unit,
    createAccountDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    var emailSignIn by remember { mutableStateOf(false) }
    var googleSignIn by remember { mutableStateOf(false) }

    val currentUser by authViewModel.signInState.collectAsStateWithLifecycle()
    Timber.d("Loading profile creation form...")

    Column(modifier.fillMaxHeight()) {
        Timber.d("Loading username field...")

        Spacer(modifier.height(20.dp))

        Timber.d("Loading confirm button...")
        when (val state = currentUser) {
            GoogleAuthService.SignInState.Idle -> {
                OutlinedButton(
                    onClick = {
                        authViewModel.initiateGoogleSignIn()
                        googleSignIn = true
                              },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                ) {
                    Text(
                        "Sign In With Google",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                OutlinedButton(
                    onClick = {
                        showEmailPasswordDialog()
                        emailSignIn = true
                              },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                ) {
                    Text(
                        "Sign In With Email",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                OutlinedButton(
                    onClick = { createAccountDialog() },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                ) {
                    Text(
                        "Create An Account With Email",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            GoogleAuthService.SignInState.Loading -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(20.dp))
                    Text("Signing in...")
                }
            }

            is GoogleAuthService.SignInState.Success -> {
                emailSignIn = false
                googleSignIn = false
                Timber.d("User successfully signed in.")
            }

            is GoogleAuthService.SignInState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.fillMaxWidth().padding(32.dp)
                ) {
                    Text(
                        "${state.message} ${state.exception?.message}.",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (googleSignIn) {
                        OutlinedButton(
                            onClick = { authViewModel.initiateGoogleSignIn() },
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text("Retry Sign-In", color = MaterialTheme.colorScheme.onBackground)
                        }
                    }
                    if (emailSignIn) {
                        OutlinedButton(
                            onClick = { showEmailPasswordDialog() },
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text("Retry Sign-In", color = MaterialTheme.colorScheme.onBackground)
                        }
                        OutlinedButton(
                            onClick = { createAccountDialog() },
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text("Create an Account", color = MaterialTheme.colorScheme.onBackground)
                        }
                    }
                }
            }
        }

        Spacer(modifier.size(height = 50.dp, width = 0.dp))

        Timber.d("Form Loaded.")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailAndPasswordDialog(
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    createAccount: Boolean,
    snackbarHostState: SnackbarHostState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    var showDatePicker by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val dob = rememberDatePickerState()
    val selectedDate = dob.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""

    var showUsernameError by remember { mutableStateOf(false) }
    var showEmailError by remember { mutableStateOf(false) }
    var showPasswordError by remember { mutableStateOf(false) }
    var showShortPasswordError by remember {mutableStateOf(false) }
    var showConfirmPasswordError by remember { mutableStateOf(false) }
    var showDobError by remember { mutableStateOf(false) }

    val newUserDetails by userViewModel.newUserState.collectAsStateWithLifecycle()

    BasicAlertDialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            colors = CardDefaults.cardColors().copy(containerColor = Color.Black),
            border = CardDefaults.outlinedCardBorder(true)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(24.dp)
            ) {
                Text("Create an account", style = MaterialTheme.typography.displaySmall)

                if (createAccount) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { newValue ->
                            if (newValue.length <= 20) {
                                username = newValue
                                showUsernameError = false
                                if (createAccount) {
                                    userViewModel.updateNewUserState(
                                        newUserDetails?.copy(username = username) ?: UserEntry(username = username)
                                    )
                                }
                            }
                        },
                        label = {
                            Text(
                                if (showUsernameError) "Please enter a username."
                                else "Username"
                            )
                        },
                        supportingText = { Text("Maximum 20 characters") },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        isError = showUsernameError,
                        modifier = modifier.padding(4.dp)
                    )
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { newValue ->
                        if (newValue.length <= 30) {
                            email = newValue
                            showEmailError = false
                            if (createAccount) {
                                userViewModel.updateNewUserState(
                                    newUserDetails?.copy(email = email) ?: UserEntry(email = email)
                                )
                            }
                        } },
                    label = { Text(
                        if (showEmailError) "Please enter an email."
                        else "Email") },
                    maxLines = 2,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    isError = showEmailError,
                    modifier = modifier.padding(4.dp)
                )

                if (createAccount) {
                    Spacer(Modifier.height(10.dp))
                }

                OutlinedTextField(
                    value = password,
                    onValueChange = { newValue ->
                        if (newValue.length <= 20) {
                            password = newValue
                            showPasswordError= false
                        } },
                    label = { Text(
                        if (showPasswordError) "Please enter a valid password."
                        else if (showShortPasswordError) "Password is less than 6 characters."
                        else "Password") },
                    supportingText = { Text("Minimum 6 characters") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    isError = showPasswordError,
                    modifier = modifier.padding(4.dp)
                )

                if (createAccount) {
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { newValue ->
                            if (newValue.length <= 20) {
                                confirmPassword = newValue
                                showConfirmPasswordError = false
                            }
                        },
                        label = {
                            Text(
                                if (showConfirmPasswordError) "Please confirm your password."
                                else "Confirm Password"
                            )
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        isError = showConfirmPasswordError,
                        modifier = modifier.padding(4.dp)
                    )

                    Spacer(Modifier.height(10.dp))

                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = {
                            selectedDate
                            userViewModel.updateNewUserState(
                                newUserDetails?.copy(dob = selectedDate) ?: UserEntry(dob = selectedDate)
                            ) },
                        label = { Text("DOB") },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = !showDatePicker }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Select Date"
                                )
                            }
                        },
                        modifier = modifier.padding(4.dp)
                    )

                    if (showDatePicker) {
                        Popup(
                            onDismissRequest = { showDatePicker = false },
                            alignment = TopStart
                        ) {
                            Box(
                                modifier = Modifier
                                    .shadow(elevation = 4.dp)
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(16.dp)
                            ) {
                                Column {
                                    DatePicker(
                                        state = dob,
                                        showModeToggle = false
                                    )
                                    Button(onClick = {showDatePicker = false }, modifier.padding(top = 8.dp)) { Text("Confirm")}
                                }
                            }
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth().padding(top = 8.dp)) {
                    OutlinedButton(onClick = {
                        signInOrCreateUserByEmail(
                            userViewModel = userViewModel,
                            authViewModel = authViewModel,
                            scope = scope,
                            snackbarHostState = snackbarHostState,
                            createAccount = createAccount,
                            username = username,
                            email = email,
                            password = password,
                            confirmPassword = confirmPassword,
                            showUsernameError = { showUsernameError = true },
                            showEmailError = { showEmailError = true },
                            showPasswordError = { showPasswordError = true },
                            showConfirmPasswordError = { showConfirmPasswordError = true },
                            showShortPasswordError = { showShortPasswordError = true },
                            onDismissRequest = onDismissRequest
                        ) }) { Text("Confirm") }

                    OutlinedButton(onClick = {
                        onDismissRequest()
                        userViewModel.updateNewUserState(UserEntry())
                    }) { Text("Cancel") }
                }
            }
        }
    }
}