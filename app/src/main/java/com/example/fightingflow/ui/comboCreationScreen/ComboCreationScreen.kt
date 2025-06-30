package com.example.fightingflow.ui.comboCreationScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.fightingflow.ui.comboDisplayScreen.ComboDisplayViewModel
import com.example.fightingflow.ui.components.ActionIcon
import com.example.fightingflow.ui.components.ProfileAndConsoleInputMenu
import com.example.fightingflow.ui.profileScreen.ProfileViewModel
import com.example.fightingflow.util.emptyCharacter
import com.example.fightingflow.util.emptyComboDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
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
    modifier: Modifier = Modifier
) {
    Timber.d("Opening Add Combo Screen...")
    Timber.d("Locking orientation until solution for lost combo data is found.")

    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    var comboReceived by remember { mutableStateOf(false) }
    val profileViewModel = koinInject<ProfileViewModel>()

    var settingsMenuExpanded by remember { mutableStateOf(false) }

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
    val textComboState by comboDisplayViewModel.textComboState.collectAsStateWithLifecycle()
    val game by comboCreationViewModel.gameTypeState.collectAsStateWithLifecycle()
    val selectedItem by comboCreationViewModel.itemIndexState.collectAsStateWithLifecycle()

    // Datastore Flows
    val username by profileViewModel.username.collectAsStateWithLifecycle()
    val characterNameState by comboDisplayViewModel.characterNameState.collectAsStateWithLifecycle()
    val comboIdState by comboCreationViewModel.comboIdState
    val editingState by comboCreationViewModel.editingState
    val iconDisplayState by comboDisplayViewModel.showIconState.collectAsStateWithLifecycle()
    val textComboDisplayState by comboDisplayViewModel.textComboState.collectAsStateWithLifecycle()
    val consoleTypeState by comboDisplayViewModel.consoleTypeState.collectAsStateWithLifecycle()
    val sf6ControlType by comboDisplayViewModel.modernOrClassicState.collectAsStateWithLifecycle()

    if (characterState.character != emptyCharacter) {
        comboCreationViewModel.getCharacterMoveEntryList(characterState.character.name)
    }

    comboCreationViewModel.getGameMoveEntryList(game.title)

    Timber.d("Flows Collected: \n Character Name: %s \n Game: %s \n ComboDisplayState: %s ",
        characterNameState.name, game, comboDisplay.comboDisplay)

    Timber.d("\n ComboString: %s \n ComboId from DS:%s \n Editing State: %s \n Character Move List: %s",
        comboAsString, comboIdState, editingState, characterMoveListState)

    Timber.d("Updating Character State")
    LaunchedEffect(characterListState, characterNameState) {
        if (characterListState.characterList.isNotEmpty() && characterNameState.name.isNotEmpty()) {
            comboCreationViewModel.characterState.update { characterState }
        }
        Timber.d("%s is loaded.", characterState.character.name)
    }

    Timber.d("Checking if in editing state & existing combo list contains data...")
    LaunchedEffect(editingState, comboIdState) {
        if (editingState) {
            if (comboIdState.isNotEmpty()) {
                Timber.d("Combo ID found, getting Combo from firestore...")
                try {
                    comboCreationViewModel.getExistingComboFromFirestore()
                    Timber.d("Combo collected from firestore.")
                } catch (e: Exception) {
                    Timber.e(e, "An error occurred collecting combo from firestore, " +
                            "\n Checking internal database for combo...")
                    try {
                        comboCreationViewModel.getExistingComboFromDb()
                        Timber.d("Combo collected from internal database.")
                    } catch (e: Exception) {
                        Timber.e(e, "An error occurred collecting combo from internal database.")
                    }
                }
            } else { Timber.d("No combo ID found.") }
        } else { Timber.d("Not in editing state.") }
    }

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
                    if (characterState.character.mutable) {
                        AsyncImage(
                            model = characterState.character.imageUri,
                            contentDescription = null,
                            modifier.size(60.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(characterState.character.imageId),
                            contentDescription = "",
                            modifier = modifier.size(60.dp)
                        )
                    }
                    ActionIcon(
                            onclick = {
                                Timber.d("Configuring Layout")
                                settingsMenuExpanded = true
                            },
                    tint = Color.White,
                    icon = Icons.Default.Settings,
                    modifier = modifier.fillMaxHeight()
                    )
                    ProfileAndConsoleInputMenu(
                        settingsMenuExpanded = settingsMenuExpanded,
                        onDismissRequest = { settingsMenuExpanded = !settingsMenuExpanded },
                        updateIconSetting = { comboDisplayViewModel.updateShowIconDisplayState(!iconDisplayState) },
                        updateTextComboSetting = { comboDisplayViewModel.updateShowComboTextState(!textComboState) },
                        iconState = iconDisplayState,
                        textComboState = textComboState
                    )
                    IconButton(
                        onClick = {
                            comboCreationViewModel.clearMoveList()
                            comboCreationViewModel.resetItemIndex()
                            comboCreationViewModel.resetComboId()
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
                    comboCreationViewModel = comboCreationViewModel,
                    username = username,
                    game = game,
                    consoleTypeState = consoleTypeState,
                    sF6ControlType = sf6ControlType,
                    updateComboData = comboCreationViewModel::updateComboDetails,
                    setSelectedItem = comboCreationViewModel::updateItemIndex,
                    selectedItem = selectedItem,
                    character = characterState.character,
                    characterName = characterNameState.name,
                    comboDisplay = comboDisplay.comboDisplay,
                    originalCombo = originalCombo.comboDisplay,
                    comboAsString = comboAsString,
                    moveList = moveListState,
                    characterMoveList = characterMoveListState,
                    gameMoveList = gameMoveListState,
                    iconDisplayState = iconDisplayState,
                    textComboDisplay = textComboDisplayState,
                    deleteMove = comboCreationViewModel::deleteMove,
                    clearMoveList = comboCreationViewModel::clearMoveList,
                    onNavigateToComboDisplay = onNavigateToComboDisplay
                )
            }
        }
    }
}
