package com.example.fightingflow.ui.comboCreationScreen.layouts

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.Console
import com.example.fightingflow.model.Game
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.ui.comboCreationScreen.CharacterMoves
import com.example.fightingflow.ui.comboCreationScreen.ComboCreationViewModel
import com.example.fightingflow.ui.comboCreationScreen.ComboDescription
import com.example.fightingflow.ui.comboCreationScreen.DamageAndDifficulty
import com.example.fightingflow.ui.comboCreationScreen.IconMoves
import com.example.fightingflow.ui.comboCreationScreen.InputDivider
import com.example.fightingflow.ui.comboCreationScreen.MoveModifiers
import com.example.fightingflow.ui.comboCreationScreen.SectionTitle
import com.example.fightingflow.ui.comboCreationScreen.TextMoves
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.characterAndMoveData.nintendoInputs
import com.example.fightingflow.util.characterAndMoveData.playstationInputs
import com.example.fightingflow.util.characterAndMoveData.xboxInputs
import timber.log.Timber
import kotlin.reflect.KFunction4


@Composable
fun StreetFighterLayout(
    context: Context,
    comboCreationViewModel: ComboCreationViewModel,
    comboDisplay: ComboDisplay,
    character: CharacterEntry,
    combo: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,
    console: Console?,
    sF6ControlType: SF6ControlType?,
    moveList: MoveEntryListUiState,
    characterMoveList: MoveEntryListUiState,
    gameMoveList: MoveEntryListUiState,
    modifier: Modifier = Modifier
) {
    Timber.d("Loading Input Selector")
    LazyColumn {
        items(items =
            if (sF6ControlType == SF6ControlType.Classic) streetFighterLayoutClassic
            else streetFighterLayoutModern
        ) { moveType ->
            when (moveType) {
                "Description" -> ComboDescription(combo, updateComboData)

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
                    moveList = gameMoveList,
                    maxItems = 5,
                    context = context,
                    console = console,
                    sF6ControlType = sF6ControlType
                )

                "Console" -> IconMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = gameMoveList,
                    context = context,
                    console = console,
                )

                "Complex Movement" -> IconMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = gameMoveList,
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
                        moveList = gameMoveList,
                        console = console,
                        maxItems = if (moveType == "SF Modern") 4 else 3
                    )

                "Mechanic" -> TextMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = gameMoveList,
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
                    characterMoveList = characterMoveList,
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
