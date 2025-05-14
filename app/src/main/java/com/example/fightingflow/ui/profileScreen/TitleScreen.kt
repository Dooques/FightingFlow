package com.example.fightingflow.ui.profileScreen

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
import kotlinx.coroutines.launch
import timber.log.Timber

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
    Timber.d("Loading title screen...")

    val profilesList by profileViewModel.allExistingProfiles.collectAsStateWithLifecycle()
    val profileCreation by profileViewModel.profileState.collectAsStateWithLifecycle()

    Timber.d("Profile List: ${profilesList.profileList}")

    val uiScale = if (deviceType.heightSizeClass == WindowHeightSizeClass.Compact) 2 else 1
    val scope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Timber.d("Loading logo...")
        Image(
            painter = painterResource(R.drawable.fighting_flow_title_logo),
            contentDescription = "Fighting Flow Logo",
            modifier = Modifier
                .size(if (uiScale == 2) 150.dp else 400.dp)
                .padding(end = if (uiScale != 2) 10.dp else 0.dp)
        )

        Timber.d("Checking if user is logged in...")
        if (isLoggedIn) {
            Timber.d("User is logged in.")
            Timber.d("Loading greeting...")
            Text(
                text = "Welcome ${username.replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = if (uiScale == 2) 25.sp else 30.sp,
                color = Color.White,
                modifier = modifier.padding(bottom = if (uiScale != 2) 20.dp else 0.dp)
            )
        }
        LazyColumn {
            Timber.d("Loading buttons...")
             item {
                 if (profilesList.profileList.isEmpty()) {
                     Row(Modifier.fillMaxWidth()) {
                         Text(
                             text = "Welcome to Fighting Flow, please create a " +
                                     "profile so we can start creating some combos!",
                             modifier = modifier.padding(horizontal = 16.dp)
                         )
                     }
                     ProfileCreationForm(
                         updateCurrentProfile = {
                             profileViewModel.updateProfileCreation(it)
                         },
                         profile = profileCreation,
                         onConfirm = {
                             scope.launch {
                                 Timber.d("Preparing to save ${profileCreation.profileCreation.username}'s profile to datastore...")
                                 val saveProfileSuccess = profileViewModel.saveProfileData()
                                 Timber.d("Profile saved to Ds.")

                                 if (saveProfileSuccess != "Success") {
                                     scope.launch {
                                         snackbarHostState.showSnackbar("Passwords do not match, please try again")
                                     }
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