package com.example.fightingflow.ui.comboCreationScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.Console
import com.example.fightingflow.model.Game
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.ui.comboCreationScreen.layouts.CustomGameLayout
import com.example.fightingflow.ui.comboCreationScreen.layouts.MortalKombatLayout
import com.example.fightingflow.ui.comboCreationScreen.layouts.StreetFighterLayout
import com.example.fightingflow.ui.comboCreationScreen.layouts.TekkenLayout
import com.example.fightingflow.ui.comboDisplayScreen.comboItem.ComboItemEditor
import com.example.fightingflow.ui.comboDisplayScreen.comboItem.ComboInfoBottom
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.emptyComboDisplay
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction4

@Composable
/* The form used to display the moves in the combo along with the inputs to add more moves to a
    combo */
fun ComboForm(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    editingState: Boolean,
    comboCreationViewModel: ComboCreationViewModel,
    username: String,
    game: Game,
    consoleTypeState: Console?,
    sF6ControlType: SF6ControlType?,
    comboDisplay: ComboDisplay,
    originalCombo: ComboDisplay,
    selectedItem: Int,
    character: CharacterEntry,
    characterName: String,
    comboAsString: String,
    moveList: MoveEntryListUiState,
    characterMoveList: MoveEntryListUiState,
    gameMoveList: MoveEntryListUiState,
    iconDisplayState: Boolean,
    textComboDisplay: Boolean,
    setSelectedItem: (Int) -> Unit,
    updateComboData: (ComboDisplayUiState) -> Unit,
    deleteMove: KFunction0<Unit>,
    clearMoveList: () -> Unit,
    onNavigateToComboDisplay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column {
        Timber.d("-- Loading Combo Form --")
        /* Placeholder composable for creating a new combo */
        if (comboDisplay.moves.isEmpty()) {
            Column {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(77.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Add some moves!", modifier = modifier.padding(4.dp))

                }
                if (textComboDisplay) {
                    ComboAsText("", modifier.padding(horizontal = 4.dp))
                    Box(modifier.padding(horizontal = 4.dp)) {
                        ComboInfoBottom(comboDisplay, Color.White)
                    }
                    Spacer(modifier.height(12.dp))
                } else {
                    Box(modifier.padding(horizontal = 4.dp)) {
                        ComboInfoBottom(comboDisplay, Color.White)
                    }
                    Spacer(modifier.height(12.dp))
                }
            }
        } else {
            Timber.d("Combo Move List exists, populating form with moves...")
            /* Shows moves added to combo once a move has been added */
            ComboItemEditor(
                context = context,
                combo = comboDisplay,
                characterEntry = character,
                username = username,
                console = consoleTypeState,
                sf6ControlType = sF6ControlType,
                fontColor = MaterialTheme.colorScheme.onBackground,
                iconDisplayState = iconDisplayState,
                textComboState = textComboDisplay,
                comboAsText = comboAsString,
                uiScale = 1f,
                setSelectedItem = setSelectedItem,
                selectedItem = selectedItem,
                modifier = modifier.padding(vertical = 4.dp),
            )
        }
        /* The buttons used to edit the moves in the combo */
        EditingButtons(
            comboCreationViewModel = comboCreationViewModel,
            deleteMove = deleteMove,
            clearMoveList = clearMoveList,
            moveList = moveList,
            scope = scope,
            snackbarHostState = snackbarHostState,
            comboDisplay = comboDisplay,
            game = game,
            editingState = editingState,
            originalCombo = originalCombo,
            onNavigateToComboDisplay = onNavigateToComboDisplay,
        )
    }

    if (moveList.moveList.isNotEmpty()) {
        Timber.d("Move Entry List exists, populating Input Selector Column...")
        game.let { innerGame ->
            when (innerGame) {
                Game.T8 -> TekkenLayout(
                    context = context,
                    comboCreationViewModel = comboCreationViewModel,
                    comboDisplay = comboDisplay,
                    character = character,
                    characterName = characterName,
                    combo = comboDisplay,
                    console = consoleTypeState,
                    updateComboData = updateComboData,
                    moveList = moveList,
                    characterMoveList = characterMoveList,
                    gameMoveList = gameMoveList
                )

                Game.SF6 -> StreetFighterLayout(
                    context = context,
                    comboCreationViewModel = comboCreationViewModel,
                    comboDisplay = comboDisplay,
                    character = character,
                    combo = comboDisplay,
                    updateComboData = updateComboData,
                    console = consoleTypeState,
                    sF6ControlType = sF6ControlType,
                    moveList = moveList,
                    characterMoveList = characterMoveList,
                    gameMoveList = gameMoveList
                )

                Game.MK1 -> MortalKombatLayout(
                    context = context,
                    comboCreationViewModel = comboCreationViewModel,
                    combo = comboDisplay,
                    console = consoleTypeState,
                    character = character,
                    updateComboData = updateComboData,
                    moveList = moveList,
                    characterMoveList = characterMoveList,
                    gameMoveList = gameMoveList
                )

                Game.CUSTOM -> CustomGameLayout(
                    context = context,
                    comboCreationViewModel = comboCreationViewModel,
                    combo = comboDisplay,
                    updateComboData = updateComboData,
                    character = character,
                    moveList = moveList,
                )
            }
        }
    }
}


