package com.example.fightingflow.ui.characterScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.ui.viewmodels.AddCharacterViewModel
import com.example.fightingflow.ui.viewmodels.ComboDisplayViewModel
import com.example.fightingflow.ui.settingsMenus.CharacterOptionsMenu
import com.example.fightingflow.ui.settingsMenus.GameSelectedMenu
import com.example.fightingflow.ui.settingsMenus.ProfileAndConsoleInputMenu
import com.example.fightingflow.util.CharacterEntryUiState
import com.example.fightingflow.util.emptyCharacter
import com.example.fightingflow.ui.viewmodels.CharacterViewModel
import com.example.fightingflow.util.CharacterEntryListUiState
import com.example.fightingflow.util.characterAndMoveData.characterMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.reflect.KFunction2
import kotlin.reflect.KSuspendFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    comboDisplayViewModel: ComboDisplayViewModel,
    characterScreenViewModel: CharacterViewModel,
    addCharacterViewModel: AddCharacterViewModel,
    navigateToComboDisplayScreen: () -> Unit,
    navigateToProfiles: () -> Unit,
    navigateToAddCharacter: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("-- Loading Character Screen --")

    val characterListState by comboDisplayViewModel.characterEntryListState.collectAsStateWithLifecycle()
    val gameSelectedState by characterScreenViewModel.gameSelectedState.collectAsStateWithLifecycle()
    val modernOrClassicState by characterScreenViewModel.modernOrClassicState.collectAsStateWithLifecycle()
    val customGameList by characterScreenViewModel.customGameList.collectAsStateWithLifecycle()

    Timber.d("-- Flows Collected --\nCharacter List: %s\nGame Selected From DS: %s\nCustom Game List: %s",
        characterListState.characterList, gameSelectedState, customGameList)

    var gameSelected by remember { mutableStateOf<String?>(null) }
    var characterList = characterListState

    gameSelectedState?.let {
        Timber.d("Getting characters by game...")
        gameSelected = gameSelectedState
        Timber.d("Game selected: $gameSelected")
        gameSelected?.let { game ->
            if (!characterMap.keys.contains(game)) {
                Timber.d("Getting all custom characters")
                comboDisplayViewModel.getCustomCharacters()
            } else {
                Timber.d("Getting characters for $game")
                characterList = CharacterEntryListUiState(characterMap[gameSelected] ?: emptyList())
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Characters", style = MaterialTheme.typography.displaySmall) },
                colors = TopAppBarDefaults.largeTopAppBarColors().copy(containerColor = Color.Transparent),
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                comboDisplayViewModel.updateEditingState(false)
                                comboDisplayViewModel.updateCharacterInDS(emptyCharacter)
                                navigateToAddCharacter()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Combo",
                            modifier = modifier.size(80.dp)
                        )
                    }
                    ProfileAndConsoleInputMenu(
                        navigate = navigateToProfiles,
                        updateConsoleInput = { comboDisplayViewModel.updateConsoleType(it) }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navigateToHome() }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = stringResource(R.string.return_to_home),
                            modifier = modifier.size(50.dp)
                        )
                    }
                },
                windowInsets = WindowInsets(0.dp),
            )
        },
        modifier = modifier
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(
                    start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
                    top = contentPadding.calculateTopPadding(),
                    end = contentPadding.calculateEndPadding(LayoutDirection.Ltr)
                )
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                GameSelectedMenu(
                    scope = scope,
                    characterScreenViewModel = characterScreenViewModel,
                    gameSelected = gameSelected,
                    sf6Option = modernOrClassicState,
                    changeSelectedGame = { gameSelected = it },
                    customGameList = customGameList
                )
            }
            Timber.d("-- Loading Character Grid --")
            LazyVerticalGrid(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Timber.d("Loading Character Grid...")
                items(items = characterList.characterList.sortedBy { character -> character.name }) { character ->
                    CharacterCard(
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        characterScreenViewModel = characterScreenViewModel,
                        addCharacterViewModel = addCharacterViewModel,
                        updateCharacterState = comboDisplayViewModel::updateCharacterState,
                        setCharacterToDS = comboDisplayViewModel::updateCharacterInDS,
                        navigateToComboDisplayScreen = navigateToComboDisplayScreen,
                        navigateToAddCharacterScreen = navigateToAddCharacter,
                        characterState = CharacterEntryUiState(character),
                        modifier = Modifier
                    )
                }
                Timber.d("Character Grid Finished.")
            }
        }
    }
}

@Composable
fun CharacterCard(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    characterScreenViewModel: CharacterViewModel,
    addCharacterViewModel: AddCharacterViewModel,
    updateCharacterState: KFunction2<String, String, Unit>,
    setCharacterToDS: KSuspendFunction1<CharacterEntry, Unit>,
    navigateToComboDisplayScreen: () -> Unit,
    navigateToAddCharacterScreen: () -> Unit,
    characterState: CharacterEntryUiState,
    modifier: Modifier = Modifier
) {
    var characterOptionsMenuExpanded by remember { mutableStateOf(false) }
    Timber.d("Loading Card: ${characterState.character.name}")
    Box(
        modifier = modifier
            .combinedClickable(
                onClick = {
                    scope.launch {
                        Timber.d("Preparing to open Combo Screen...")
                        updateCharacterState(
                            characterState.character.name,
                            characterState.character.game
                        )
                        Timber.d("Updated Character State in Combo View Model")
                        Timber.d("Preparing to add ${characterState.character.name} to datastore")
                        setCharacterToDS(characterState.character)
                        Timber.d("Opening Combo Screen")
                        navigateToComboDisplayScreen()
                    }
                },
                onLongClick = {
                    characterOptionsMenuExpanded = true
                }
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()
        ) {
            Timber.d("Checking if custom character and if imageUri is not null")
            Timber.d("Character: $characterState")
            val currentCharacter = characterState.character
            Timber.d("Current character: $currentCharacter")

            if (characterState.character.mutable) {
                val currentImageUri = currentCharacter.imageUri
                if (!currentImageUri.isNullOrBlank()) {
                    Timber.d("Character is Custom and URI is not null " +
                            "\n Loading image from files... \n Uri: %s",
                        currentImageUri)
                    AsyncImage(
                        model = currentImageUri.toUri(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = modifier.size(150.dp)
                    )
                } else {
                    Timber.d("Character mutable, but URI is null")
                    Image(
                        painter = painterResource(characterState.character.imageId),
                        contentDescription = characterState.character.name,
                        contentScale = ContentScale.Fit,
                        modifier = modifier.size(150.dp)
                    )
                }
            } else {
                Timber.d("Character is not custom.")
                Image(
                    painter = painterResource(characterState.character.imageId),
                    contentDescription = characterState.character.name,
                    contentScale = ContentScale.Fit,
                    modifier = modifier.size(150.dp)
                )
            }
            Text(
                text = characterState.character.name,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            if (characterState.character.mutable) {
                CharacterOptionsMenu(
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    characterScreenViewModel = characterScreenViewModel,
                    characterOptionsMenuExpanded = characterOptionsMenuExpanded,
                    characterEntry = characterState.character,
                    navigateToAddCharacter = {
                        scope.launch {
                            Timber.d(
                                "-- Preparing to launch Add Character Screen --" +
                                        "\n Updating character state to: ${characterState.character.name}"
                            )
                            setCharacterToDS(characterState.character)
                            Timber.d("Updating selected game to: ${characterState.character.game}")
                            characterScreenViewModel.updateGameInDs(characterState.character.game)
                            Timber.d("Setting edit state to True")
                            addCharacterViewModel.editState = true
                            Timber.d("Navigating to Add Character Screen")
                            navigateToAddCharacterScreen()
                        }
                    },
                    onDismiss = { characterOptionsMenuExpanded = false }
                )
            }
        }
    }
}