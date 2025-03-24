package com.example.fightingflow.ui.comboScreen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.MoveEntry

@Composable
fun AddComboScreen(
    comboViewModel: ComboViewModel,
    onConfirm: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val character by comboViewModel.characterState.collectAsState()
    val combo by comboViewModel.comboState.collectAsState()
    val comboAsString by comboViewModel.comboAsStringState.collectAsState()
    val moveList by comboViewModel.allMoves.collectAsState()

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxSize()
    ) {
        Header(character, navigateBack)
        ComboForm(comboViewModel, character, combo, comboAsString, moveList, onConfirm)
    }
}

@Composable
fun Header(
    character: CharacterEntry,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = navigateBack) { Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, Modifier.size(50.dp)) }
        Text(text = character.name, style = MaterialTheme.typography.displayLarge)
        Image(painter = painterResource(character.imageId), contentDescription = null, modifier.size(50.dp))
    }
}

@Composable
fun ComboForm(
    comboViewModel: ComboViewModel,
    character: CharacterEntry,
    combo: ComboDisplay,
    comboAsString: String,
    moveList: List<MoveEntry>,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column {
        if (combo.moves.isNotEmpty()) {
            ComboDisplay(context, comboViewModel, combo)
        }
        InputSelector(context, character, combo, comboViewModel, comboAsString, onConfirm, moveList,)
    }
}

@Composable
fun ComboDisplay(
    context: Context,
    comboViewModel: ComboViewModel,
    combo: ComboDisplay,
    modifier: Modifier = Modifier
) {
    val fontColor = MaterialTheme.colorScheme.onBackground
    val containerColor = MaterialTheme.colorScheme.surfaceDim
    val uiScale = 1f

    ComboUI(
        context = context,
        combo = combo,
        fontColor = fontColor,
        containerColor = containerColor,
        uiScale = uiScale,
        modifier = modifier.padding(vertical = 4.dp))
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InputSelector(
    context: Context,
    character: CharacterEntry,
    combo: ComboDisplay,
    comboViewModel: ComboViewModel,
    comboAsString: String,
    onConfirm: () -> Unit,
    moveList: List<MoveEntry>,
    modifier: Modifier = Modifier
) {
    val selectorLayout = listOf(
         "Text Combo", "Buttons", "Divider", "Radio Buttons", "Damage", "Movement", "Input", "Divider",
        "Stances", "Common", "Mishima", "Divider", "${character.name}'s Stances", "Character",
        "Divider", "Mechanics", "Mechanics Input", "Stage",
    )
    LazyColumn {
        items(items = selectorLayout) { moveType ->
            when (moveType) {
                "Text Combo" -> ComboTextEntry(comboAsString)
                "Radio Buttons" -> RadioButtons(combo, comboViewModel)
                "Damage" -> DamageBreak(combo, comboViewModel)
                "Movement" -> IconMoves(moveType, moveList,comboViewModel, context)
                "Input" -> IconMoves(moveType, moveList, comboViewModel, context)
                "Common", "Mishima", "Character", "Mechanics Input", "Stage" -> TextMoves(moveType, moveList, character, comboViewModel)
                "Buttons" -> ConfirmAndClear(comboViewModel, onConfirm)
                "Divider" -> InputDivider()
                "Stances", "Mechanics", "${character.name}'s Stances" -> StanceAndMechanicsTitle(moveType)
            }
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
fun ComboTextEntry(comboAsString: String, modifier: Modifier = Modifier) {
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
fun RadioButtons(combo: ComboDisplay, comboViewModel: ComboViewModel, modifier: Modifier = Modifier) {
    var counterHit by remember {mutableStateOf(false)}
    var hold by remember {mutableStateOf(false)}
    var justFrame by remember {mutableStateOf(false)}
    Column(modifier.padding(horizontal = 16.dp)) {
        Text("Move Modifiers")
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Counter Hit")
                RadioButton(
                    selected = counterHit,
                    onClick = { counterHit = !counterHit },
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Hold")
                RadioButton(
                    selected = hold,
                    onClick = { hold = !hold },
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Just Frame")
                RadioButton(
                    selected = justFrame,
                    onClick = { justFrame = !justFrame },
                )
            }
        }
    }
}

@Composable
fun DamageBreak(combo: ComboDisplay, comboViewModel: ComboViewModel, modifier: Modifier = Modifier) {
    var damageValue by remember { mutableIntStateOf(combo.damage) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = damageValue.toString(),
            onValueChange = {
                damageValue = it.toIntOrNull() ?: 0
                comboViewModel.updateComboData(combo.copy(damage = damageValue))
            },
            maxLines = 1,
            label = { Text("Damage") },
            modifier = modifier.fillMaxWidth(0.5f).padding(horizontal = 4.dp)
        )
        Button(
            onClick = { comboViewModel.updateMoveList(moveName = "break") },
            colors = ButtonDefaults.buttonColors().copy(containerColor = Color(0xffed1664), contentColor = Color.White),
            modifier = modifier.fillMaxWidth().padding(horizontal = 4.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth()) {
                Text("Add Break")
                Image(
                    painter = painterResource(R.drawable.move_divider),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
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
    moveList: List<MoveEntry>,
    comboViewModel: ComboViewModel,
    context: Context,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth().padding(4.dp)
    ) {
        moveList.forEach { move ->
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
                                    comboViewModel.updateMoveList(move.moveName)
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
    moveList: List<MoveEntry>,
    character: CharacterEntry,
    comboViewModel: ComboViewModel,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        moveList.forEach { move ->
            var color = Color.White
            var booleanStatement = move.moveType == moveType && move.moveName != "move_break"

            when (moveType) {
                "Mishima" -> {
                    color = Color.Blue
                    booleanStatement = booleanStatement && (
                            character.fightingStyle.contains("Mishima") ||
                            character.fightingStyle.contains("Devil")
                            )
                }
                "Character" -> {
                    color = Color.Red
                    booleanStatement = booleanStatement && (character.uniqueMoves.contains(move.moveName))
                }
                "Common" -> {
                    color = Color.Gray
                }
                "Stage" -> {
                    color = Color.Green
                }
                else -> {}
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
                            color = if (color == Color.White || color == Color.Green) Color.Black else Color.White,
                            fontSize = (14.sp),
                            modifier = modifier.padding(1.dp).clickable(
                                enabled = true,
                                onClick = {
                                    comboViewModel.updateMoveList(move.moveName)
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
    comboViewModel: ComboViewModel,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth().padding(vertical = 4.dp).padding(bottom = 8.dp)) {
        OutlinedButton(
            onClick = {
                comboViewModel.saveComboToDb()
                onConfirm()
                      },
            content = { Text("Confirm", color = MaterialTheme.colorScheme.onBackground) }
        )
        OutlinedButton(
            onClick = { comboViewModel.deleteLastMove() },
            content = {
                Text("Delete Last", color = MaterialTheme.colorScheme.onBackground) }
        )
        OutlinedButton(
            onClick = { comboViewModel.clearMoveList() },
            content = { Text("Clear", color = MaterialTheme.colorScheme.onBackground) }
        )
    }
}