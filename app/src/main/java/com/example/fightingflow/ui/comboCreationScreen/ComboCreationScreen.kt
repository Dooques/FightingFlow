package com.example.fightingflow.ui.comboCreationScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fightingflow.ui.comboDisplayScreen.ComboDisplayViewModel
import com.example.fightingflow.ui.profileScreen.ProfileViewModel
import com.example.fightingflow.util.emptyCharacter
import com.example.fightingflow.util.emptyComboDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun ComboCreationScreen(
    comboDisplayViewModel: ComboDisplayViewModel,
    comboCreationViewModel: ComboCreationViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onNavigateToComboDisplay: () -> Unit,
    navigateBack: () -> Unit,
    navigateHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("Opening Add Combo Screen...")
    Timber.d("Locking orientation until solution for lost combo data is found.")

    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    var comboReceived by remember { mutableStateOf(false) }
    val profileViewModel = koinInject<ProfileViewModel>()

    // ComboViewModel
    val characterState by comboDisplayViewModel.characterState.collectAsStateWithLifecycle()
    val characterListState by comboDisplayViewModel.characterEntryListState.collectAsStateWithLifecycle()
    val moveListState by comboDisplayViewModel.moveEntryListUiState.collectAsStateWithLifecycle()

    // ComboCreationViewModel
    val comboDisplay by comboCreationViewModel.comboDisplayState.collectAsStateWithLifecycle()
    val originalCombo by comboCreationViewModel.originalCombo.collectAsStateWithLifecycle()
    val comboAsString by comboCreationViewModel.comboAsStringState.collectAsStateWithLifecycle()
    val characterMoveListState by comboCreationViewModel.characterMoveEntryList.collectAsStateWithLifecycle()
    val gameMoveListState by comboCreationViewModel.gameMoveEntryList.collectAsStateWithLifecycle()
    val game by comboCreationViewModel.gameSelected.collectAsStateWithLifecycle()

    // Datastore Flows
    val username by profileViewModel.username.collectAsStateWithLifecycle()
    val characterNameState by comboDisplayViewModel.characterNameState.collectAsStateWithLifecycle()
    val comboIdState by comboCreationViewModel.comboIdState
    val editingState by comboCreationViewModel.editingState

    if (characterState.character != emptyCharacter) {
        comboCreationViewModel.getCharacterMoveEntryList(characterState.character.name)
    }

    if (game.isNotEmpty()) {
        comboCreationViewModel.getGameMoveEntryList(game)
    }

    Timber.d("Flows Collected: ")
    Timber.d("Character Name: ${characterNameState.name} ")
    Timber.d("ComboDisplayState: ${comboDisplay.comboDisplay}")
    Timber.d("ComboString: $comboAsString")
    Timber.d("ComboId from DS: $comboIdState")
    Timber.d("Editing State: $editingState")
    Timber.d("Character Move List: $characterMoveListState")

    Timber.d("Updating Character State")
    if (characterListState.characterList.isNotEmpty() && characterNameState.name.isNotEmpty()) {
        comboCreationViewModel.characterState.update { characterState }
    }
    Timber.d("${characterState.character.name} is loaded.")

    Timber.d("Checking if in editing state & existing combo list contains data...")
    if (editingState) {
        if (comboIdState.isNotEmpty()) {
            if (!comboReceived) {
                Timber.d("Combo found and editing mode is true...")
                comboCreationViewModel.getExistingCombo()
                comboReceived = true
            } else { Timber.d("Combo already collected.") }
        } else { Timber.d("Combo Ds Empty.") }
    } else { Timber.d("Not in editing state.") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = characterState.character.name,
                        style = MaterialTheme.typography.displayMedium,
                        modifier = modifier.padding(start = 16.dp)
                    )
                },
                actions = {
                    Image(
                        painter = painterResource(characterState.character.imageId),
                        contentDescription = "",
                        modifier = modifier.size(60.dp)
                    )
                    IconButton(
                        onClick = {
                            comboCreationViewModel.clearMoveList()
                            navigateBack()
                                  },
                        modifier.fillMaxHeight()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Return to Combo screen",
                            tint = Color.White,
                            modifier = modifier.size(100.dp)
                        )
                    }
                },
                windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
            )
        }
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Timber.d("Loading Header...")
            if (comboDisplay.comboDisplay != emptyComboDisplay || !editingState) {
                ComboForm(
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    editingState = editingState,
                    username = username,
                    game = game,
                    updateComboData = comboCreationViewModel::updateComboDetails,
                    updateMoveList = comboCreationViewModel::updateMoveList,
                    character = characterState.character,
                    characterName = characterNameState.name,
                    comboDisplay = comboDisplay.comboDisplay,
                    originalCombo = originalCombo.comboDisplay,
                    comboAsString = comboAsString,
                    moveList = moveListState,
                    characterMoveList = characterMoveListState,
                    gameMoveList = gameMoveListState,
                    saveCombo = comboCreationViewModel::saveCombo,
                    deleteLastMove = comboCreationViewModel::deleteLastMove,
                    clearMoveList = comboCreationViewModel::clearMoveList,
                    onNavigateToComboDisplay = onNavigateToComboDisplay
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewBox(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Return to Combo screen",
            tint = Color(0xffed1664),
            modifier = modifier
                .padding(start = 5.dp, top = 2.dp)
                .size(100.dp)
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Return to Combo screen",
            tint = Color.White,
            modifier = modifier.size(100.dp)
        )
    }
}
