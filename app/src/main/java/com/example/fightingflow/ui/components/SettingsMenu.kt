package com.example.fightingflow.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SettingsMenu(
    settingsMenuExpanded: Boolean,
    onDismissRequest: () -> Unit,
    updateIconSetting: () -> Unit,
    updateTextComboSetting: () -> Unit,
    iconState: Boolean,
    textComboState: Boolean
) {
    DropdownMenu(
        expanded = settingsMenuExpanded,
        onDismissRequest = { onDismissRequest() }
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