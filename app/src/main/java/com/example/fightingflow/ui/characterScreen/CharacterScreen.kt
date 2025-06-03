package com.example.fightingflow.ui.characterScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fightingflow.data.datastore.Game
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.ui.comboDisplayScreen.ComboDisplayViewModel
import com.example.fightingflow.ui.components.GameSelectedMenu
import com.example.fightingflow.util.CharacterUiState
import com.example.fightingflow.ui.components.SettingsMenu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    scope: CoroutineScope,
    comboDisplayViewModel: ComboDisplayViewModel,
    characterScreenViewModel: CharacterScreenViewModel,
    onClick: () -> Unit,
    navigateToProfiles: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("\nLoading Character Screen")

    val characterListState by comboDisplayViewModel.characterEntryListState.collectAsStateWithLifecycle()
    val gameSelectedState by characterScreenViewModel.gameSelected.collectAsStateWithLifecycle()
    val modernOrClassicState by characterScreenViewModel.modernOrClassicState.collectAsStateWithLifecycle()

    Timber.d("Flows Collected")
    Timber.d("Character List: ${characterListState.characterList}")
    Timber.d("Game Selected From DS: ${gameSelectedState?.title}")


    var gameSelected by remember { mutableStateOf<Game?>(null) }

    gameSelectedState?.let {
        Timber.d("Getting characters by game...")
        gameSelected = gameSelectedState
        Timber.d("Game selected: $gameSelected")
        gameSelected?.let { game -> comboDisplayViewModel.getCharacterEntryListByGame(game) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Characters", style = MaterialTheme.typography.displaySmall) },
                actions = {
                    SettingsMenu(
                        navigate = navigateToProfiles,
                        updateConsoleInput = { comboDisplayViewModel.updateConsoleType(it) }
                    )
                },
                windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
            )
        },
    ) { contentPadding ->

        Column(Modifier.padding(contentPadding)) {
            Row(modifier.fillMaxWidth()) {
                GameSelectedMenu(
                    scope = scope,
                    characterScreenViewModel = characterScreenViewModel,
                    gameSelected = gameSelected,
                    sf6Option = modernOrClassicState,
                    changeSelectedGame = { gameSelected = it },
                )
            }
            LazyVerticalGrid(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                columns = GridCells.Fixed(3)
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
    Box(
        modifier = modifier
            .clickable(
                onClick = {
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
                modifier = modifier.size(150.dp)
            )
            Text(
                text = characterState.character.name,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
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
            "Street Fighter VI" -> Color(color = 0xFFff914d)
            "Mortal Kombat 1" -> Color(color = 0xFFDC143C)
            else -> Color.Green
        }

        Text(
            text ="Game Selected: ",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(start = 16.dp)
        )
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
    }
}