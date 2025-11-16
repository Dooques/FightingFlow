package com.dooques.fightingflow.ui.userScreen.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dooques.fightingflow.model.UserEntry
import com.dooques.fightingflow.ui.userScreen.abstractedFunctions.signInByEmail
import com.dooques.fightingflow.ui.viewmodels.AuthViewModel
import com.dooques.fightingflow.ui.viewmodels.UserViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailSignInDialog(
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    createAccount: Boolean,
    showConfirmDialog: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showEmailError by remember { mutableStateOf(false) }
    var showPasswordError by remember { mutableStateOf(false) }
    var showShortPasswordError by remember {mutableStateOf(false) }

    var hidePassword by remember { mutableStateOf(true) }

    val newUserDetails by userViewModel.newUserDetailsState.collectAsStateWithLifecycle()

    BasicAlertDialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            colors = CardDefaults.cardColors().copy(containerColor = Color.Black),
            border = CardDefaults.outlinedCardBorder(true)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(24.dp)
            ) {
                Text("Sign In With Email", style = MaterialTheme.typography.displaySmall)

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
                            } } },
                    label = { Text(
                        if (showEmailError) "Please enter an email"
                        else "Email") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    isError = showEmailError,
                    modifier = modifier.padding(4.dp)
                )

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

                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth().padding(top = 8.dp)) {
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                val result = signInByEmail(
                                    authViewModel = authViewModel,
                                    userViewModel = userViewModel,
                                    email = email,
                                    password = password,
                                    showEmailError = { showEmailError = true },
                                    showPasswordError = { showPasswordError = true },
                                    onDismissRequest = onDismissRequest
                                )
                                result
                                    .onSuccess {
                                        showConfirmDialog()

                                }
                                    .onFailure {
                                        Timber.e(result.exceptionOrNull(), "Failure signing in...")
                                    }
                            }
                        }
                    ) { Text("Confirm") }

                    OutlinedButton(onClick = {
                        onDismissRequest()
                        userViewModel.updateNewUserState(UserEntry())
                    }) { Text("Cancel") }
                }
            }
        }
    }
}
