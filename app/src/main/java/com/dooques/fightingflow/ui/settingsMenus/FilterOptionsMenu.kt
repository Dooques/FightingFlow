package com.dooques.fightingflow.ui.settingsMenus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dooques.fightingflow.model.Game
import com.dooques.fightingflow.model.MoveEntry
import com.dooques.fightingflow.model.SF6ControlType
import com.dooques.fightingflow.util.characterAndMoveData.moveMap
import timber.log.Timber

@Composable
fun FilterOptionsMenu(
    game: Game,
    character: String,
    addMoveToFilter: (MoveEntry) -> Unit,
    currentFilterList: MutableList<MoveEntry>,
    removeMoveFromFilter: (MoveEntry) -> Unit,
    modifier: Modifier = Modifier,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var characterSubmenuExpanded by remember { mutableStateOf(false) }
    var mechanicSubmenuExpanded by remember { mutableStateOf(false) }
    var stageSubmenuExpanded by remember { mutableStateOf(false) }
    IconButton(
        onClick = { menuExpanded = true },
    ) {
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            modifier = modifier.size(30.dp)
        )
    }
    Box {
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        "Character Moves",
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                },
                onClick = { characterSubmenuExpanded = true }
            )
            MoveFilterItemMenu(
                submenuExpanded = characterSubmenuExpanded,
                submenuDismiss = { characterSubmenuExpanded = false },
                mainMenuDismiss = { menuExpanded = false},
                game = game,
                character = character,
                addMoveToFilter = addMoveToFilter,
                removeMoveFromFilter = removeMoveFromFilter,
                currentFilterList = currentFilterList,
                moveType = "Character"
            )
            DropdownMenuItem(
                text = {
                    Text(
                        "Stage Actions",
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                },
                onClick = { stageSubmenuExpanded = true }
            )
            MoveFilterItemMenu(
                submenuExpanded = stageSubmenuExpanded,
                submenuDismiss = { stageSubmenuExpanded = false },
                mainMenuDismiss = { menuExpanded = false},
                game = game,
                character = character,
                addMoveToFilter = addMoveToFilter,
                removeMoveFromFilter = removeMoveFromFilter,
                currentFilterList = currentFilterList,
                moveType = "Stage"
            )
            Box {
                DropdownMenuItem(
                    text = {
                        Text(
                            "Game Mechanics",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        )
                    },
                    onClick = { mechanicSubmenuExpanded = true }
                )
                MoveFilterItemMenu(
                    submenuExpanded = mechanicSubmenuExpanded,
                    submenuDismiss = { mechanicSubmenuExpanded = false },
                    mainMenuDismiss = { menuExpanded = false },
                    game = game,
                    character = character,
                    addMoveToFilter = addMoveToFilter,
                    removeMoveFromFilter = removeMoveFromFilter,
                    currentFilterList = currentFilterList,
                    moveType = "Mechanic"
                )
            }
            DropdownMenuItem(
                text = { Text("Clear Move Filters") },
                onClick = {}
            )
            DropdownMenuItem(
                text = { Text("Close") },
                onClick = {}
            )
        }
    }
}

@Composable
fun MoveFilterItemMenu(
    moveType: String,
    game: Game,
    character: String,
    submenuExpanded: Boolean,
    mainMenuDismiss: () -> Unit,
    currentFilterList: MutableList<MoveEntry>,
    addMoveToFilter: (MoveEntry) -> Unit,
    removeMoveFromFilter: (MoveEntry) -> Unit,
    submenuDismiss: () -> Unit,
) {
    DropdownMenu(
        expanded = submenuExpanded,
        onDismissRequest = {
            submenuDismiss()
            mainMenuDismiss()
        }
    ) {
        Timber.d(" Move Type: $moveType\n Current Filter List: $currentFilterList")
        var finalList: List<MoveEntry> = emptyList()
        if (moveType == "Character") {
            Timber.d(" Getting character: $character")
            Timber.d(" Game: ${game.title.split(" ")[0]}")
            val characterList = moveMap["character"]
                ?.get("standard")
                ?.get(
                    when (game) {
                        Game.T8 -> "Tekken"
                        Game.MK1 -> "Mortal Kombat"
                        Game.SF6-> "Street Fighter"
                        else -> null
                    })
                ?.filter { it.character == character }
            if (characterList != null) finalList = characterList
        } else {
            val filteredList = moveMap["inputs"]
                ?.get("standard")
                ?.get(
                    when (game) {
                        Game.T8 -> "Tekken"
                        Game.MK1 -> "Mortal Kombat"
                        Game.SF6-> "Street Fighter"
                        else -> null
                    })
                ?.filter { it.moveType == moveType }
            if (filteredList != null) finalList = filteredList
        }
        Timber.d(" Final list: $finalList")
        finalList.forEach {
            var selected by remember { mutableStateOf(false) }
            Row {
                DropdownMenuItem(
                    text = { Text(it.moveName) },
                    trailingIcon = {
                        if (selected) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null)
                        } },
                    onClick = {
                        if (!currentFilterList.contains(it)) {
                            Timber.d(" Adding ${it.moveName} to filter")
                            addMoveToFilter(it)
                            selected = true
                        } else {
                            Timber.d(" Removing ${it.moveName} from filter")
                            removeMoveFromFilter(it)
                            selected = false
                        }
                    }
                )
            }
        }
    }
}
