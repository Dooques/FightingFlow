package com.dooques.fightingflow.ui.comboItem

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
import com.dooques.fightingflow.data.firebase.GoogleAuthService
import com.dooques.fightingflow.model.ComboDisplay
import com.dooques.fightingflow.model.Console
import com.dooques.fightingflow.model.Game
import com.dooques.fightingflow.model.SF6ControlType
import com.dooques.fightingflow.model.UserDataForCombos
import com.dooques.fightingflow.ui.comboCreationScreen.ComboAsText
import com.dooques.fightingflow.util.inputConverter.convertInputsToConsole
import com.dooques.fightingflow.ui.viewmodels.ComboDisplayViewModel
import com.dooques.fightingflow.ui.viewmodels.UserDetailsState
import com.dooques.fightingflow.util.CharacterEntryUiState
import com.dooques.fightingflow.util.characterAndMoveData.characterMap
import com.dooques.fightingflow.util.emptyCharacter
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import kotlin.collections.first


@Composable
fun ComboItemEditor(
    context: Context,
    scope: CoroutineScope,
    comboDisplayViewModel: ComboDisplayViewModel,
    currentUser: GoogleAuthService.SignInState,
    userData: UserDataForCombos,
    userDetails: UserDetailsState,
    comboCreationState: Boolean,
    combo: ComboDisplay,
    comboAsText: String,
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

    Timber.d("--Loading combo editor--")
    Box(modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
        Column {
            Timber.d(" Loading flow row\n Move List: ${combo.moves}")
            if (iconDisplayState) {
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    itemVerticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Timber.d(" Loading moves from combo...")

                    val mutableMoveList = combo.moves.toMutableList()

                    mutableMoveList.forEachIndexed { index, it ->
                        Timber.d(" $it from ${it.game}")
                        val move = convertInputsToConsole(
                            move = it,
                            game = when (it.game) {
                                "Tekken 8" -> Game.T8
                                "Street Fighter 6" -> Game.SF6
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
                                    },
                                ) {
                                    MoveBreak()
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
                                    },
                                ) {
                                    MiscInput(move)
                                }
                            }

                            "SF Modern", "SF Classic", "Input", "Movement", "Complex Movement", "Console" -> {
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
                                    },
                                ) {
                                    InputMove(
                                        context = context,
                                        input = move,
                                    )
                                }
                            }

                            "Common", "Console Text"  -> SelectableItem(
                                color = if (index == selectedIndex) Color.Gray else Color.Transparent,
                                changeColor = {
                                    if (index != selectedIndex) {
                                        selectedIndex = index
                                        setSelectedItem(index)
                                    } else {
                                        selectedIndex = mutableMoveList.size
                                        setSelectedItem(mutableMoveList.size)
                                    }
                                },
                            ) {
                                TextMove(
                                    move = move,
                                    bgColor = Color(0xFF444444),
                                )
                            }

                            "Special", "Unique Move"-> {
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
                                    },
                                ) {
                                    TextMove(
                                        move = move,
                                        bgColor = Color(0xFF0067B3),
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
                                    },
                                ) {
                                    TextMove(
                                        move = move,
                                        bgColor = Color(0xFF2f5233),
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
                                    },
                                ) {
                                    TextMove(
                                        move = move,
                                        bgColor = Color(0xFF8155BA),
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
                                    },
                                ) {
                                    TextMove(
                                        move = move,
                                        bgColor = Color(0xFFDC143C),
                                    )
                                }
                            }

                            "Modifier", "MK Input", "Text Input" -> {
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
                                    },
                                ) {
                                    TextMove(
                                        move = move,
                                        bgColor = Color.White,
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
            ComboInfoBottom(
                scope = scope,
                comboDisplayViewModel = comboDisplayViewModel,
                currentUser = currentUser,
                comboCreationState = comboCreationState,
                combo = combo,
                characterEntry = CharacterEntryUiState(
                    characterMap[combo.game]?.first { combo.character == it.name } ?: emptyCharacter
                ),
                userData = userData,
                userDetails = userDetails,
                toShare = false,
                fontColor = fontColor
            )
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
                .background(color)
                .clickable { changeColor(true) }
                .padding(horizontal = 2.dp)
    ) {
        content()
    }

}