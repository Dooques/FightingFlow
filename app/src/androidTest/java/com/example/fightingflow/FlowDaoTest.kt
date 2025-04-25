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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class FlowDaoTest {
    private lateinit var profileDao: ProfileDao
    private lateinit var comboDao: ComboDao
    private lateinit var characterDao: CharacterDao
    private lateinit var moveDao: MoveDao
    private lateinit var flowDatabase: FlowDatabase

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
    private var profile1 = ProfileEntry(1, "Sam", "", "D00ques", false)
    private var profile2 = ProfileEntry(2, "Dave", "", "0blivionIV", false)

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
        val profileUpdate = ProfileEntry(1, "Dooques", "", "Password420", true)
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
    val combo1 = ComboEntry(
        1,
        UUID.randomUUID().toString(),
        CharacterEntry(
            id = 1,
            name = "Reina",
            imageId = 1,
            fightingStyle = "Mishima",
            uniqueMoves = "",
            combosById = ""
        ),
        damage = 60,
        createdBy = "Sam",
        moves = "1, 2, 1",
    )
    val combo2 = ComboEntry(
        2,
        UUID.randomUUID().toString(),
        CharacterEntry(
            id = 2,
            name = "Asuka",
            imageId = 1,
            fightingStyle = "Kazama",
            uniqueMoves = "",
            combosById = ""
        ),
        damage = 60,
        createdBy = "Sam",
        moves = "1, 2, 1",
    )
    val combo3 = ComboEntry(
        3,
        UUID.randomUUID().toString(),
        CharacterEntry(
            id = 1,
            name = "Reina",
            imageId = 1,
            fightingStyle = "Mishima",
            uniqueMoves = "",
            combosById = ""
        ),
        damage = 60,
        createdBy = "Dave",
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
        val combo = comboDao.getCombo(combo1.comboId).first()
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
        val character = CharacterEntry(id = 1,
            name = "Reina",
            imageId = 1,
            fightingStyle = "Mishima",
            uniqueMoves = "",
            combosById = ""
        )
        val combos = comboDao.getAllCombosByCharacter(character).first()
        assertEquals(combo1, combos[0])
        assertEquals(combo3, combos[1])
    }

    @Test
    @Throws(IOException::class)
    fun getCombosByCreator() = runBlocking {
        addAllCombosToDb()
        val combos = comboDao.getAllCombosByProfile("Sam").first()
        assertEquals(combo1, combos[0])
        assertEquals(combo2, combos[1])
    }

    val character1 = CharacterEntry(
        id = 1,
        name = "Reina",
        imageId = 1,
        fightingStyle = "Mishima",
        uniqueMoves = "",
        combosById = ""
    )
    val character2 = CharacterEntry(
        id = 2,
        name = "Asuka",
        imageId = 1,
        fightingStyle = "Kazama",
        uniqueMoves = "",
        combosById = ""
    )

    private suspend fun addCharactersToDb() {
        characterDao.insert(character1)
        characterDao.insert(character2)
    }

    @Test
    @Throws(IOException::class)
    fun getCharacter() = runBlocking {
        addCharactersToDb()
        val character = characterDao.getCharacter("Reina").first()
        assertEquals(character1, character)
    }
    @Test
    @Throws(IOException::class)
    fun getAllCharacters() = runBlocking {
        addCharactersToDb()
        val characters = characterDao.getAllCharacters().first()
        assertEquals(characters[0], character1)
        assertEquals(characters[1], character2)
    }

    val move1 = MoveEntry(
        id = 1,
        moveName = "forward",
        notation = "f",
        moveType = "Movement",
        counterHit = false,
        hold = false,
        justFrame = false,
        associatedCharacter = "Generic"
    )
    val move2 = MoveEntry(
        id = 2,
        moveName = "one",
        notation = "1",
        moveType = "Input",
        counterHit = false,
        hold = false,
        justFrame = false,
        associatedCharacter = "Generic"
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