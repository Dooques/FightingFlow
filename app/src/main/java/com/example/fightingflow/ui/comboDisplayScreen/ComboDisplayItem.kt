package com.example.fightingflow.ui.comboDisplayScreen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.data.TestData
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.ui.theme.FightingFlowTheme
import com.example.fightingflow.util.CharacterEntryListUiState
import com.example.fightingflow.util.characterAndMoveData.CharacterAndMoveData
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.CaptureController
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import timber.log.Timber


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ComboDisplay(
    context: Context,
    captureController: CaptureController,
    toShare: Boolean,
    display: Boolean,
    characterEntryListUiState: CharacterEntryListUiState,
    combo: ComboDisplay,
    username: String,
    uiScale: Float,
    fontColor: Color,
    modifier: Modifier = Modifier,
) {
    Timber.d("Loading Combo Moves Composable...")
    Box(modifier.fillMaxWidth().capturable(captureController)) {
        if (toShare) {
            Image(
                painterResource(
                    characterEntryListUiState.characterList.first { it.name == combo.character }
                        .imageId
                ),
                null,
                modifier
                    .size(50.dp)
                    .align(Alignment.BottomEnd)
            )
        }
        Column(
            modifier.padding(horizontal = (4 * uiScale).dp, vertical = (4 * uiScale).dp)
        ) {
            Timber.d("Loading flow row...")
            if (display) {
                ComboInfoTop(combo, uiScale)
            }
            FlowRow(
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.Start,
                itemVerticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Timber.d("Loading moves from combo...")
                combo.moves.forEach { move ->
                    Timber.d(move.moveName)
                    when (move.moveType) {
                        "Break" -> MoveBreak(uiScale, modifier.align(Alignment.CenterVertically))
                        "Misc" -> MiscInput(move)
                        "Input", "SF Input", "Movement", "Complex Movement" -> InputMove(
                            context = context,
                            input = move,
                            uiScale = uiScale,
                            modifier = modifier
                                .align(Alignment.CenterVertically)
                                .padding(4.dp)
                        )

                        "Common" ->
                            TextMove(
                                input = move,
                                color = Color(0xFF444444),
                                uiScale = uiScale,
                                modifier = modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(4.dp)
                            )

                        "Special" ->
                            TextMove(
                                input = move,
                                color = Color(0xFF0067B3),
                                uiScale = uiScale,
                                modifier = modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(4.dp)
                            )
                        "Stage" ->
                            TextMove(
                                input = move,
                                color = Color(0xFF2f5233),
                                uiScale = uiScale,
                                modifier = modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(4.dp)
                            )

                        "Mechanics Input", "Super Art", "Mishima" ->
                            TextMove(
                                input = move,
                                color = Color(0xFF8155BA),
                                uiScale = uiScale,
                                modifier = modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(4.dp)
                            )

                        "Character", "Fatal Blow", "Drive"   ->
                            TextMove(
                                input = move,
                                color = Color(0xFFDC143C),
                                uiScale = uiScale,
                                modifier = modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(4.dp)
                            )

                        "Modifier", "MK Input" ->
                            TextMove(
                                input = move,
                                color = Color.White,
                                uiScale = uiScale,
                                modifier = modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(4.dp)
                            )
                    }
                }
            }
            ComboInfoBottom(combo, username, fontColor)
        }
    }
}

@Composable
fun ComboInfoTop(
    combo: ComboDisplay,
    uiScale: Float,
    modifier: Modifier = Modifier
) {
    val fontSize = if (uiScale == 2f) 20.sp else 16.sp
    Box(Modifier.fillMaxWidth()) {
        Row(
            modifier = modifier.align(alignment = Alignment.CenterStart)
        ) {
            Text(text = combo.description, fontSize = fontSize)
        }
        Row(modifier.align(Alignment.CenterEnd)) {
            Text(text = combo.character, fontSize = fontSize)
            Spacer(modifier.width((4 * uiScale).dp))
            Text(text = "|")
            Spacer(modifier.width((4 * uiScale).dp))
            Text(text = combo.dateCreated, fontSize = fontSize)
        }
    }
}

@Composable
fun ComboInfoBottom(
    combo: ComboDisplay,
    profile: String,
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
            Text(text = combo.createdBy.ifEmpty { profile }, color = fontColor)
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
    Row(modifier = modifier) {
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
            .clip(RoundedCornerShape(25.dp))
            .background(color)
    ) {
        Text(
            text = input.moveName,
            color = when (color) {
                Color.Green, Color.White, Color.Yellow -> Color.Black
                else -> Color.White
            },
            fontSize = (16.sp * uiScale),
            lineHeight = 17.sp,
            modifier = modifier
                .padding(horizontal = 1.dp, vertical = 0.dp)
        )
    }
}

@Composable
fun MoveBreak(
    uiScale: Float,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Filled.PlayArrow,
        contentDescription = "",
        tint = Color.Cyan,
        modifier = modifier.size((20 * uiScale).dp)
    )
}

@Composable
fun MiscInput(
    move: MoveEntry,
    modifier: Modifier = Modifier
) {
    Text(
        text = move.notation,
        fontSize = 40.sp,
        color = MaterialTheme.colorScheme.onBackground,

    )
}

@Composable
@Preview(device = "spec:width=673dp,height=841dp")
fun ComboItemPreviewTablet() {
    FightingFlowTheme {
        Surface {
            ComboDisplay(
                context = LocalContext.current,
                captureController = rememberCaptureController(),
                toShare = true,
                display = false,
                characterEntryListUiState = CharacterEntryListUiState(CharacterAndMoveData().characterEntries[0]),
                combo = TestData(CharacterAndMoveData()).comboItem.comboDisplay,
                username = "",
                uiScale = 2f,
                fontColor = Color.White,
            )
        }
    }
}

@Composable
@Preview(device = "spec:width=411dp,height=891dp")
fun ComboItemPreviewPhone() {
    FightingFlowTheme {
        Surface {
            ComboDisplay(
                context = LocalContext.current,
                captureController = rememberCaptureController(),
                toShare = true,
                display = true,
                characterEntryListUiState = CharacterEntryListUiState(CharacterAndMoveData().characterEntries[0]),
                combo = TestData(CharacterAndMoveData()).comboItem.comboDisplay,
                username = "",
                uiScale = 1f,
                fontColor = Color.White,
            )
        }
    }
}