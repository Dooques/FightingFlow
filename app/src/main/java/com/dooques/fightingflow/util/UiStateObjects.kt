package com.dooques.fightingflow.util

import com.dooques.fightingflow.model.CharacterEntry
import com.dooques.fightingflow.model.ComboDisplay
import com.dooques.fightingflow.model.ComboEntry
import com.dooques.fightingflow.model.ComboEntryFb
import com.dooques.fightingflow.model.MoveEntry
import com.dooques.fightingflow.model.UserEntry

data class CharacterEntryUiState(val character: CharacterEntry = emptyCharacter)

data class CharacterEntryListUiState(val characterList: List<CharacterEntry> = listOf())

data class MoveEntryListUiState(val moveList: List<MoveEntry> = listOf())

data class ComboEntryListUiState(val comboEntryList: List<ComboEntry> = listOf())

data class ComboEntryFbListUiState(val comboEntryFbList: List<ComboEntryFb> = listOf())

data class ComboDisplayListUiState(val comboDisplayList: List<ComboDisplay> = listOf())

data class ComboEntryUiState(val comboEntry: ComboEntry = emptyComboEntry)

data class ComboDisplayUiState(val comboDisplay: ComboDisplay = emptyComboDisplay)

data class CharNameUiState(val name: String = "")

data class CharImageUiState(val image: Int = 0)

data class ProfileUiState(val profile: UserEntry = emptyUser)

data class ProfileListUiState(val profileList: List<UserEntry> = listOf())

