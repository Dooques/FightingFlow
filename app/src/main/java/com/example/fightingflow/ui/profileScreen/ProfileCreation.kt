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
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.util.PROFILE_SCREEN_TAG
import com.example.fightingflow.util.ProfileCreationUiState
import kotlinx.coroutines.launch

@Composable
fun ProfileCreationUi(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel,
    snackBarHostState: SnackbarHostState,
    updateCurrentUser: (ProfileCreationUiState) -> Unit,
    navigateBack: () -> Unit,
    profile: ProfileCreationUiState
) {
    Log.d(PROFILE_SCREEN_TAG, "")
    Log.d(PROFILE_SCREEN_TAG, "Loading ProfileCreationUi...")
    val scope = rememberCoroutineScope()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Log.d(PROFILE_SCREEN_TAG, "Loading Title...")
        Text(
            text = "Create a profile",
            color = Color.White,
            fontSize = 32.sp,
            modifier = modifier.padding(vertical = 16.dp)
        )
        Log.d(PROFILE_SCREEN_TAG, "Loading ProfileCreationForm...")
        ProfileCreationForm(
            updateCurrentProfile = updateCurrentUser,
            profile = profile,
            onConfirm = {
                scope.launch {
                    Log.d(PROFILE_SCREEN_TAG, "")
                    Log.d(PROFILE_SCREEN_TAG, "Preparing to save ${profile.profileCreation.username}'s profile to datastore...")
                    val saveProfileSuccess = profileViewModel.updateCurrentUserInDs()
                    Log.d(PROFILE_SCREEN_TAG, "Profile saved to Ds.")
                    if (saveProfileSuccess == "Success") {
                        Log.d(PROFILE_SCREEN_TAG, "Saving Profile to database...")
                        profileViewModel.saveProfileToDb()
                        Log.d(PROFILE_SCREEN_TAG, "Profile saved to Db.")
                        Log.d(PROFILE_SCREEN_TAG, "Logging in profile...")
                        profileViewModel.loginProfile()
                        Log.d(PROFILE_SCREEN_TAG, "Profile logged in.")
                        Log.d(PROFILE_SCREEN_TAG, "Returning to title screen...")
                        navigateBack()
                    } else {
                        snackBarHostState.showSnackbar("Passwords do not match, please try again")
                    }
                }
            }
        )
    }
}

@Composable
fun ProfileCreationForm(
    updateCurrentProfile: (ProfileCreationUiState) -> Unit,
    profile: ProfileCreationUiState,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d(PROFILE_SCREEN_TAG, "")
    Column(modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
        Spacer(modifier.size(height = 40.dp, width = 0.dp))
        Log.d(PROFILE_SCREEN_TAG, "Loading username field...")
        TextInputField("username",
            { username -> updateCurrentProfile(ProfileCreationUiState(profile.profileCreation.copy(username = username))) }
        )

        Log.d(PROFILE_SCREEN_TAG, "Loading password field...")
        TextInputField("password",
            { password -> updateCurrentProfile(ProfileCreationUiState(profile.profileCreation.copy(password = password))) }
        )

        Log.d(PROFILE_SCREEN_TAG, "Loading confirm password field...")
        TextInputField("Confirm\nPassword",
            { confirmPassword -> updateCurrentProfile(ProfileCreationUiState(profile.profileCreation.copy(confirmPassword = confirmPassword))) }
        )

        Spacer(modifier.size(height = 40.dp, width = 0.dp))
        Log.d(PROFILE_SCREEN_TAG, "Loading confirm button...")
        OutlinedButton(
            onClick = onConfirm,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) { Text("Confirm", color = Color.White) }
        Spacer(modifier.size(height = 100.dp, width = 0.dp))
        Log.d(PROFILE_SCREEN_TAG, "Form Loaded.")
    }
}

@Composable
fun TextInputField(
    type: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val typeCap = type.replaceFirstChar { it.uppercaseChar() }
    var inputText by remember { mutableStateOf("") }

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
        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                onValueChange(inputText)
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
    }
}