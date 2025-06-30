package com.example.fightingflow.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.fightingflow.model.ComboEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ComboDao: BaseDao<ComboEntry> {
    @Query("select * from combo_table where id = :id")
    fun getCombo(id: String): Flow<ComboEntry?>

    @Query("SELECT * FROM combo_table")
    fun getAllCombos(): Flow<List<ComboEntry>>

    @Query("select * from combo_table where character = :character")
    fun getAllCombosByCharacter(character: String): Flow<List<ComboEntry>?>

    @Query("select * from combo_table where created_by = :userName")
    fun getAllCombosByProfile(userName: String): Flow<List<ComboEntry>?>
}