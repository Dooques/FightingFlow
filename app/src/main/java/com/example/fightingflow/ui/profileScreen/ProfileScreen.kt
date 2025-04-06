package com.example.fightingflow.ui.profileScreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.fightingflow.util.PROFILE_SCREEN_TAG
import com.example.fightingflow.util.ProfileCreationUiState
import com.example.fightingflow.util.emptyProfile


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel,
    navigateBack: () -> Unit,
    updateCurrentUser: (ProfileCreationUiState) -> Unit,
) {
    Log.d(PROFILE_SCREEN_TAG, "")
    Log.d(PROFILE_SCREEN_TAG, "Loading profile screen...")

    val loggedInState by profileViewModel.loggedInState.collectAsState()
    val profileCreation by profileViewModel.profileState.collectAsState()
    val combosByProfile by profileViewModel.combosByProfile.collectAsState()
    val currentProfile = profileViewModel.currentProfile
    Log.d(PROFILE_SCREEN_TAG, "Flows Collected:" +
            "\nloggedInState: $loggedInState" +
            "\nprofileCreation: ${profileCreation.profileCreation}" +
            "\ncurrentProfile: ${currentProfile.profile}" +
            "\ncombosByProfile: ${combosByProfile.comboDisplayList}")

    Log.d(PROFILE_SCREEN_TAG, "Checking if valid profile exists...")
    if (currentProfile.profile != emptyProfile) {
        Log.d(PROFILE_SCREEN_TAG, "Profile exists, preparing to load ProfileDetailsUi...")
        ProfileDetailsUi(
            profileViewModel = profileViewModel,
            isProfileLoggedIn = loggedInState,
            currentProfile = currentProfile,
            combosByProfile = combosByProfile,
            navigateBack = navigateBack,
        )
    } else {
        Log.d(PROFILE_SCREEN_TAG, "Profile doesn't exist, preparing to load ProfileCreationUi...")
        ProfileCreationUi(
            profileViewModel = profileViewModel,
            updateCurrentUser = updateCurrentUser,
            navigateBack = navigateBack,
            profile = profileCreation,
        )
    }
    Log.d(PROFILE_SCREEN_TAG, "Profile screen loaded.")
}
