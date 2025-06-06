package com.example.fightingflow.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fightingflow.data.datastore.ComboDsRepository.Companion.comboId
import com.example.fightingflow.data.datastore.ComboDsRepository.Companion.editingState
import com.example.fightingflow.model.ComboDisplay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber


interface ComboDsRepository {
    suspend fun setCombo(comboDisplay: ComboDisplay)
    suspend fun setEditingState(editingStateValue: Boolean)

    fun getComboId(): Flow<String>
    fun getEditingState(): Flow<Boolean>

    companion object {
        val comboId = stringPreferencesKey("combo_id")
        val editingState = booleanPreferencesKey("editing_state")
    }
}

class ComboDatastoreRepository(private val dataStore: DataStore<Preferences>): ComboDsRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("combo_data")

    override suspend fun setCombo(comboDisplay: ComboDisplay) {
        Timber.d("Setting combo to datastore...")
        Timber.d("ComboId: ${comboDisplay.comboId}")
        dataStore.edit { preference ->
            preference[comboId] = comboDisplay.comboId
        }
    }

    override suspend fun setEditingState(editingStateValue: Boolean) {
        Timber.d("Setting editing state to datastore...")
        Timber.d("Editing state: $editingState")
        dataStore.edit { preference ->
            preference[editingState] = editingStateValue
        }
    }

    override fun getComboId() = dataStore.data
        .map { preference ->
            preference[comboId] ?: ""
        }

    override fun getEditingState(): Flow<Boolean> = dataStore.data
        .map { preference ->
            preference[editingState] ?: false
        }
}