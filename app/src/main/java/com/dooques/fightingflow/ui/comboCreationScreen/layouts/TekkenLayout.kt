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
fun TekkenLayout(
    context: Context,
    comboCreationViewModel: ComboCreationViewModel,
    profanityViewModel: ProfanityViewModel,
    comboDisplay: ComboDisplay,
    character: CharacterEntry,
    characterName: String?,
    combo: ComboDisplay,
    console: Console?,
    updateComboData: (ComboDisplayUiState) -> Unit,
    moveList: MoveEntryListUiState,
    modifier: Modifier = Modifier
) {
    Timber.d("Loading Input Selector")

    LazyColumn {
        val mishimaChar = characterName in mishima

        if (mishimaChar) {
            Timber.d("Using mishima layout")
        } else {
            Timber.d("Using normal character layout")
        }
        items(items = tekkenLayout) { moveType ->
            when (moveType) {
                "Move Modifiers" -> MoveModifiers()

                "Description" -> ComboDescription(
                    combo = combo,
                    profanityViewModel = profanityViewModel,
                    updateComboData = updateComboData
                )

                "Damage" -> DamageAndDifficulty(
                    comboDisplay = comboDisplay,
                    updateComboData = updateComboData,
                )

                "Movement" -> IconMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = moveList,
                    context = context,
                    console = console,
                )

                "Console" -> IconMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = moveList,
                    context = context,
                    console = console,
                )

                "Input" -> if (console == Console.STANDARD) IconMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = moveList,
                    context = context,
                    console = console,
                )

                "Misc" -> IconMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = moveList,
                    maxItems = 6,
                    context = context,
                    console = console
                )

                "Common", "Mechanic", "Stage" -> TextMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = moveList,
                    console = console,
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

                "Mishima" -> if (character.name in mishima) TextMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    moveType = moveType,
                    moveList = moveList,
                    maxItems = if (moveList.moveList.size <= 4) 4 else 5,
                    console = console
                )

                "Character" -> CharacterMoves(
                    comboCreationViewModel = comboCreationViewModel,
                    characterMoveList = moveList,
                    moveType = moveType,
                    character = character,
                )

                "Divider" -> InputDivider()
                "Mishima Divider" -> if (character.name in mishima) InputDivider()

                "Stances", "Heat and Rage", "Inputs", "Movements", "Stage Mechanics",
                "Mechanics", "Modifiers", "Combo Description", "Damage and Break", "Misc Inputs" ->
                    SectionTitle(moveType)

                "Mishima Moves" -> if (character.name in mishima) SectionTitle(moveType)

                "Character Stances" -> SectionTitle(
                    title = when (character.game) {
                        "Tekken 8" -> "${character.name}'s Stances"
                        "Street Fighter VI", "Mortal Kombat 1" -> "${character.name}'s Moves"
                        else -> "Invalid Game Selected"
                    }
                )
            }
            Timber.d("$moveType loaded.")
        }
        item {
            Spacer(modifier.size(120.dp))
        }
    }
}

