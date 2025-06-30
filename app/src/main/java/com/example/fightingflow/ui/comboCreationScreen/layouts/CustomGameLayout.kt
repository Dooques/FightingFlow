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
import com.example.fightingflow.model.ControlType
import com.example.fightingflow.model.Game
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
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.arcSysMoves
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.movement
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.numpadNotationMoves
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.tagFighterMoves
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.virtuaFighterMoves
import com.example.fightingflow.util.characterAndMoveData.mk1Moves
import com.example.fightingflow.util.characterAndMoveData.streetFighter6Moves
import com.example.fightingflow.util.characterAndMoveData.tekken8Moves
import timber.log.Timber
import kotlin.reflect.KFunction4

@Composable
fun CustomGameLayout(
    context: Context,
    comboCreationViewModel: ComboCreationViewModel,
    combo: ComboDisplay,
    character: CharacterEntry,
    moveList: MoveEntryListUiState,
    updateComboData: (ComboDisplayUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("-- Loading Input Selector --")

    val moveSet = MoveEntryListUiState(
        when (character.controlType) {
            ControlType.ArcSys.type -> arcSysMoves
            ControlType.MortalKombat.type -> mk1Moves
            ControlType.NumpadNotation.type -> numpadNotationMoves
            ControlType.StreetFighter.type -> streetFighter6Moves
            ControlType.TagFighter.type -> tagFighterMoves
            ControlType.Tekken.type -> tekken8Moves
            ControlType.VirtuaFighter.type -> virtuaFighterMoves
            else -> emptyList()
        }
    )
    var layoutFilter = customGameLayout
    val console = Console.STANDARD

    if (character.uniqueMoves.isNullOrBlank()) {
        layoutFilter = layoutFilter.filter {category -> category != "Unique Moves" && category != "Unique Move"}
    }

    if (moveSet.moveList != tekken8Moves || moveSet.moveList != streetFighter6Moves || moveSet.moveList != mk1Moves) {
        layoutFilter =
            layoutFilter.filter { category -> category != "Mechanics" && category != "Mechanic" && category != "Mechanic Divider" }
    }

    Timber.d("Move Set: $moveSet")
    Timber.d("Form Layout: $customGameLayout")
    Timber.d("All Moves: ${moveList.moveList.subList(moveList.moveList.size - 10, moveList.moveList.size)}")
    LazyColumn {
        items(items = layoutFilter) { moveType ->
            when (moveType) {
                "Description" -> ComboDescription(combo, updateComboData)

                "Move Modifiers" -> MoveModifiers()

                "Damage" -> DamageAndDifficulty(
                    comboDisplay = combo,
                    updateComboData = updateComboData,
                )

                "Input", "SF Classic" -> if (console == Console.STANDARD) IconMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = moveSet,
                    maxItems = if (moveType == "SF Classic") 3 else 5,
                    context = context,
                    console = console
                )

                "Movement" -> IconMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = if (moveSet.moveList == numpadNotationMoves) MoveEntryListUiState(numpadNotationMoves) else MoveEntryListUiState(movement),
                    maxItems = 5,
                    context = context,
                    console = console
                )

//                "Console" -> IconMoves(
//                    moveType = moveType,
//                    moveList = moveList,
//                    updateMoveList = updateMoveList,
//                    maxItems = 5,
//                    context = context,
//                    console = console
//                )

                "Misc" -> IconMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = moveList,
                    maxItems = 6,
                    context = context,
                    console = console
                )

                "Mechanic", "Text Input" -> TextMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveList = moveSet,
                    moveType = moveType,
                    console = console,
                )

                "Unique Move" -> TextMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = MoveEntryListUiState(moveList.moveList.filter {
                        it.game == character.game && it.character == character.name
                    }),
                    console = console,
                )

//                "Console Text" -> TextMoves(
//                    moveType = moveType,
//                    moveList = when (console) {
//                        Console.PLAYSTATION -> MoveEntryListUiState(playstationInputs)
//                        Console.XBOX -> MoveEntryListUiState(xboxInputs)
//                        Console.NINTENDO -> MoveEntryListUiState(nintendoInputs)
//                        else -> MoveEntryListUiState()
//                    },
//                    updateMoveList = updateMoveList,
//                    console = console,
//                    maxItems = 6
//                )

                "Divider", "Mechanic Divider" -> InputDivider()

                "Heat and Rage", "Inputs", "Movements", "Stage Mechanics", "Mechanics",
                "Modifiers", "Combo Description", "Damage and Break", "Misc Inputs",
                "Block and Stance", "Unique Moves" -> SectionTitle(moveType)
            }
            Timber.d("$moveType loaded.")
        }
        item {
            Spacer(modifier.size(120.dp))
        }
    }
}
