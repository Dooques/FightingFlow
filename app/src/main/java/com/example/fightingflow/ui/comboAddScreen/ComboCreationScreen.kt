package com.example.fightingflow.ui.comboAddScreen

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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.ui.comboScreen.ComboItem
import com.example.fightingflow.ui.comboScreen.ComboDisplayViewModel
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.MoveEntryListUiState
import kotlinx.coroutines.flow.update
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun ComboCreationScreen(
    comboCreationViewModel: ComboCreationViewModel,
    comboDisplayViewModel: ComboDisplayViewModel,
    saveComboDetailsToDs: (ComboDisplayUiState) -> Unit,
    updateComboData: (ComboDisplayUiState) -> Unit,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    saveCombo: () -> Unit,
    deleteLastMove: () -> Unit,
    clearMoveList: () -> Unit,
    onConfirm: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("")
    Timber.d("Opening Add Combo Screen...")

    Timber.d("Locking orientation until solution for lost combo data is found.")
    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    // ComboViewModel
    val characterState by comboDisplayViewModel.characterState.collectAsState()
    val characterListState by comboDisplayViewModel.characterEntryListState.collectAsState()
    val moveListState by comboDisplayViewModel.moveEntryListUiState.collectAsState()

    // AddComboViewModel
    val characterFromAddCombo by comboCreationViewModel.characterState.collectAsState()
    val comboDisplayState by comboCreationViewModel.comboDisplayState.collectAsState()
    val comboAsString by comboCreationViewModel.comboAsStringState.collectAsState()
    val existingComboList by comboCreationViewModel.existingCombos.collectAsState()
    val editingState by comboCreationViewModel.editingState

    // Datastore Flows
    val characterNameState by comboDisplayViewModel.characterNameState.collectAsState()
    val characterImageState by comboDisplayViewModel.characterImageState.collectAsState()
    val comboIdState by comboCreationViewModel.comboIdFromDs.collectAsState()

    Timber.d(
        "Flows Collected: " +
                "\nCharacter Details (Ds): " +
                "\nName: ${characterNameState.name} " +
                "\nImage: ${characterImageState.image}" +
                "\nMove List: ${moveListState.moveList}"
    )
    Timber.d("")
    Timber.d("\nCharacter: ${characterFromAddCombo.character}" +
            "\nComboDisplayState: ${comboDisplayState.comboDisplay}" +
            "\nComboString: $comboAsString" +
            "\nComboId from DS: $comboIdState" +
            "\nExistingComboList: $existingComboList"
    )

    Timber.d("Updating Character State")
    if (characterListState.characterList.isNotEmpty() && characterNameState.name.isNotEmpty()) {
        comboCreationViewModel.characterState.update { characterState }
    }
    Timber.d("${characterState.character.name} is loaded.")

    Timber.d("Checking if in editing state & existing combo list contains data...")
    if (editingState && existingComboList.comboEntryList.isNotEmpty()) {
        Timber.d("Existing combos contains data, preparing to add combo to ComboDisplayState...")
        comboCreationViewModel.getExistingComboFromList()
    }

    Timber.d("")
    Timber.d("Combo: ${comboDisplayState.comboDisplay}" +
            "\nComboMoves: ${comboDisplayState.comboDisplay.moves}"
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = characterState.character.name,
                            style = MaterialTheme.typography.displayMedium,
                            modifier = modifier.padding(start = 16.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Return to Combo screen",
                            modifier.size(80.dp)
                        )
                    }
                },
                actions = {
                    Image(
                        painter = painterResource(characterState.character.imageId),
                        contentDescription = "",
                        modifier = modifier.size(60.dp)
                    )
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
            Timber.d("")
            Timber.d("Loading Header...")
            ComboForm(
                updateComboData = updateComboData,
                updateMoveList = updateMoveList,
                character = characterState.character,
                characterName = characterNameState.name,
                combo = comboDisplayState.comboDisplay,
                comboAsString = comboAsString,
                moveList = moveListState,
                saveCombo = saveCombo,
                deleteLastMove = deleteLastMove,
                clearMoveList = clearMoveList,
                onConfirm = onConfirm
            )
        }
    }
}

@Composable
fun Header(
    characterName: String,
    characterImage: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Timber.d("Loading Back Icon.")
        IconButton(onClick = navigateBack) { Icon(imageVector = Icons.Default.Close, contentDescription = null, Modifier.size(50.dp)) }
        Timber.d("Loading Character Name")
        Text(text = characterName, style = MaterialTheme.typography.displayLarge)
        Timber.d("Loading Character Icon")
        Image(painter = painterResource(characterImage), contentDescription = null, modifier.size(50.dp))
    }
}

@Composable
fun ComboForm(
    updateComboData: (ComboDisplayUiState) -> Unit,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    character: CharacterEntry,
    characterName: String,
    combo: ComboDisplay,
    comboAsString: String,
    moveList: MoveEntryListUiState,
    saveCombo: () -> Unit,
    deleteLastMove: () -> Unit,
    clearMoveList: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("")
    val context = LocalContext.current
    Column {
        Timber.d("Loading Combo Form")
        if (combo.moves.isNotEmpty()) {
            Timber.d("Combo Move List exists, populating with moves...")
            ComboDisplay(context, combo)
        }
        if (moveList.moveList.isNotEmpty()) {
            Timber.d("Move Entry List exists, populating Input Selector Column...")
            InputSelector(
                context = context,
                character = character,
                characterName = characterName,
                combo = combo,
                updateComboData = updateComboData,
                updateMoveList = updateMoveList,
                comboAsString = comboAsString,
                saveCombo = saveCombo,
                deleteLastMove = deleteLastMove,
                clearMoveList = clearMoveList,
                onConfirm = onConfirm,
                moveList = moveList
            )
        }
    }
}

@Composable
fun ComboDisplay(
    context: Context,
    combo: ComboDisplay,
    modifier: Modifier = Modifier
) {
    val fontColor = MaterialTheme.colorScheme.onBackground
    val containerColor = MaterialTheme.colorScheme.surfaceDim
    val uiScale = 1f
    Timber.d("Getting combo display composable from Combo Screen")
    ComboItem(
        context = context,
        combo = combo,
        fontColor = fontColor,
        containerColor = containerColor,
        uiScale = uiScale,
        modifier = modifier.padding(vertical = 4.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InputSelector(
    context: Context,
    character: CharacterEntry,
    characterName: String,
    combo: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    comboAsString: String,
    saveCombo: () -> Unit,
    deleteLastMove: () -> Unit,
    clearMoveList: () -> Unit,
    onConfirm: () -> Unit,
    moveList: MoveEntryListUiState,
    modifier: Modifier = Modifier
) {
    Timber.d("Loading Input Selector")
    val mishima = listOf("Reina", "Heihachi", "Jin", "Kazuya", "Devil Jin")

    val mishimaSelectorLayout = listOf(
         "Text Combo", "Buttons", "Divider", "Radio Buttons", "Damage", "Divider", "Inputs Title", "Movement", "Input", "Divider",
        "Stances", "Common", "Divider", "Mishima Moves", "Mishima", "Divider", "${characterName}'s Stances", "Character",
        "Divider", "Mechanics", "Mechanics Input", "Stage",
    )

    val selectorLayout = listOf(
         "Text Combo", "Buttons", "Divider", "Radio Buttons", "Damage", "Divider", "Inputs", "Movement", "Input", "Divider",
        "Stances", "Common", "Divider", "${characterName}'s Stances", "Character",
        "Divider", "Mechanics", "Mechanics Input", "Stage",
    )

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
                "Damage" -> DamageAndBreak(combo, updateComboData, updateMoveList, moveList)
                "Movement" -> IconMoves(moveType, moveList, updateMoveList, context)
                "Input" -> IconMoves(moveType, moveList, updateMoveList, context)
                "Common", "Mishima", "Character", "Mechanics Input", "Stage" -> TextMoves(moveType, moveList, character, updateMoveList)
                "Buttons" -> ConfirmAndClear(saveCombo, deleteLastMove, clearMoveList, onConfirm)
                "Divider" -> InputDivider()
                "Stances", "Mechanics", "${character.name}'s Stances", "Inputs", "Mishima Moves" -> StanceAndMechanicsTitle(moveType)
            }
            Timber.d("$moveType loaded.")
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
        Button(
            onClick = {
                updateMoveList("break", moveList)
                Timber.d("Adding break to combo move list.")
                      },
            colors = ButtonDefaults.buttonColors().copy(containerColor = Color(0xffed1664), contentColor = Color.White),
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

@OptIn(ExperimentalLayoutApi::class)
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

@OptIn(ExperimentalLayoutApi::class)
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
fun ConfirmAndClear(
    saveCombo: () -> Unit,
    deleteLastMove: () -> Unit,
    clearMoveList: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("Loading confirm and clear buttons")
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth().padding(vertical = 4.dp).padding(bottom = 8.dp)) {
        OutlinedButton(
            onClick = {
                saveCombo()
                Timber.d("Combo saved, returning to Combo Screen")
                onConfirm()
                      },
            content = { Text("Confirm", color = MaterialTheme.colorScheme.onBackground) }
        )
        OutlinedButton(
            onClick = {
                deleteLastMove()
                Timber.d("Last move deleted.")
                      },
            content = {
                Text("Delete Last", color = MaterialTheme.colorScheme.onBackground) }
        )
        OutlinedButton(
            onClick = {
                clearMoveList()
                Timber.d("Combo move list cleared.")
            },
            content = { Text("Clear", color = MaterialTheme.colorScheme.onBackground) }
        )
    }
}