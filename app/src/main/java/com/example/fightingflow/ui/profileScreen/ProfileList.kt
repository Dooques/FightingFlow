package com.example.fightingflow.ui.profileScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fightingflow.util.PROFILE_SCREEN_TAG
import com.example.fightingflow.util.ProfileCreationUiState
import com.example.fightingflow.util.ProfileUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ProfileList(
    profileViewModel: ProfileViewModel,
    username: String,
    loggedInState: Boolean,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("")
    Timber.d("Loading profile list...")

    val profileList by profileViewModel.allExistingProfiles.collectAsStateWithLifecycle()
    val profileCreation by profileViewModel.profileState.collectAsStateWithLifecycle()
    Timber.d(
        "Flows Collected:" +
                "\nprofileList: ${profileList.profileList}"
    )

    var showCreationForm by remember { mutableStateOf(false) }

    Column(
        modifier.fillMaxSize()
    ) {
        Timber.d("Loading Home Button")
        Box {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Return to Character Select",
                modifier
                    .size(60.dp)
                    .clickable(onClick = { navigateBack() })
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Existing Profiles")
        }
        Spacer(modifier.size(40.dp))
        LazyColumn {
            items(items = profileList.profileList) { profile ->
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(profile.username.replaceFirstChar { it.uppercase() }, modifier.align(Alignment.CenterStart).padding(start = 40.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.align(Alignment.CenterEnd).padding(end = 32.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                scope.launch {
                                    if (loggedInState && profile.username == username) {
                                        profileViewModel.logoutProfile()
                                        profileViewModel.updateUsernameInDs(username)
                                    } else {
                                        profileViewModel.loginProfile()
                                        profileViewModel.updateUsernameInDs(profile.username)
                                    }
                                }
                            },
                            modifier = modifier.width(150.dp)
                        ) {
                            Text(
                                if (loggedInState && profile.username == username) "Logout Profile" else "Login Profile",
                                color = Color.White,
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red,
                            modifier = modifier.clickable(
                                onClick = {
                                    scope.launch {
                                        profileViewModel.deleteProfileFromDb(ProfileUiState(profile))
                                        snackbarHostState.showSnackbar("${profile.username}'s profile has been deleted.")
                                    }
                                }
                            )
                                .padding(start = 32.dp)
                        )
                    }
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            if (showCreationForm) {
                ProfileCreationForm(
                    updateCurrentProfile = {profileViewModel.updateProfileCreation(it)},
                    profile = profileCreation,
                    onConfirm = {
                        scope.launch {
                            Timber.d("")
                            Timber.d("Preparing to save ${profileCreation.profileCreation.username}'s profile to datastore...")
                            val saveProfileSuccess = profileViewModel.saveProfileData()
                            Timber.d("Profile saved to Ds.")

                            if (saveProfileSuccess == "Success") {
                                Timber.d("Saving Profile to database...")
                                profileViewModel.saveProfileToDb()
                                Timber.d("Profile saved to Db.")
                                Timber.d("Logging in profile...")
                                profileViewModel.loginProfile()
                                Timber.d("Profile logged in.")
                                Timber.d("Returning to title screen...")
                            } else {
                                snackbarHostState.showSnackbar("Passwords do not match, please try again")
                            }
                            showCreationForm = false
                        }
                    }
                )
            } else {
                OutlinedButton(onClick = { showCreationForm = true }) {
                    Text(
                        "Create A New Profile",
                        color = Color.White
                    )
                }
            }
        }
    }
}