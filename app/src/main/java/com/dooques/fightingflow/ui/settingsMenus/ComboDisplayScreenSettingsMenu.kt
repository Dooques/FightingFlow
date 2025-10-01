package com.dooques.fightingflow.ui.settingsMenus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.dooques.fightingflow.model.Console

@Composable
fun ComboDisplayScreenSettingsMenu(
    updatePublicComboState: () -> Unit,
    updateFilterOptions: () -> Unit,
    showPublicComboState: Boolean,
    updateConsoleInput: (Console) -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var submenuExpanded by remember { mutableStateOf(false) }

    var parentMenuItemSize by remember { mutableStateOf(IntSize.Zero) }
    var subMenuOffset by remember { mutableStateOf(DpOffset(0.dp, 0.dp)) }
    val density = LocalDensity.current

    IconButton(onClick = { menuExpanded = !menuExpanded }) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
        )
    }

    Box(
        modifier.wrapContentSize()
    ) {
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
            offset = DpOffset(y = 20.dp, x = 0.dp)
        ) {

            DropdownMenuItem(
                text = {
                    Text(
                        if (showPublicComboState) "Show Your Combos"
                        else "Show All Combos"
                    )
                },
                onClick = {
                    menuExpanded = !menuExpanded
                    updatePublicComboState()
                }
            )

//            DropdownMenuItem(
//                text = { Text("Filter Combos") },
//                onClick = {
//                    menuExpanded = !menuExpanded
//                    updateFilterOptions()
//                }
//            )

            Box(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    parentMenuItemSize = coordinates.size
                }
            ) {
                DropdownMenuItem(
                    text = { Text("Console Inputs") },
                    onClick = {
                        val parentWidthDp = with(density) { parentMenuItemSize.width.toDp() }
                        val horizontalOffset = (-parentWidthDp.value - 116).dp
                        subMenuOffset = DpOffset(horizontalOffset, 72.dp)
                        submenuExpanded = !submenuExpanded
                    }
                )
            }

            DropdownMenuItem(
                text = { Text("Swipe right on a combo\nfor more options") },
                onClick = {},
                enabled = false,
                colors = MenuDefaults.itemColors()
                    .copy(textColor = MaterialTheme.colorScheme.onBackground)
            )
        }

        ConsoleInputsSubMenu(
            optionSelected = { updateConsoleInput(it) },
            onDismiss = { submenuExpanded = false },
            onDismissParent = { menuExpanded = false },
            offset = subMenuOffset,
            expanded = submenuExpanded
        )
    }
}