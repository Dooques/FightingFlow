package com.example.fightingflow.ui.comboCreationScreen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.fightingflow.ui.comboItem.ComboItemEditor
import com.example.fightingflow.ui.comboDisplayScreen.inputConverter.convertInputsToConsole
import com.example.fightingflow.ui.comboItem.ComboInfoBottom
import com.example.fightingflow.ui.comboItem.ComboItemViewModel
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.characterAndMoveData.convertibleInputs
import com.example.fightingflow.util.emptyComboDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction4
import kotlin.reflect.KSuspendFunction0

@Composable
fun ComboForm(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    editingState: Boolean,
    username: String,
    game: Game?,
    consoleTypeState: Console?,
    sF6ControlType: SF6ControlType?,
    comboDisplay: ComboDisplay,
    originalCombo: ComboDisplay,
    updateComboData: (ComboDisplayUiState) -> Unit,
    setSelectedItem: (Int) -> Unit,
    selectedItem: Int,
    updateMoveList: KFunction4<String, MoveEntryListUiState, Game?, Console?, Unit>,
    character: CharacterEntry,
    characterName: String,
    comboAsString: String,
    moveList: MoveEntryListUiState,
    characterMoveList: MoveEntryListUiState,
    gameMoveList: MoveEntryListUiState,
    iconDisplayState: Boolean,
    textComboDisplay: Boolean,
    saveCombo: KSuspendFunction0<Unit>,
    deleteMove: KFunction0<Unit>,
    clearMoveList: () -> Unit,
    onNavigateToComboDisplay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column {
        Timber.d("Loading Combo Form")
        Timber.d("Combo Move List exists, populating with moves...")
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
                        ComboInfoBottom(emptyComboDisplay, username, Color.White)
                    }
                    Spacer(modifier.height(12.dp))
                } else {
                    Box(modifier.padding(horizontal = 4.dp)) {
                        ComboInfoBottom(emptyComboDisplay, username, Color.White)
                    }
                    Spacer(modifier.height(12.dp))
                }
            }
        } else {
            ComboItemEditor(
                context = context,
                combo = comboDisplay,
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
        EditingButtons(
            deleteMove = deleteMove,
            clearMoveList = clearMoveList,
            updateMoveList = updateMoveList,
            moveList = moveList,
            scope = scope,
            snackbarHostState = snackbarHostState,
            comboDisplay = comboDisplay,
            saveCombo = saveCombo,
            editingState = editingState,
            originalCombo = originalCombo,
            onNavigateToComboDisplay = onNavigateToComboDisplay,
        )
    }

    if (moveList.moveList.isNotEmpty()) {
        Timber.d("Move Entry List exists, populating Input Selector Column...")
        game?.let { innerGame ->
            when (innerGame) {
                Game.T8 -> TekkenLayout(
                    context = context,
                    comboDisplay = comboDisplay,
                    character = character,
                    characterName = characterName,
                    combo = comboDisplay,
                    console = consoleTypeState,
                    updateComboData = updateComboData,
                    updateMoveList = updateMoveList,
                    moveList = moveList,
                    characterMoveList = characterMoveList,
                    gameMoveList = gameMoveList
                )

                Game.SF6 -> StreetFighterLayout(
                    context = context,
                    comboDisplay = comboDisplay,
                    combo = comboDisplay,
                    updateComboData = updateComboData,
                    updateMoveList = updateMoveList,
                    console = consoleTypeState,
                    sF6ControlType = sF6ControlType,
                    moveList = moveList,
                    characterMoveList = characterMoveList,
                    gameMoveList = gameMoveList
                )

                Game.MK1 -> MortalKombatLayout(
                    context = context,
                    combo = comboDisplay,
                    console = consoleTypeState,
                    updateComboData = updateComboData,
                    updateMoveList = updateMoveList,
                    moveList = moveList,
                    characterMoveList = characterMoveList,
                    gameMoveList = gameMoveList
                )

                else -> CustomGameLayout(
                    context = context,
                    combo = comboDisplay,
                    console = consoleTypeState,
                    updateComboData = updateComboData,
                    updateMoveList = updateMoveList,
                    moveList = moveList,
                    characterMoveList = characterMoveList,
                    gameMoveList = gameMoveList
                )
            }
        }
    }
}


