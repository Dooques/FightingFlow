package com.example.fightingflow.ui.userScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.example.fightingflow.viewmodels.AuthViewModel
import com.example.fightingflow.viewmodels.UserViewModel
import timber.log.Timber

@Composable
fun UserCreationForm(
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    var showUsernameError by remember { mutableStateOf(false) }
    var showSignupForm by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("")}

    val currentUser by authViewModel.signInState.collectAsStateWithLifecycle()
    val newUserDetails by userViewModel.newUserState.collectAsStateWithLifecycle()
    Timber.d("Loading profile creation form...")

    Column(modifier.fillMaxHeight()) {
        Timber.d("Loading username field...")
        if (showSignupForm) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        if (username.length <= 20) {
                            username = it
                            showUsernameError = false
                            userViewModel.updateNewUserState(newUserDetails?.copy(username = username)!!)
                        } },
                    label = { Text(
                        if (showUsernameError) "Please enter a username."
                        else "Username") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    isError = showUsernameError,
                    modifier = modifier.fillMaxWidth(0.7f)
                )
            }
        }

        Spacer(modifier.height(20.dp))

        Timber.d("Loading confirm button...")
        when (val state = currentUser) {
            GoogleAuthService.SignInState.Idle -> {
                OutlinedButton(
                    onClick = { authViewModel.initiateGoogleSignIn() },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                ) {
                    Text(
                        "Sign in with Google",
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
                Timber.d("User successfully signed in.")
            }

            is GoogleAuthService.SignInState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.fillMaxWidth().padding(32.dp)
                ) {
                    Text(
                        "Error: ${state.message}.",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = { authViewModel.initiateGoogleSignIn() },
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text("Retry Sign-In", color = MaterialTheme.colorScheme.onBackground)
                    }
                }
            }
        }


        Spacer(modifier.size(height = 50.dp, width = 0.dp))

        Timber.d("Form Loaded.")
    }
}