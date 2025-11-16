package com.dooques.fightingflow.ui.comboCreationScreen

import androidx.compose.foundation.layout.Arrangement
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
import com.dooques.fightingflow.data.firebase.GoogleAuthService
import com.dooques.fightingflow.model.CharacterEntry
import com.dooques.fightingflow.model.ComboDisplay
import com.dooques.fightingflow.model.Console
import com.dooques.fightingflow.model.Game
import com.dooques.fightingflow.model.SF6ControlType
import com.dooques.fightingflow.model.UserDataForCombos
import com.dooques.fightingflow.ui.comboCreationScreen.layouts.CustomGameLayout
import com.dooques.fightingflow.ui.comboCreationScreen.layouts.MortalKombatLayout
import com.dooques.fightingflow.ui.comboCreationScreen.layouts.StreetFighterLayout
import com.dooques.fightingflow.ui.comboCreationScreen.layouts.TekkenLayout
import com.dooques.fightingflow.ui.comboItem.ComboItemEditor
import com.dooques.fightingflow.ui.comboItem.ComboInfoBottom
import com.dooques.fightingflow.util.ComboDisplayUiState
import com.dooques.fightingflow.util.MoveEntryListUiState
import com.dooques.fightingflow.ui.viewmodels.AuthViewModel
import com.dooques.fightingflow.ui.viewmodels.ComboCreationViewModel
import com.dooques.fightingflow.ui.viewmodels.ComboDisplayViewModel
import com.dooques.fightingflow.ui.viewmodels.ProfanityViewModel
import com.dooques.fightingflow.ui.viewmodels.UserDetailsState
import com.dooques.fightingflow.util.CharacterEntryUiState
import com.dooques.fightingflow.util.characterAndMoveData.characterMap
import com.dooques.fightingflow.util.emptyCharacter
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import kotlin.reflect.KFunction0

@Composable
/* The form used to display the moves in the combo along with the inputs to add more moves to a
    combo */
fun ComboForm(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    editingState: Boolean,
    comboDisplayViewModel: ComboDisplayViewModel,
    comboCreationViewModel: ComboCreationViewModel,
    authViewModel: AuthViewModel,
    profanityViewModel: ProfanityViewModel,
    comboCreationState: Boolean,
    game: Game,
    consoleTypeState: Console?,
    sF6ControlType: SF6ControlType?,
    currentUser: GoogleAuthService.SignInState,
    userData: UserDataForCombos,
    userDetails: UserDetailsState,
    comboDisplay: ComboDisplay,
    originalCombo: ComboDisplay,
    selectedItem: Int,
    character: CharacterEntry,
    characterName: String?,
    comboAsString: String,
    moveList: MoveEntryListUiState,
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
        Spacer(modifier.height(20.dp))
        /* Placeholder composable for creating a new combo */
        if (comboDisplay.moves.isEmpty()) {
            Box (modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    ) {
                        Text(
                            text = "Add some moves!",
                            modifier = modifier.padding(horizontal = 4.dp)
                        )
                    }
                    if (textComboDisplay) {
                        ComboAsText("")
                    }
                    ComboInfoBottom(
                        scope = scope,
                        comboDisplayViewModel = comboDisplayViewModel,
                        comboCreationState = comboCreationState,
                        combo = comboDisplay,
                        characterEntry = CharacterEntryUiState(
                            characterMap[game.title]?.firstOrNull { characterName == it.name }
                                ?: emptyCharacter
                        ),
                        currentUser = currentUser,
                        userData = userData,
                        userDetails = userDetails,
                        toShare = false,
                        fontColor = Color.White,
                    )
                }
            }
        } else {
            Timber.d("Combo Move List exists, populating form with moves...\n")
            /* Shows moves added to combo once a move has been added */
            ComboItemEditor(
                context = context,
                scope = scope,
                comboDisplayViewModel = comboDisplayViewModel,
                currentUser = currentUser,
                userData = userData,
                userDetails = userDetails,
                comboCreationState = comboCreationState,
                combo = comboDisplay,
                console = consoleTypeState,
                sf6ControlType = sF6ControlType,
                fontColor = MaterialTheme.colorScheme.onBackground,
                iconDisplayState = iconDisplayState,
                textComboState = textComboDisplay,
                comboAsText = comboAsString,
                uiScale = 1f,
                setSelectedItem = setSelectedItem,
                selectedItem = selectedItem,
            )
        }
        Spacer(modifier.height(10.dp))
        /* The buttons used to edit the moves in the combo */
        EditingButtons(
            comboCreationViewModel = comboCreationViewModel,
            authViewModel = authViewModel,
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
                    profanityViewModel = profanityViewModel,
                    comboDisplay = comboDisplay,
                    character = character,
                    characterName = characterName,
                    combo = comboDisplay,
                    console = consoleTypeState,
                    updateComboData = updateComboData,
                    moveList = moveList,
                )

                Game.SF6 -> StreetFighterLayout(
                    context = context,
                    comboCreationViewModel = comboCreationViewModel,
                    profanityViewModel = profanityViewModel,
                    comboDisplay = comboDisplay,
                    character = character,
                    combo = comboDisplay,
                    updateComboData = updateComboData,
                    console = consoleTypeState,
                    sF6ControlType = sF6ControlType,
                    moveList = moveList,
                )

                Game.MK1 -> MortalKombatLayout(
                    context = context,
                    comboCreationViewModel = comboCreationViewModel,
                    profanityViewModel = profanityViewModel,
                    combo = comboDisplay,
                    console = consoleTypeState,
                    character = character,
                    updateComboData = updateComboData,
                    moveList = moveList,
                )

                Game.CUSTOM -> CustomGameLayout(
                    context = context,
                    comboCreationViewModel = comboCreationViewModel,
                    profanityViewModel = profanityViewModel,
                    combo = comboDisplay,
                    updateComboData = updateComboData,
                    character = character,
                    moveList = moveList,
                )
            }
        }
    }
}


