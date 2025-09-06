package com.dooques.fightingflow.ui.comboCreationScreen

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
import com.dooques.fightingflow.model.ControlType
import com.dooques.fightingflow.model.SF6ControlType
import com.dooques.fightingflow.ui.viewmodels.ComboDisplayViewModel
import com.dooques.fightingflow.ui.components.ActionIcon
import com.dooques.fightingflow.ui.components.ProfileAndConsoleInputMenu
import com.dooques.fightingflow.ui.viewmodels.UserViewModel
import com.dooques.fightingflow.util.emptyComboDisplay
import com.dooques.fightingflow.ui.viewmodels.AuthViewModel
import com.dooques.fightingflow.ui.viewmodels.ComboCreationViewModel
import com.dooques.fightingflow.ui.viewmodels.ProfanityViewModel
import com.dooques.fightingflow.util.ComboDisplayUiState
import com.dooques.fightingflow.util.characterAndMoveData.characterMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun ComboCreationScreen(
    authViewModel: AuthViewModel,
    comboDisplayViewModel: ComboDisplayViewModel,
    comboCreationViewModel: ComboCreationViewModel,
    userViewModel: UserViewModel,
    profanityViewModel: ProfanityViewModel,
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

    // ComboDisplayViewModel
    val characterStateDisplay by comboDisplayViewModel.characterState.collectAsStateWithLifecycle()

    // ComboCreationViewModel
    val characterStateCreation by comboCreationViewModel.characterState.collectAsStateWithLifecycle()
    val comboDisplayState by comboCreationViewModel.comboDisplayState.collectAsStateWithLifecycle()
    val originalComboState by comboCreationViewModel.originalComboState.collectAsStateWithLifecycle()
    val comboAsString by comboCreationViewModel.comboAsStringState.collectAsStateWithLifecycle()
    val moveListState by comboCreationViewModel.moveEntryList.collectAsStateWithLifecycle()
    val textComboState by comboDisplayViewModel.textComboState.collectAsStateWithLifecycle()
    val game by comboCreationViewModel.gameTypeState.collectAsStateWithLifecycle()
    val selectedItem by comboCreationViewModel.itemIndexState.collectAsStateWithLifecycle()

    // Datastore Flows
    val characterNameState by comboDisplayViewModel.characterNameState.collectAsStateWithLifecycle()
    val gameSelectedState by comboDisplayViewModel.gameSelectedState.collectAsStateWithLifecycle()
    val comboIdState by comboCreationViewModel.comboIdState.collectAsStateWithLifecycle()
    val editingState by comboCreationViewModel.editingState
    val iconDisplayState by comboDisplayViewModel.showIconState.collectAsStateWithLifecycle()
    val textComboDisplayState by comboDisplayViewModel.textComboState.collectAsStateWithLifecycle()
    val consoleTypeState by comboDisplayViewModel.consoleTypeState.collectAsStateWithLifecycle()
    val sf6ControlType by comboDisplayViewModel.modernOrClassicState.collectAsStateWithLifecycle()

    // Firebase Flows
    val userData by userViewModel.userDataMap.collectAsStateWithLifecycle()

    // Auth Flows
    val currentUser by authViewModel.signInState.collectAsStateWithLifecycle()
    val userDetails by userViewModel.userDetailsState.collectAsStateWithLifecycle()

    var settingsMenuExpanded by remember { mutableStateOf(false) }
    var characterUpdated by remember { mutableStateOf(false) }
    var comboUpdated by remember { mutableStateOf(false) }

    val newCombo = emptyComboDisplay.copy(
        id = if (!characterMap.keys.contains(game.title)) UUID.randomUUID().toString() else "",
        character = characterStateDisplay.character.name,
        controlType = when (sf6ControlType) {
            SF6ControlType.Classic -> ControlType.StreetFighterC.type
            SF6ControlType.Modern -> ControlType.StreetFighterM.type
            else -> characterStateDisplay.character.controlType
                                            },
        game = characterStateDisplay.character.game
    )

    Timber.d("--Flows Collected-- \n Character Name: %s \n CharacterState: %s\n CharacterStateCreation: %s \n" +
            " Game: %s\n ComboDisplayState: %s\n ComboString: %s \n ComboId from DS:%s \n Editing State: %s \n Character Move List: %s",
        characterNameState?.name, characterStateDisplay.character, characterStateCreation.character,
        game, comboDisplayState.comboDisplay, comboAsString, comboIdState, editingState, moveListState)

    LaunchedEffect(characterNameState, gameSelectedState) {
        Timber.d("--Launched Effect triggered by character change--")
        if (characterNameState != null && characterNameState?.name?.isNotBlank() == true) {
            try {
                Timber.d(" Getting ${characterNameState!!.name} from database...")
                comboDisplayViewModel.updateCharacterState(
                    characterNameState!!.name,
                    gameSelectedState
                )
                Timber.d(" Character returned: $characterStateDisplay")
            } catch (e: NoSuchElementException) {
                Timber.e(e, " Character Error, no element found in character list.")
            }
        }
    }

    Timber.d("Updating Character State")
    LaunchedEffect(characterStateDisplay) {
        Timber.d("--Launched Effect triggered to update CharacterState Update--")
        try {
            if (characterStateCreation != characterStateDisplay) {
                Timber.d(" Character state is empty, updating ith display screen value")
                comboCreationViewModel.characterState.update { characterStateDisplay }

                Timber.d(" Creating move list for character in CreationViewModel")
                comboCreationViewModel.getMoveEntryList(characterStateDisplay.character)
                characterUpdated = true
            } else {
                if (moveListState.moveList.first().character != characterStateDisplay.character.name) {
                    Timber.d(" Move List does not match selected character, updating move list.")
                    comboCreationViewModel.getMoveEntryList(characterStateDisplay.character)
                    characterUpdated = true
                }
                characterUpdated = false
            }
            Timber.d(" %s is loaded.", characterStateCreation.character.name)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    LaunchedEffect(comboDisplayState) {
        Timber.d("--Launch Effect triggered by combo state change--")
        if (!editingState) {
            if (!comboUpdated) {
                Timber.d(" Adding character to combo details")
                comboCreationViewModel.updateComboDetails(ComboDisplayUiState(newCombo))
                comboUpdated = true
                Timber.d("Combo updated: $comboDisplayState")
            } else {
                Timber.d(" No Changes detected.")
            }
        } else {
            Timber.d(" In editing state closing launched effect.")
            comboUpdated = false
        }
    }

    LaunchedEffect(comboIdState) {
        Timber.d("--Launched Effect triggered by comboIdState change--")
        Timber.d(" ComboID: $comboIdState")
        if (comboIdState.isEmpty()) {
            Timber.d(" No combo ID found.")
            return@LaunchedEffect
        }

        if (!editingState) {
            Timber.d(" Not in editing state.")
            return@LaunchedEffect
        }

        if (!characterStateCreation.character.mutable) {
            Timber.d(" Combo ID found, getting Combo from firestore...")
            try {
                comboCreationViewModel.getExistingComboFromFirestore()
                Timber.d(" Combo collected from firestore.")
            } catch (e: Exception) {
                Timber.e(
                    e, " An error occurred collecting combo from firestore, " +
                            "\n Checking internal database for combo..."
                )
            }
        } else {
            Timber.d(" Character is not mutable, checking database for combo.")
            try {
                comboCreationViewModel.getExistingComboFromDb()
                Timber.d(" Combo collected from internal database.")
            } catch (e: Exception) {
                Timber.e(e, " An error occurred collecting combo from internal database.")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = characterStateDisplay.character.name,
                        style = MaterialTheme.typography.displayMedium,
                        modifier = modifier.padding(start = 16.dp)
                    )
                },
                actions = {
                    if (characterStateDisplay.character.mutable) {
                        AsyncImage(
                            model = characterStateDisplay.character.imageUri,
                            contentDescription = null,
                            modifier.size(60.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(characterStateDisplay.character.imageId),
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
            if (comboDisplayState.comboDisplay != emptyComboDisplay || !editingState) {
                ComboForm(
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    editingState = editingState,
                    comboCreationViewModel = comboCreationViewModel,
                    comboDisplayViewModel = comboDisplayViewModel,
                    authViewModel = authViewModel,
                    profanityViewModel = profanityViewModel,
                    comboCreationState = true,
                    game = game,
                    consoleTypeState = consoleTypeState,
                    sF6ControlType = sf6ControlType,
                    updateComboData = comboCreationViewModel::updateComboDetails,
                    setSelectedItem = comboCreationViewModel::updateItemIndex,
                    currentUser = currentUser,
                    userData = userData,
                    userDetails = userDetails,
                    selectedItem = selectedItem,
                    character = characterStateDisplay.character,
                    characterName = characterNameState?.name,
                    comboDisplay = comboDisplayState.comboDisplay,
                    originalCombo = originalComboState.comboDisplay,
                    comboAsString = comboAsString,
                    moveList = moveListState,
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
