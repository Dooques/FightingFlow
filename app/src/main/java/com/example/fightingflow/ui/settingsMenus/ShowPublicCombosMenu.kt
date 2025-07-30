package com.example.fightingflow.ui.settingsMenus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Modifier

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
