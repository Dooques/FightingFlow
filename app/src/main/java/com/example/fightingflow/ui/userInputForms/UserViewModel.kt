package com.example.fightingflow.ui.userInputForms

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.UserDataRepository
import com.example.fightingflow.model.UserEntry
import com.example.fightingflow.data.datastore.FlowPreferencesRepository
import com.example.fightingflow.data.datastore.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(
    private val userDataRepository: UserDataRepository,
    private val preferencesRepository: PreferencesRepository
): ViewModel() {

    companion object {
        private const val TIME_MILLIS = 5_000L
    }

    private val emptyUser = UserEntry(
        id = 1,
        username = "",
        email = "",
        password = "",
        dob = "",
        tos = false,
        marketing = false,
        loggedIn = false
    )

    private val _userState: MutableStateFlow<UserEntry> = MutableStateFlow(emptyUser)
    val userState = _userState.asStateFlow()

    val loggedInState = updateLoggedInState()

    val existingUser = mutableStateOf(emptyUser)

    // Update User State
    fun updateTosSelection(input: Boolean) {
        _userState.value = userState.value.copy(tos = input)
    }

    fun updateMarketingSelection(input: Boolean) {
        _userState.value = _userState.value.copy(marketing = input)
    }

    fun updateCurrentUser(user: UserEntry) {
        _userState.value = user
    }

    // Save User Data
    fun loginUser() {
        viewModelScope.launch {
            preferencesRepository.loginUser(true)
        }
    }

    fun updateLoggedInState() =
        preferencesRepository.isUserLoggedIn()
            .map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = false
            )

    private fun getUser() = userDataRepository.getUser(
            userState.value.username, userState.value.password
        ).map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyUser
        )

    fun saveUserData() {
        viewModelScope.launch {
            if (getUser().value == emptyUser)
                userDataRepository.insert(userState.value)
            loginUser()
        }
    }
}