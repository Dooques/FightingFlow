package com.example.fightingflow.ui.comboViewScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.ActionIcon
import com.example.fightingflow.util.CharacterUiState
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.SwipeableItem
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val TAG = "ComboScreen"

@Composable
fun ComboScreen(
    deviceType: WindowSizeClass,
    comboViewModel: ComboViewModel,
    updateCharacterState: (String) -> Unit,
    getMoveEntryData: (List<MoveEntry>, ComboDisplay) -> ComboDisplay,
    onAddCombo: () -> Unit,
    onEditCombo: (ComboDisplayUiState) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Log.d(TAG, "")
    Log.d(TAG, "\nOpening Combo Screen...")

    // Room Flows
    val characterState by comboViewModel.characterState.collectAsState()
    val comboState by comboViewModel.comboDisplayState.collectAsState()
    val characterListState by comboViewModel.characterEntryListState.collectAsState()
    val comboDisplayListState by comboViewModel.comboDisplayListState.collectAsState()
    val comboEntryListState by comboViewModel.comboEntryListSate.collectAsState()
    val moveListState by comboViewModel.moveEntryListState.collectAsState()

    // Datastore Flows
    val characterNameState by comboViewModel.characterNameState.collectAsState()
    val characterImageState by comboViewModel.characterImageState.collectAsState()
    Log.d(TAG, "Flows Collected")

    Log.d(TAG, "Character: ${characterState.character}")
    Log.d(TAG, "Character Details: \n${characterNameState.name} \n${characterImageState.image}")
    Log.d(TAG, "Combo Display List: ${comboDisplayListState.comboDisplayList}")
    Log.d(TAG, "Combo Entry List: ${comboEntryListState.comboEntryList}")

    Log.d(TAG, "Updating character data")
    Log.d(TAG, "Character List: ${characterListState.characterList}")
    if (characterListState.characterList.isNotEmpty() && characterNameState.name.isNotEmpty()) {

        try { updateCharacterState(characterNameState.name) }
        catch(e: NoSuchElementException) {
            Log.d(TAG, "Character Error, no element found in character list.")
        }
    }

    val context = LocalContext.current
    val fontColor = MaterialTheme.colorScheme.onBackground
    val containerColor = MaterialTheme.colorScheme.surfaceDim
    val scope = rememberCoroutineScope()

    val uiScale = if (deviceType.widthSizeClass != WindowWidthSizeClass.Compact) 2f else 1f

    val updatedCombos =
        comboDisplayListState.comboDisplayList.map { combo ->
            getMoveEntryData(moveListState.moveList, combo)
        }
    Log.d(TAG, "Updated Combo list: $updatedCombos")

    val combosByCharacter =
        updatedCombos
            .mapNotNull {
                if (it.character == characterState.character.name) it else null
            }
            .toMutableList()
    Log.d(TAG, "Combos reduced to ${characterState.character.name}'s: $combosByCharacter")

    Column {
        Log.d(TAG, "Character Details \n Name: ${characterNameState.name} \n Image: ${characterImageState.image}")
        Log.d(TAG, "Checking details valid...")

        if (characterImageState.image != 0) {
            Log.d(TAG, "")
            Log.d(TAG, "Loading header...")
            Log.d(TAG, "Character details valid, loading header.")
            Header(
                fontColor = fontColor,
                character = characterState.character,
                characterName = characterNameState.name,
                characterImage = characterImageState.image,
                onAddCombo = onAddCombo
            )
        }
        Log.d(TAG, "Header loaded.")

        LazyColumn {
            Log.d(TAG, "")
            Log.d(TAG, "Getting display combos as lazy column with swipeable actions.")
             itemsIndexed(items = combosByCharacter) { index, combo ->
                 val isOptionRevealed by remember { mutableStateOf(false) }
                 SwipeableItem(
                     isRevealed = isOptionRevealed,
                     onExpanded = {
                         combosByCharacter[index] = combo.copy(areOptionsRevealed = true)
                     },
                     onCollapsed = {
                         combosByCharacter[index] = combo.copy(areOptionsRevealed = false)
                     },
                     actions = {
                         // Share Combo
                         ActionIcon(
                             onclick = {
                                 Log.d(TAG,"Sharing Combo")
                                 Toast.makeText(
                                     context,
                                     "Combo ${combo.comboId} was shared.",
                                     Toast.LENGTH_SHORT).show()
                                       },
                             tint = Color.Blue,
                             icon = Icons.Default.Share,
                             modifier = modifier.fillMaxHeight()
                         )
                         // Edit Combo
//                         ActionIcon(
//                             onclick = {
//                                 Log.d(TAG, "Preparing to edit combo")
//                                 Log.d(TAG, "")
//                                 onEditCombo(ComboDisplayUiState(combo))
//                                 Toast.makeText(context, "Combo ${combo.comboId} is being sent to the editor.", Toast.LENGTH_SHORT).show()
//                                       },
//                             tint = Color.Green,
//                             icon = Icons.Default.Edit,
//                             modifier = modifier.fillMaxHeight()
//                         )
                         // Delete Combo
                         ActionIcon(
                             onclick = {
                                 scope.launch {
                                     comboViewModel.deleteCombo(combo, comboEntryListState.comboEntryList)
                                     Log.d("", "UI deleted: $combo")
                                 }
                                 Toast.makeText(context, "Combo ${combo.comboId} was deleted.", Toast.LENGTH_SHORT).show()
                                       },
                             tint = Color.Red,
                             icon = Icons.Default.Delete,
                             modifier = modifier.fillMaxHeight()
                         )
                     },
                     modifier = modifier,
                 ) {
                     ComboItem(
                         context = context,
                         fontColor = fontColor,
                         combo = combo,
                         containerColor = containerColor,
                         uiScale = uiScale,
                         modifier = modifier.padding(vertical = 4.dp, horizontal = 0.dp)
                     )
                }
            }
        }
    }
}

@Composable
fun Header(
    character: CharacterEntry,
    characterName: String,
    characterImage: Int,
    fontColor: Color,
    onAddCombo: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Log.d(TAG, "")
        Log.d(TAG, "Loading Character Image ${characterImage}...")
        Image(
            painter = painterResource(characterImage),
            contentDescription = characterName,
            modifier = Modifier
                .size(75.dp)
                .align(Alignment.CenterStart)
        )
        Log.d(TAG, "Loading Character Name: ${characterName}...")
        Text(
            text = characterName,
            color = fontColor,
            fontSize = if (character.name.length > 9) 60.sp else 80.sp,
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier.align(Alignment.Center)
        )
        Log.d(TAG, "Loading Icon image")
        IconButton(
            modifier = modifier
                .align(Alignment.CenterEnd)
                .size(75.dp),
            onClick = onAddCombo,
            content = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_combo),
                    modifier.size(75.dp)
                )
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ComboItem(
    context: Context,
    combo: ComboDisplay,
    containerColor: Color,
    uiScale: Float,
    fontColor: Color,
    modifier: Modifier = Modifier,
) {
    Column {
        FlowRow(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Start,
            modifier = modifier
                .fillMaxWidth()
                .background(containerColor)
                .padding(horizontal = 4.dp, vertical = 4.dp)
        ) {
            combo.moves.forEach { move ->
                when (move.moveType) {
                    "Break" -> MoveBreak(modifier.align(Alignment.CenterVertically))
                    "Input", "Movement" -> {
                        InputMove(
                            context,
                            move,
                            uiScale,
                            modifier
                                .align(Alignment.CenterVertically)
                                .padding(4.dp)
                        )
                    }
                    "Common" -> {
                        TextMove(
                            move,
                            Color.Gray,
                            uiScale,
                            modifier
                                .align(Alignment.CenterVertically)
                                .padding(4.dp)
                        )
                    }
                    "Character" -> {
                        TextMove(
                            move,
                            Color.Red,
                            uiScale,
                            modifier
                                .align(Alignment.CenterVertically)
                                .padding(4.dp)
                        )
                    }

                    "Stage" -> {
                        TextMove(
                            move,
                            Color.Green,
                            uiScale,
                            modifier
                                .align(Alignment.CenterVertically)
                                .padding(4.dp)
                        )
                    }

                    "Mishima" -> {
                        TextMove(
                            move,
                            Color.Blue,
                            uiScale,
                            modifier
                                .align(Alignment.CenterVertically)
                                .padding(4.dp)
                        )
                    }
                    "Mechanics Input" -> {
                        TextMove(
                            move,
                            Color.Blue,
                            uiScale,
                            modifier
                                .align(Alignment.CenterVertically)
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
        ComboData(combo, fontColor)
    }
}

@Composable
fun ComboData(
    combo: ComboDisplay,
    fontColor: Color,
    modifier: Modifier = Modifier
) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth()) {
        Row {
            Text(text = "Damage: ", color = fontColor)
            Text(text = combo.damage.toString(), color = Color.Red)
        }
        Row {
            Text(text = "Created by: ", color = fontColor)
            Text(text = combo.createdBy, color = fontColor)
        }
    }
}

@Composable
fun InputMove(
    context: Context,
    input: MoveEntry,
    uiScale: Float,
    modifier: Modifier = Modifier
) {
    Row {
        MoveImage(input.moveName, uiScale = uiScale,  context = context)
    }
}

@Composable
fun MoveImage(
    move: String,
    context: Context,
    uiScale: Float,
    modifier: Modifier = Modifier
) {
    val moveId = remember(move) { context.resources.getIdentifier(move, "drawable", context.packageName) }
    val size = 40.dp

    Image(
        painter = painterResource(id = moveId),
        contentDescription = move,
        modifier = modifier
            .padding(horizontal = 4.dp)
            .size(size * uiScale)
    )
}

@Composable
fun TextMove(
    input: MoveEntry,
    color: Color,
    uiScale: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .background(color)
            .padding(horizontal = 2.dp)
    ) {
        Row {
            Text(
                input.moveName,
                color = if (color == Color.White) Color.Black else Color.White,
                fontSize = (16.sp * uiScale)
            )
        }
    }
}

@Composable
fun MoveBreak(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Filled.PlayArrow,
        contentDescription = "",
        tint = Color.Cyan,
        modifier = modifier.size(20.dp)
    )
}