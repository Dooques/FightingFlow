package com.example.fightingflow.ui.comboCreationScreen.layouts

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.ui.comboCreationScreen.CharacterMoves
import com.example.fightingflow.ui.comboCreationScreen.ComboAsText
import com.example.fightingflow.ui.comboCreationScreen.ComboDescription
import com.example.fightingflow.ui.comboCreationScreen.ConfirmAndClear
import com.example.fightingflow.ui.comboCreationScreen.DamageAndConfirm
import com.example.fightingflow.ui.comboCreationScreen.IconMoves
import com.example.fightingflow.ui.comboCreationScreen.InputDivider
import com.example.fightingflow.ui.comboCreationScreen.MoveModifiers
import com.example.fightingflow.ui.comboCreationScreen.SectionTitle
import com.example.fightingflow.ui.comboCreationScreen.TextMoves
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.MoveEntryListUiState
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import kotlin.reflect.KSuspendFunction0

@Composable
fun TekkenLayout(
    context: Context,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    editingState: Boolean,
    comboDisplay: ComboDisplay,
    originalCombo: ComboDisplay,
    character: CharacterEntry,
    characterName: String,
    combo: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,
    updateMoveList: (String, MoveEntryListUiState) -> Unit,
    comboAsString: String,
    saveCombo: KSuspendFunction0<Unit>,
    deleteLastMove: () -> Unit,
    clearMoveList: () -> Unit,
    onNavigateToComboDisplay: () -> Unit,
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
        items(items = if (mishimaChar) tekkenLayoutMishima else tekkenLayout) { moveType ->
            when (moveType) {
                "Text Combo" -> ComboAsText(comboAsString)

                "Move Modifiers" -> MoveModifiers()

                "Description" -> ComboDescription(combo, updateComboData)

                "Damage" -> DamageAndConfirm(
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    editingState = editingState,
                    comboDisplay = comboDisplay,
                    originalCombo = originalCombo,
                    saveCombo = saveCombo,
                    updateComboData = updateComboData,
                    updateMoveList = updateMoveList,
                    moveList = moveList,
                    onNavigateToComboDisplay = onNavigateToComboDisplay,
                )

                "Movement", "Input" -> IconMoves(
                    moveType = moveType,
                    moveList = gameMoveList,
                    updateMoveList = updateMoveList,
                    maxItemsPerRow = 5,
                    context = context
                )

                "Misc" -> IconMoves(
                    moveType = moveType,
                    moveList = moveList,
                    updateMoveList = updateMoveList,
                    maxItemsPerRow = 6,
                    context = context
                )

                "Common", "Mechanics Input", "Stage" -> TextMoves(
                    moveType = moveType,
                    moveList = moveList,
                    character = character,
                    updateMoveList = updateMoveList
                )

                "Mishima" -> TextMoves(
                    moveType = moveType,
                    moveList = characterMoveList,
                    character = character,
                    updateMoveList = updateMoveList
                )

                "Character" -> CharacterMoves(
                    characterMoveList = characterMoveList,
                    moveType = moveType,
                    updateMoveList = updateMoveList
                )

                "Buttons" -> ConfirmAndClear(
                    deleteLastMove = deleteLastMove,
                    clearMoveList = clearMoveList,
                    updateMoveList = updateMoveList,
                    moveList = moveList,
                )

                "Divider" -> InputDivider()

                "Stances", "Heat and Rage", "Inputs", "Movements", "Mishima Moves", "Stage Mechanics",
                "Modifiers", "Combo Description", "Damage and Break", "Misc Inputs" ->
                    SectionTitle(moveType)

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

