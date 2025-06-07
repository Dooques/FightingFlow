package com.example.fightingflow.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fightingflow.model.CharacterEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao: BaseDao<CharacterEntry> {

    @Query("select * from character_table where name = :name")
    fun getCharacter(name: String): Flow<CharacterEntry?>

    @Query("select * from character_table where game = :game")
    fun getCharactersFromGame(game: String): Flow<List<CharacterEntry>>

    @Query("select * from character_table where game = :game and name = :name")
    fun getCharacterByGameAndName(name: String, game: String): Flow<CharacterEntry?>
}