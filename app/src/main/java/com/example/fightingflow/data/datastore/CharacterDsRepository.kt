package com.example.fightingflow.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fightingflow.data.datastore.CharacterDsRepository.Companion.characterName
import com.example.fightingflow.data.datastore.CharacterDsRepository.Companion.customGameList
import com.example.fightingflow.data.datastore.CharacterDsRepository.Companion.imageId
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.util.emptyCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface CharacterDsRepository {
    suspend fun updateCharacter(character: CharacterEntry)
    suspend fun updateCustomGameList(gameList: String)

    fun getName(): Flow<String>
    fun getImage(): Flow<Int>
    fun getCustomGameList(): Flow<String>

    companion object {
        const val TAG = "SelectedCharacterRepository"
        val characterName = stringPreferencesKey(name = "character_name")
        val imageId = intPreferencesKey(name = "image_id")
        val customGameList = stringPreferencesKey(name = "custom_games")
    }
}

class CharacterDatastoreRepository(private val dataStore: DataStore<Preferences>): CharacterDsRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "character_data")

    override suspend fun updateCharacter(character: CharacterEntry) {
        dataStore.edit { preference ->
            preference[characterName] = character.name
            preference[imageId] = character.imageId
        }
    }

    override suspend fun updateCustomGameList(gameList: String) {
        dataStore.edit { preference ->
            preference[customGameList] = gameList
        }
    }

    override fun getName(): Flow<String> = dataStore.data
        .map { preference ->
            preference[characterName] ?: "Nothing Selected"
    }

    override fun getImage(): Flow<Int> = dataStore.data
        .map { preference ->
            preference[imageId] ?: emptyCharacter.imageId
        }

    override fun getCustomGameList(): Flow<String> = dataStore.data
        .map { preference ->
            preference[customGameList] ?: "Invalid List"
        }
}