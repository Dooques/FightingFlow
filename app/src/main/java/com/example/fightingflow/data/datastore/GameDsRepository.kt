package com.example.fightingflow.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fightingflow.data.datastore.GameDsRepository.Companion.game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GameDsRepository {
    suspend fun updateGame(selectedGame: String)

    fun getGame(): Flow<String>

    companion object {
        const val TAG = "GameDatastore"
        val game = stringPreferencesKey(name = "game_name")
    }
}

class GameDatastoreRepository(private val dataStore: DataStore<Preferences>): GameDsRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "game_data")

    override suspend fun updateGame(selectedGame: String) {
        dataStore.edit { preference ->
            preference[game] = selectedGame
        }
    }

    override fun getGame(): Flow<String> = dataStore.data
        .map { preference ->
            preference[game] ?: "Tekken 8"
        }

}
