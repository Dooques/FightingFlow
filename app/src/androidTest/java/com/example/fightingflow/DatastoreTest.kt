package com.example.fightingflow

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fightingflow.data.datastore.CharacterDatastoreRepository
import com.example.fightingflow.data.datastore.CharacterDsRepository
import com.example.fightingflow.data.datastore.ComboDatastoreRepository
import com.example.fightingflow.data.datastore.ComboDsRepository
import com.example.fightingflow.data.datastore.ProfileDatastoreRepository
import com.example.fightingflow.data.datastore.ProfileDsRepository
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.ImmutableList
import com.example.fightingflow.util.emptyCharacter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import java.io.IOException
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class DatastoreTest {
    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())

    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = testScope,
        produceFile = { tmpFolder.newFile("user.preferences_pb") }
    )

    /* ProfileDataStore */
    private val testProfileRepository: ProfileDsRepository = ProfileDatastoreRepository(testDataStore)

    @Test
    @Throws(IOException::class)
    fun firstTimeDataValues_ReturnDefaultValues () = testScope.runTest {
        val loginState = testProfileRepository.profileLoggedInState().first()
        val username = testProfileRepository.getUsername().first()

        assertEquals(false, loginState)
        assertEquals("Invalid User", username)
    }

    @Test
    @Throws(IOException::class)
    fun updateLoggedInState_ReturnUpdatedLoginState() = testScope.runTest {
        testProfileRepository.updateLoggedInState(true)
        val loginState = testProfileRepository.profileLoggedInState().first()
        assertEquals(true, loginState)
    }

    @Test
    @Throws(IOException::class)
    fun updateUsername_ReturnUpdatedUsername() = testScope.runTest {
        testProfileRepository.updateUsername("Dooques")
        val username = testProfileRepository.getUsername().first()
        assertEquals("Dooques", username)
    }

    /* Character Repository */
    private val testCharacterRepository: CharacterDsRepository = CharacterDatastoreRepository(testDataStore)
    private val reina = CharacterEntry(
        id = 1,
        name = "Reina",
        imageId = 1,
        fightingStyle = "Mishima",
        combosById = "",
        game = "Tekken",
    )

    @Test
    @Throws(IOException::class)
    fun firstTimeDataValue_ReturnDefaultCharacterValues() = testScope.runTest {
        val characterName = testCharacterRepository.getName().first()
        val characterImage = testCharacterRepository.getImage().first()
        assertEquals("Nothing Selected", characterName)
        assertEquals(emptyCharacter.imageId, characterImage)
    }

    @Test
    @Throws(IOException::class)
    fun updateCharacterName_ReturnUpdatedName() = testScope.runTest {
        testCharacterRepository.updateCharacter(reina)
        val characterName = testCharacterRepository.getName().first()
        val characterImage = testCharacterRepository.getImage().first()
        assertEquals("Reina", characterName)
        assertEquals(1, characterImage)
    }

    private val testComboRepository: ComboDsRepository = ComboDatastoreRepository(testDataStore)

    private val combo = ComboDisplay(
        comboId = UUID.randomUUID().toString(),
        description = "Combo",
        character = "Reina",
        damage = 60,
        createdBy = "Sam",
        dateCreated = "19/05/2025",
        moves = ImmutableList(
            listOf(
                MoveEntry(
                    id = 1,
                    moveName = "forward",
                    notation = "f",
                    moveType = "Movement",
                    counterHit = false,
                    hold = false,
                    justFrame = false,
                    character = "Generic"
                )
            )
        )
    )

    @Test
    @Throws(IOException::class)
    fun firstTimeDataValue_ReturnDefaultValues() = testScope.runTest {
        val comboID = testComboRepository.getComboId().first()
        assertEquals("", comboID)
    }

    @Test
    @Throws(IOException::class)
    fun updateComboIF_ReturnComboID() = testScope.runTest {
        testComboRepository.updateComboState(combo)
        val comboID = testComboRepository.getComboId().first()
        assertEquals(combo.comboId, comboID)
    }
}