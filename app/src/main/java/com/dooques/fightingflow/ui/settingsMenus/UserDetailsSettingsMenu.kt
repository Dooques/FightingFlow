package com.dooques.fightingflow.ui.settingsMenus

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.dooques.fightingflow.R
import com.dooques.fightingflow.data.firebase.GoogleAuthService
import com.dooques.fightingflow.ui.viewmodels.AuthViewModel
import com.dooques.fightingflow.ui.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun UserDetailsSettingsMenu(
    scope: CoroutineScope,
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    currentUser: GoogleAuthService.SignInState,
    navigateBack: () -> Unit,
    showReauthDialog: () -> Unit,
) {

    var userSettingsMenuExpanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { userSettingsMenuExpanded = !userSettingsMenuExpanded }
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "User Settings Menu"
        )
        Box {
            DropdownMenu(
                expanded = userSettingsMenuExpanded,
                onDismissRequest = { userSettingsMenuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Sign Out") },
                    onClick = {
                        scope.launch {
                            authViewModel.signOut()
                            navigateBack()
                        }
                    }
                )
                DropdownMenuItem(
                    text = { Text("Delete Account") },
                    onClick = {
                        Timber.d("Deleting current User...")
                        scope.launch {
                            userViewModel.deleteUser((currentUser as GoogleAuthService.SignInState.Success).user.userId)
                            val result = authViewModel.deleteUser()
                            userViewModel.checkUserAuthenticated(
                                result = result,
                                currentUser = currentUser,
                                navigateBack = navigateBack,
                                showReauthDialog = showReauthDialog,
                            )
                        }
                    },
                )

            }
        }
    }
}
