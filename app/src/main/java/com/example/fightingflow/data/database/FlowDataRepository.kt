package com.example.fightingflow.data.database

import com.example.fightingflow.data.database.dao.CharacterDao
import com.example.fightingflow.data.database.dao.ComboDao
import com.example.fightingflow.data.database.dao.MoveDao
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.MoveEntry
import kotlinx.coroutines.flow.Flow

interface FlowRepository {
    // Character
    fun getCharacter(name: String): Flow<CharacterEntry?>
    fun getCharactersByGame(game: String): Flow<List<CharacterEntry>>
    fun getCharacterByNameAndGame(name: String, game: String): Flow<CharacterEntry?>
    fun getCustomCharacters(): Flow<List<CharacterEntry>?>
    suspend fun updateCharacter(characterEntry: CharacterEntry)
    suspend fun insertCharacter(characterEntry: CharacterEntry)

    // Combo
    fun getCombo(id: Int): Flow<ComboEntry?>
    fun getAllCombos(): Flow<List<ComboEntry>>
    fun getAllCombosByCharacter(character: String): Flow<List<ComboEntry>?>
    fun getAllCombosByProfile(username: String): Flow<List<ComboEntry>?>
    suspend fun updateCombo(comboEntry: ComboEntry)

    // Move
    fun getMove(name: String): Flow<MoveEntry?>
    fun getAllMoves(): Flow<List<MoveEntry>>
    fun getAllMovesByCharacter(character: String): Flow<List<MoveEntry>>
    fun getAllMovesByGame(game: String): Flow<List<MoveEntry>>

    // Insert Data
    suspend fun insertAllCharacters(characterList: List<CharacterEntry>)
    suspend fun insertMoves(moveList: List<MoveEntry>)
    suspend fun insertCombo(comboEntry: ComboEntry)

    // Delete
    suspend fun deleteCombo(comboEntry: ComboEntry)
    suspend fun deleteCharacter(characterEntry: CharacterEntry)
    suspend fun deleteMove(moveEntry: MoveEntry)
}

class FlowDataRepository(
    private val characterDao: CharacterDao,
    private val comboDao: ComboDao,
    private val moveDao: MoveDao
): FlowRepository {

    // Get Character
    override fun getCharacter(name: String): Flow<CharacterEntry?> =
        characterDao.getCharacter(name)

    override fun getCharactersByGame(game: String): Flow<List<CharacterEntry>> =
        characterDao.getCharactersFromGame(game)

    override fun getCharacterByNameAndGame(name: String, game: String) =
        characterDao.getCharacterByGameAndName(name, game)

    override fun getCustomCharacters(): Flow<List<CharacterEntry>?> =
        characterDao.getAllCustomCharacters()


    // Get Combo
    override fun getCombo(id: Int): Flow<ComboEntry?> =
        comboDao.getCombo(id)

    override fun getAllCombos(): Flow<List<ComboEntry>> =
        comboDao.getAllCombos()

    override fun getAllCombosByCharacter(character: String): Flow<List<ComboEntry>?> =
        comboDao.getAllCombosByCharacter(character)

    override fun getAllCombosByProfile(username: String): Flow<List<ComboEntry>?> =
        comboDao.getAllCombosByProfile(username)


    // Get Moves
    override fun getMove(name: String): Flow<MoveEntry?> =
        moveDao.getMove(name)

    override fun getAllMoves(): Flow<List<MoveEntry>> =
        moveDao.getAllMoves()

    override fun getAllMovesByCharacter(character: String): Flow<List<MoveEntry>> =
        moveDao.getAllMovesByCharacter(character)

    override fun getAllMovesByGame(game: String): Flow<List<MoveEntry>> =
        moveDao.getAllMovesByGame(game)

    override suspend fun insertAllCharacters(characterList: List<CharacterEntry>) =
        characterDao.insertAll(characterList)


    // Insert
    override suspend fun insertMoves(moveList: List<MoveEntry>) =
        moveDao.insertAll(moveList)

    override suspend fun insertCombo(comboEntry: ComboEntry) =
        comboDao.insert(comboEntry)

    override suspend fun insertCharacter(characterEntry: CharacterEntry) =
        characterDao.insert(characterEntry)


    // Update
    override suspend fun updateCharacter(characterEntry: CharacterEntry) =
        characterDao.update(characterEntry)

    override suspend fun updateCombo(comboEntry: ComboEntry) =
        comboDao.update(comboEntry)


    // Delete
    override suspend fun deleteCombo(comboEntry: ComboEntry) =
        comboDao.delete(comboEntry)

    override suspend fun deleteCharacter(characterEntry: CharacterEntry) =
        characterDao.delete(characterEntry)

    override suspend fun deleteMove(moveEntry: MoveEntry) =
        moveDao.delete(moveEntry)
}