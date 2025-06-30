package com.example.fightingflow.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.Console
import com.example.fightingflow.model.Game
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.ui.characterScreen.CharacterViewModel
import com.example.fightingflow.ui.characterScreen.GameSelectedHeader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ProfileAndConsoleInputMenu(
    navigate: () -> Unit,
    updateConsoleInput: (Console) -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var submenuExpanded by remember { mutableStateOf(false) }
    IconButton(onClick = {menuExpanded = !menuExpanded}) {
        Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")

        Box(modifier = modifier.fillMaxSize()) {
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Profiles") },
                    onClick = navigate
                )
                DropdownMenuItem(
                    text = { Text("Console Inputs") },
                    onClick = { submenuExpanded = !submenuExpanded }
                )
                if (submenuExpanded) {
                    ConsoleInputsMenu(
                        optionSelected = { updateConsoleInput(it) },
                        onDismiss = { submenuExpanded = false },
                        onDismissParent = { menuExpanded = false }
                    )
                }
            }
        }
    }
}

@Composable
fun ShowPublicCombosMenu(
    updatePublicComboState: () -> Unit,
    showPublicComboState: Boolean,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }
    IconButton(onClick = {menuExpanded = !menuExpanded}) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings"
        )
        Box(modifier = modifier.fillMaxSize()) {
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(
                        if (showPublicComboState) "Show Your Combos"
                        else "Show All Combos"
                    ) },
                    onClick = {
                        menuExpanded = !menuExpanded
                        updatePublicComboState()
                    }

                )
            }
        }
    }
}

@Composable
fun ConsoleInputsMenu(
    optionSelected: (Console) -> Unit,
    onDismiss: () -> Unit,
    onDismissParent: () -> Unit,
    offset: DpOffset = DpOffset(160.dp, 25.dp)
) {
    DropdownMenu(
        expanded = true,
        onDismissRequest = { onDismiss(); onDismissParent() },
        offset = offset
        ) {
        DropdownMenuItem(
            text = { Text("Standard") },
            onClick = {
                Timber.d("Setting input type to Nintendo...")
                optionSelected(Console.STANDARD)
                onDismiss()
                onDismissParent()
            }
        )
        DropdownMenuItem(
            text = { Text("Playstation") },
            onClick = {
                Timber.d("Setting input type to Playstation...")
                optionSelected(Console.PLAYSTATION)
                onDismiss()
                onDismissParent()
            }
        )
        DropdownMenuItem(
            text = { Text("Xbox") },
            onClick = {
                Timber.d("Setting input type to Xbox...")
                optionSelected(Console.XBOX)
                onDismiss()
                onDismissParent()
            }
        )
        DropdownMenuItem(
            text = { Text("Nintendo") },
            onClick = {
                Timber.d("Setting input type to Nintendo...")
                optionSelected(Console.NINTENDO)
                onDismiss()
                onDismissParent()
            }
        )
    }
}
@Composable
fun GameSelectedMenu(
    scope: CoroutineScope,
    characterScreenViewModel: CharacterViewModel,
    gameSelected: String?,
    sf6Option: SF6ControlType?,
    changeSelectedGame: (String) -> Unit,
    customGameList: List<String>,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        var gameMenuExpanded by remember { mutableStateOf(false) }
        var sf6OptionsExpanded by remember { mutableStateOf(false) }
        var customGameListExpanded by remember { mutableStateOf(false) }

        gameSelected?.let { game ->
            GameSelectedHeader(
                gameSelected = game,
                onClick = { gameMenuExpanded = !gameMenuExpanded },
                modifier = modifier.align(Alignment.CenterStart)
            )
        }
        Box(
            modifier = modifier
                .fillMaxWidth(0.4f)
                .align(Alignment.Center)
        ) {
            DropdownMenu(
                expanded = gameMenuExpanded,
                onDismissRequest = { gameMenuExpanded = false },
                modifier = modifier.align(Alignment.Center)
            ) {
                // Select MK1
                DropdownMenuItem(
                    text = { Text("Mortal Kombat 1") },
                    onClick = {
                        gameMenuExpanded = false
                        changeSelectedGame(Game.MK1.title)
                        scope.launch {
                            characterScreenViewModel.updateGameInDs(Game.MK1.title)
                        }
                    }
                )

                // Select SF6
                DropdownMenuItem(
                    text = { Text("Street Fighter VI") },
                    onClick = {
                        sf6OptionsExpanded = true
                    }
                )

                // Select Tekken 8
                DropdownMenuItem(
                    text = { Text("Tekken 8") },
                    onClick = {
                        gameMenuExpanded = false
                        changeSelectedGame(Game.T8.title)
                        scope.launch {
                            characterScreenViewModel.updateGameInDs(Game.T8.title)
                        }
                    }
                )

                // Select Custom
                DropdownMenuItem(
                    text = { Text("Custom") },
                    onClick = {
                        customGameListExpanded = true
                    }
                )

                if (sf6OptionsExpanded) {
                    SF6ModernOrClassicMenu(
                        scope = scope,
                        characterScreenViewModel = characterScreenViewModel,
                        option = sf6Option,
                        onDismiss = { sf6OptionsExpanded = false },
                        changeSelectedGame = { changeSelectedGame(it) },
                        onDismissParent = { gameMenuExpanded = false },
                    )
                }

                if (customGameListExpanded) {
                    CustomGameListMenu(
                        scope = scope,
                        characterScreenViewModel = characterScreenViewModel,
                        customGameList = customGameList,
                        changeSelectedGame = changeSelectedGame,
                        onDismiss = { customGameListExpanded = false },
                        onDismissParent = { gameMenuExpanded = false },
                    )
                }
            }
        }
    }
}


@Composable
fun SF6ModernOrClassicMenu(
    scope: CoroutineScope,
    characterScreenViewModel: CharacterViewModel,
    option: SF6ControlType?,
    changeSelectedGame: (String) -> Unit,
    onDismiss: () -> Unit,
    onDismissParent: () -> Unit,
    offset: DpOffset = DpOffset(5.dp, 0.dp)
) {
    DropdownMenu(
        expanded = true,
        onDismissRequest = { onDismiss(); onDismissParent() },
        offset = offset
    ) {
        DropdownMenuItem(
            text = { Text("Modern") },
            onClick = {
                Timber.d("Setting input type to ${SF6ControlType.Classic}...")
                scope.launch {
                    characterScreenViewModel.updateGameInDs(Game.SF6.title)
                    characterScreenViewModel.updateSF6ControlType(SF6ControlType.Classic)
                }
                changeSelectedGame(Game.SF6.title)
                onDismiss()
                onDismissParent()
            }
        )
        DropdownMenuItem(
            text = { Text("Classic") },
            onClick = {
                Timber.d("Setting input type to ${SF6ControlType.Modern}...")
                scope.launch {
                    characterScreenViewModel.updateGameInDs(Game.SF6.title)
                    characterScreenViewModel.updateSF6ControlType(SF6ControlType.Modern)
                }
                changeSelectedGame(Game.SF6.title)
                onDismiss()
                onDismissParent()
            }
        )
    }
}

@Composable
fun CustomGameListMenu(
    scope: CoroutineScope,
    characterScreenViewModel: CharacterViewModel,
    customGameList: List<String>,
    changeSelectedGame: (String) -> Unit,
    onDismiss: () -> Unit,
    onDismissParent: () -> Unit,
    offset: DpOffset = DpOffset(5.dp, 0.dp)
) {
    DropdownMenu(
        expanded = true,
        onDismissRequest = {
            onDismiss()
            onDismissParent()
        },
        offset = offset
    ) {
        DropdownMenuItem(
            text = { Text("All Characters") },
            onClick = {
                scope.launch {
                    characterScreenViewModel.updateGameInDs("All")
                }
                changeSelectedGame(Game.CUSTOM.title)
                onDismiss()
                onDismissParent()
            }
        )
        customGameList.forEach { game ->
            if (game.isNotEmpty()) {
                DropdownMenuItem(
                    text = { Text(game) },
                    onClick = {
                        scope.launch {
                            characterScreenViewModel.updateGameInDs(game)
                        }
                        changeSelectedGame(Game.CUSTOM.title)
                        onDismiss()
                        onDismissParent()
                    }
                )
            }
        }
    }
}

@Composable
fun CharacterOptionsMenu(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    characterScreenViewModel: CharacterViewModel,
    characterOptionsMenuExpanded: Boolean,
    characterEntry: CharacterEntry,
    navigateToAddCharacter: () -> Unit,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        expanded = characterOptionsMenuExpanded,
        onDismissRequest = { onDismiss() }
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.edit_character)) },
            onClick = navigateToAddCharacter
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.delete_character)) },
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Are you sure you want to delete this character?",
                        actionLabel = "Yes",
                        withDismissAction = true,
                        duration = SnackbarDuration.Indefinite
                    ).run {
                        when (this) {
                            SnackbarResult.ActionPerformed -> {
                                scope.launch {
                                    characterScreenViewModel.deleteCustomCharacter(characterEntry)
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "${characterEntry.name} has been deleted."
                                        )
                                    }
                                }
                            }
                            SnackbarResult.Dismissed -> Timber.d("Character deletion cancelled.")
                        }
                    }
                }
                onDismiss()
            }
        )
    }
}
