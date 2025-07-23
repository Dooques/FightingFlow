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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun UserCreationForm(
    scope: CoroutineScope,
    authViewModel: AuthViewModel,
    showEmailPasswordDialog: () -> Unit,
    createAccountDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentUser by authViewModel.signInState.collectAsStateWithLifecycle()
    Timber.d("Loading profile creation form...")

    Column(modifier.fillMaxHeight()) {
        Timber.d("Loading username field...")

        Spacer(modifier.height(20.dp))

        Timber.d("Loading confirm button...")
        when (val state = currentUser) {
            GoogleAuthService.SignInState.Idle -> {

                // Google Sign In / Create
                OutlinedButton(
                    onClick = { scope.launch { authViewModel.initiateGoogleSignIn() } },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                ) {
                    Text("Sign In With Google", color = MaterialTheme.colorScheme.onBackground)
                }

                // Sign In With Email
                OutlinedButton(
                    onClick = { showEmailPasswordDialog() },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                ) {
                    Text("Sign In With Email", color = MaterialTheme.colorScheme.onBackground)
                }

                // Create with Email
                OutlinedButton(
                    onClick = { createAccountDialog() },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                ) {
                    Text("Create An Account With Email", color = MaterialTheme.colorScheme.onBackground)
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
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                authViewModel.initiateGoogleSignIn()
                            } },
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text("Retry Google Sign-In", color = MaterialTheme.colorScheme.onBackground)
                    }
                    OutlinedButton(
                        onClick = { showEmailPasswordDialog() },
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text("Retry Email Sign-In", color = MaterialTheme.colorScheme.onBackground)
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

        Spacer(modifier.size(height = 50.dp, width = 0.dp))

        Timber.d("Form Loaded.")
    }
}
