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
import com.dooques.fightingflow.model.ControlType
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
import com.dooques.fightingflow.util.characterAndMoveData.customInputLayouts.arcSysMoves
import com.dooques.fightingflow.util.characterAndMoveData.customInputLayouts.movement
import com.dooques.fightingflow.util.characterAndMoveData.customInputLayouts.numpadNotationMoves
import com.dooques.fightingflow.util.characterAndMoveData.customInputLayouts.tagFighterMoves
import com.dooques.fightingflow.util.characterAndMoveData.customInputLayouts.virtuaFighterMoves
import com.dooques.fightingflow.util.characterAndMoveData.moveEntriesMK1
import com.dooques.fightingflow.util.characterAndMoveData.moveEntriesSF6
import com.dooques.fightingflow.util.characterAndMoveData.moveEntriesT8
import com.dooques.fightingflow.ui.viewmodels.ProfanityViewModel
import timber.log.Timber

@Composable
fun CustomGameLayout(
    context: Context,
    comboCreationViewModel: ComboCreationViewModel,
    profanityViewModel: ProfanityViewModel,
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
            ControlType.MortalKombat.type -> moveEntriesMK1
            ControlType.NumpadNotation.type -> numpadNotationMoves
            ControlType.StreetFighterC.type -> moveEntriesSF6
            ControlType.StreetFighterM.type -> moveEntriesSF6
            ControlType.TagFighter.type -> tagFighterMoves
            ControlType.Tekken.type -> moveEntriesT8
            ControlType.VirtuaFighter.type -> virtuaFighterMoves
            else -> emptyList()
        }
    )
    var layoutFilter = customGameLayout
    val console = Console.STANDARD

    if (character.uniqueMoves.isNullOrBlank()) {
        layoutFilter = layoutFilter.filter {category -> category != "Unique Moves" && category != "Unique Move"}
    }

    if (moveSet.moveList != moveEntriesT8 || moveSet.moveList != moveEntriesSF6 || moveSet.moveList != moveEntriesMK1) {
        layoutFilter =
            layoutFilter.filter { category -> category != "Mechanics" && category != "Mechanic" && category != "Mechanic Divider" }
    }

    Timber.d("Move Set: $moveSet")
    Timber.d("Form Layout: ${customGameLayout}")
    Timber.d("All Moves: ${moveList.moveList.subList(moveList.moveList.size - 10, moveList.moveList.size)}")
    LazyColumn {
        items(items = layoutFilter) { moveType ->
            when (moveType) {
                "Description" -> ComboDescription(
                    combo = combo,
                    profanityViewModel = profanityViewModel,
                    updateComboData = updateComboData
                )

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
