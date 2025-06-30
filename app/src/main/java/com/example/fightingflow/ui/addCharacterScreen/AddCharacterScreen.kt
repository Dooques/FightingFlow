package com.example.fightingflow.ui.addCharacterScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.fightingflow.R
import com.example.fightingflow.data.mediastore.MediaStoreUtil
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ControlType
import com.example.fightingflow.ui.characterScreen.CharacterViewModel
import com.example.fightingflow.ui.comboDisplayScreen.ComboDisplayViewModel
import com.example.fightingflow.util.emptyCharacter
import com.example.fightingflow.util.featureColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCharacterScreen(
    addCharacterViewModel: AddCharacterViewModel,
    characterViewModel: CharacterViewModel,
    comboDisplayViewModel: ComboDisplayViewModel,
    navigateBack: () -> Unit,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navigateToSchemeInfo: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("-- Loading Custom Character Screen --")
    val mediaStoreUtil = koinInject<MediaStoreUtil>()

    var character by remember { mutableStateOf(CharacterEntry(
        name = "",
        fightingStyle = "",
        imageId = R.drawable.mokujin,
        game = "Custom",
        controlType = "Tekken",
        uniqueMoves = "",
        mutable = true
    )) }

    val customGameList by addCharacterViewModel.customGameList.collectAsStateWithLifecycle()
    val characterUiStateVM by addCharacterViewModel.characterUiState.collectAsStateWithLifecycle()
    val characterNameFromDs by addCharacterViewModel.characterNameState.collectAsStateWithLifecycle()
    val gameSelectedState by addCharacterViewModel.gameSelectedState.collectAsStateWithLifecycle()

    val editState = addCharacterViewModel.editState
    var controlTypeDropdownExpanded by remember { mutableStateOf(false) }

    Timber.d("Loading Launched effect to update character state...")
    LaunchedEffect(editState, characterUiStateVM) {
        if (editState) {
            Timber.d("Checking character name matches DS...")
            if (character.name != characterNameFromDs && character.game != gameSelectedState) {
                character = characterUiStateVM.character
                Timber.d("Collecting character from DB")
                addCharacterViewModel.getCharacterToEdit()
            }
        } else {
            Timber.d("Not in editing mode")
        }
    }

    Timber.d("Loading launched effect to get existing character")
    LaunchedEffect(editState, characterNameFromDs) {
        if (editState && characterNameFromDs.isNotBlank() &&
            characterUiStateVM.character.name != characterNameFromDs) {
            Timber.d("Getting character to edit...")
            addCharacterViewModel.getCharacterToEdit()
        }
    }

    Timber.d("-- Flows -- " +
            "\n Custom Game List: %s \n Existing Character Values \n Char to Edit Name: %s \n Game: %s \n ",
        customGameList, characterNameFromDs, gameSelectedState)

    Timber.d("\n New Character Values: %s \n Name: %s \n Fighting Style: %s Control Type: %s \n Game: %s" +
            "\n Unique Moves: %s \n Image URI: %s \n Mutable: %s \n ",
        character, character.name, character.fightingStyle, character.controlType, character.game,
        character.uniqueMoves, character.imageUri, character.mutable)

    Timber.d(
        "\n Existing Character: %s \n Name: %s \n Fighting Style: %s \n Control Type: %s" +
                "\n Game: %s \n Unique Moves: %s \n Image URI: %s \n ",
        characterUiStateVM.character, characterUiStateVM.character.fightingStyle,
        characterUiStateVM.character.controlType, characterUiStateVM.character.game,
        characterUiStateVM.character.uniqueMoves, characterUiStateVM.character.imageUri,
        characterUiStateVM.character.mutable)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Character", style = MaterialTheme.typography.displaySmall) },
                actions = {
                    IconButton(onClick = { navigateToSchemeInfo() }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "See Control Schemes",
                            tint = Color.White,
                            modifier = modifier.size(100.dp)
                        )
                    }
                    IconButton(onClick = { scope.launch {
                        comboDisplayViewModel.updateCharacterInDS(emptyCharacter)
                        addCharacterViewModel.clearCharacterState()
                        navigateBack()
                    } },
                        modifier.fillMaxHeight()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Return to Combo screen",
                            tint = Color.White,
                            modifier = modifier.size(100.dp)
                        )
                    }
                },
                windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
            )
        },
    ) { innerPadding ->
        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = character.name,
                        onValueChange = {
                            Timber.d("Updating character name...")
                            character = character.copy(name = it)
                        },
                        label = { Text("Character Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )

                    OutlinedTextField(
                        value = character.fightingStyle,
                        onValueChange = {
                            Timber.d("Updating character fighting style...")
                            character = character.copy(fightingStyle = it)
                                        },
                        label = { Text("Fighting Style") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )

                    OutlinedTextField(
                        value = character.game,
                        onValueChange = {
                            character = character.copy(game = it)
                        },
                        label = { Text("Game") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        trailingIcon = { IconButton(
                            onClick = { character = character.copy(game = "") }
                        ) { Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(R.string.clear_current_value)
                        ) } },
                        supportingText = {
                            Text(
                                text = "Keep game titles consistent if adding multiple characters from one game.",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = modifier.padding(horizontal = 4.dp)
                            )
                        }
                    )

                    ExposedDropdownMenuBox(
                        expanded = controlTypeDropdownExpanded,
                        onExpandedChange = {
                            controlTypeDropdownExpanded = !controlTypeDropdownExpanded
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = character.controlType,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Control Type") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = controlTypeDropdownExpanded) },
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                                .fillMaxWidth(),
                        )
                        ExposedDropdownMenu(
                            expanded = controlTypeDropdownExpanded,
                            onDismissRequest = { controlTypeDropdownExpanded = false }
                        ) {
                            ControlType.entries.forEach { controlType ->
                                DropdownMenuItem(
                                    text = { Text(controlType.type) },
                                    onClick = {
                                        character = character.copy(controlType = controlType.type)
                                        controlTypeDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Box(modifier.padding(bottom = 4.dp)) {
                        OutlinedTextField(
                            value = character.uniqueMoves ?: "",
                            onValueChange = {
                                character = character.copy(uniqueMoves = it)
                            },
                            label = { Text("Unique Moves") },
                            supportingText = {
                                Text(
                                    text = "List your character's unique moves, separated by commas.",
                                    modifier = modifier.padding(horizontal = 4.dp)
                                )
                            },
                            enabled = true,
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3,
                        )
                    }

                    CharacterImagePicker(
                        mediaStoreUtil = mediaStoreUtil,
                        imageUri = character.imageUri?.toUri() ?: Uri.EMPTY,
                        character = character,
                        updateImageId = {
                            character = character.copy(imageUri = it.toString())
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ConfirmButton(
                        addCharacterViewModel = addCharacterViewModel,
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        editState = editState,
                        character = character,
                        navigateBack = navigateBack
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterImagePicker(
    mediaStoreUtil: MediaStoreUtil,
    imageUri: Uri?,
    character: CharacterEntry,
    updateImageId: (Uri?) -> Unit,
    modifier: Modifier = Modifier
) {
    var setBuffer by remember { mutableStateOf(false) }

    val pickMedia = rememberLauncherForActivityResult(
        contract = PickVisualMedia(),
        onResult = { uri: Uri? ->
            Timber.d("-- Launching Photo Picker --")
            Timber.d("")
            if (uri != null) {
                Timber.d("URI: $uri")
                val savedUri = mediaStoreUtil.copyImageToAppStorage(uri, character.name)
                updateImageId(savedUri)
            } else {
                Timber.d("No Media Selected")
            }
        }
    )

    Box(
        modifier.fillMaxWidth()
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = modifier
                    .size(100.dp)
                    .align(Alignment.CenterStart)
            )
        } else {
            Image(
                painter = painterResource(character.imageId),
                contentDescription = null,
                modifier
                    .size(120.dp)
                    .align(Alignment.CenterStart)
            )
        }

        Column(
            modifier = modifier
                .fillMaxWidth(0.7f)
                .align(Alignment.CenterEnd)
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        text = "Select an image for your character.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = modifier
                    )
                    Text(
                        text = "For the best result, use a PNG with a transparent background.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = modifier
                    )
                }
            }
            OutlinedButton(
                onClick = {
                    setBuffer = true
                    pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                    setBuffer = false
                          },
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Pick a Photo",
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

        }
    }
}

@Composable
fun ConfirmButton(
    addCharacterViewModel: AddCharacterViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    editState: Boolean,
    character: CharacterEntry,
    navigateBack: () -> Unit
) {
    var setBuffer by remember { mutableStateOf(false) }
    val mediaStoreUtil = koinInject<MediaStoreUtil>()
    var imageFile: File?
    var imageUri: Uri? = null

    Button(
        onClick = {
            Timber.d("-- Updating Database with Character values --")
            Timber.d("Checking if ImageURI value is empty...")
            Timber.d("URI: ${character.imageUri}")
            if (!character.imageUri.isNullOrBlank()) {
                Timber.d("Image URI string is valid, converting to File")
                imageFile =
                    character.imageUri.let { mediaStoreUtil.getFileFromUri(uri = it.toUri()) }
                Timber.d("imageFile: $imageFile")

                    imageUri = imageFile?.let { mediaStoreUtil.finalizeImage(it, character.name) }
                    Timber.d("ImageURI: $imageUri")
            }

            Timber.d("Checking if imageURI is null...")
            if (character.imageUri != null ) {
                Timber.d("Image URI not null, ")
                val updatedCharacter = character.copy(imageUri = imageUri.toString())

                scope.launch {
                    val result = addCharacterViewModel.saveCharacter(updatedCharacter)
                    setBuffer = true
                    Timber.d("Result: $result")
                    if (result is CharacterDbResult.Success) {
                        if (!editState) {
                            addCharacterViewModel.updateCustomGameList(character.game)
                            snackbarHostState.showSnackbar(
                                message = "Successfully added character to database.",
                                withDismissAction = true
                            )
                            setBuffer = false
                            navigateBack()
                        } else {
                            snackbarHostState.showSnackbar(
                                message = "Successfully updated character in database.",
                                withDismissAction = true,
                            )
                            setBuffer = false
                            navigateBack()
                        }
                    } else if (result is CharacterDbResult.Error) {
                            snackbarHostState.showSnackbar(
                                message = result.e.toString(),
                                withDismissAction = true
                            )
                        setBuffer = false
                        }
                    }
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar("Please add an image for your character.")
                    }
                    Timber.d("Image URI is null")
                }
        },
        enabled = character.game.isNotEmpty() ||
                character.name.isNotEmpty() ||
                character.controlType.isNotBlank() ||
                !character.uniqueMoves.isNullOrBlank(),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = featureColor,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        if ( setBuffer ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        if (!setBuffer) {
            Text(
                text = "Save Character",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color =  Color.White,
                    shadow = Shadow(color = Color.Black.copy(alpha = 0.5f), offset = Offset(2f, 2f), blurRadius = 4f)
                )
            )
        }
    }
}