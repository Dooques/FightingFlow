package com.example.fightingflow.ui.comboItem

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.Console
import com.example.fightingflow.model.Game
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.model.UserDataForCombos
import com.example.fightingflow.ui.comboCreationScreen.ComboAsText
import com.example.fightingflow.util.inputConverter.convertInputsToConsole
import com.example.fightingflow.ui.viewmodels.ComboDisplayViewModel
import com.example.fightingflow.ui.viewmodels.UserDetailsState
import com.example.fightingflow.util.CharacterEntryUiState
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.CaptureController
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ComboItemDisplay(
    context: Context,
    scope: CoroutineScope,
    captureController: CaptureController,
    toShare: Boolean,
    display: Boolean,
    comboDisplayViewModel: ComboDisplayViewModel,
    characterEntryUiState: CharacterEntryUiState,
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
    modifier: Modifier = Modifier,
) {
    Timber.d("-- Loading Combo Moves Composable... --")
    Box(modifier.fillMaxWidth().capturable(captureController)) {

        Column(
            modifier.padding(4.dp)
        ) {
//            Timber.d("Loading flow row... \n Move List: %s", combo.moves)
            if (display) {
                ComboInfoTop(combo, uiScale)
            }
            if (iconDisplayState) {
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy((4 * uiScale).dp),
                    horizontalArrangement = Arrangement.spacedBy((4 * uiScale).dp),
                    itemVerticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Timber.d("Loading moves from combo...")
                    combo.moves.forEach {
//                        Timber.d("$it from ${it.game}")
//                        Timber.d("Console Type: $console")
//                        Timber.d("If not Standard, converting to console input.")
                        val move = if (console != Console.STANDARD) convertInputsToConsole(
                            move = it,
                            game = when (it.game) {
                                "Tekken 8" -> Game.T8
                                "Street Fighter VI" -> Game.SF6
                                "Mortal Kombat 1" -> Game.MK1
                                else -> null
                            },
                            console = console,
                            classic = sf6ControlType == SF6ControlType.Classic
                        ) else it

                        Timber.d("Move: $move")
                        when (move.moveType) {
                            "Break" -> MoveBreak()

                            "Misc" -> MiscInput(move, modifier)

                            "SF Modern", "SF Classic", "Input", "Movement", "Complex Movement", "Console" ->
                                InputMove(
                                    context = context,
                                    input = move,
                                    modifier = modifier
                                        .align(Alignment.CenterVertically)
                                )

                            "Common", "Console Text" ->
                                TextMove(
                                    move = move,
                                    bgColor = Color(0xFF444444),
                                )

                            "Special", "Unique Move" ->
                                TextMove(
                                    move = move,
                                    bgColor = Color(0xFF0067B3),
                                )

                            "Stage" ->
                                TextMove(
                                    move = move,
                                    bgColor = Color(0xFF2f5233),
                                )

                            "Mishima", "Mechanic" ->
                                TextMove(
                                    move = move,
                                    bgColor = Color(0xFF8155BA),
                                )

                            "Character", "Fatal Blow", "Drive", "Super Art"->
                                TextMove(
                                    move = move,
                                    bgColor = Color(0xFFDC143C),
                                )

                            "Modifier", "MK Input", "Text Input" ->
                                TextMove(
                                    move = move,
                                    bgColor = Color.White,
                                )
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
                comboCreationState = comboCreationState,
                combo = combo,
                characterEntry = characterEntryUiState,
                currentUser = currentUser,
                userData = userData,
                userDetails = userDetails,
                toShare = toShare,
                fontColor = fontColor
            )
        }
    }
}
