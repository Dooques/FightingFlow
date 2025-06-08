package com.example.fightingflow.ui.comboItem

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.Console
import com.example.fightingflow.model.Game
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.ui.comboCreationScreen.ComboAsText
import com.example.fightingflow.ui.comboDisplayScreen.inputConverter.convertInputsToConsole
import timber.log.Timber


@Composable
fun ComboItemEditor(
    context: Context,
    combo: ComboDisplay,
    comboAsText: String,
    username: String,
    console: Console?,
    sf6ControlType: SF6ControlType?,
    iconDisplayState: Boolean,
    textComboState: Boolean,
    uiScale: Float,
    fontColor: Color,
    setSelectedItem: (Int) -> Unit,
    selectedItem: Int,
    modifier: Modifier = Modifier,
) {
    val highlightColor = Color.Gray
    var selectedIndex = selectedItem

    Timber.d("Loading combo editor...")
    Box(modifier.fillMaxWidth()) {
        Column(
            modifier.padding(horizontal = (4 * uiScale).dp, vertical = (4 * uiScale).dp)
        ) {
            Timber.d("Loading flow row...")
            Timber.d("Move List: ${combo.moves}")
            if (iconDisplayState) {
                FlowRow(
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.Start,
                    itemVerticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Timber.d("Loading moves from combo...")

                    val mutableMoveList = combo.moves.toMutableList()

                    mutableMoveList.forEachIndexed { index, it ->
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
                            "Break" -> {
                                SelectableItem(
                                    color = if (index == selectedIndex) Color.Gray else Color.Transparent,
                                    changeColor = {
                                        if (index != selectedIndex) {
                                            selectedIndex = index
                                            setSelectedItem(index)
                                        } else {
                                            selectedIndex = mutableMoveList.size
                                            setSelectedItem(mutableMoveList.size)
                                        }
                                    }
                                ) {
                                    MoveBreak(
                                        uiScale,
                                        modifier
                                            .align(Alignment.CenterVertically)
                                    )
                                }
                            }

                            "Misc" -> {
                                SelectableItem(
                                    color = if (index == selectedIndex) Color.Gray else Color.Transparent,
                                    changeColor = {
                                        if (index != selectedIndex) {
                                            selectedIndex = index
                                            setSelectedItem(index)
                                        } else {
                                            selectedIndex = mutableMoveList.size
                                            setSelectedItem(mutableMoveList.size)
                                        }
                                    }
                                ) {
                                    MiscInput(move)
                                }
                            }

                            "Input", "Movement", "Complex Movement", "Console" -> {
                                SelectableItem(
                                    color = if (index == selectedIndex) Color.Gray else Color.Transparent,
                                    changeColor = {
                                        if (index != selectedIndex) {
                                            selectedIndex = index
                                            setSelectedItem(index)
                                        } else {
                                            selectedIndex = mutableMoveList.size
                                            setSelectedItem(mutableMoveList.size)
                                        }
                                    }
                                ) {
                                    InputMove(
                                        context = context,
                                        input = move,
                                        uiScale = uiScale,
                                        modifier = modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(4.dp)
                                    )
                                }
                            }

                            "SF Classic", "SF Modern" -> {
                                SelectableItem(
                                    color = if (index == selectedIndex) Color.Gray else Color.Transparent,
                                    changeColor = {
                                        if (index != selectedIndex) {
                                        selectedIndex = index
                                        setSelectedItem(index)
                                        } else {
                                        selectedIndex = mutableMoveList.size
                                        setSelectedItem(mutableMoveList.size)
                                        }
                                    }
                                ) {
                                    TextMove(
                                        move = move,
                                        color = if (move.moveName.contains("L")) {
                                            Color(0xFFf0c027)
                                        } else if (move.moveName.contains("M")) {
                                            Color(0xFFe23a10)
                                        } else if (move.moveName.contains("H")) {
                                            Color(0xFFff0000)
                                        } else {
                                            Color(0xFF7ed957)
                                        },
                                        uiScale = uiScale,
                                        modifier = modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(4.dp)
                                    )
                                }
                            }

                            "Common", "Console Text" -> SelectableItem(
                                color = if (index == selectedIndex) Color.Gray else Color.Transparent,
                                changeColor = {
                                    if (index != selectedIndex) {
                                        selectedIndex = index
                                        setSelectedItem(index)
                                    } else {
                                        selectedIndex = mutableMoveList.size
                                        setSelectedItem(mutableMoveList.size)
                                    }
                                }
                            ) {
                                TextMove(
                                    move = move,
                                    color = Color(0xFF444444),
                                    uiScale = uiScale,
                                    modifier = modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(4.dp)
                                )
                            }

                            "Special" -> {
                                SelectableItem(
                                    color = if (index == selectedIndex) Color.Gray else Color.Transparent,
                                    changeColor = {
                                        if (index != selectedIndex) {
                                            selectedIndex = index
                                            setSelectedItem(index)
                                        } else {
                                            selectedIndex = mutableMoveList.size
                                            setSelectedItem(mutableMoveList.size)
                                        }
                                    }
                                ) {
                                    TextMove(
                                        move = move,
                                        color = Color(0xFF0067B3),
                                        uiScale = uiScale,
                                        modifier = modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(4.dp)
                                    )
                                }
                            }

                            "Stage" -> {
                                SelectableItem(
                                    color = if (index == selectedIndex) Color.Gray else Color.Transparent,
                                    changeColor = {
                                        if (index != selectedIndex) {
                                            selectedIndex = index
                                            setSelectedItem(index)
                                        } else {
                                            selectedIndex = mutableMoveList.size
                                            setSelectedItem(mutableMoveList.size)
                                        }
                                    }
                                ) {
                                    TextMove(
                                        move = move,
                                        color = Color(0xFF2f5233),
                                        uiScale = uiScale,
                                        modifier = modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(4.dp)
                                    )
                                }
                            }

                            "Mechanic", "Mishima" -> {
                                SelectableItem(
                                    color = if (index == selectedIndex) Color.Gray else Color.Transparent,
                                    changeColor = {
                                        if (index != selectedIndex) {
                                            selectedIndex = index
                                            setSelectedItem(index)
                                        } else {
                                            selectedIndex = mutableMoveList.size
                                            setSelectedItem(mutableMoveList.size)
                                        }
                                    }
                                ) {
                                    TextMove(
                                        move = move,
                                        color = Color(0xFF8155BA),
                                        uiScale = uiScale,
                                        modifier = modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(4.dp)
                                    )
                                }
                            }

                            "Character", "Fatal Blow", "Drive", "Super Art" -> {
                                SelectableItem(
                                    color = if (index == selectedIndex) Color.Gray else Color.Transparent,
                                    changeColor = {
                                        if (index != selectedIndex) {
                                            selectedIndex = index
                                            setSelectedItem(index)
                                        } else {
                                            selectedIndex = mutableMoveList.size
                                            setSelectedItem(mutableMoveList.size)
                                        }
                                    }
                                ) {
                                    TextMove(
                                        move = move,
                                        color = Color(0xFFDC143C),
                                        uiScale = uiScale,
                                        modifier = modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(4.dp)
                                    )
                                }
                            }

                            "Modifier", "MK Input" -> {
                                SelectableItem(
                                    color = if (index == selectedIndex) Color.Gray else Color.Transparent,
                                    changeColor = {
                                        if (index != selectedIndex) {
                                            selectedIndex = index
                                            setSelectedItem(index)
                                        } else {
                                            selectedIndex = mutableMoveList.size
                                            setSelectedItem(mutableMoveList.size)
                                        }
                                    }
                                ) {
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
                }
            }
            if (textComboState) {
                ComboAsText(comboAsText)
            }
            ComboInfoBottom(combo, username, fontColor)
        }
    }
}

@Composable
fun SelectableItem(
    modifier: Modifier = Modifier,
    color: Color,
    changeColor: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier =
            modifier
                .padding(2.dp)
                .background(color)
                .clickable {
                    changeColor(true)
        }
    ) {
        content()
    }

}