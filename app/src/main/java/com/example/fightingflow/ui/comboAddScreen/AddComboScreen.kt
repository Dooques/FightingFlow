package com.example.fightingflow.ui.comboAddScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.ViewGroup
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
import androidx.compose.material.icons.filled.Close
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
import com.example.fightingflow.ui.comboViewScreen.ComboItem
import com.example.fightingflow.ui.comboViewScreen.ComboViewModel
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.MoveListUiState
import kotlinx.coroutines.flow.first

const val SCREEN_TAG = "AddComboScreen"

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun AddComboScreen(
    addComboViewModel: AddComboViewModel,
    comboViewModel: ComboViewModel,
    updateCharacterState: (String) -> Unit,
    saveComboDetailsToDs: (ComboDisplayUiState) -> Unit,
    getComboDetailsFromDs: (MoveListUiState) -> Unit,
    updateComboData: (ComboDisplayUiState) -> Unit,
    updateMoveList: (String, MoveListUiState) -> Unit,
    saveCombo: () -> Unit,
    deleteLastMove: () -> Unit,
    clearMoveList: () -> Unit,
    onConfirm: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d(SCREEN_TAG, "")
    Log.d(SCREEN_TAG, "Opening Add Combo Screen...")

    Log.d(SCREEN_TAG, "Locking orientation until solution for lost combo data is found.")
    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    // ComboViewModel
    val characterState by comboViewModel.characterState.collectAsState()
    val characterListState by comboViewModel.characterEntryListState.collectAsState()
    val moveListState by comboViewModel.moveEntryListState.collectAsState()

    // AddComboViewModel
    val comboDisplayState by addComboViewModel.comboState.collectAsState()
    val comboAsString by addComboViewModel.comboAsStringState.collectAsState()

    // Datastore Flows
    val characterNameState by comboViewModel.characterNameState.collectAsState()
    val characterImageState by comboViewModel.characterImageState.collectAsState()
    Log.d(SCREEN_TAG, "Flows Collected: " +
            "\nCharacter: ${characterState.character}" +
            "\n\nCharacter Details (Ds): " +
            "\nName: ${characterNameState.name} " +
            "\nImage: ${characterImageState.image}" +
            "\nCombo Display: ${comboDisplayState.comboDisplay}" +
            "\nMove List: ${moveListState.moveList}"
    )

    Log.d(SCREEN_TAG, "Updating Character State")
    if (characterListState.characterList.isNotEmpty() && characterNameState.name.isNotEmpty()) {
        updateCharacterState(characterNameState.name)
    }
    Log.d(SCREEN_TAG, "${characterState.character.name} is loaded.")

//    if (comboDisplayState != ComboDisplayUiState()) {
//        Log.d(SCREEN_TAG, "Loading combo from datastore")
//        getComboDetailsFromDs(moveListState)
//    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxSize()
    ) {
        Log.d(SCREEN_TAG, "")
        Log.d(SCREEN_TAG, "Loading Header...")
        if (characterImageState.image != 0) {
            Header(
                characterNameState.name,
                characterImageState.image,
                navigateBack
            )
        }
        ComboForm(
            addComboViewModel,
            updateComboData,
            updateMoveList,
            characterState.character,
            characterNameState.name,
            comboDisplayState.comboDisplay,
            comboAsString,
            moveListState,
            saveCombo,
            deleteLastMove,
            clearMoveList,
            onConfirm
        )
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
        Log.d(SCREEN_TAG, "Loading Back Icon.")
        IconButton(onClick = navigateBack) { Icon(imageVector = Icons.Default.Close, contentDescription = null, Modifier.size(50.dp)) }
        Log.d(SCREEN_TAG, "Loading Character Name")
        Text(text = characterName, style = MaterialTheme.typography.displayLarge)
        Log.d(SCREEN_TAG, "Loading Character Icon")
        Image(painter = painterResource(characterImage), contentDescription = null, modifier.size(50.dp))
    }
}

@Composable
fun ComboForm(
    addComboViewModel: AddComboViewModel,
    updateComboData: (ComboDisplayUiState) -> Unit,
    updateMoveList: (String, MoveListUiState) -> Unit,
    character: CharacterEntry,
    characterName: String,
    combo: ComboDisplay,
    comboAsString: String,
    moveList: MoveListUiState,
    saveCombo: () -> Unit,
    deleteLastMove: () -> Unit,
    clearMoveList: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d(SCREEN_TAG, "")
    val context = LocalContext.current
    Column {
        Log.d(SCREEN_TAG, "Loading Combo Form")
        if (combo.moves.isNotEmpty()) {
            Log.d(SCREEN_TAG,"Combo Move List exists, populating with moves...")
            ComboDisplay(context, combo)
        }
        if (moveList.moveList.isNotEmpty()) {
            Log.d(SCREEN_TAG, "Move Entry List exists, populating Input Selector Column...")
            InputSelector(
                context,
                character,
                characterName,
                combo,
                updateComboData,
                updateMoveList,
                comboAsString,
                saveCombo,
                deleteLastMove,
                clearMoveList,
                onConfirm,
                moveList
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
    Log.d(SCREEN_TAG, "Getting combo display composable from Combo Screen")
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
    updateMoveList: (String, MoveListUiState) -> Unit,
    comboAsString: String,
    saveCombo: () -> Unit,
    deleteLastMove: () -> Unit,
    clearMoveList: () -> Unit,
    onConfirm: () -> Unit,
    moveList: MoveListUiState,
    modifier: Modifier = Modifier
) {
    Log.d(SCREEN_TAG, "Loading Input Selector")
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
            Log.d(SCREEN_TAG,"Using mishima layout")
        } else {
            Log.d(SCREEN_TAG, "Using normal character layout")
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
            Log.d(SCREEN_TAG, "$moveType loaded.")
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
        Log.d(SCREEN_TAG, "Loading Radio Buttons")
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
                        Log.d(SCREEN_TAG, "Setting counter hit to $counterHit")
                              },
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Hold")
                RadioButton(
                    selected = hold,
                    onClick = {
                        hold = !hold
                        Log.d(SCREEN_TAG, "Setting hold to $hold")
                              },
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Just Frame")
                RadioButton(
                    selected = justFrame,
                    onClick = {
                        justFrame = !justFrame
                        Log.d(SCREEN_TAG, "Setting just frame to $justFrame")
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
    updateMoveList: (String, MoveListUiState) -> Unit,
    moveList: MoveListUiState,
    modifier: Modifier = Modifier
) {
    var damageValue by remember { mutableIntStateOf(combo.damage) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Log.d(SCREEN_TAG, "Loading Damage and Break Row")
        // Damage Input
        OutlinedTextField(
            value = damageValue.toString(),
            onValueChange = {
                damageValue = it.toIntOrNull() ?: 0
                updateComboData(ComboDisplayUiState(combo.copy(damage = damageValue)))
                Log.d(SCREEN_TAG, "damage: $damageValue")
            },
            maxLines = 1,
            label = { Text("Damage") },
            modifier = modifier.fillMaxWidth(0.5f).padding(horizontal = 4.dp)
        )

        // Add Break
        Button(
            onClick = {
                updateMoveList("break", moveList)
                Log.d(SCREEN_TAG, "Adding break to combo move list.")
                      },
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
    moveList: MoveListUiState,
    updateMoveList: (String, MoveListUiState) -> Unit,
    context: Context,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth().padding(4.dp)
    ) {
        Log.d(SCREEN_TAG, "Loading Input Icons")
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
                                    Log.d(VM_TAG, "${move.moveName} selected, preparing to add combo to list...")
                                    updateMoveList(move.moveName, moveList)
                                    Log.d(SCREEN_TAG, "Added ${move.moveName} to combo move list.")
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
    moveList: MoveListUiState,
    character: CharacterEntry,
    updateMoveList: (String, MoveListUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Log.d(SCREEN_TAG, "Loading $moveType text moves")
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
                                    Log.d(VM_TAG, "${move.moveName} selected, preparing to add combo to list...")
                                    updateMoveList(move.moveName, moveList)
                                    Log.d(SCREEN_TAG, "Added ${move.moveName} to combo move list.")
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
    Log.d(SCREEN_TAG, "Loading confirm and clear buttons")
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth().padding(vertical = 4.dp).padding(bottom = 8.dp)) {
        OutlinedButton(
            onClick = {
                saveCombo()
                Log.d(SCREEN_TAG, "Combo saved, returning to Combo Screen")
                onConfirm()
                      },
            content = { Text("Confirm", color = MaterialTheme.colorScheme.onBackground) }
        )
        OutlinedButton(
            onClick = {
                deleteLastMove()
                Log.d(SCREEN_TAG, "Last move deleted.")
                      },
            content = {
                Text("Delete Last", color = MaterialTheme.colorScheme.onBackground) }
        )
        OutlinedButton(
            onClick = {
                clearMoveList()
                Log.d(SCREEN_TAG, "Combo move list cleared.")
                      },
            content = { Text("Clear", color = MaterialTheme.colorScheme.onBackground) }
        )
    }
}