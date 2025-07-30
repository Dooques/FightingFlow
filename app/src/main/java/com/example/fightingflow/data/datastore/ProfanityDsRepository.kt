package com.example.fightingflow.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fightingflow.data.datastore.ProfanityDsRepository.Companion.profanityData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ProfanityDsRepository {
    suspend fun updateProfanityData(string: String)
    fun getProfanityData(): Flow<String>

    companion object {
        val profanityData = stringPreferencesKey(name = "profanity_data")
    }
}

class ProfanityDatastoreRepository(private val dataStore: DataStore<Preferences>): ProfanityDsRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profanity_data")

    override suspend fun updateProfanityData(string: String) {
        dataStore.edit { preference ->
            preference[profanityData] = string
        }
    }
    override fun getProfanityData() = dataStore.data
        .map { preference ->
            preference[profanityData] ?: ""
        }
}



