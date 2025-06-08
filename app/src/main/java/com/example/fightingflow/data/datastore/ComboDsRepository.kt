package com.example.fightingflow.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fightingflow.data.datastore.ComboDsRepository.Companion.comboId
import com.example.fightingflow.data.datastore.ComboDsRepository.Companion.editingState
import com.example.fightingflow.model.ComboDisplay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber


interface ComboDsRepository {
    suspend fun updateComboIdState(comboDisplay: ComboDisplay)
    suspend fun updateEditingState(editingStateValue: Boolean)

    fun getComboId(): Flow<Int>
    fun getEditingState(): Flow<Boolean>

    companion object {
        val comboId = intPreferencesKey("combo_id")
        val editingState = booleanPreferencesKey("editing_state")
    }
}

class ComboDatastoreRepository(private val dataStore: DataStore<Preferences>): ComboDsRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("combo_data")

    override suspend fun updateComboIdState(comboDisplay: ComboDisplay) {
        Timber.d("Setting combo to datastore...")
        Timber.d("ComboId: ${comboDisplay.id}")
        dataStore.edit { preference ->
            preference[comboId] = comboDisplay.id
        }
    }

    override suspend fun updateEditingState(editingStateValue: Boolean) {
        Timber.d("Setting editing state to datastore...")
        Timber.d("Editing state: $editingState")
        dataStore.edit { preference ->
            preference[editingState] = editingStateValue
        }
    }

    override fun getComboId() = dataStore.data
        .map { preference ->
            preference[comboId] ?: 0
        }

    override fun getEditingState(): Flow<Boolean> = dataStore.data
        .map { preference ->
            preference[editingState] ?: false
        }
}