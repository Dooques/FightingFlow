package com.example.fightingflow.ui.profileScreen

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.fightingflow.util.PROFILE_SCREEN_TAG
import com.example.fightingflow.util.ProfileCreationUiState
import com.example.fightingflow.util.emptyProfile


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    profileViewModel: ProfileViewModel,
    navigateBack: () -> Unit,
    updateCurrentUser: (ProfileCreationUiState) -> Unit,
) {
    Log.d(PROFILE_SCREEN_TAG, "")
    Log.d(PROFILE_SCREEN_TAG, "Loading profile screen...")

    val currentProfile by profileViewModel.currentProfile.collectAsState()
    val loggedInState by profileViewModel.loggedInState.collectAsState()
    val existingProfiles by profileViewModel.allExistingProfiles.collectAsState()

    val profileCreation by profileViewModel.profileState.collectAsState()
    val combosByProfile by profileViewModel.combosByProfile.collectAsState()
    Log.d(
        PROFILE_SCREEN_TAG, "Flows Collected:" +
                "\nloggedInState: $loggedInState" +
                "\nprofileCreation: ${profileCreation.profileCreation}" +
                "\ncurrentProfile: ${currentProfile.profile}" +
                "\ncombosByProfile: ${combosByProfile.comboDisplayList}" +
                "\nexistingProfiles: ${existingProfiles.profileList}"
    )

    var editProfile by remember { mutableStateOf(false) }

    Log.d(PROFILE_SCREEN_TAG, "Checking if valid profile exists...")
    if (existingProfiles.profileList.isEmpty() || editProfile) {
        Log.d(PROFILE_SCREEN_TAG, "No profiles exist, preparing to load ProfileCreationUi...")
        ProfileCreationUi(
            profileViewModel = profileViewModel,
            snackBarHostState = snackbarHostState,
            updateCurrentUser = updateCurrentUser,
            navigateBack = navigateBack,
            profile = profileCreation,
        )
    } else {
        ProfileList(
            profileList = existingProfiles,
            editProfileStateChange = { editProfile = !editProfile},
            navigateBack = navigateBack
        )
        Log.d(PROFILE_SCREEN_TAG, "Profile screen loaded.")
    }
    Log.d(PROFILE_SCREEN_TAG, "Profile exists, preparing to load ProfileDetailsUi...")
    ProfileDetailsUi(
        profileViewModel = profileViewModel,
        isProfileLoggedIn = loggedInState,
        currentProfile = currentProfile,
        combosByProfile = combosByProfile,
        navigateBack = navigateBack,
    )
}

