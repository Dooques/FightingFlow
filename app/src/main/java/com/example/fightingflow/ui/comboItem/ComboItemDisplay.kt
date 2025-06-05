package com.example.fightingflow.ui.comboItem

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fightingflow.data.datastore.Console
import com.example.fightingflow.data.datastore.Game
import com.example.fightingflow.data.datastore.SF6ControlType
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.ui.comboCreationScreen.ComboAsText
import com.example.fightingflow.ui.comboDisplayScreen.inputConverter.convertInputsToConsole
import com.example.fightingflow.util.CharacterEntryListUiState
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.CaptureController
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ComboItemDisplay(
    context: Context,
    captureController: CaptureController,
    toShare: Boolean,
    display: Boolean,
    characterEntryListUiState: CharacterEntryListUiState,
    combo: ComboDisplay,
    comboAsText: String,
    username: String,
    console: Console?,
    sf6ControlType: SF6ControlType?,
    iconDisplayState: Boolean,
    textComboState: Boolean,
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
            Timber.d("Move List: ${combo.moves}")
            if (display) {
                ComboInfoTop(combo, uiScale)
            }
            if (iconDisplayState) {
                FlowRow(
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.Start,
                    itemVerticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Timber.d("Loading moves from combo...")
                    combo.moves.forEach {
                        Timber.d("$it from ${it.game}")
                        val move = convertInputsToConsole(
                            move = it,
                            game = when (it.game) {
                                "Tekken 8" -> Game.T8
                                "Street Fighter VI" -> Game.SF6
                                "Mortal Kombat 1" -> Game.MK1
                                else -> null
                            },
                            console = console,
                            classic = sf6ControlType == SF6ControlType.Classic
                        )

                        Timber.d(move.moveName)
                        when (move.moveType) {
                            "Break" -> MoveBreak(
                                uiScale,
                                modifier.align(Alignment.CenterVertically)
                            )

                            "Misc" -> MiscInput(move)

                            "Input", "Movement", "Complex Movement", "Console" -> InputMove(
                                context = context,
                                input = move,
                                uiScale = uiScale,
                                modifier = modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(4.dp)
                            )

                            "SF Classic", "SF Modern" -> TextMove(
                                move = move,
                                color = if (move.moveName.contains("L")) {
                                    Color(0xFFf0c027)
                                } else if (move.moveName.contains("M")) {
                                    Color(0xFFe23a10)
                                } else if (move.moveName.contains("H")) {
                                    Color(0xFFff0000)
                                } else { Color(0xFF7ed957) },
                                uiScale = uiScale,
                                modifier = modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(4.dp)
                            )

                            "Common", "Console Text" ->
                                TextMove(
                                    move = move,
                                    color = Color(0xFF444444),
                                    uiScale = uiScale,
                                    modifier = modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(4.dp)
                                )

                            "Special" ->
                                TextMove(
                                    move = move,
                                    color = Color(0xFF0067B3),
                                    uiScale = uiScale,
                                    modifier = modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(4.dp)
                                )

                            "Stage" ->
                                TextMove(
                                    move = move,
                                    color = Color(0xFF2f5233),
                                    uiScale = uiScale,
                                    modifier = modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(4.dp)
                                )

                            "Mishima" ->
                                TextMove(
                                    move = move,
                                    color = Color(0xFF8155BA),
                                    uiScale = uiScale,
                                    modifier = modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(4.dp)
                                )

                            "Character", "Fatal Blow", "Drive", "Super Art"->
                                TextMove(
                                    move = move,
                                    color = Color(0xFFDC143C),
                                    uiScale = uiScale,
                                    modifier = modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(4.dp)
                                )

                            "Modifier", "Mechanic", "MK Input" ->
                                TextMove(
                                    move = move,
                                    color = Color.White,
                                    uiScale = uiScale,
                                    modifier = modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(4.dp)
                                )
                        }
                    }
                }
            }
            if (textComboState) {
                ComboAsText(comboAsText)
            }
            ComboInfoBottom(combo, username, fontColor)
        }
    }
}
