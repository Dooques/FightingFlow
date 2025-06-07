package com.example.fightingflow.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.fightingflow.model.Console
import com.example.fightingflow.model.Game
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.ui.characterScreen.CharacterScreenViewModel
import com.example.fightingflow.ui.characterScreen.GameSelectedHeader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun SettingsMenu(
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
    characterScreenViewModel: CharacterScreenViewModel,
    gameSelected: Game?,
    sf6Option: SF6ControlType?,
    changeSelectedGame: (Game) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        gameSelected?.title?.let { game ->
            GameSelectedHeader(
                gameSelected = game,
                modifier = modifier.align(Alignment.CenterStart)
            )
        }

        var gameMenuExpanded by remember { mutableStateOf(false) }
        var subMenuExpanded by remember { mutableStateOf(false) }

        IconButton(
            onClick = { gameMenuExpanded = !gameMenuExpanded },
            modifier = modifier.fillMaxWidth(0.4f).align(Alignment.Center)
        ) {
            DropdownMenu(
                expanded = gameMenuExpanded,
                onDismissRequest = { gameMenuExpanded = false },
                modifier = modifier.align(Alignment.Center)
            ) {
                DropdownMenuItem(
                    text = { Text("Mortal Kombat 1") },
                    onClick = {
                        gameMenuExpanded = false
                        changeSelectedGame(Game.MK1)
                        scope.launch {
                            characterScreenViewModel.updateGameInDs(Game.MK1.title)
                        }
                    }
                )
                DropdownMenuItem(
                    text = { Text("Street Fighter VI") },
                    onClick = {
                        subMenuExpanded = true
                        changeSelectedGame(Game.SF6)
                        scope.launch {
                            characterScreenViewModel.updateGameInDs(Game.SF6.title)
                        }
                    }
                )
                DropdownMenuItem(
                    text = { Text("Tekken 8") },
                    onClick = {
                        gameMenuExpanded = false
                        changeSelectedGame(Game.T8)
                        scope.launch {
                            characterScreenViewModel.updateGameInDs(Game.T8.title)
                        }
                    }
                )
                if (subMenuExpanded) {
                    SF6ModernOrClassicMenu(
                        scope = scope,
                        characterScreenViewModel = characterScreenViewModel,
                        option = sf6Option,
                        onDismiss = { subMenuExpanded = false },
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
    characterScreenViewModel: CharacterScreenViewModel,
    option: SF6ControlType?,
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
            trailingIcon = {
                if (option == SF6ControlType.Modern) Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null)
                else Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null
                    )
            },
            onClick = {
                Timber.d("Setting input type to ${SF6ControlType.Classic}...")
                scope.launch {
                    characterScreenViewModel.updateSF6ControlType(SF6ControlType.Classic)
                }
                onDismiss()
                onDismissParent()
            }
        )
        DropdownMenuItem(
            text = { Text("Classic") },
            trailingIcon = {
                if (option == SF6ControlType.Classic) Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null)
                else Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null)
            },
            onClick = {
                Timber.d("Setting input type to ${SF6ControlType.Modern}...")
                scope.launch {
                    characterScreenViewModel.updateSF6ControlType(SF6ControlType.Modern)
                }
                onDismiss()
                onDismissParent()
            }
        )
    }
}
