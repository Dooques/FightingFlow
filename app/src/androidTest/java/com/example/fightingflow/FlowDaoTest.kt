package com.example.fightingflow

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fightingflow.data.database.FlowDatabase
import com.example.fightingflow.data.database.dao.CharacterDao
import com.example.fightingflow.data.database.dao.ComboDao
import com.example.fightingflow.data.database.dao.MoveDao
import com.example.fightingflow.data.database.dao.ProfileDao
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.ProfileEntry
import com.example.fightingflow.util.emptyComboEntry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class FlowDaoTest {
    private lateinit var profileDao: ProfileDao
    private lateinit var comboDao: ComboDao
    private lateinit var characterDao: CharacterDao
    private lateinit var moveDao: MoveDao
    private lateinit var flowDatabase: FlowDatabase

    private val asuka = CharacterEntry(
        id = 2,
        name = "Asuka",
        imageId = 1,
        fightingStyle = "Kazama",
        combosById = "",
        controlType = "",
        game = "Tekken",
    )

    private val reina = CharacterEntry(
        id = 1,
        name = "Reina",
        imageId = 1,
        fightingStyle = "Mishima",
        combosById = "",
        controlType = "",
        game = "Tekken",
    )

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        flowDatabase = Room.inMemoryDatabaseBuilder(
            context, FlowDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        profileDao = flowDatabase.getUserDao()
        comboDao = flowDatabase.getComboDao()
        characterDao = flowDatabase.getCharacterDao()
        moveDao = flowDatabase.getMoveDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        flowDatabase.close()
    }

    /* Profile Dao Tests */
    private var profile1 = ProfileEntry(
        id = 1,
        username = "Sam",
        profilePic = "",
        password = "D00ques",
        loggedIn = false
    )
    private var profile2 = ProfileEntry(
        id = 2,
        username = "Dave",
        profilePic = "",
        password = "0blivionIV",
        loggedIn = false
    )

    private suspend fun addProfileToDatabase() {
        profileDao.insert(profile1)
    }
    private suspend fun addTwoProfilesToDatabase() {
        profileDao.insert(profile1)
        profileDao.insert(profile2)
    }

    @Test
    @Throws(IOException::class)
    fun daoInsert_daoGetProfile_InsertItemIntoDb () = runBlocking {
        addProfileToDatabase()
        val profileByName = profileDao.getProfile("Sam").first()
        assertEquals(profileByName, profile1)
    }

    @Test
    @Throws(IOException::class)
    fun daoGetAllProfiles_AddMultipleProfiles() = runBlocking {
        addTwoProfilesToDatabase()
        val allProfiles = profileDao.getAllProfiles().first()
        assertEquals(allProfiles[0], profile1)
        assertEquals(allProfiles[1], profile2)
    }

    @Test
    @Throws(IOException::class)
    fun updateProfile_UpdateProfileInDb() = runBlocking {
        addProfileToDatabase()
        val profileUpdate = ProfileEntry(
            id = 1,
            username = "Dooques",
            profilePic = "",
            password = "Password420",
            loggedIn = true
        )
        profileDao.update(profileUpdate)
        val updatedProfile = profileDao.getAllProfiles().first()
        assertEquals(updatedProfile[0], profileUpdate)
    }

    @Test
    @Throws(IOException::class)
    fun daoDeleteProfiles_DeletesAllItemsFromDb() = runBlocking {
        addTwoProfilesToDatabase()
        profileDao.delete(profile1)
        profileDao.delete(profile2)
        val result = profileDao.getAllProfiles().first()
        assert(result.isEmpty())
    }

    /* ComboDaoTests */
    private val combo1 = ComboEntry(
        id = 1,
        title = "Combo",
        character = "reina",
        damage = 60,
        createdBy = "Sam",
        dateCreated = "19/05/2025",
        moves = "1, 2, 1",
    )
    private val combo2 = ComboEntry(
        id = 2,
        title = "Combo",
        character = "asuka",
        damage = 60,
        createdBy = "Sam",
        dateCreated = "19/05/2025",
        moves = "1, 2, 1",
    )
    private val combo3 = ComboEntry(
        id = 3,
        title = "Combo",
        character = "reina",
        damage = 60,
        createdBy = "Dave",
        dateCreated = "19/05/2025",
        moves = "1, 2, 1",
    )

    private suspend fun addAllCombosToDb() {
        comboDao.insert(combo1)
        comboDao.insert(combo2)
        comboDao.insert(combo3)
    }

    @Test
    @Throws(IOException::class)
    fun getComboByUUID() = runBlocking {
        addAllCombosToDb()
        val combo = comboDao.getCombo(combo1.id).first()
        assertEquals(combo1, combo)
    }

    @Test
    @Throws(IOException::class)
    fun getAllCombos() = runBlocking {
        addAllCombosToDb()
        val combos = comboDao.getAllCombos().first()
        assert(combos.isNotEmpty())
    }

    @Test
    @Throws(IOException::class)
    fun getComboByCharacter() = runBlocking {
        addAllCombosToDb()
        val combos = comboDao.getAllCombosByCharacter("reina").first()
        assertEquals(combo1, combos?.get(0) ?: emptyComboEntry)
        assertEquals(combo3, combos?.get(1) ?: emptyComboEntry)
    }

    @Test
    @Throws(IOException::class)
    fun getCombosByCreator() = runBlocking {
        addAllCombosToDb()
        val combos = comboDao.getAllCombosByProfile("Sam").first()
        assertEquals(combo1, combos?.get(0) ?: emptyComboEntry)
        assertEquals(combo2, combos?.get(1) ?: emptyComboEntry)
    }

    private suspend fun addCharactersToDb() {
        characterDao.insert(reina)
        characterDao.insert(asuka)
    }

    @Test
    @Throws(IOException::class)
    fun getCharacter() = runBlocking {
        addCharactersToDb()
        val character = characterDao.getCharacter("Reina").first()
        assertEquals(reina, character)
    }

    @Test
    @Throws(IOException::class)
    fun getAllCharactersByGame() = runBlocking {
        addCharactersToDb()
        val characters = characterDao.getCharactersFromGame("Tekken").first()
        assertEquals(characters[0], reina)
        assertEquals(characters[1], asuka)
    }

    private val move1 = MoveEntry(
        id = 1,
        moveName = "forward",
        notation = "f",
        moveType = "Movement",
        character = "Generic"
    )
    private val move2 = MoveEntry(
        id = 2,
        moveName = "one",
        notation = "1",
        moveType = "Input",
        character = "Generic"
    )
    private suspend fun insertAllMoves() {
        moveDao.insert(move1)
        moveDao.insert(move2)
    }

    @Test
    @Throws(IOException::class)
    fun getMove() = runBlocking {
        insertAllMoves()
        val move = moveDao.getMove("forward").first()
        assertEquals(move1, move)
    }

    @Test
    @Throws(IOException::class)
    fun getAllMoves() = runBlocking {
        insertAllMoves()
        val moves = moveDao.getAllMoves().first()
        assertEquals(move1, moves[0])
        assertEquals(move2, moves[1])
    }
}