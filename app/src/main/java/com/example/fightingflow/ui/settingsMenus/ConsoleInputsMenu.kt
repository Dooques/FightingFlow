package com.example.fightingflow.ui.settingsMenus

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.fightingflow.model.Console
import timber.log.Timber

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

