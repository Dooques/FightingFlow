package com.example.fightingflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fightingflow.R
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.example.fightingflow.ui.userScreen.UserCreationForm
import com.example.fightingflow.ui.userScreen.dialogs.EmailAndPasswordDialog
import com.example.fightingflow.ui.userScreen.dialogs.UserDetailsDialog
import com.example.fightingflow.viewmodels.AuthViewModel
import com.example.fightingflow.viewmodels.UserDetailsState
import com.example.fightingflow.viewmodels.UserViewModel
import timber.log.Timber

@Composable
fun TitleScreen(
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

    Timber.d("Current User: %s\nUser Details: %s", currentUser, userDetails)

    val uiScale = if (deviceType.heightSizeClass == WindowHeightSizeClass.Compact) 2 else 1
    val scope = rememberCoroutineScope()

    var showUserDetailsDialog by remember { mutableStateOf(false) }
    var showEmailPasswordDialog by remember { mutableStateOf(false) }
    var createAccountState by remember { mutableStateOf(false) }

    Timber.d("Flows Collected:\nshowUserDetailsDialog: %s\nshowEmailDialog: %s\ncreateAccountState: %s",
        showUserDetailsDialog, showEmailPasswordDialog, createAccountState)

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
        if (userDetails is UserDetailsState.Loaded && currentUser is GoogleAuthService.SignInState.Success) {
            Timber.d("User is logged in, loading greeting...")
            val username = (userDetails as UserDetailsState.Loaded)
                .user.username?.replaceFirstChar { it.uppercase() } ?: "Invalid Username"
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
                                userViewModel.getUserDetailsFromFb((currentUser as GoogleAuthService.SignInState.Success).user.userId)
                            }
                        }

                        LaunchedEffect(userDetails) {
                            Timber.d(
                                "User Details: %s\nCurrent User: %s",
                                userDetails, currentUser
                            )
                            when (userDetails) {
                                is UserDetailsState.NotFound -> {
                                    Timber.d("User data is null, opening user details dialog.")
                                    showUserDetailsDialog = true
                                }

                                is UserDetailsState.Loaded -> {
                                    Timber.d("User data exists, logging in user as normal.")
                                    showUserDetailsDialog = false
                                }

                                is UserDetailsState.Loading -> {
                                    Timber.d("User Details are not loaded yet.")
                                    showUserDetailsDialog = false
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
                            scope = scope,
                            authViewModel = authViewModel,
                            showEmailPasswordDialog = { showEmailPasswordDialog = true; createAccountState = false },
                            createAccountDialog = { showEmailPasswordDialog = true; createAccountState = true },
                        )
                    }
                }
            }
        }
    }
    if (showUserDetailsDialog && !createAccountState) {
        UserDetailsDialog(
            scope = scope,
            userViewModel = userViewModel,
            currentUser = currentUser,
            userDetailsState = userDetails,
            onDismissDialog = { showUserDetailsDialog = false }
        )
    }
    if (showEmailPasswordDialog) {
        EmailAndPasswordDialog(
            userViewModel = userViewModel,
            authViewModel = authViewModel,
            createAccount = createAccountState,
            snackbarHostState = snackbarHostState,
            onDismissRequest = {
                showEmailPasswordDialog = false
                createAccountState = false
            }
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

