package com.example.fightingflow.util

import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.UserEntry

data class CharacterEntryUiState(val character: CharacterEntry = emptyCharacter)

data class CharacterEntryListUiState(val characterList: List<CharacterEntry> = listOf())

data class MoveEntryListUiState(val moveList: List<MoveEntry> = listOf())

data class ComboEntryListUiState(val comboEntryList: List<ComboEntry> = listOf())

data class ComboDisplayListUiState(val comboDisplayList: List<ComboDisplay> = listOf())

data class ComboEntryUiState(val comboEntry: ComboEntry = emptyComboEntry)

data class ComboDisplayUiState(val comboDisplay: ComboDisplay = emptyComboDisplay)

data class CharNameUiState(val name: String = "")

data class CharImageUiState(val image: Int = 0)

data class ProfileUiState(val profile: UserEntry = emptyProfile )

data class ProfileListUiState(val profileList: List<UserEntry> = listOf())

