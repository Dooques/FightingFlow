package com.example.fightingflow.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fightingflow.data.datastore.ComboDsRepository.Companion.comboId
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.util.COMBO_DS_REPO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


interface ComboDsRepository {
    suspend fun setCombo(comboDisplay: ComboDisplay)

    fun getComboId(): Flow<String>

    companion object {
        val comboId = stringPreferencesKey("combo_id")
    }
}

class ComboDatastoreRepository(private val dataStore: DataStore<Preferences>): ComboDsRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("combo_data")

    override suspend fun setCombo(comboDisplay: ComboDisplay) {
        Log.d(COMBO_DS_REPO, "")
        Log.d(COMBO_DS_REPO, "Setting combo to datastore...")
        Log.d(COMBO_DS_REPO, "ComboId: ${comboDisplay.comboId}")
        dataStore.edit { preference ->
            preference[comboId] = comboDisplay.comboId
        }
    }

    override fun getComboId() = dataStore.data
        .map { preference ->
            preference[comboId] ?: ""
        }
}