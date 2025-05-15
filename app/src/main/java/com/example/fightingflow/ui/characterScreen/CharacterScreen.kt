package com.example.fightingflow.ui.characterScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.ui.comboDisplayScreen.ComboDisplayViewModel
import com.example.fightingflow.util.CharacterUiState
import timber.log.Timber

const val TAG = "CharacterScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    comboDisplayViewModel: ComboDisplayViewModel,
    onClick: () -> Unit,
    navigateBack: () -> Unit,
    navigateToProfiles: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("")
    Timber.d("\nLoading Character Screen")

    val characterListState by comboDisplayViewModel.characterEntryListState.collectAsState()
    Timber.d("Flows Collected")
    Timber.d("Character List: ${characterListState.characterList}")

    var gameMenuExpanded by remember { mutableStateOf(false) }
    var settingsMenuExpanded by remember { mutableStateOf(false) }
    var gameSelected by remember { mutableStateOf("Tekken 8") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Characters", style = MaterialTheme.typography.displaySmall) },
                actions = {
                    IconButton(onClick = {settingsMenuExpanded = !settingsMenuExpanded}) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                        DropdownMenu(
                            expanded = settingsMenuExpanded,
                            onDismissRequest = { settingsMenuExpanded = false}
                        ) {
                            DropdownMenuItem(
                                text = { Text("Profiles") },
                                onClick = navigateToProfiles
                            )
                        }
                    }
                },
                windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
            )
        },
    ) { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxWidth()) {
                GameSelectedHeader(gameSelected, modifier.align(Alignment.CenterStart))
                IconButton(onClick = { gameMenuExpanded = !gameMenuExpanded}, modifier.align(Alignment.CenterEnd)) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(R.string.open_menu),
                        modifier = Modifier.size(80.dp)
                    )
                    DropdownMenu(expanded = gameMenuExpanded,
                        onDismissRequest = { gameMenuExpanded = false}) {
                        DropdownMenuItem(
                            text = { Text("Tekken 8") },
                            onClick = { gameSelected = "Tekken 8" }
                        )
                        DropdownMenuItem(
                            text = { Text("Street Fighter 6") },
                            onClick = { gameSelected = "Street Fighter 6"}
                        )
                        DropdownMenuItem(
                            text = { Text("Mortal Kombat 1") },
                            onClick = { gameSelected = "Mortal Kombat 1" }
                        )
                    }
                }

            }
            Spacer(modifier.size(16.dp))
            LazyVerticalGrid(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                columns = GridCells.Fixed(3),
                modifier = modifier
                    .padding(16.dp)
            ) {
                Timber.d("Loading Character Grid...")
                items(items = characterListState.characterList.sortedBy { it.name }) { character ->
                    CharacterCard(
                        updateCharacterState = comboDisplayViewModel::updateCharacterState,
                        setCharacterToDS = comboDisplayViewModel::updateCharacterInDS,
                        onClick = onClick,
                        characterState = CharacterUiState(character),
                        modifier = modifier
                    )
                }
                Timber.d("Character Grid Finished.")
            }
        }
    }
}

@Composable
fun CharacterCard(
    updateCharacterState: (String) -> Unit,
    setCharacterToDS: (CharacterEntry) -> Unit,
    onClick: () -> Unit,
    characterState: CharacterUiState,
    modifier: Modifier = Modifier
) {
    Timber.d("Loading Card: ${characterState.character.name}")
    Card(
        colors = CardDefaults.cardColors(Color.White),
        modifier = modifier
            .clickable(
                onClick = {
                    Timber.d("")
                    Timber.d("Preparing to open Combo Screen...")
                    updateCharacterState(characterState.character.name)
                    Timber.d("Updated Character State in Combo View Model")
                    Timber.d("Preparing to add ${characterState.character.name} to datastore")
                    setCharacterToDS(characterState.character)
                    Timber.d("Opening Combo Screen")
                    onClick()
                }
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(characterState.character.imageId),
                contentDescription = characterState.character.name,
                contentScale = ContentScale.Fit,
                modifier = modifier.size(100.dp)
            )
            Text(
                text = characterState.character.name,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black
            )
        }
    }
}

@Composable
fun GameSelectedHeader(gameSelected: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        val gameColor = when (gameSelected) {
            "Tekken 8" -> Color(color = 0xFFed1664)
            "Street Fighter 6" -> Color(color = 0xFFff914d)
            "Mortal Kombat 1" -> Color(color = 0xFF38b6ff)
            else -> Color.Green
        }
        Text(
            text ="Game Selected: ",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(start = 16.dp)
        )
        Text(
            text = gameSelected,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .clip(shape = RoundedCornerShape(100.dp))
                .background(gameColor)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}