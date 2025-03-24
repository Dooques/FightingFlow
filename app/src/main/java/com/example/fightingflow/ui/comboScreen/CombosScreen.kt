package com.example.fightingflow.ui.comboScreen

import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.MoveEntry

@Composable
fun ComboScreen(
    deviceType: WindowSizeClass,
    comboViewModel: ComboViewModel,
    onAddCombo: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val character by comboViewModel.characterState.collectAsState()
    val combosByCharacter = comboViewModel.getCombosByCharacter()
    Log.d("", character.toString())

    val context = LocalContext.current
    val fontColor = MaterialTheme.colorScheme.onBackground
    val containerColor = MaterialTheme.colorScheme.surfaceDim
    val orientation = LocalConfiguration.current.orientation

    val uiScale =
        if (
            (deviceType.heightSizeClass == WindowHeightSizeClass.Compact && orientation == ORIENTATION_LANDSCAPE) ||
            (deviceType.heightSizeClass == WindowHeightSizeClass.Expanded && deviceType.widthSizeClass == WindowWidthSizeClass.Expanded)
            ) 2f else 1f

    Column {
        Header(fontColor = fontColor, character = character, onAddCombo = onAddCombo)
        LazyColumn {
             items(items = combosByCharacter) { combo ->
                ComboUI(
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

@Composable
fun Header(
    character: CharacterEntry,
    fontColor: Color,
    onAddCombo: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Log.d("", "${character.name} ${character.imageId}")
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(character.imageId),
            contentDescription = character.name,
            modifier = Modifier
                .size(75.dp)
                .align(Alignment.CenterStart)
        )
        Text(
            text = character.name,
            color = fontColor,
            fontSize = if (character.name.length > 9) 60.sp else 80.sp,
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier.align(Alignment.Center)
        )
        IconButton(
            modifier = modifier.align(Alignment.CenterEnd).size(75.dp),
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
fun ComboUI(
    modifier: Modifier = Modifier,
    context: Context,
    combo: ComboDisplay,
    containerColor: Color,
    uiScale: Float,
    fontColor: Color
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
                        )
                    }

                    "Common" -> {
                        TextMove(
                            move,
                            Color.Gray,
                            uiScale,
                            modifier
                                .align(Alignment.CenterVertically)
                        )
                    }

                    "Character" -> {
                        TextMove(
                            move,
                            Color.Red,
                            uiScale,
                            modifier
                                .align(Alignment.CenterVertically)
                        )
                    }

                    "Stage" -> {
                        TextMove(
                            move,
                            Color.Green,
                            uiScale,
                            modifier
                                .align(Alignment.CenterVertically)
                        )
                    }

                    "Mishima" -> {
                        TextMove(
                            move,
                            Color.Blue,
                            uiScale,
                            modifier
                                .align(Alignment.CenterVertically)
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
        modifier = modifier.padding(horizontal = 4.dp).size(size * uiScale)
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
                input.notation,
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