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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.material3.SnackbarResult
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.ui.comboCreationScreen.layouts.MortalKombatLayout
import com.example.fightingflow.ui.comboCreationScreen.layouts.StreetFighterLayout
import com.example.fightingflow.ui.comboCreationScreen.layouts.TekkenLayout
import com.example.fightingflow.ui.comboDisplayScreen.ComboDisplayItem
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
    game: String,
    comboDisplay: ComboDisplay,
    originalCombo: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    character: CharacterEntry,
    characterName: String,
    comboAsString: String,
    moveList: MoveEntryListUiState,
    characterMoveList: MoveEntryListUiState,
    gameMoveList: MoveEntryListUiState,
    iconDisplayState: Boolean,
    textComboDisplay: Boolean,
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
    ComboDisplayItem(
        context = context,
        captureController = rememberCaptureController(),
        toShare = false,
        display = false,
        characterEntryListUiState = CharacterEntryListUiState(),
        combo = comboDisplay,
        username =  username,
        fontColor = MaterialTheme.colorScheme.onBackground,
        iconDisplayState = iconDisplayState,
        textComboState = textComboDisplay,
        comboAsText = comboAsString,
        uiScale = 1f,
        modifier = modifier.padding(vertical = 4.dp)
    )
    if (moveList.moveList.isNotEmpty()) {
        Timber.d("Move Entry List exists, populating Input Selector Column...")
        when (game) {

            "Tekken 8" -> TekkenLayout(
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
                characterMoveList = characterMoveList,
                gameMoveList = gameMoveList
            )

            "Street Fighter VI" -> StreetFighterLayout(
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
                characterMoveList = characterMoveList,
                gameMoveList = gameMoveList
            )

            "Mortal Kombat 1" -> MortalKombatLayout(
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
                characterMoveList = characterMoveList,
                gameMoveList = gameMoveList
            )
        }
    }
}

@Composable
fun InputDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(modifier = modifier.padding(vertical = 8.dp))
}

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    val titleCheck = if (title == "Fatal Blow Title") "Fatal Blow" else title
    Text(titleCheck, modifier = modifier.padding(start = 16.dp))
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
    maxItemsPerRow: Int,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        maxItemsInEachRow = maxItemsPerRow,
        modifier = modifier.fillMaxWidth().padding(4.dp)
    ) {
        Timber.d("Loading Input Icons")
        Timber.d("MoveType: $moveType")
        Timber.d("Move List: $moveList")

        moveList.moveList.forEach { move ->
            if (move.moveType == moveType) {
                val moveId = remember(move) {
                    context.resources.getIdentifier(move.moveName, "drawable", context.packageName)
                }
                Box(modifier.padding(vertical = 4.dp).clip(RoundedCornerShape(10.dp))) {
                    Image(
                        painter = painterResource(moveId),
                        contentDescription = move.moveName,
                        modifier = modifier
                            .padding(horizontal = 16.dp)
                            .size(30.dp)
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
    Timber.d("Moves: $moveList")
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        Timber.d("Loading $moveType text moves")
        moveList.moveList.forEach { move ->

            var color = Color.White
            val booleanStatement = move.moveType == moveType && move.moveName != "move_break"

            when (moveType) {
                "Mishima" -> {
                    color = Color(0xFF000c66)
                }

                "Character" -> {
                    color = Color.Red
                }

                "Mechanics Input" -> {
                    color = Color(0xFF8155BA)
                }

                "Common" -> {
                    color = Color(0xFF444444)
                }

                "Stage" -> {
                    color = Color(0xFF2f5233)
                }

                "Modifier" -> {
                    color = Color.White
                }
            }

            if (booleanStatement) {
                Box(modifier.padding(horizontal = 4.dp, vertical = 4.dp)) {
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
                            fontWeight = FontWeight.ExtraBold,
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
    moveType: String,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("Moves: $characterMoveList")
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        Timber.d("Loading character moves...")
        val color = if (moveType == "Special") Color(0xFF0067B3) else Color(0xFFDC143C)
        val fontColor = Color.White

        characterMoveList.moveList.forEach { move ->
            if (move.moveType == moveType) {
                Box(modifier.padding(horizontal = 4.dp, vertical = 4.dp)) {
                    Box(
                        modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(color)
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(
                            text = move.moveName,
                            color = fontColor,
                            fontSize = (14.sp),
                            fontWeight = FontWeight.ExtraBold,
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
}

@Composable
fun DamageAndConfirm(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    comboDisplay: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,
    saveCombo: KSuspendFunction0<Unit>,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    moveList: MoveEntryListUiState,
    editingState: Boolean,
    originalCombo: ComboDisplay,
    onNavigateToComboDisplay: () -> Unit,

    modifier: Modifier = Modifier
) {
    var damageValue by remember { mutableIntStateOf(comboDisplay.damage) }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Timber.d("Loading Damage and Break Row")

        // Damage Input
        OutlinedTextField(
            value = damageValue.toString(),
            onValueChange = {
                damageValue = it.toIntOrNull() ?: damageValue
                updateComboData(ComboDisplayUiState(comboDisplay.copy(damage = damageValue)))
                Timber.d("damage: $damageValue")
            },
            maxLines = 1,
            label = { Text("Damage") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = modifier.fillMaxWidth(0.5f)
        )

        // Confirm
        Button(
            onClick = {
                Timber.d("Preparing to Save Combo...")
                Timber.d("Checking if changes have been made to combo...")
                    scope.launch {
                        if (editingState) {
                            if (comboDisplay.moves.isEmpty()) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Combo has no moves")
                                }
                            } else if (comboDisplay == originalCombo) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("No changes to original combo")
                                }
                            } else if (comboDisplay.damage == 0) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "No damage value, would you still like to save the combo?",
                                        actionLabel = "Save",
                                        withDismissAction = true
                                    ).run { when (this) {
                                        SnackbarResult.Dismissed -> Timber.d("Snackbar Dismissed")
                                        SnackbarResult.ActionPerformed -> {
                                            saveCombo()
                                            onNavigateToComboDisplay()
                                        }
                                    } }
                                }
                            } else {
                                scope.launch {
                                    Timber.d("Saving combo")
                                    saveCombo()
                                }
                            }
                        } else {
                            if (comboDisplay.moves.isEmpty()) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Combo has no moves")
                                }
                            } else if (comboDisplay.damage == 0) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "No damage value, would you still like to save the combo?",
                                        actionLabel = "Save",
                                        withDismissAction = true
                                    ).run { when (this) {
                                        SnackbarResult.Dismissed -> Timber.d("Snackbar Dismissed")
                                        SnackbarResult.ActionPerformed -> {
                                            saveCombo()
                                            onNavigateToComboDisplay()
                                        }
                                    } }
                                }
                            } else {
                                scope.launch {
                                    Timber.d("Saving combo")
                                    saveCombo()
                                }
                            }
                        }
                    }
            },
            colors = ButtonDefaults.buttonColors().copy(containerColor = Color(0xffed1664),),
            modifier = modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Confirm", color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
fun ConfirmAndClear(
    moveList: MoveEntryListUiState,
    deleteLastMove: () -> Unit,
    clearMoveList: () -> Unit,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("Loading confirm and clear buttons")
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp).padding(bottom = 8.dp)
    ) {
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
        ) {
            Text("Break")
        }
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

