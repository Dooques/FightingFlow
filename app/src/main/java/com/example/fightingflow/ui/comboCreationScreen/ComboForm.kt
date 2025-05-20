package com.example.fightingflow.ui.comboCreationScreen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.ui.comboDisplayScreen.ComboDisplay
import com.example.fightingflow.util.CharacterEntryListUiState
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.MoveEntryListUiState
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.reflect.KSuspendFunction0

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
    characterMoveList: MoveEntryListUiState,
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
        TekkenInputSelector(
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
            moveList = moveList,
            characterMoveList = characterMoveList
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
    ComboDisplay(
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
fun InputDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(modifier = modifier.padding(vertical = 8.dp))
}

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(title, modifier = modifier.padding(start = 16.dp))
}

@Composable
fun ComboAsText(
    comboAsString: String,
    modifier: Modifier = Modifier
) {
    Column {
        OutlinedTextField(
            value = comboAsString,
            onValueChange = {},
            minLines = 2,
            label = { Text("Your combo") },
            readOnly = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun ComboDescription(
    combo: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    var description by remember { mutableStateOf(combo.description.ifEmpty { "" }) }
    OutlinedTextField(
        value = description,
        onValueChange = {
            if (description.length <= 34 ) {
                description = it
            updateComboData(ComboDisplayUiState(combo.copy(description = description)))
            } },
        label = { Text("Write a description of your combo...") },
        maxLines = 1,
        trailingIcon = {
            IconButton(onClick = { description = "" }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear description")
        } },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    )
}

@Composable
fun MoveModifiers(modifier: Modifier = Modifier) {
    var counterHit by remember { mutableStateOf(false) }
    var hold by remember { mutableStateOf(false) }
    var justFrame by remember { mutableStateOf(false) }
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
                }
                "Mechanics Input" -> { color = Color.Yellow }
                "Common" -> { color = Color.Gray }
                "Stage" -> { color = Color.Green }
                "Modifier" -> { color = Color.White }
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
fun CharacterMoves(
    characterMoveList: MoveEntryListUiState,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Timber.d("Loading character moves...")
        val color = Color.Red

        characterMoveList.moveList.forEach { move ->
            Box(modifier.padding(horizontal = 2.dp, vertical = 4.dp)) {
                Box(
                    modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(color)
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = move.moveName,
                        color = Color.White,
                        fontSize = (14.sp),
                        modifier = modifier.padding(1.dp).clickable(
                            enabled = true,
                            onClick = {
                                Timber.d("${move.moveName} selected, preparing to add combo to list...")
                                updateMoveList(move.moveName, characterMoveList)
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
fun DamageAndBreak(
    combo: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    moveList: MoveEntryListUiState,
    modifier: Modifier = Modifier
) {
    var damageValue by remember { mutableIntStateOf(combo.damage) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 8.dp)
    ) {
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

