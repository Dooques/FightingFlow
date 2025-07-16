package com.example.fightingflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fightingflow.R
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.example.fightingflow.model.UserEntry
import com.example.fightingflow.ui.components.convertMillisToDate
import com.example.fightingflow.ui.userScreen.UserCreationForm
import com.example.fightingflow.viewmodels.AuthViewModel
import com.example.fightingflow.viewmodels.UserDetailsState
import com.example.fightingflow.viewmodels.UserSaveResult
import com.example.fightingflow.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber
import java.time.LocalDate

@Composable
fun TitleScreen(
    scope: CoroutineScope,
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    snackbarHostState: SnackbarHostState,
    deviceType: WindowSizeClass,
    onCharSelect: () -> Unit,
    onProfileSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("--Loading title screen--")

    val currentUser by authViewModel.signInState.collectAsStateWithLifecycle()
    val userDetails by userViewModel.userDetailsState.collectAsStateWithLifecycle()

    Timber.d("Current User: $currentUser")
    Timber.d("User Details: $userDetails")

    val uiScale = if (deviceType.heightSizeClass == WindowHeightSizeClass.Compact) 2 else 1
    val scope = rememberCoroutineScope()
    var showUserDialog by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.fighting_flow_title_logo),
            contentDescription = stringResource(R.string.fighting_flow_logo),
            modifier = Modifier
                .size(if (uiScale == 2) 150.dp else 400.dp)
                .padding(end = if (uiScale != 2) 10.dp else 0.dp)
        )

        Timber.d("Checking if user is logged in...")
        if (currentUser is GoogleAuthService.SignInState.Success) {
            Timber.d("User is logged in, loading greeting...")
            val username = (currentUser as GoogleAuthService.SignInState.Success)
                .user.displayName?.split(" ")[0] ?: "Invalid Username"
            Text(
                text = "Welcome $username",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = if (uiScale == 2) 25.sp else 30.sp,
                color = Color.White,
                modifier = modifier.padding(bottom = if (uiScale != 2) 20.dp else 0.dp)
            )
        }
        else {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.please_create_a_profile),
                    modifier = modifier.padding(horizontal = 32.dp)
                )
            }
        }

        LazyColumn {
            Timber.d("Loading buttons...")
            item {
                when (currentUser) {
                    is GoogleAuthService.SignInState.Success -> {
                        AccessButton(
                            buttonText = stringResource(R.string.char_select),
                            onClick = onCharSelect,
                            modifier = modifier.padding(8.dp)
                        )
                        AccessButton(
                            buttonText = stringResource(R.string.your_details),
                            onClick = onProfileSelect,
                            modifier = modifier.padding(8.dp)
                        )

                        LaunchedEffect(currentUser) {
                            if (currentUser is GoogleAuthService.SignInState.Success) {
                                Timber.d("Launched Effect triggered on successful sign in.")

                                userViewModel.getUserFromFb(
                                    (currentUser as GoogleAuthService.SignInState.Success)
                                        .user.userId
                                )
                            }
                        }

                        LaunchedEffect(userDetails, currentUser) {
                            Timber.d(
                                "User Details: %s\nCurrent User: %s",
                                userDetails,
                                currentUser
                            )
                            when (userDetails) {
                                is UserDetailsState.NotFound -> {
                                    Timber.d("User data is null, opening user details dialog.")
                                    showUserDialog = true
                                }

                                is UserDetailsState.Loaded -> {
                                    Timber.d("User data exists, logging in user as normal.")
                                    showUserDialog = false
                                }

                                is UserDetailsState.Loading -> {
                                    Timber.d("User Details are not loaded yet.")
                                    showUserDialog = false
                                }

                                is UserDetailsState.Error -> {
                                    Timber.e(
                                        (userDetails as UserDetailsState.Error).e,
                                        "Error loading User Details"
                                    )
                                }

                                else -> Timber.d("User Details are not loaded or idle.")
                            }
                        }
                    }

                    else -> {
                        Spacer(modifier.height(20.dp))
                        UserCreationForm(
                            userViewModel = userViewModel,
                            authViewModel = authViewModel,
                        )
                    }
                }
            }
        }
    }
    if (showUserDialog) {
        UsernameAndDobDialog(
            scope = scope,
            userViewModel = userViewModel,
            currentUser = currentUser,
            dismissDialog = { showUserDialog = false }
        )
    }
}

@Composable
fun AccessButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color.White),
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth(0.8F)
    ) {
        Text(
            text = buttonText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameAndDobDialog(
    scope: CoroutineScope,
    userViewModel: UserViewModel,
    currentUser: GoogleAuthService.SignInState,
    dismissDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    val dob = rememberDatePickerState()
    val selectedDate = dob.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""
    var showDatePicker by remember { mutableStateOf(false) }


    BasicAlertDialog(onDismissRequest = { dismissDialog }) {
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
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = modifier.padding(vertical = 16.dp)
                )

                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = { selectedDate },
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
                )
                OutlinedButton(
                    onClick = {
                        dismissDialog()
                        scope.launch {
                            val currentUserState = (currentUser as GoogleAuthService.SignInState.Success)
                            userViewModel.updateNewUserState(UserEntry(
                                userId = currentUserState.user.userId,
                                username = username,
                                email = currentUserState.user.email,
                                profilePic = currentUserState.user.photo.toString(),
                                dateCreated = LocalDate.now().toString(),
                            ))
                            val result = userViewModel.createUser()
                            when (result) {
                                is UserSaveResult.Success -> { dismissDialog() }
                                is UserSaveResult.Error -> { Timber.e(result.e, "Error saving user.") }
                            } } },
                    modifier.padding(top = 16.dp)
                ) { Text("Confirm Details") }
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
