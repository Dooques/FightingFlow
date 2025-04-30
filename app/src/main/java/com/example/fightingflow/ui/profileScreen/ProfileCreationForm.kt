package com.example.fightingflow.ui.profileScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.fightingflow.util.PROFILE_SCREEN_TAG
import com.example.fightingflow.util.ProfileCreationUiState
import timber.log.Timber

@Composable
fun ProfileCreationForm(
    updateCurrentProfile: (ProfileCreationUiState) -> Unit,
    profile: ProfileCreationUiState,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("")
    Timber.d("Loading profile creation form...")

    Column(modifier.fillMaxHeight()) {
        Timber.d("Loading username field...")
        TextInputField(
            type = "username",
            updateProfileState = { username ->
                updateCurrentProfile(
                    ProfileCreationUiState(
                        profileCreation = profile.profileCreation.copy(username = username)
                    )) },
            modifier = modifier.padding(4.dp))

        Timber.d("Loading password field...")
        TextInputField(
            type = "password",
            updateProfileState = { password ->
                updateCurrentProfile(
                    ProfileCreationUiState(
                        profileCreation = profile.profileCreation.copy(password = password)
                )) },
            modifier.padding(4.dp)
        )

        Timber.d("Loading confirm password field...")
        TextInputField(
            type = "Confirm\nPassword",
            updateProfileState = { confirmPassword ->
                updateCurrentProfile(
                    ProfileCreationUiState(profileCreation = profile.profileCreation.copy(confirmPassword = confirmPassword)
                    )) },
            modifier = modifier.padding(4.dp)
        )

        Spacer(modifier.size(height = 20.dp, width = 0.dp))
        Timber.d("Loading confirm button...")
        OutlinedButton(
            onClick = onConfirm,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) { Text("Confirm", color = Color.White) }
        Spacer(modifier.size(height = 50.dp, width = 0.dp))
        Timber.d("Form Loaded.")
    }
}

@Composable
fun TextInputField(
    type: String,
    updateProfileState: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val typeCap = type.replaceFirstChar { it.uppercaseChar() }
    var inputText by remember { mutableStateOf("") }
    val isItPassword = (type == "password" || type == "Confirm\nPassword")

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = typeCap,
            color = Color.White
        )
        if (isItPassword) {
            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                    updateProfileState(inputText)
                },
                visualTransformation = PasswordVisualTransformation(),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
        } else {
            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                    updateProfileState(inputText)
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
        }
    }
}