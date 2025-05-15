package com.example.fightingflow.ui.comboCreationScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.ui.comboDisplayScreen.ComboItem
import com.example.fightingflow.ui.comboDisplayScreen.ComboDisplayViewModel
import com.example.fightingflow.ui.profileScreen.ProfileViewModel
import com.example.fightingflow.util.CharacterEntryListUiState
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.MoveEntryListUiState
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber
import kotlin.reflect.KSuspendFunction0

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
//    Timber.d("Locking orientation until solution for lost combo data is found.")
    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    var comboReceived by remember { mutableStateOf(false) }
    val profileViewModel = koinInject<ProfileViewModel>()

    // ComboViewModel
    val characterState by comboDisplayViewModel.characterState.collectAsStateWithLifecycle()
    val characterListState by comboDisplayViewModel.characterEntryListState.collectAsStateWithLifecycle()
    val moveListState by comboDisplayViewModel.moveEntryListUiState.collectAsStateWithLifecycle()

    // ComboCreationViewModel
    val characterFromAddCombo by comboCreationViewModel.characterState.collectAsStateWithLifecycle()
    val comboDisplay by comboCreationViewModel.comboDisplayState.collectAsStateWithLifecycle()
    val originalCombo by comboCreationViewModel.originalCombo.collectAsStateWithLifecycle()
    val comboAsString by comboCreationViewModel.comboAsStringState.collectAsStateWithLifecycle()

    // Datastore Flows
    val username by profileViewModel.username.collectAsStateWithLifecycle()
    val characterNameState by comboDisplayViewModel.characterNameState.collectAsStateWithLifecycle()
    val characterImageState by comboDisplayViewModel.characterImageState.collectAsStateWithLifecycle()
    val comboIdState by comboCreationViewModel.comboIdState
    val editingState by comboCreationViewModel.editingState

    Timber.d("Flows Collected: ")
    Timber.d("Character Details (Ds): ")
    Timber.d("Name: ${characterNameState.name} ")
    Timber.d("Image: ${characterImageState.image}")

    Timber.d("Character: ${characterFromAddCombo.character}")
    Timber.d("ComboDisplayState: ${comboDisplay.comboDisplay}")
    Timber.d("ComboString: $comboAsString")
    Timber.d("ComboId from DS: $comboIdState")
    Timber.d("Editing State: $editingState")

    Timber.d("Updating Character State")
    if (characterListState.characterList.isNotEmpty() && characterNameState.name.isNotEmpty()) {
        comboCreationViewModel.characterState.update { characterState }
    }
    Timber.d("${characterState.character.name} is loaded.")

    Timber.d("Checking if in editing state & existing combo list contains data...")
    if (editingState) {
        if (comboIdState.isNotEmpty()) {
            if(!comboReceived) {
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
                    IconButton(onClick = { navigateBack() }, modifier.fillMaxHeight()) {
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
            ComboForm(
                scope = scope,
                snackbarHostState = snackbarHostState,
                editingState = editingState,
                username = username,
                updateComboData = comboCreationViewModel::updateComboDetails,
                updateMoveList = comboCreationViewModel::updateMoveList,
                character = characterState.character,
                characterName = characterNameState.name,
                comboDisplay = comboDisplay.comboDisplay,
                originalCombo = originalCombo.comboDisplay,
                comboAsString = comboAsString,
                moveList = moveListState,
                saveCombo = comboCreationViewModel::saveCombo,
                deleteLastMove = comboCreationViewModel::deleteLastMove,
                clearMoveList = comboCreationViewModel::clearMoveList,
                onNavigateToComboDisplay = onNavigateToComboDisplay
            )
        }
    }
}

@Composable
fun ComboForm(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    editingState: Boolean,
    username: String,
    comboDisplay: ComboDisplay,
    originalCombo: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    character: CharacterEntry,
    characterName: String,
    comboAsString: String,
    moveList: MoveEntryListUiState,
    saveCombo: KSuspendFunction0<Unit>,
    deleteLastMove: () -> Unit,
    clearMoveList: () -> Unit,
    onNavigateToComboDisplay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column {
        Timber.d("Loading Combo Form")
        Timber.d("Combo Move List exists, populating with moves...")
        if (comboDisplay.moves.isEmpty()) {
            Row(
                modifier = modifier.height(57.dp).padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Add some moves!", modifier = modifier.padding(4.dp))
            }
        }
    }
    ComboDisplay(context, comboDisplay, username)
    if (moveList.moveList.isNotEmpty()) {
        Timber.d("Move Entry List exists, populating Input Selector Column...")
        InputSelector(
            context = context,
            scope = scope,
            snackbarHostState = snackbarHostState,
            editingState = editingState,
            comboDisplay = comboDisplay,
            originalCombo = originalCombo,
            character = character,
            characterName = characterName,
            combo = comboDisplay,
            updateComboData = updateComboData,
            updateMoveList = updateMoveList,
            comboAsString = comboAsString,
            saveCombo = saveCombo,
            deleteLastMove = deleteLastMove,
            clearMoveList = clearMoveList,
            onNavigateToComboDisplay = onNavigateToComboDisplay,
            moveList = moveList
        )
    }
}

@Composable
fun ComboDisplay(
    context: Context,
    combo: ComboDisplay,
    username: String,
    modifier: Modifier = Modifier
) {
    val fontColor = MaterialTheme.colorScheme.onBackground
    val uiScale = 1f
    Timber.d("Getting combo display composable from Combo Screen")
    ComboItem(
        context = context,
        captureController = rememberCaptureController(),
        toShare = false,
        display = false,
        characterEntryListUiState = CharacterEntryListUiState(),
        combo = combo,
        username =  username,
        fontColor = fontColor,
        uiScale = uiScale,
        modifier = modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun InputSelector(
    context: Context,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    editingState: Boolean,
    comboDisplay: ComboDisplay,
    originalCombo: ComboDisplay,
    character: CharacterEntry,
    characterName: String,
    combo: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    comboAsString: String,
    saveCombo: KSuspendFunction0<Unit>,
    deleteLastMove: () -> Unit,
    clearMoveList: () -> Unit,
    onNavigateToComboDisplay: () -> Unit,
    moveList: MoveEntryListUiState,
    modifier: Modifier = Modifier
) {
    Timber.d("Loading Input Selector")

    LazyColumn {
        val mishimaChar = characterName in mishima
        if (mishimaChar) {
            Timber.d("Using mishima layout")
        } else {
            Timber.d("Using normal character layout")
        }
        items(items = if (mishimaChar) mishimaSelectorLayout else selectorLayout) { moveType ->
            when (moveType) {
                "Text Combo" -> ComboTextEntry(comboAsString)
                "Radio Buttons" -> RadioButtons()
                "Damage" -> DamageAndBreak(
                    combo = combo,
                    updateComboData = updateComboData,
                    updateMoveList = updateMoveList,
                    moveList = moveList
                )
                "Movement" -> IconMoves(
                    moveType = moveType,
                    moveList = moveList,
                    updateMoveList = updateMoveList,
                    context = context
                )
                "Input" -> IconMoves(
                    moveType = moveType,
                    moveList = moveList,
                    updateMoveList = updateMoveList,
                    context = context
                )
                "Common", "Mishima", "Character", "Mechanics Input", "Stage" -> TextMoves(
                    moveType = moveType,
                    moveList = moveList,
                    character = character,
                    updateMoveList = updateMoveList
                )
                "Buttons" -> ConfirmAndClear(
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    editingState = editingState,
                    comboDisplay = comboDisplay,
                    originalCombo = originalCombo,
                    saveCombo = saveCombo,
                    deleteLastMove = deleteLastMove,
                    clearMoveList = clearMoveList,
                    onNavigateToComboDisplay = onNavigateToComboDisplay,
                )
                "Divider" -> InputDivider()
                "Stances", "Mechanics", "${character.name}'s Stances", "Inputs", "Mishima Moves" -> StanceAndMechanicsTitle(moveType)
            }
            Timber.d("$moveType loaded.")
        }
        item {
            Spacer(modifier.size(120.dp))
        }
    }
}

@Composable
fun InputDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(modifier = modifier.padding(vertical = 8.dp))
}

@Composable
fun StanceAndMechanicsTitle(title: String, modifier: Modifier = Modifier) {
    Text(title, modifier = modifier.padding(start = 16.dp))
}

@Composable
fun ComboTextEntry(
    comboAsString: String,
    modifier: Modifier = Modifier
) {
    Column {
        OutlinedTextField(
            value = comboAsString,
            onValueChange = {},
            minLines = 2,
            label = {Text("Your combo")},
            readOnly = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun RadioButtons(modifier: Modifier = Modifier) {
    var counterHit by remember {mutableStateOf(false)}
    var hold by remember {mutableStateOf(false)}
    var justFrame by remember {mutableStateOf(false)}
    Column(modifier.padding(horizontal = 16.dp)) {
        Timber.d("Loading Radio Buttons")
        Text("Move Modifiers")
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Counter Hit")
                RadioButton(
                    selected = counterHit,
                    onClick = {
                        counterHit = !counterHit
                        Timber.d("Setting counter hit to $counterHit")
                              },
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Hold")
                RadioButton(
                    selected = hold,
                    onClick = {
                        hold = !hold
                        Timber.d("Setting hold to $hold")
                              },
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Just Frame")
                RadioButton(
                    selected = justFrame,
                    onClick = {
                        justFrame = !justFrame
                        Timber.d("Setting just frame to $justFrame")
                              },
                )
            }
        }
    }
}



@Composable
fun IconMoves(
    moveType: String,
    moveList: MoveEntryListUiState,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    context: Context,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth().padding(4.dp)
    ) {
        Timber.d("Loading Input Icons")
        moveList.moveList.forEach { move ->
            if (move.moveType == moveType) {
                val moveId = remember(move) { context.resources.getIdentifier(move.moveName, "drawable", context.packageName) }
                Box(modifier.padding(vertical = 4.dp).clip(RoundedCornerShape(10.dp))) {
                    Image(
                        painter = painterResource(moveId),
                        contentDescription = move.moveName,
                        modifier = modifier
                            .padding(horizontal = 16.dp)
                            .size(40.dp)
                            .clickable(
                                enabled = true,
                                onClick = {
                                    Timber.d("${move.moveName} selected, preparing to add combo to list...")
                                    updateMoveList(move.moveName, moveList)
                                    Timber.d("Added ${move.moveName} to combo move list.")
                                }
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun TextMoves(
    moveType: String,
    moveList: MoveEntryListUiState,
    character: CharacterEntry,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Timber.d("Loading $moveType text moves")
        moveList.moveList.forEach { move ->
            var color = Color.White
            var booleanStatement = move.moveType == moveType && move.moveName != "move_break"

            when (moveType) {
                "Mishima" -> {
                    color = Color.Blue
                    booleanStatement = booleanStatement && (character.fightingStyle.contains("Mishima") || character.name.contains("Devil"))
                }
                "Character" -> {
                    color = Color.Red
                    booleanStatement = booleanStatement && (character.uniqueMoves.contains(move.moveName))
                }
                "Mechanics Input" -> { color = Color.Yellow }
                "Common" -> { color = Color.Gray }
                "Stage" -> { color = Color.Green }
            }

            if (booleanStatement) {
                Box(modifier.padding(horizontal = 2.dp, vertical = 4.dp)) {
                    Box(
                        modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(color)
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(
                            move.moveName,
                            color = if (color == Color.White || color == Color.Green || color == Color.Yellow) Color.Black else Color.White,
                            fontSize = (14.sp),
                            modifier = modifier.padding(1.dp).clickable(
                                enabled = true,
                                onClick = {
                                    Timber.d("${move.moveName} selected, preparing to add combo to list...")
                                    updateMoveList(move.moveName, moveList)
                                    Timber.d("Added ${move.moveName} to combo move list.")
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DamageAndBreak(
    combo: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    moveList: MoveEntryListUiState,
    modifier: Modifier = Modifier
) {
    var damageValue by remember { mutableIntStateOf(combo.damage) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Timber.d("Loading Damage and Break Row")
        // Damage Input
        OutlinedTextField(
            value = damageValue.toString(),
            onValueChange = {
                damageValue = it.toIntOrNull() ?: damageValue
                updateComboData(ComboDisplayUiState(combo.copy(damage = damageValue)))
                Timber.d("damage: $damageValue")
            },
            maxLines = 1,
            label = { Text("Damage") },
            modifier = modifier.fillMaxWidth(0.5f).padding(horizontal = 4.dp)
        )

        // Add Break
        OutlinedButton(
            onClick = {
                updateMoveList("break", moveList)
                Timber.d("Adding break to combo move list.")
            },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = modifier.fillMaxWidth().padding(horizontal = 4.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth()) {
                Text("Add Break")
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = modifier
                        .size(20.dp)
                )
            }
        }
    }
}

@Composable
fun ConfirmAndClear(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    editingState: Boolean,
    comboDisplay: ComboDisplay,
    originalCombo: ComboDisplay,
    saveCombo: KSuspendFunction0<Unit>,
    deleteLastMove: () -> Unit,
    clearMoveList: () -> Unit,
    onNavigateToComboDisplay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("Loading confirm and clear buttons")
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp).padding(bottom = 8.dp)
    ) {
        // Confirm
        Button(
            onClick = {
                if (comboDisplay == originalCombo) {
                    scope.launch {
                        if (editingState) {
                            snackbarHostState.showSnackbar("No changes to the original combo...")
                        } else {
                            snackbarHostState.showSnackbar("Combo has no moves...")
                        }
                    }
                } else if (comboDisplay.damage == 0) {
                    scope.launch {
                        snackbarHostState.showSnackbar("No damage value...")
                    }
                } else {
                    scope.launch {
                        Timber.d("Preparing to save combo...")
                        saveCombo()
                        Timber.d("Combo saved, returning to Combo Screen")
                        onNavigateToComboDisplay()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors().copy(containerColor = Color(0xffed1664),),
            content = { Text("Confirm", color = MaterialTheme.colorScheme.onBackground) }
        )
        // Delete Last
        OutlinedButton(
            onClick = {
                Timber.d("Deleting last move...")
                deleteLastMove()
                Timber.d("Last move deleted.")
            },
            content = {
                Text("Delete Last", color = MaterialTheme.colorScheme.onBackground) }
        )
        // Clear Move List
        OutlinedButton(
            onClick = {
                Timber.d("Clearing move list...")
                clearMoveList()
                Timber.d("Combo move list cleared.")
            },
            content = { Text("Clear", color = MaterialTheme.colorScheme.onBackground) }
        )
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
