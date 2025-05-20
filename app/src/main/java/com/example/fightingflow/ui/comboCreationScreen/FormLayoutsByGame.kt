package com.example.fightingflow.ui.comboCreationScreen

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
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.MoveEntryListUiState
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import kotlin.reflect.KSuspendFunction0

@Composable
fun TekkenInputSelector(
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

                "Damage" -> DamageAndBreak(
                    combo = combo,
                    updateComboData = updateComboData,
                    updateMoveList = updateMoveList,
                    moveList = moveList
                )

                "Movement" -> IconMoves(
                    moveType = moveType,
                    moveList = moveList,
                    updateMoveList = updateMoveList,
                    context = context
                )

                "Input" -> IconMoves(
                    moveType = moveType,
                    moveList = moveList,
                    updateMoveList = updateMoveList,
                    context = context
                )

                "Common", "Mishima", "Mechanics Input", "Stage", "Modifier" ->
                    TextMoves(
                        moveType = moveType,
                        moveList = moveList,
                        character = character,
                        updateMoveList = updateMoveList
                    )

                "Character" -> CharacterMoves(
                    characterMoveList = characterMoveList,
                    updateMoveList = updateMoveList
                )

                "Buttons" -> ConfirmAndClear(
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    editingState = editingState,
                    comboDisplay = comboDisplay,
                    originalCombo = originalCombo,
                    saveCombo = saveCombo,
                    deleteLastMove = deleteLastMove,
                    clearMoveList = clearMoveList,
                    onNavigateToComboDisplay = onNavigateToComboDisplay,
                )

                "Divider" ->
                    InputDivider()

                "Stances", "Mechanics", "Inputs", "Movements", "Mishima Moves", "Stages",
                "Modifiers", "Combo Description", "Damage and Break" ->
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
@Composable
fun StreetFighterLayout(
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
    modifier: Modifier = Modifier
) {
    Timber.d("Loading Input Selector")
    LazyColumn {
        items(items = streetFighterLayout) { moveType ->
            when (moveType) {
                "Text Combo" -> ComboAsText(comboAsString)

                "Move Modifiers" -> MoveModifiers()

                "Damage" -> DamageAndBreak(
                    combo = combo,
                    updateComboData = updateComboData,
                    updateMoveList = updateMoveList,
                    moveList = moveList
                )

                "Movement" -> IconMoves(
                    moveType = moveType,
                    moveList = moveList,
                    updateMoveList = updateMoveList,
                    context = context
                )

                "Input" -> IconMoves(
                    moveType = moveType,
                    moveList = moveList,
                    updateMoveList = updateMoveList,
                    context = context
                )

                "Drive", "Overdrive", "Special", "Super Art" ->
                    TextMoves(
                        moveType = moveType,
                        moveList = moveList,
                        character = character,
                        updateMoveList = updateMoveList
                    )

                "Buttons" -> ConfirmAndClear(
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    editingState = editingState,
                    comboDisplay = comboDisplay,
                    originalCombo = originalCombo,
                    saveCombo = saveCombo,
                    deleteLastMove = deleteLastMove,
                    clearMoveList = clearMoveList,
                    onNavigateToComboDisplay = onNavigateToComboDisplay,
                )

                "Divider" ->
                    InputDivider()

                "Drive Moves", "Overdrive Moves", "Inputs", "Movements", "Special Moves",
                "Super Arts" -> SectionTitle(moveType)
            }
            Timber.d("$moveType loaded.")
        }
        item {
            Spacer(modifier.size(120.dp))
        }
    }
}
