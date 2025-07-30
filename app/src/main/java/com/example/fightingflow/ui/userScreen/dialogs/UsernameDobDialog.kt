package com.example.fightingflow.ui.userScreen.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.example.fightingflow.model.UserEntry
import com.example.fightingflow.ui.components.convertMillisToDate
import com.example.fightingflow.ui.userScreen.abstractedFunctions.checkUserAge
import com.example.fightingflow.viewmodels.ProfanityViewModel
import com.example.fightingflow.viewmodels.UserDetailsState
import com.example.fightingflow.viewmodels.UserSaveResult
import com.example.fightingflow.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsDialog(
    scope: CoroutineScope,
    userViewModel: UserViewModel,
    profanityViewModel: ProfanityViewModel,
    currentUser: GoogleAuthService.SignInState,
    userDetailsState: UserDetailsState,
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    val dob = rememberDatePickerState()
    val selectedDob = dob.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""
    var showUserTooYoung by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var usernameErrorMessage by remember { mutableStateOf("") }
    var showUsernameError by remember { mutableStateOf(false) }
    var showUsernameProfanityError by remember { mutableStateOf(false) }

    profanityViewModel.readJsonFromAssets(context, "profanityFilter.JSON")

    BasicAlertDialog(onDismissRequest = { onDismissDialog() }) {
        Card(
            colors = CardDefaults.cardColors().copy(containerColor = Color.Black),
            border = CardDefaults.outlinedCardBorder(true)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(24.dp)
            ) {
                Text(
                    "Your Details",
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(8.dp)
                )
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                        if (profanityViewModel.checkForUsernameInProfanityFilter(it)) {
                            showUsernameProfanityError = true
                        } else {
                            showUsernameProfanityError = false
                            showUsernameError = false
                        }
                                    },
                    label = { Text("Username") },
                    isError = showUsernameError || showUsernameProfanityError,
                    supportingText = {
                        if (showUsernameError) {
                            usernameErrorMessage = "Username is not valid"
                            if (showUsernameProfanityError) {
                                usernameErrorMessage = "Username contains offensive language"
                            }
                        }
                            Text(usernameErrorMessage)
                                     },
                    modifier = modifier.padding(vertical = 16.dp)
                )

                OutlinedTextField(
                    value = selectedDob,
                    onValueChange = { selectedDob; if (checkUserAge(selectedDob)) { showUserTooYoung = false } },
                    label = { Text("DOB") },
                    readOnly = true,
                    isError = showUserTooYoung,
                    supportingText = { Text("You must be at least 16 years old")},
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = !showDatePicker }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select Date"
                            )
                        }
                    },
                )
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth().padding(16.dp)) {
                    OutlinedButton(
                        onClick = {
                            if (!checkUserAge(selectedDob)) {
                                showUserTooYoung = true
                                return@OutlinedButton
                            }
                            onDismissDialog()
                            scope.launch {
                                when (userDetailsState) {
                                    is UserDetailsState.NotFound -> {
                                        val currentUserState = (currentUser as GoogleAuthService.SignInState.Success)
                                        userViewModel.updateNewUserState(
                                            UserEntry(
                                                userId = currentUserState.user.userId,
                                                username = username,
                                                name = currentUserState.user.displayName,
                                                email = currentUserState.user.email,
                                                profilePic = currentUserState.user.photo.toString(),
                                                dob = selectedDob,
                                                dateCreated = LocalDate.now().toString(),
                                            )
                                        )
                                        val result = userViewModel.createUser()
                                        when (result) {
                                            is UserSaveResult.Success -> {
                                                onDismissDialog()
                                            }

                                            is UserSaveResult.Error -> {
                                                Timber.e(result.e, "Error saving user")
                                            }
                                        }
                                    }
                                    else -> null
                                }
                            }
                        }
                    ) { Text("Confirm") }
                    OutlinedButton(onClick = { onDismissDialog() }) {
                        Text("Cancel")
                    }
                }
            }
        }
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
}
