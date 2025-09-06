package com.dooques.fightingflow.ui.userScreen.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dooques.fightingflow.model.UserEntry
import com.dooques.fightingflow.ui.components.convertMillisToDate
import com.dooques.fightingflow.ui.userScreen.abstractedFunctions.signInOrCreateUserByEmail
import com.dooques.fightingflow.ui.viewmodels.AuthViewModel
import com.dooques.fightingflow.ui.viewmodels.ProfanityViewModel
import com.dooques.fightingflow.ui.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailAndPasswordDialog(
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    profanityViewModel: ProfanityViewModel,
    createAccount: Boolean,
    snackbarHostState: SnackbarHostState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    var showDatePicker by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val dob = rememberDatePickerState()
    val selectedDob = dob.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""

    var showUsernameError by remember { mutableStateOf(false) }
    var showUsernameIllegalWordError by remember { mutableStateOf(false) }
    var showNameError by remember { mutableStateOf(false) }
    var showEmailError by remember { mutableStateOf(false) }
    var showPasswordError by remember { mutableStateOf(false) }
    var showShortPasswordError by remember {mutableStateOf(false) }
    var showConfirmPasswordError by remember { mutableStateOf(false) }
    var showDobError by remember { mutableStateOf(false) }
    var showUserTooYoung by remember { mutableStateOf(false) }

    var hidePassword by remember { mutableStateOf(true) }
    var hideConfirmPassword by remember { mutableStateOf(true) }

    val newUserDetails by userViewModel.newUserState.collectAsStateWithLifecycle()

    BasicAlertDialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            colors = CardDefaults.cardColors().copy(containerColor = Color.Black),
            border = CardDefaults.outlinedCardBorder(true)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(24.dp)
            ) {
                Text("Create an account", style = MaterialTheme.typography.displaySmall)

                if (createAccount) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { newValue ->
                            if (newValue.length <= 20) {
                                if (profanityViewModel.checkForUsernameInProfanityFilter(newValue)) {
                                    showUsernameIllegalWordError = true
                                } else {
                                    username = newValue
                                    showUsernameError = false
                                    showUsernameIllegalWordError = false
                                    if (createAccount) {
                                        userViewModel.updateNewUserState(
                                            newUserDetails?.copy(username = username) ?: UserEntry(
                                                username = username
                                            )
                                        )
                                    }
                                }
                            }
                        },
                        label = { Text("Username") },
                        supportingText = { Text(
                            if (showUsernameError) {
                                "Please enter a username"
                            } else if (showUsernameIllegalWordError) {
                                "Username contains an inappropriate word"
                            } else
                                "Maximum 20 characters"
                        ) },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        isError = showUsernameError || showUsernameIllegalWordError,
                        modifier = modifier.padding(4.dp)
                    )

                    OutlinedTextField(
                        value = name,
                        onValueChange = { newValue ->
                            if (newValue.length <= 20) {
                                name = newValue
                                showNameError = false
                                if (createAccount) {
                                    userViewModel.updateNewUserState(
                                        newUserDetails?.copy(name = name) ?: UserEntry(name = name)
                                    )
                                }
                            }
                        },
                        label = { Text(if (showNameError) "Please enter your name" else "Name") },
                        supportingText = { Text("This is only visible to you") },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        isError = showNameError,
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
                        if (showEmailError) "Please enter an email"
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
                        if (showPasswordError) "Please enter a password"
                        else if (showShortPasswordError) "Password is less than 6 characters"
                        else "Password") },
                    supportingText = { Text("Minimum 6 characters") },
                    trailingIcon = { IconButton(onClick = { hidePassword = !hidePassword }) {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "Hide Password")
                    } },
                    visualTransformation =
                        if (hidePassword) {PasswordVisualTransformation() }
                        else VisualTransformation.None,
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
                                if (showConfirmPasswordError) "Please confirm your password"
                                else "Confirm Password"
                            )
                        },
                        maxLines = 1,
                        supportingText = { Text("You must be at least 16 years old")},
                        trailingIcon = { IconButton(onClick = { hideConfirmPassword = !hideConfirmPassword }) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = "Hide Password")
                        } },
                        visualTransformation =
                            if (hidePassword) {PasswordVisualTransformation() }
                            else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        isError = showConfirmPasswordError,
                        modifier = modifier.padding(4.dp)
                    )

                    Spacer(Modifier.height(10.dp))

                    OutlinedTextField(
                        value = selectedDob,
                        onValueChange = {
                            selectedDob
                            userViewModel.updateNewUserState(newUserDetails?.copy(dob = selectedDob) ?: UserEntry(dob = selectedDob)) },
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
                        isError = showDobError,
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
                            name = name,
                            dob = selectedDob,
                            email = email,
                            password = password,
                            confirmPassword = confirmPassword,
                            showUsernameError = { showUsernameError = true },
                            showEmailError = { showEmailError = true },
                            showPasswordError = { showPasswordError = true },
                            showConfirmPasswordError = { showConfirmPasswordError = true },
                            showShortPasswordError = { showShortPasswordError = true },
                            showUserTooYoung = { showUserTooYoung = true },
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
