package com.example.fightingflow.ui.settingsMenus

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.fightingflow.model.Game
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.ui.viewmodels.CharacterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ModernOrClassicSubMenu(
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

