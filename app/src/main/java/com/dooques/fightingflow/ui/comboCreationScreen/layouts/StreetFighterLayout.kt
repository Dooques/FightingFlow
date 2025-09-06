package com.dooques.fightingflow.ui.comboCreationScreen.layouts

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dooques.fightingflow.model.CharacterEntry
import com.dooques.fightingflow.model.ComboDisplay
import com.dooques.fightingflow.model.Console
import com.dooques.fightingflow.model.SF6ControlType
import com.dooques.fightingflow.ui.comboCreationScreen.CharacterMoves
import com.dooques.fightingflow.ui.viewmodels.ComboCreationViewModel
import com.dooques.fightingflow.ui.comboCreationScreen.ComboDescription
import com.dooques.fightingflow.ui.comboCreationScreen.DamageAndDifficulty
import com.dooques.fightingflow.ui.comboCreationScreen.IconMoves
import com.dooques.fightingflow.ui.comboCreationScreen.InputDivider
import com.dooques.fightingflow.ui.comboCreationScreen.MoveModifiers
import com.dooques.fightingflow.ui.comboCreationScreen.SectionTitle
import com.dooques.fightingflow.ui.comboCreationScreen.TextMoves
import com.dooques.fightingflow.util.ComboDisplayUiState
import com.dooques.fightingflow.util.MoveEntryListUiState
import com.dooques.fightingflow.util.characterAndMoveData.nintendoInputs
import com.dooques.fightingflow.util.characterAndMoveData.playstationInputs
import com.dooques.fightingflow.util.characterAndMoveData.xboxInputs
import com.dooques.fightingflow.ui.viewmodels.ProfanityViewModel
import timber.log.Timber


@Composable
fun StreetFighterLayout(
    context: Context,
    comboCreationViewModel: ComboCreationViewModel,
    profanityViewModel: ProfanityViewModel,
    comboDisplay: ComboDisplay,
    character: CharacterEntry,
    combo: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,
    console: Console?,
    sF6ControlType: SF6ControlType?,
    moveList: MoveEntryListUiState,
    modifier: Modifier = Modifier
) {
    Timber.d("Loading Input Selector")
    LazyColumn {
        items(items =
            if (sF6ControlType == SF6ControlType.Classic) streetFighterLayoutClassic
            else streetFighterLayoutModern
        ) { moveType ->
            when (moveType) {
                "Description" -> ComboDescription(
                    combo = combo,
                    profanityViewModel = profanityViewModel,
                    updateComboData = updateComboData
                )

                "Move Modifiers" -> MoveModifiers()

                "Damage" -> DamageAndDifficulty(
                    comboDisplay = comboDisplay,
                    updateComboData = updateComboData,
                )

                "Misc" -> IconMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = moveList,
                    maxItems = 6,
                    context = context,
                    console = console,
                    sF6ControlType = sF6ControlType
                )

                "Movement" -> IconMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = moveList,
                    maxItems = 5,
                    context = context,
                    console = console,
                    sF6ControlType = sF6ControlType
                )

                "Console" -> IconMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = moveList,
                    sF6ControlType = sF6ControlType,
                    context = context,
                    console = console,
                )

                "Complex Movement" -> IconMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = moveList,
                    maxItems = 3,
                    context = context,
                    console = console,
                    sF6ControlType = sF6ControlType
                )

                "SF Modern", "SF Classic" -> if (console == Console.STANDARD)
                    IconMoves(
                        comboCreationViewModel = comboCreationViewModel,
                        context = context,
                        moveType = moveType,
                        moveList = moveList,
                        console = console,
                        maxItems = 4
                    )

                "Mechanic" -> TextMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = moveList,
                    console = console
                )

                "Console Text" -> TextMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = when (console) {
                        Console.PLAYSTATION -> MoveEntryListUiState(playstationInputs)
                        Console.XBOX -> MoveEntryListUiState(xboxInputs)
                        Console.NINTENDO -> MoveEntryListUiState(nintendoInputs)
                        else -> MoveEntryListUiState()
                    },
                    console = console,
                    maxItems = 6
                )

                "Special", "Super Art" -> CharacterMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    characterMoveList = moveList,
                    character = character,
                    moveType = moveType,
                )

                "Divider" ->
                    InputDivider()

                "Drive Moves", "Overdrive Moves", "Classic Inputs", "Modern Inputs", "Movements",
                "Special Moves", "Super Arts", "Complex Movements", "Mechanics", "Misc Inputs" ->
                    SectionTitle(moveType)
            }
            Timber.d("$moveType loaded.")
        }
        item {
            Spacer(modifier.size(120.dp))
        }
    }
}
