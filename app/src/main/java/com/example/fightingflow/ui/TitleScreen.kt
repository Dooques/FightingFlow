package com.example.fightingflow.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.fightingflow.ui.profileScreen.ProfileCreationForm
import com.example.fightingflow.ui.profileScreen.ProfileViewModel
import com.example.fightingflow.util.ProfileCreationUiState
import com.example.fightingflow.util.TITLE_TAG
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun TitleScreen(
    profileViewModel: ProfileViewModel,
    snackbarHostState: SnackbarHostState,
    deviceType: WindowSizeClass,
    isLoggedIn: Boolean,
    username: String,
    onCharSelect: () -> Unit,
    onProfileSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d(TITLE_TAG, "")
    Log.d(TITLE_TAG, "Loading title screen...")

    val profilesList by profileViewModel.allExistingProfiles.collectAsStateWithLifecycle()
    val profileCreation by profileViewModel.profileState.collectAsStateWithLifecycle()

    Log.d(TITLE_TAG, "Profile List: ${profilesList.profileList}")

    val uiScale = if (deviceType.heightSizeClass == WindowHeightSizeClass.Compact) 2 else 1
    val scope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Log.d(TITLE_TAG, "Loading logo...")
        Image(
            painter = painterResource(R.drawable.fighting_flow_title_logo),
            contentDescription = "Fighting Flow Logo",
            modifier = Modifier
                .size(if (uiScale == 2) 150.dp else 400.dp)
                .padding(end = if (uiScale != 2) 10.dp else 0.dp)
        )

        Log.d(TITLE_TAG, "Checking if user is logged in...")
        if (isLoggedIn) {
            Log.d(TITLE_TAG, "User is logged in.")
            Log.d(TITLE_TAG, "Loading greeting...")
            Text(
                text = "Welcome ${username.replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = if (uiScale == 2) 25.sp else 30.sp,
                color = Color.White,
                modifier = modifier.padding(bottom = if (uiScale != 2) 20.dp else 0.dp)
            )
        }
        LazyColumn {
            Log.d(TITLE_TAG, "Loading buttons...")
             item {
                 if (profilesList.profileList.isEmpty()) {
                     Row(Modifier.fillMaxWidth()) {
                         Text(
                             text = "Welcome to Fighting Flow, please create a profile so we can start creating some combos!",
                             modifier = modifier.padding(horizontal = 16.dp)
                         )
                     }
                     ProfileCreationForm(
                         updateCurrentProfile = { profileViewModel.updateProfileCreation(profileCreation) },
                         profile = profileCreation,
                         onConfirm = {
                             scope.launch {
                                 Log.d(TITLE_TAG, "")
                                 Log.d(
                                     TITLE_TAG,
                                     "Preparing to save ${profileCreation.profileCreation.username}'s profile to datastore..."
                                 )
                                 val saveProfileSuccess = profileViewModel.saveProfileData()
                                 Log.d(TITLE_TAG, "Profile saved to Ds.")

                                 if (saveProfileSuccess == "Success") {
                                     Log.d(TITLE_TAG, "Saving Profile to database...")
                                     profileViewModel.saveProfileToDb()
                                     Log.d(TITLE_TAG, "Profile saved to Db.")

                                     Log.d(TITLE_TAG, "Logging in profile...")
                                     profileViewModel.loginProfile()
                                     Log.d(TITLE_TAG, "Profile logged in.")
                                     Log.d(TITLE_TAG, "Returning to title screen...")
                                 } else {
                                     snackbarHostState.showSnackbar("Passwords do not match, please try again")
                                 }
                             }
                         }
                     )
                 } else {
                     AccessButton(
                         buttonText = stringResource(R.string.char_select),
                         onClick = onCharSelect,
                         modifier = modifier.padding(8.dp)
                     )
                     AccessButton(
                         buttonText = stringResource(R.string.profile_select),
                         onClick = onProfileSelect,
                         modifier = modifier.padding(8.dp)
                     )
                 }
            }
        }
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