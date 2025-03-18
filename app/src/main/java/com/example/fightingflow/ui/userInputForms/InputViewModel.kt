package com.example.fightingflow.ui.userInputForms

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fightingflow.data.database.OfflineUserDataRepository
import com.example.fightingflow.data.database.UserDataRepository
import com.example.fightingflow.model.UserEntry
import com.example.fightingflow.data.database.dao.UserDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InputViewModel(
    private val userDataRepository: UserDataRepository
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
        marketing = false
    )

    val userState = mutableStateOf(emptyUser)

    val existingUser = mutableStateOf(emptyUser)

    fun updateTosSelection(input: Boolean) {
        userState.value = userState.value.copy(tos = input)
    }

    fun updateMarketingSelection(input: Boolean) {
        userState.value = userState.value.copy(marketing = input)
    }

    fun updateCurrentUser(user: UserEntry) {
        userState.value = user
    }

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
        }
    }
}