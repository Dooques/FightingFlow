package com.dooques.fightingflow.ui.settingsMenus

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.dooques.fightingflow.model.Game
import com.dooques.fightingflow.ui.viewmodels.CharacterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

