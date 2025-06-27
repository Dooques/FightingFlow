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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Star
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.Console
import com.example.fightingflow.model.Game
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.ui.comboDisplayScreen.inputConverter.convertInputsToConsole
import com.example.fightingflow.ui.comboDisplayScreen.comboItem.ComboItemViewModel
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.characterAndMoveData.convertibleInputs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber
import kotlin.reflect.KFunction4
import kotlin.reflect.KSuspendFunction0


@Composable
fun InputDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(modifier = modifier.padding(vertical = 8.dp))
}

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    val titleCheck =
        if (title == "Fatal Blow Title") "Fatal Blow"
        else title
    Text(titleCheck, modifier = modifier.padding(start = 16.dp))
}

@Composable
fun ComboAsText(
    comboAsString: String,
    modifier: Modifier = Modifier
) {
    val comboItemViewModel = koinInject<ComboItemViewModel>()
    Column {
        OutlinedTextField(
            value = if (comboAsString.isNotEmpty())comboItemViewModel.processComboStringForDisplay(comboAsString) else comboAsString,
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
    var description by remember { mutableStateOf(combo.title.ifEmpty { "" }) }
    OutlinedTextField(
        value = description,
        onValueChange = {
            if (description.length <= 34 ) {
                description = it
                updateComboData(ComboDisplayUiState(combo.copy(title = description)))
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
    modifier: Modifier = Modifier,
    moveType: String,
    moveList: MoveEntryListUiState,
    updateMoveList: KFunction4<String, MoveEntryListUiState, Game, Console?, Unit>? = null,
    console: Console?,
    sF6ControlType: SF6ControlType? = SF6ControlType.Invalid,
    context: Context,
    maxItems: Int = 5,
) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        maxItemsInEachRow = maxItems,
        modifier = modifier.fillMaxWidth().padding(4.dp)
    ) {
        Timber.d("Loading Input Icons " +
                "\n MoveType: $moveType")

        moveList.moveList.forEach {
            val game = when (it.game) {
                "Tekken 8" -> Game.T8
                "Street Fighter VI" -> Game.SF6
                "Mortal Kombat 1" -> Game.MK1
                else -> Game.CUSTOM
            }

            val move = if (it.moveName in convertibleInputs && game != Game.CUSTOM) convertInputsToConsole(
                move = it,
                game = game,
                console = console,
                classic = sF6ControlType == SF6ControlType.Classic
            ) else it

            if (move.moveType == moveType) {
                val moveId =
                    remember(move) {
                        context.resources.getIdentifier(
                            move.moveName,
                            "drawable",
                            context.packageName
                        )
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
                                    Timber.d("Move: $move")
                                    if (updateMoveList != null) {
                                        if (moveType == "Misc") {
                                            updateMoveList(move.moveName, moveList, game, null)
                                        } else {
                                            console?.let { outerConsole ->
                                                Timber.d("Console: $outerConsole")
                                                Timber.d("Game: $game")
                                                updateMoveList(
                                                    move.moveName,
                                                    moveList,
                                                    game,
                                                    outerConsole
                                                )
                                            }
                                            Timber.d("Added ${move.moveName} to combo move list.")
                                        }
                                    }
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
    modifier: Modifier = Modifier,
    moveType: String,
    moveList: MoveEntryListUiState,
    maxItems: Int = 5,
    console: Console?,
    updateMoveList: KFunction4<String, MoveEntryListUiState, Game, Console?, Unit>? = null,
) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        maxItemsInEachRow = maxItems,
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        Timber.d("Loading $moveType text moves" +
                " \n Console: $console")
        moveList.moveList.forEach { move ->

            if (move.moveType == moveType) Timber.d("Move: $move")

            val game = when (move.game) {
                "Tekken 8" -> Game.T8
                "Street Fighter VI" -> Game.SF6
                "Mortal Kombat 1" -> Game.MK1
                else -> Game.CUSTOM
            }

            val booleanStatement = move.moveType == moveType && move.moveName != "move_break"

            val color: Color = when (moveType) {
                "Mishima" -> { Color(0xFF000c66) }

                "SF Classic", "SF Modern" ->
                    if (move.moveName.contains("L")) {
                        Color(0xFFf0c027)
                    } else if (move.moveName.contains("M")) {
                        Color(0xFFff914d)
                    } else if (move.moveName.contains("H")) {
                        Color(0xFFff0000)
                    } else { Color(0xFF7ed957) }

                "Character" -> { Color.Red }

                "Mechanic" -> { Color(0xFF8155BA) }

                "Common" -> { Color(0xFF444444) }

                "Stage" -> { Color(0xFF2f5233) }

                "Unique Move" -> { Color(0xFF0067B3) }

                else -> Color.White

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
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = if (color == Color.White || color == Color.Green) Color.Black else Color.White,
                                shadow = Shadow(color = Color.Black.copy(alpha = 0.5f), offset = Offset(2f, 2f), blurRadius = 4f)
                            ),
                            modifier = modifier.padding(1.dp).clickable(
                                enabled = true,
                                onClick = {
                                    Timber.d("$move selected, preparing to add combo to list..." +
                                            "\n $console \n $game")

                                    console?.let { outerConsole ->
                                        if (updateMoveList != null) {
                                            updateMoveList(
                                                move.moveName,
                                                moveList,
                                                game,
                                                outerConsole
                                            )
                                        }
                                    }
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
    modifier: Modifier = Modifier,
    characterMoveList: MoveEntryListUiState,
    character: CharacterEntry,
    moveType: String,
    updateMoveList: KFunction4<String, MoveEntryListUiState, Game, Console?, Unit>,
    maxItems: Int = 5,
) {
    Timber.d("Moves: $characterMoveList")
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        maxItemsInEachRow = maxItems,
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        Timber.d("Loading character moves...")
        val color =
            if (moveType == "Special") Color(0xFF0067B3)
            else Color(0xFFDC143C)

        val game = when (character.game) {
            Game.T8.title -> Game.T8
            Game.SF6.title -> Game.SF6
            Game.MK1.title -> Game.MK1
            else -> Game.CUSTOM
        }

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
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = if (color == Color.White || color == Color.Green) Color.Black else Color.White,
                                shadow = Shadow(color = Color.Black, offset = Offset(2f, 2f), blurRadius = 2f)
                            ),
                            modifier = modifier.padding(1.dp).clickable(
                                enabled = true,
                                onClick = {
                                    Timber.d("${move.moveName} selected, preparing to add combo to list...")
                                    updateMoveList(move.moveName, characterMoveList, game, null)
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
fun DamageAndDifficulty(
    comboDisplay: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,

    modifier: Modifier = Modifier
) {
    var damageValue by remember { mutableIntStateOf(comboDisplay.damage) }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
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
            modifier = modifier.fillMaxWidth(0.4f)
        )
        Row {
            (1..5).forEachIndexed { index, item ->
                IconButton(onClick = { updateComboData(
                    ComboDisplayUiState(comboDisplay.copy(difficulty = item.toFloat()))) }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = if (index <= comboDisplay.difficulty - 1)
                            Color.Yellow else Color.Gray,
                        modifier = modifier.size(30.dp)
                        )
                }
            }
        }
    }
}

@Composable
fun ConfirmButton(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    comboDisplay: ComboDisplay,
    saveCombo: KSuspendFunction0<Unit>,
    editingState: Boolean,
    originalCombo: ComboDisplay,
    onNavigateToComboDisplay: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                            onNavigateToComboDisplay()
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
                            onNavigateToComboDisplay()
                        }
                    }
                }
            }
        },
        colors = ButtonDefaults.buttonColors().copy(containerColor = Color(0xffed1664),),
    ) {
        Text(
            text = "Save",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color =  Color.White,
                shadow = Shadow(color = Color.Black.copy(alpha = 0.5f), offset = Offset(2f, 2f), blurRadius = 4f)
            )
        )
    }
}

@Composable
fun EditingButtons(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    game: Game,
    comboDisplay: ComboDisplay,
    saveCombo: KSuspendFunction0<Unit>,
    editingState: Boolean,
    originalCombo: ComboDisplay,
    onNavigateToComboDisplay: () -> Unit,
    moveList: MoveEntryListUiState,
    deleteMove: () -> Unit,
    clearMoveList: () -> Unit,
    updateMoveList: KFunction4<String, MoveEntryListUiState, Game, Console?, Unit>,
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
                updateMoveList("break", moveList, game, null)
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
                deleteMove()
                Timber.d("Last move deleted.")
            },
            content = {
                Text("Delete", color = MaterialTheme.colorScheme.onBackground) }
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
        ConfirmButton(
            scope = scope,
            snackbarHostState = snackbarHostState,
            comboDisplay = comboDisplay,
            saveCombo = saveCombo,
            editingState = editingState,
            originalCombo = originalCombo,
            onNavigateToComboDisplay = onNavigateToComboDisplay,
        )
    }
}
