package com.example.fightingflow.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fightingflow.data.datastore.ComboDsRepository.Companion.TAG
import com.example.fightingflow.data.datastore.ComboDsRepository.Companion.character
import com.example.fightingflow.data.datastore.ComboDsRepository.Companion.comboId
import com.example.fightingflow.data.datastore.ComboDsRepository.Companion.createdBy
import com.example.fightingflow.data.datastore.ComboDsRepository.Companion.damage
import com.example.fightingflow.data.datastore.ComboDsRepository.Companion.moveList
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.ui.comboAddScreen.VM_TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


interface ComboDsRepository {
    suspend fun setCombo(comboDisplay: ComboDisplay, comboAsString: String)

    fun getComboId(): Flow<String>
    fun getCharacter(): Flow<String>
    fun getDamage(): Flow<Int>
    fun getCreatedBy(): Flow<String>
    fun getMoveList(): Flow<String>


    companion object {
        const val TAG = "ComboRepository"
        val comboId = stringPreferencesKey("combo_id")
        val character = stringPreferencesKey("character")
        val damage = intPreferencesKey("damage")
        val createdBy = stringPreferencesKey("created_by")
        val moveList = stringPreferencesKey("move_list")
    }
}

class ComboDatastoreRepository(private val dataStore: DataStore<Preferences>): ComboDsRepository {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("combo_data")


    override suspend fun setCombo(comboDisplay: ComboDisplay, comboAsString: String) {
        Log.d(TAG, "")
        Log.d(TAG, "Setting combo to datastore...")
        Log.d(TAG, "Combo Display: $comboDisplay")
        Log.d(TAG, "Combo String: $comboAsString")
        dataStore.edit { preference ->
            preference[comboId] = comboDisplay.comboId
            preference[character] = comboDisplay.character
            preference[damage] = comboDisplay.damage
            preference[createdBy] = comboDisplay.createdBy
            preference[moveList] = comboAsString
        }
        Log.d(VM_TAG, "Saved details: " +
                "\ncomboId: ${dataStore.data.map { preference -> preference[comboId] }} " +
                "\ncharacter: ${dataStore.data.map { preference -> preference[character] }}" +
                "\ndamage: ${dataStore.data.map { preference -> preference[damage] }}" +
                "\ncreatedBy: ${dataStore.data.map { preference -> preference[createdBy] }}" +
                "\nmoveList: ${dataStore.data.map { preference -> preference[moveList] }}"
        )
    }

    override fun getComboId() = dataStore.data
        .map { preference ->
            preference[comboId] ?: ""
        }

    override fun getCharacter() = dataStore.data
        .map { preference ->
            preference[character] ?: ""
        }

    override fun getDamage() = dataStore.data
        .map { preference ->
            preference[damage] ?: 0
        }

    override fun getCreatedBy() = dataStore.data
        .map { preference ->
            preference[createdBy] ?: ""
        }

    override fun getMoveList() = dataStore.data
        .map {preference ->
            preference[moveList] ?: ""
        }
}

data class ComboDataStore(
    val comboId: String = "",
    val character: String = "",
    val damage: Int = 0,
    val createdBy: String = "",
    val moveList: String = ""
)