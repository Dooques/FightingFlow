package com.example.fightingflow.ui.settingsMenus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fightingflow.model.Game
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.viewmodels.CharacterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
fun GameSelectedHeader(gameSelected: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        val gameColor = when (gameSelected) {
            "Tekken 8" -> Color(color = 0xFFed1664)
            "Street Fighter VI" -> Color(color = 0xFFff914d)
            "Mortal Kombat 1" -> Color(color = 0xFFDC143C)
            else -> Color.Green
        }

        Text(
            text ="Game Selected: ",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(start = 16.dp)
        )

        Box {
            Text(
                text = gameSelected,
                style = MaterialTheme.typography.bodyLarge
                    .copy(
                        shadow = Shadow(
                            color = gameColor,
                            offset = Offset(10f, 10f),
                            blurRadius = 30f,
                        )
                    ),
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                modifier = modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Box(
                modifier
                    .width(160.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .clickable(onClick = onClick)
            )
        }
    }
}
