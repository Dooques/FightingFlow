package com.example.fightingflow.ui.comboScreen

import android.content.Context
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.R
import com.example.fightingflow.data.DataSource
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.MoveString
import com.example.fightingflow.ui.theme.FightingFlowTheme

@Composable
fun ComboScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val fontColor = MaterialTheme.colorScheme.onBackground
    val containerColor = MaterialTheme.colorScheme.surfaceDim
    val sampleList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    Column {
        Header(fontColor = fontColor)
        LazyColumn {
             items(items = sampleList) {
                ComboUI(
                    context = context,
                    fontColor = fontColor,
                    containerColor = containerColor,
                    modifier = modifier.padding(vertical = 4.dp, horizontal = 0.dp)
                )
            }
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    fontColor: Color
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(R.drawable.reina_sprite),
            contentDescription = stringResource(R.string.reina),
            modifier = Modifier.size(75.dp).align(Alignment.CenterStart)
        )
        Text(
            text = stringResource(R.string.reina),
            color = fontColor,
            fontSize = 80.sp,
            style = MaterialTheme.typography.displayLarge,
            modifier = modifier.align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ComboUI(
    modifier: Modifier = Modifier,
    context: Context,
    containerColor: Color,
    fontColor: Color
) {
    val sampleCombo = DataSource.combo
    Column {
        FlowRow(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .background(containerColor)
                .padding(horizontal = 0.dp, vertical = 4.dp)
        ) {
            sampleCombo.moves.forEach { move ->
                when (move.moveType.lowercase()) {
                    "input" -> {
                        InputMove(
                            context,
                            move.moveString,
                            modifier.padding(4.dp).align(Alignment.CenterVertically)
                        )
                        if (sampleCombo.moves.last() != move) {
                            MoveBreak(modifier.align(Alignment.CenterVertically))
                        }
                    }

                    "common" -> {
                        TextMove(
                            move,
                            Color.Blue,
                            modifier.padding(4.dp).align(Alignment.CenterVertically)
                        )
                        if (sampleCombo.moves.last() != move) {
                            MoveBreak(modifier.align(Alignment.CenterVertically))
                        }
                    }

                    "character" -> {
                        TextMove(
                            move,
                            Color.Red,
                            modifier.padding(4.dp).align(Alignment.CenterVertically)
                        )
                        if (sampleCombo.moves.last() != move) {
                            MoveBreak(modifier.align(Alignment.CenterVertically))
                        }
                    }

                    "stage" -> {
                        TextMove(
                            move,
                            Color.Green,
                            modifier.padding(4.dp).align(Alignment.CenterVertically)
                        )
                        if (sampleCombo.moves.last() != move) {
                            MoveBreak(modifier.align(Alignment.CenterVertically))
                        }
                    }
                }
            }
        }
        ComboData(sampleCombo, fontColor)
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
        Text(text = combo.character, color = fontColor)
        Row {
            Text(text = "Created by: ", color = fontColor)
            Text(text = combo.createdBy, color = fontColor)
        }
    }
}

@Composable
fun InputMove(
    context: Context,
    input: List<String>,
    modifier: Modifier = Modifier
) {
    Row {
        input.forEach {
            MoveImage(it, context)
        }
    }
}

@Composable
fun MoveImage(
    move: String,
    context: Context,
    modifier: Modifier = Modifier
) {
    val moveId = remember(move) { context.resources.getIdentifier(move, "drawable", context.packageName) }
    val size = 40.dp

    Image(
        painter = painterResource(id = moveId),
        contentDescription = move,
        modifier = modifier.size(size)
    )
}

@Composable
fun TextMove(
    input: MoveString,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(modifier.background(color).padding(horizontal = 2.dp)) {
        Row {
            input.moveString.forEach {
                Text(it, color = if (color == Color.White) Color.Black else Color.White)
            }
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

@Preview()
@Composable
fun ComboPreview() {
    FightingFlowTheme {
        ComboScreen()
    }
}