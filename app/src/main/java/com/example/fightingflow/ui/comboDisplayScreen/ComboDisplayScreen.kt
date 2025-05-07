package com.example.fightingflow.ui.comboDisplayScreen

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.ui.comboCreationScreen.ComboCreationViewModel
import com.example.fightingflow.util.ActionIcon
import com.example.fightingflow.util.SwipeableItem
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComboDisplayScreen(
    deviceType: WindowSizeClass,
    snackbarHostState: SnackbarHostState,
    comboDisplayViewModel: ComboDisplayViewModel,
    updateCharacterState: (String) -> Unit,
    onNavigateToComboEditor: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Timber.d("Opening Combo Screen...")

    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    val comboCreationViewModel = koinInject<ComboCreationViewModel>()

    Timber.d("Getting combo data from database...")
    comboDisplayViewModel.getComboDisplayList()

    // Room Flows
    val characterState by comboDisplayViewModel.characterState.collectAsState()
    val characterListState by comboDisplayViewModel.characterEntryListState.collectAsState()
    val comboDisplayListState by comboDisplayViewModel.comboDisplayListState.collectAsState()
    val comboEntryListState by comboDisplayViewModel.comboEntryListState.collectAsState()

    // Datastore Flows
    val characterNameState by comboDisplayViewModel.characterNameState.collectAsState()
    val characterImageState by comboDisplayViewModel.characterImageState.collectAsState()

    Timber.d("Flows Collected")
    Timber.d("Character: ${characterState.character}")
    Timber.d("Character Details: ${characterNameState.name} ${characterImageState.image}")
    Timber.d("Combo Display List: ${comboDisplayListState.comboDisplayList}")
    Timber.d("Combo Entry List: ${comboEntryListState.comboEntryList}")
    Timber.d("Updating character data")
    Timber.d("Character List: ${characterListState.characterList}")

    if (characterListState.characterList.isNotEmpty() && characterNameState.name.isNotEmpty()) {
        try {
            updateCharacterState(characterNameState.name)
        } catch (e: NoSuchElementException) {
            Timber.e(e, "Character Error, no element found in character list.")
        }
    }

    val fontColor = MaterialTheme.colorScheme.onBackground
    val containerColor = MaterialTheme.colorScheme.surfaceDim
    val scope = rememberCoroutineScope()

    val uiScale = if (deviceType.widthSizeClass != WindowWidthSizeClass.Compact) 2f else 1f

    Timber.d("Checking combos for ${characterNameState.name}")
    val combosByCharacter =
        if (comboDisplayListState.comboDisplayList.isNotEmpty()) {
            Timber.d("Combos found.")
            comboDisplayListState.comboDisplayList
                .mapNotNull {
                    if (it.character == characterState.character.name) it else null
                }
                .toMutableList()
        } else {
            Timber.d("No combos found.")
            emptyList<ComboDisplay>().toMutableList()
        }
    Timber.d("Combos reduced to ${characterState.character.name}'s: $combosByCharacter")

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
                    } },
                actions = {
                    Image(
                        painter = painterResource(characterState.character.imageId),
                        contentDescription = "",
                        modifier = modifier.size(60.dp)
                    )
                    IconButton(onClick = {
                        comboCreationViewModel.editingState.value = false
                        onNavigateToComboEditor()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Combo",
                            modifier = modifier.size(80.dp)
                        )
                } },
                windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
        )
        }
    ) { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            Timber.d("Character Details \n Name: ${characterNameState.name} \n Image: ${characterImageState.image}")
            Timber.d("Checking details valid...")

            LazyColumn {
                Timber.d("Getting display combos as lazy column with swipeable actions.")
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
                                    Timber.d("Sharing Combo")
                                    Toast.makeText(
                                        context,
                                        "Combo ${combo.comboId} was shared.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                tint = Color.Blue,
                                icon = Icons.Default.Share,
                                modifier = modifier.fillMaxHeight()
                            )
                            // Edit Combo
                            ActionIcon(
                                onclick = {
                                    Timber.d("Preparing to edit combo")
                                    scope.launch {
                                        comboDisplayViewModel.saveComboIdToDs(combo)
                                        comboCreationViewModel.editingState.value = true
                                        snackbarHostState.showSnackbar(
                                            message = "Combo ${combo.comboId} is being sent to the editor.",
                                            duration = SnackbarDuration.Short
                                            )
                                        onNavigateToComboEditor()
                                    }
                                },
                                tint = Color.Green,
                                icon = Icons.Default.Edit,
                                modifier = modifier.fillMaxHeight()
                            )
                            // Delete Combo
                            ActionIcon(
                                onclick = {
                                    scope.launch {
                                        comboDisplayViewModel.deleteCombo(
                                            combo,
                                            comboEntryListState.comboEntryList
                                        )
                                        Timber.d("UI deleted: $combo")
                                    }
                                    Toast.makeText(
                                        context,
                                        "Combo ${combo.comboId} was deleted.",
                                        Toast.LENGTH_SHORT
                                    ).show()
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
    Timber.d("Loading Combo Moves Composable...")
    Column {
        Timber.d("Loading flow row...")
        FlowRow(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Start,
            modifier = modifier
                .fillMaxWidth()
                .background(containerColor)
                .padding(horizontal = 4.dp, vertical = 4.dp)
        ) {
            Timber.d("Loading moves from combo...")
            combo.moves.forEach { move ->
                Timber.d(move.moveName)
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

                    "SCOMBO_TAGe" -> {
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