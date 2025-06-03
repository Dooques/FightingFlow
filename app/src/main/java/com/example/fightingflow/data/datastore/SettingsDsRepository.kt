package com.example.fightingflow.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fightingflow.data.datastore.SettingsDsRepository.Companion.controlType
import com.example.fightingflow.data.datastore.SettingsDsRepository.Companion.game
import com.example.fightingflow.data.datastore.SettingsDsRepository.Companion.modernOrClassicState
import com.example.fightingflow.data.datastore.SettingsDsRepository.Companion.showComboTextState
import com.example.fightingflow.data.datastore.SettingsDsRepository.Companion.showIconState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SettingsDsRepository {
    suspend fun updateConsoleType(selectedGame: Int)
    suspend fun updateIconDisplayState(showIcons: Boolean)
    suspend fun updateShowComboTextState(showText: Boolean)
    suspend fun updateSf6ControlType(type: Int)
    suspend fun updateGame(selectedGame: String)

    fun getGame(): Flow<String>
    fun getConsoleType(): Flow<Int>
    fun getIconDisplayState(): Flow<Boolean>
    fun getComboTextState(): Flow<Boolean>
    fun getSF6ControlType(): Flow<Int>

    companion object {
        val controlType = intPreferencesKey(name = "control_type")
        val showIconState = booleanPreferencesKey(name = "show_icons")
        val showComboTextState = booleanPreferencesKey(name = "show_combo_text")
        val game = stringPreferencesKey(name = "game_name")
        val modernOrClassicState = intPreferencesKey(name = "modern_or_classic")
    }
}

class SettingsDatastoreRepository(private val dataStore: DataStore<Preferences>): SettingsDsRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "game_data")

    /* Update Values */
    override suspend fun updateConsoleType(selectedGame: Int) {
        dataStore.edit { preference ->
            preference[controlType] = selectedGame
        }
    }

    override suspend fun updateIconDisplayState(showIcons: Boolean) {
       dataStore.edit { preference ->
           preference[showIconState] = showIcons
       }
    }

    override suspend fun updateShowComboTextState(showText: Boolean) {
        dataStore.edit { preference ->
            preference[showComboTextState] = showText
        }
    }
    override suspend fun updateGame(selectedGame: String) {
        dataStore.edit { preference ->
            preference[game] = selectedGame
        }
    }

    override suspend fun updateSf6ControlType(type: Int) {
        dataStore.edit { preference ->
            preference[modernOrClassicState] = type
        }
    }

    /* Get Values */
    override fun getConsoleType(): Flow<Int> = dataStore.data
        .map { preference ->
            preference[controlType] ?: 0
        }

    override fun getIconDisplayState(): Flow<Boolean> = dataStore.data
        .map { preference ->
            preference[showIconState] ?: true
        }

    override fun getComboTextState(): Flow<Boolean> = dataStore.data
        .map { preference ->
            preference[showComboTextState] ?: false
        }

    override fun getGame(): Flow<String> = dataStore.data
        .map { preference ->
            preference[game] ?: "Tekken 8"
        }

    override fun getSF6ControlType(): Flow<Int> = dataStore.data
        .map { preference ->
            preference[modernOrClassicState] ?: 0
        }
}

enum class Console {
    STANDARD, XBOX, PLAYSTATION, NINTENDO
}

enum class Game(val title: String) {
    T8(title = "Tekken 8"),
    MK1(title = "Mortal Kombat 1"),
    SF6(title = "Street Fighter VI")
}

enum class SF6ControlType(val type: Int) {
    Modern(type = 0), Classic(type = 1), Invalid(type = 2)
}
