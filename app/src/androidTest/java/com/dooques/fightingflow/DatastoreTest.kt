package com.example.fightingflow

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dooques.fightingflow.data.datastore.CharacterDatastoreRepository
import com.dooques.fightingflow.data.datastore.CharacterDsRepository
import com.dooques.fightingflow.data.datastore.ComboDatastoreRepository
import com.dooques.fightingflow.data.datastore.ComboDsRepository
import com.dooques.fightingflow.data.datastore.ProfileDatastoreRepository
import com.dooques.fightingflow.data.datastore.UserDsRepository
import com.dooques.fightingflow.model.CharacterEntry
import com.dooques.fightingflow.model.ComboDisplay
import com.dooques.fightingflow.model.MoveEntry
import com.dooques.fightingflow.util.ImmutableList
import com.dooques.fightingflow.util.emptyCharacter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import java.io.IOException
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
    private val testProfileRepository: UserDsRepository = ProfileDatastoreRepository(testDataStore)

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
        controlType = "",
        numpadNotation = false,
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
        title = "Combo",
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
                    character = "Generic"
                )
            )
        )
    )

    @Test
    @Throws(IOException::class)
    fun firstTimeDataValue_ReturnDefaultValues() = testScope.runTest {
        val comboID = testComboRepository.getComboId().first()
        assertEquals(0, comboID.toInt())
    }

    @Test
    @Throws(IOException::class)
    fun updateComboIF_ReturnComboID() = testScope.runTest {
        testComboRepository.updateComboIdState(combo)
        val comboID = testComboRepository.getComboId().first()
        assertEquals(combo.id, comboID)
    }
}