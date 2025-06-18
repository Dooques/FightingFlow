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
import com.example.fightingflow.ui.comboCreationScreen.CharacterMoves
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
fun TekkenLayout(
    context: Context,
    comboDisplay: ComboDisplay,
    character: CharacterEntry,
    characterName: String,
    combo: ComboDisplay,
    console: Console?,
    updateComboData: (ComboDisplayUiState) -> Unit,
    updateMoveList: KFunction4<String, MoveEntryListUiState, Game, Console?, Unit>,
    moveList: MoveEntryListUiState,
    characterMoveList: MoveEntryListUiState,
    gameMoveList: MoveEntryListUiState,
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
                    updateComboData = updateComboData
                )

                "Damage" -> DamageAndDifficulty(
                    comboDisplay = comboDisplay,
                    updateComboData = updateComboData,
                )

                "Movement" -> IconMoves(
                    moveType = moveType,
                    moveList = gameMoveList,
                    updateMoveList = updateMoveList,
                    context = context,
                    console = console,
                )

                "Console" -> IconMoves(
                    moveType = moveType,
                    moveList = gameMoveList,
                    updateMoveList = updateMoveList,
                    context = context,
                    console = console,
                )

                "Input" -> if (console == Console.STANDARD) IconMoves(
                    moveType = moveType,
                    moveList = gameMoveList,
                    updateMoveList = updateMoveList,
                    context = context,
                    console = console,
                )

                "Misc" -> IconMoves(
                    moveType = moveType,
                    moveList = moveList,
                    updateMoveList = updateMoveList,
                    maxItems = 6,
                    context = context,
                    console = console
                )

                "Common", "Mechanic", "Stage" -> TextMoves(
                    moveType = moveType,
                    moveList = gameMoveList,
                    updateMoveList = updateMoveList,
                    console = console,
                )

                "Console Text" -> TextMoves(
                    moveType = moveType,
                    moveList = when (console) {
                        Console.PLAYSTATION -> MoveEntryListUiState(playstationInputs)
                        Console.XBOX -> MoveEntryListUiState(xboxInputs)
                        Console.NINTENDO -> MoveEntryListUiState(nintendoInputs)
                        else -> MoveEntryListUiState()
                    },
                    updateMoveList = updateMoveList,
                    console = console,
                    maxItems = 6
                )

                "Mishima" -> if (character.name in mishima) TextMoves(
                    moveType = moveType,
                    moveList = characterMoveList,
                    updateMoveList = updateMoveList,
                    maxItems = if (characterMoveList.moveList.size <= 4) 4 else 5,
                    console = console
                )

                "Character" -> CharacterMoves(
                    characterMoveList = characterMoveList,
                    moveType = moveType,
                    character = character,
                    updateMoveList = updateMoveList,
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

