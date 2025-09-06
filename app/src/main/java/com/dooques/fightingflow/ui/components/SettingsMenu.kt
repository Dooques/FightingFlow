package com.dooques.fightingflow.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun ProfileAndConsoleInputMenu(
    settingsMenuExpanded: Boolean,
    onDismissRequest: () -> Unit,
    updateIconSetting: () -> Unit,
    updateTextComboSetting: () -> Unit,
    iconState: Boolean,
    textComboState: Boolean
) {
    DropdownMenu(
        expanded = settingsMenuExpanded,
        onDismissRequest = { onDismissRequest() },
        offset = DpOffset(x = 16.dp, y = (-40).dp)
    ) {
        DropdownMenuItem(
            text = { Text( "Icons in Combos") },
            trailingIcon = {
                if (iconState) Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
                else Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null
                )
            },
            onClick = { updateIconSetting() }
        )
        DropdownMenuItem(
            text = { Text("Text in Combos") },
            trailingIcon = {
                if (textComboState)
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                else
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null
                    )
            },
            onClick = { updateTextComboSetting() }
        )
    }
}