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
import com.example.fightingflow.data.datastore.Console
import com.example.fightingflow.data.datastore.Game
import com.example.fightingflow.data.datastore.SF6ControlType
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.ui.comboCreationScreen.CharacterMoves
import com.example.fightingflow.ui.comboCreationScreen.ComboDescription
import com.example.fightingflow.ui.comboCreationScreen.DamageAndConfirm
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
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction4
import kotlin.reflect.KSuspendFunction0


@Composable
fun StreetFighterLayout(
    context: Context,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    editingState: Boolean,
    character: CharacterEntry,
    comboDisplay: ComboDisplay,
    originalCombo: ComboDisplay,
    combo: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,
    updateMoveList: KFunction4<String, MoveEntryListUiState, Game?, Console?, Unit>,
    comboAsString: String,
    console: Console?,
    sF6ControlType: SF6ControlType?,
    saveCombo: KSuspendFunction0<Unit>,
    deleteLastMove: KFunction0<Unit>,
    clearMoveList: () -> Unit,
    onNavigateToComboDisplay: () -> Unit,
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

                "Damage" -> DamageAndConfirm(
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    editingState = editingState,
                    comboDisplay = comboDisplay,
                    originalCombo = originalCombo,
                    saveCombo = saveCombo,
                    updateComboData = updateComboData,
                    onNavigateToComboDisplay = onNavigateToComboDisplay,
                )

                "Misc" -> IconMoves(
                    moveType = moveType,
                    moveList = moveList,
                    updateMoveList = updateMoveList,
                    maxItemsPerRow = 6,
                    context = context,
                    console = console,
                    sF6ControlType = sF6ControlType
                )

                "Movement", "Complex Movement", "Console" -> IconMoves(
                    moveType = moveType,
                    moveList = gameMoveList,
                    updateMoveList = updateMoveList,
                    maxItemsPerRow = 5,
                    context = context,
                    console = console,
                    sF6ControlType = sF6ControlType
                )

                "SF Classic" -> if (console == Console.STANDARD)
                    TextMoves(
                        moveType = moveType,
                        moveList = gameMoveList,
                        updateMoveList = updateMoveList,
                        console = console,
                        maxItems = 3
                    )

                "SF Modern" -> if (console == Console.STANDARD)
                    TextMoves(
                        moveType = moveType,
                        moveList = gameMoveList,
                        updateMoveList = updateMoveList,
                        console = console
                    )

                "Mechanic" -> TextMoves(
                    moveType = moveType,
                    moveList = gameMoveList,
                    updateMoveList = updateMoveList,
                    console = console
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

                "Special", "Super Art" -> CharacterMoves(
                    characterMoveList = characterMoveList,
                    moveType = moveType,
                    updateMoveList = updateMoveList
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
