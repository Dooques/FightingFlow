package com.example.fightingflow.ui.profileScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.ProfileDbRepository
import com.example.fightingflow.data.database.TekkenDbRepository
import com.example.fightingflow.data.datastore.ProfileDsRepository
import com.example.fightingflow.model.ProfileEntry
import com.example.fightingflow.model.toDisplay
import com.example.fightingflow.model.toEntry
import com.example.fightingflow.util.ComboDisplayListUiState
import com.example.fightingflow.util.PROFILE_VM_TAG
import com.example.fightingflow.util.ProfileCreationUiState
import com.example.fightingflow.util.ProfileListUiState
import com.example.fightingflow.util.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val profileRepository: ProfileDsRepository,
    private val profileDbRepository: ProfileDbRepository,
    private val tekkenDataRepository: TekkenDbRepository,
): ViewModel() {

    companion object {
        private const val TIME_MILLIS = 2_000L
    }

    // Update User State
    val profileState = MutableStateFlow(ProfileCreationUiState())

    fun updateProfileCreation(profile: ProfileCreationUiState) {
        profileState.update { ProfileCreationUiState(profile.profileCreation) }
    }


    // Datastore Functions
    val currentProfile = MutableStateFlow(getCurrentProfileDetailsFromDs())

    val loggedInState =
        profileRepository.isProfileLoggedIn()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = false
            )

    suspend fun loginProfile() {
        profileRepository.updateLoggedInState(true)
    }

    suspend fun logoutProfile() {
        profileRepository.updateLoggedInState(false)
    }

    fun getCurrentProfileDetailsFromDs(): ProfileUiState {
        Log.d(PROFILE_VM_TAG, "")
        Log.d(PROFILE_VM_TAG, "Loading existing user from datastore")
        Log.d(PROFILE_VM_TAG, "Getting username...")
        val username = profileRepository.getUsername().map { it }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                    initialValue = String()
                )
        Log.d(PROFILE_VM_TAG, "Username: ${username.value}")
        Log.d(PROFILE_VM_TAG, "Getting password...")
        val password = profileRepository.getPassword().map { it }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                    initialValue = String()
                )
        Log.d(PROFILE_VM_TAG, "Password: ${password.value}")
        Log.d(PROFILE_VM_TAG, "Getting profile pic...")
        val profilePic = profileRepository.getProfilePic().map { it }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                    initialValue = String()
                )
        Log.d(PROFILE_VM_TAG, "Profile pic: ${profilePic.value}")
        Log.d(PROFILE_VM_TAG, "All data loaded.")
        Log.d(PROFILE_VM_TAG, "Converting data to Profile Entry...")
        val currentProfile = ProfileEntry(
            id = 0,
            username = username.value,
            profilePic = profilePic.value,
            password = password.value,
            loggedIn = false
        )
        Log.d(PROFILE_VM_TAG, "Current profile: $currentProfile")
        return ProfileUiState(currentProfile)
    }

    suspend fun updateCurrentUserInDs(): String {
        Log.d(PROFILE_VM_TAG, "")
        Log.d(PROFILE_VM_TAG, "Checking passwords match...")
        if (profileState.value.profileCreation.password == profileState.value.profileCreation.confirmPassword) {
            Log.d(PROFILE_VM_TAG, "Passwords match, adding Profile to datastore...")
            Log.d(PROFILE_VM_TAG, "Updating data in datastore from ViewModel...")
            Log.d(PROFILE_VM_TAG, "ProfileCreationUiState: ${profileState.value.profileCreation}")
            Log.d(PROFILE_VM_TAG, "ProfileEntryUiState: ${profileState.value.profileCreation.toEntry()}")
            profileRepository.setProfileDetails(ProfileUiState(profileState.value.profileCreation.toEntry()))
            Log.d(PROFILE_VM_TAG, "Profile added to datastore.")
            return "Success"
        } else {
            return ""
        }
    }


    // Database Functions

    val allExistingProfiles =
        profileDbRepository.getAllProfiles()
            .map { ProfileListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ProfileListUiState()
            )


    val combosByProfile =
        tekkenDataRepository.getAllCombosByProfile(currentProfile.value.profile.username)
            .map { ComboDisplayListUiState(it.map { it.toDisplay() }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ComboDisplayListUiState()
            )

    suspend fun saveProfileToDb() {
        Log.d(PROFILE_VM_TAG, "")
        Log.d(PROFILE_VM_TAG, "Saving profile to database...")
        profileDbRepository.insert(profileState.value.profileCreation.toEntry())
    }
}