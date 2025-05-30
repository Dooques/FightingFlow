package com.example.fightingflow.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fightingflow.data.datastore.SettingsDsRepository.Companion.controlType
import com.example.fightingflow.data.datastore.SettingsDsRepository.Companion.showComboTextState
import com.example.fightingflow.data.datastore.SettingsDsRepository.Companion.showIconState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SettingsDsRepository {
    suspend fun updateControlType(selectedGame: Int)
    suspend fun updateIconDisplayState(showIcons: Boolean)
    suspend fun updateShowComboTextState(showText: Boolean)

    fun getControlType(): Flow<Int>
    fun getIconDisplayState(): Flow<Boolean>
    fun getComboTextState(): Flow<Boolean>

    companion object {
        val controlType = intPreferencesKey(name = "control_type")
        val showIconState = booleanPreferencesKey(name = "show_icons")
        val showComboTextState = booleanPreferencesKey(name = "show_combo_text")
    }
}

class SettingsDatastoreRepository(private val dataStore: DataStore<Preferences>): SettingsDsRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "game_data")

    /* Update Values */
    override suspend fun updateControlType(selectedGame: Int) {
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

    /* Get Values */
    override fun getControlType(): Flow<Int> = dataStore.data
        .map { preference ->
            preference[controlType] ?: 1
        }

    override fun getIconDisplayState(): Flow<Boolean> = dataStore.data
        .map { preference ->
            preference[showIconState] ?: true
        }

    override fun getComboTextState(): Flow<Boolean> = dataStore.data
        .map { preference ->
            preference[showComboTextState] ?: false
        }
}

enum class ControlType {
    STANDARD, XBOX, PLAYSTATION, NINTENDO
}
