package com.example.fightingflow.ui.profileScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.ProfileDbRepository
import com.example.fightingflow.data.database.TekkenDbRepository
import com.example.fightingflow.data.datastore.ProfileDsRepository
import com.example.fightingflow.model.toDisplay
import com.example.fightingflow.model.toEntry
import com.example.fightingflow.util.ComboDisplayListUiState
import com.example.fightingflow.util.PROFILE_VM_TAG
import com.example.fightingflow.util.ProfileCreationUiState
import com.example.fightingflow.util.ProfileListUiState
import com.example.fightingflow.util.ProfileUiState
import com.example.fightingflow.util.emptyProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val profileDsRepository: ProfileDsRepository,
    private val profileDbRepository: ProfileDbRepository,
    private val tekkenDataRepository: TekkenDbRepository,
): ViewModel() {

    companion object {
        private const val TIME_MILLIS = 2_000L
    }

    // Update Profile State
    val profileState = MutableStateFlow(ProfileCreationUiState())

    fun updateProfileCreation(profile: ProfileCreationUiState) {
        profileState.update { ProfileCreationUiState(profile.profileCreation) }
    }

    suspend fun saveProfileData(): String {
        Log.d(PROFILE_VM_TAG, "")
        Log.d(PROFILE_VM_TAG, "Checking passwords match...")
        if (profileState.value.profileCreation.password == profileState.value.profileCreation.confirmPassword) {
            Log.d(PROFILE_VM_TAG, "Passwords match, adding Profile to datastore...")
            Log.d(PROFILE_VM_TAG, "Updating data in datastore from ViewModel...")
            Log.d(PROFILE_VM_TAG, "ProfileCreationUiState: ${profileState.value.profileCreation}")
            Log.d(PROFILE_VM_TAG, "ProfileEntryUiState: ${profileState.value.profileCreation.toEntry()}")
            updateUsernameInDs(profileState.value.profileCreation.username)
            Log.d(PROFILE_VM_TAG, "Profile added to datastore.")
            return "Success"
        } else {
            return ""
        }
    }


    // Datastore Functions
    val username = profileDsRepository.getUsername()
        .mapNotNull { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = ""
    )

    val loggedInState =
        profileDsRepository.profileLoggedInState()
            .mapNotNull { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = false
            )

    suspend fun updateUsernameInDs(username: String) {
        profileDsRepository.updateUsername(username)
    }

    suspend fun loginProfile() {
        profileDsRepository.updateLoggedInState(true)
    }

    suspend fun logoutProfile() {
        profileDsRepository.updateLoggedInState(false)
    }


    // Database Functions
    val allExistingProfiles =
        profileDbRepository.getAllProfiles()
            .mapNotNull { ProfileListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = ProfileListUiState()
            )

    val currentProfile = profileDbRepository.getProfile(username.value)
        .mapNotNull { ProfileUiState(it ?: emptyProfile) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIME_MILLIS),
            initialValue = ProfileUiState()
        )

    val combosByProfile =
        tekkenDataRepository.getAllCombosByProfile(currentProfile.value.profile.username)
            .mapNotNull { ComboDisplayListUiState(it.map { it.toDisplay() }) }
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

    suspend fun deleteProfileFromDb(profile: ProfileUiState) {
        Log.d(PROFILE_VM_TAG, "")
        Log.d(PROFILE_VM_TAG, "Deleting profile from database...")
        profileDbRepository.delete(profile.profile)
    }
}