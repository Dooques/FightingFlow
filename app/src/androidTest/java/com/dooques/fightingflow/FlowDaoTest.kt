package com.example.fightingflow

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dooques.fightingflow.data.database.FlowDatabase
import com.dooques.fightingflow.data.database.dao.CharacterDao
import com.dooques.fightingflow.data.database.dao.ComboDao
import com.dooques.fightingflow.data.database.dao.MoveDao
import com.dooques.fightingflow.model.CharacterEntry
import com.dooques.fightingflow.model.ComboEntry
import com.dooques.fightingflow.model.MoveEntry
import com.dooques.fightingflow.util.emptyComboEntry
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
        numpadNotation = false,
        game = "Tekken",
    )

    private val reina = CharacterEntry(
        id = 1,
        name = "Reina",
        imageId = 1,
        fightingStyle = "Mishima",
        combosById = "",
        controlType = "",
        numpadNotation = false,
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

        comboDao = flowDatabase.getComboDao()
        characterDao = flowDatabase.getCharacterDao()
        moveDao = flowDatabase.getMoveDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        flowDatabase.close()
    }

    /* ComboDaoTests */
    private val combo1 = ComboEntry(
        title = "Combo",
        character = "reina",
        damage = 60,
        createdBy = "Sam",
        dateCreated = "19/05/2025",
        moves = "1, 2, 1",
    )
    private val combo2 = ComboEntry(
        title = "Combo",
        character = "asuka",
        damage = 60,
        createdBy = "Sam",
        dateCreated = "19/05/2025",
        moves = "1, 2, 1",
    )
    private val combo3 = ComboEntry(
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