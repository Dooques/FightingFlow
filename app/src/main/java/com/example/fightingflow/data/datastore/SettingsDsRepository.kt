package com.example.fightingflow.data.datastore

import android.content.Context
import android.service.controls.Control
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fightingflow.data.datastore.SettingsDsRepository.Companion.controlType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SettingsDsRepository {
    suspend fun updateControlType(selectedGame: Int)

    fun getControlType(): Flow<Int>

    companion object {
        val controlType = intPreferencesKey(name = "control_type")
    }
}

class SettingsDatastoreRepository(private val dataStore: DataStore<Preferences>): SettingsDsRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "game_data")

    override suspend fun updateControlType(selectedGame: Int) {
        dataStore.edit { preference ->
            preference[controlType] = selectedGame
        }
    }

    override fun getControlType(): Flow<Int> = dataStore.data
        .map { preference ->
            preference[controlType] ?: 1
        }

}

enum class ControlType {
    STANDARD, XBOX, PLAYSTATION, NINTENDO
}
