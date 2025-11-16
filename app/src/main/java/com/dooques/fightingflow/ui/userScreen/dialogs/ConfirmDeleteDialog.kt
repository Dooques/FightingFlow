package com.dooques.fightingflow.ui.userScreen.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dooques.fightingflow.data.firebase.GoogleAuthService
import com.dooques.fightingflow.ui.viewmodels.AuthViewModel
import com.dooques.fightingflow.ui.viewmodels.UserDetailsState
import com.dooques.fightingflow.ui.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDeleteDialog(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    currentUser: GoogleAuthService.SignInState,
    userDetailsState: UserDetailsState,
    showReauthDialog: () -> Unit,
    navigateBack: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("--Confirm User Dialog--")
    var confirmUsername by remember { mutableStateOf("") }

    var confirmUserError by remember { mutableStateOf(false) }
    val confirmErrorText = "Usernames do not match"

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            colors = CardDefaults.cardColors().copy(containerColor = Color.Black),
            border = CardDefaults.outlinedCardBorder(true)
        ) {
            Column(
                modifier = modifier.padding(24.dp)
            ) {

                Text(
                    "Are you sure you want to delete your account?",
                    modifier = modifier.padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = confirmUsername,
                    onValueChange = { confirmUsername = it },
                    label = { Text(if (confirmUserError) confirmErrorText else "Confirm your username") },
                    isError = confirmUserError,
                    modifier = modifier.padding(vertical = 8.dp)
                )

                Row {
                    OutlinedButton(
                        onClick = {
                            Timber.d(" Checking confirmed username matches user details")
                            if (userDetailsState is UserDetailsState.Loaded &&
                                userDetailsState.user.username.lowercase() == confirmUsername.lowercase()
                            ) {
                                Timber.d(" Username matches, launching coroutine")
                                scope.launch {
                                    val userId = (currentUser as GoogleAuthService.SignInState.Success).user.userId
                                    Timber.d(" Preparing to delete user")
                                    try {
                                        userViewModel.deleteUser(userId).getOrThrow()
                                        Timber.d(" Successfully deleted User Details, deleting auth user")
                                        authViewModel.deleteAuthUser().getOrThrow()
                                        Timber.d(" Successfully deleted Auth user")

                                        Timber.d(" Deletion process successful, navigating back to Title Screen")
                                        navigateBack()
                                    } catch (e: Exception) {
                                        Timber.e(e, " User deletion failed, reauthenticating user..." +
                                                "\n Dismissing Window")
                                        if (e is FirebaseAuthRecentLoginRequiredException) {
                                            showReauthDialog()
                                            onDismissRequest()
                                        } else {
                                            Timber.e(e, " An error occurred during deletion process.")
                                            snackbarHostState.showSnackbar(
                                                message = "Error deleting user account: $e",
                                                withDismissAction = true,
                                                duration = SnackbarDuration.Indefinite
                                            )
                                            onDismissRequest()
                                        }
                                    }
                                }
                            } else {
                                Timber.d(" Error confirming user")
                                confirmUserError = true
                            }
                        },
                        modifier = modifier.padding(end = 8.dp)
                    ) { Text("Confirm Delete", color = MaterialTheme.colorScheme.onBackground) }

                    OutlinedButton(
                        onClick = { onDismissRequest() }
                    ) { Text("Cancel", color = MaterialTheme.colorScheme.onBackground) }
                }
            }
        }
    }
}
