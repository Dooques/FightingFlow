package com.example.fightingflow.ui.addCharacterScreen

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract.Contacts.Photo
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.fightingflow.R
import com.example.fightingflow.data.mediastore.MediaStoreUtil
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ControlType
import com.example.fightingflow.util.featureColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCharacterScreen(
    navigateBack: () -> Unit,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    Timber.d("-- Loading Custom Character Screen --")
    val addCharacterViewModel = koinInject<AddCharacterViewModel>()

    var character by remember {
        mutableStateOf(
            CharacterEntry(
                name = "",
                fightingStyle = "",
                imageId = R.drawable.mokujin,
                game = "Custom",
                controlType = "Tekken",
                mutable = true
            )
        )
    }

    var gameDropdownExpanded by remember { mutableStateOf(false) }
    var controlTypeDropdownExpanded by remember { mutableStateOf(false) }

    var uniqueMoves by remember { mutableStateOf("") }
    var controlTypeValue by remember { mutableStateOf(ControlType.Tekken) }
    var game by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf(character.characterImageUri?.toUri()) }

    Timber.d("-- Character --")
    Timber.d("Character: $character")
    Timber.d("-- Mutable Values --")
    Timber.d("Unique Moves: $uniqueMoves")
    Timber.d("Control Type: $controlTypeValue")
    Timber.d("Game: $game")
    Timber.d("Image URI: $imageUri")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Character", style = MaterialTheme.typography.displaySmall) },
                actions = {
                    IconButton(
                        onClick = { navigateBack() },
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
                        onValueChange = { character = character.copy(name = it) },
                        label = { Text("Character Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )

                    OutlinedTextField(
                        value = character.fightingStyle,
                        onValueChange = { character = character.copy(fightingStyle = it) },
                        label = { Text("Fighting Style") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )

                    OutlinedTextField(
                        value = game,
                        onValueChange = {
                            character = character.copy(game = it)
                            game = it
                        },
                        label = { Text("Game") },
                        placeholder = { Text("Custom") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
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
                            value = controlTypeValue.type,
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
                                        controlTypeValue = controlType
                                        controlTypeDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Box(modifier.padding(bottom = 4.dp)) {
                        OutlinedTextField(
                            value = uniqueMoves,
                            onValueChange = {
                                character = character.copy(uniqueMoves = it)
                                uniqueMoves = it
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
                        imageUri = imageUri,
                        character = character,
                        updateImageId = {
                            imageUri = it
                            character = character.copy(characterImageUri = it.toString())
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ConfirmButton(
                        addCharacterViewModel = addCharacterViewModel,
                        scope = scope,
                        snackbarHostState = snackbarHostState,
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
    imageUri: Uri?,
    character: CharacterEntry,
    updateImageId: (Uri?) -> Unit,
    modifier: Modifier = Modifier
) {
    var setBuffer by remember { mutableStateOf(false) }
    val mediaStoreUtil = koinInject<MediaStoreUtil>()

    val pickMedia = rememberLauncherForActivityResult(
        contract = PickVisualMedia(),
        onResult = { uri: Uri? ->
            Timber.d("-- Launching Photo Picker --")
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
        Modifier.fillMaxWidth()
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
                modifier = modifier.fillMaxWidth()
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
    character: CharacterEntry,
    navigateBack: () -> Unit
) {
    var setBuffer by remember { mutableStateOf(false) }

    Button(
        onClick = {
            addCharacterViewModel.updateCharacterUiState(character)
            scope.launch { addCharacterViewModel.updateCustomGameList(character.game) }
            scope.launch {
                var result = addCharacterViewModel.saveCharacter(false)
                setBuffer = true
                when (result) {
                    "Success" -> {
                        snackbarHostState.showSnackbar(
                            message = "Successfully added character to database.",
                            duration = SnackbarDuration.Short,
                            withDismissAction = true
                        )
                        setBuffer = false
                        navigateBack()
                    }
                    "Character Exists" -> {
                        snackbarHostState.showSnackbar(
                            message = "Character exists, would you like to update existing data?",
                            duration = SnackbarDuration.Indefinite,
                            withDismissAction = true,
                            actionLabel =  "Update"
                        ).run {
                            result = addCharacterViewModel.saveCharacter(true)
                            when (result) {
                                "Success" -> {
                                    snackbarHostState.showSnackbar(
                                        message = "Successfully updated character in database.",
                                        duration = SnackbarDuration.Short,
                                        withDismissAction = true
                                    )
                                    setBuffer = false
                                    navigateBack()
                                }
                                "Character Immutable" -> {
                                    snackbarHostState.showSnackbar(
                                        message = "Cannot edit this character.",
                                        duration = SnackbarDuration.Short,
                                        withDismissAction = true
                                    )
                                }
                                else -> {
                                    snackbarHostState.showSnackbar(
                                        message = "Error entering character into database",
                                        duration = SnackbarDuration.Short,
                                        withDismissAction = true
                                    )
                                    setBuffer = false
                                } } } }
                    else -> {
                        snackbarHostState.showSnackbar(
                            message = result,
                            duration = SnackbarDuration.Short,
                            withDismissAction = true
                        )
                        setBuffer = false
                    } } } },
        enabled = character.game.isNotEmpty() ||
                character.name.isNotEmpty() ||
                !character.controlType.isNullOrBlank() ||
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