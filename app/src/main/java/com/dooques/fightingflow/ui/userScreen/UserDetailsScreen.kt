package com.dooques.fightingflow.ui.userScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.dooques.fightingflow.data.firebase.GoogleAuthService
import com.dooques.fightingflow.ui.userScreen.dialogs.EmailAndPasswordDialog
import com.dooques.fightingflow.ui.userScreen.dialogs.ReauthDialog
import com.dooques.fightingflow.ui.viewmodels.AuthViewModel
import com.dooques.fightingflow.ui.viewmodels.ProfanityViewModel
import com.dooques.fightingflow.ui.viewmodels.UserDetailsState
import com.dooques.fightingflow.ui.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    profanityViewModel: ProfanityViewModel,
    navigateBack: () -> Unit,
    navigateHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("--Loading user details--")

    var showReauthDialog by remember { mutableStateOf(false) }
    var showEmailPasswordDialog by remember { mutableStateOf(false) }

    val currentUser by authViewModel.signInState.collectAsStateWithLifecycle()
    val userDetails by userViewModel.userDetailsState.collectAsStateWithLifecycle()

    var menuExpanded by remember { mutableStateOf(false) }

    Timber.d("Flows Collected: \nCurrent User: %s\nUser Details: %s",
        currentUser, userDetails)

    when (currentUser) {
        is GoogleAuthService.SignInState.Idle -> navigateBack()
        else -> null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Your Details",
                        style = MaterialTheme.typography.displayMedium,
                        modifier = modifier.padding(start = 16.dp)
                    ) },
                navigationIcon = {
                    IconButton(
                        onClick = { navigateBack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Return to Character Select",
                            modifier = modifier.size(50.dp)
                        )
                    } },) },
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { contentPadding ->

        Column(
            modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Spacer(modifier.size(20.dp))
            when (currentUser) {
                is GoogleAuthService.SignInState.Success -> {
                        when (userDetails) {
                            is UserDetailsState.Loaded -> {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    Box(Modifier.fillMaxWidth().padding(horizontal = 32.dp)) {
                                        if (userDetails is UserDetailsState.Loaded) {
                                            Text(
                                                text = (userDetails as UserDetailsState.Loaded).user.username.toString(),
                                                style = MaterialTheme.typography.displayMedium,
                                                modifier = modifier.align(CenterStart)
                                            )
                                        }
                                        Box(
                                            modifier = modifier.align(CenterEnd)
                                        ) {
                                            AsyncImage(
                                                model = (currentUser as GoogleAuthService.SignInState.Success).user.photo,
                                                contentDescription = "User Image",
                                                modifier = modifier
                                                    .size(80.dp)
                                                    .clip(CircleShape)
                                                    .clickable(onClick = { menuExpanded = true })
                                            )
                                            ChangeProfileImage(
                                                menuExpanded,
                                                onDismissRequest = { menuExpanded = false })
                                        }
                                    }
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        "Name: ${(userDetails as UserDetailsState.Loaded).user.name}",
                                        modifier = modifier.padding(horizontal = 32.dp)
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        "Email: ${(currentUser as GoogleAuthService.SignInState.Success).user.email.toString()}",
                                        modifier = modifier.padding(horizontal = 32.dp)
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        "Date of Birth: ${(userDetails as UserDetailsState.Loaded).user.dob}",
                                        modifier = modifier.padding(horizontal = 32.dp)
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        "Date Created: ${(userDetails as UserDetailsState.Loaded).user.dateCreated}",
                                        modifier = modifier.padding(horizontal = 32.dp)
                                    )
                                }
                                Spacer(modifier.size(20.dp))
                            }

                            else -> {
                                val currentUserState =
                                    currentUser as GoogleAuthService.SignInState.Success
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        "Name: ${currentUserState.user.displayName}",
                                        modifier = modifier.padding(horizontal = 32.dp)
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        "Email: ${currentUserState.user.email.toString()}",
                                        modifier = modifier.padding(horizontal = 32.dp)
                                    )
                                }
                            }
                        }

                    OutlinedButton(
                        onClick = {
                            Timber.d("Logging out %s", (currentUser as GoogleAuthService.SignInState.Success).user.displayName)
                            scope.launch {
                                authViewModel.signOut()

                                navigateBack()
                            }
                        },
                        modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp, vertical = 8.dp)
                    ) {
                        Text("Log Out", color = Color.White)
                    }
                    OutlinedButton(
                        onClick = {
                            Timber.d("Deleting current User...")
                            scope.launch {
                                userViewModel.deleteUser((currentUser as GoogleAuthService.SignInState.Success).user.userId)
                                val result = authViewModel.deleteUser()
                                if (result != null) {
                                    when {
                                        result.isSuccess -> {
                                            navigateBack()
                                        }

                                        result.isFailure -> {
                                            val exception = result.exceptionOrNull()
                                            if (exception is FirebaseAuthRecentLoginRequiredException) {
                                                showReauthDialog = true
                                                val resultReauth = authViewModel.deleteUser()
                                                if (resultReauth != null) {
                                                    when {
                                                        resultReauth.isSuccess -> {
                                                            userViewModel.deleteUser((currentUser as GoogleAuthService.SignInState.Success).user.userId)
                                                            authViewModel.signOut()
                                                            navigateBack()
                                                        }

                                                        resultReauth.isFailure -> Timber.e(
                                                            resultReauth.exceptionOrNull(),
                                                            "Error deleting user"
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp, vertical = 8.dp)
                    ) {
                        Text("Delete Account", color = Color.White)
                    }
                }

                else -> {
                    Row(Modifier.padding(horizontal = 16.dp)) {
                        Text("Not currently logged in")
                    }
                }
            }
        }
        if (showReauthDialog) {
            ReauthDialog(
                scope = scope,
                authViewModel = authViewModel,
                userViewModel = userViewModel,
                showEmailPasswordDialog = { showEmailPasswordDialog = true },
                onDismissRequest = { showReauthDialog = false },
            )
        }
        if (showEmailPasswordDialog) {
            EmailAndPasswordDialog(
                userViewModel = userViewModel,
                authViewModel = authViewModel,
                profanityViewModel = profanityViewModel,
                createAccount = false,
                snackbarHostState = snackBarHostState,
                onDismissRequest = { showEmailPasswordDialog = false },
            )
        }
    }
}

@Composable
fun ChangeProfileImage(menuExpanded: Boolean, onDismissRequest: () -> Unit) {
    DropdownMenu(expanded = menuExpanded, onDismissRequest = onDismissRequest) {
        DropdownMenuItem(
            text = { Text("Change Profile Photo") },
            onClick = {}
        )
    }
}