package com.example.fightingflow.ui.addCharacterScreen

import android.service.controls.Control
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ControlType
import com.example.fightingflow.model.Game
import com.example.fightingflow.util.emptyCharacter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCharacterScreen(
    navigateBack: () -> Unit,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val addCharacterViewModel = koinInject<AddCharacterViewModel>()
    var character by remember { mutableStateOf(CharacterEntry(
        name = "",
        fightingStyle = "",
        imageId = R.drawable.mokujin,
        game = "Custom",
        controlType = "Tekken",
        mutable = true
    )) }

    var gameDropdownExpanded by remember { mutableStateOf(false) }
    var controlTypeDropdownExpanded by remember { mutableStateOf(false) }

    var uniqueMoves by remember { mutableStateOf("") }
    var controlTypeValue by remember { mutableStateOf(ControlType.Tekken) }
    var game by remember { mutableStateOf("") }

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

        var setBuffer by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Enter Character Details",
                style = MaterialTheme.typography.bodyLarge
            )

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
            )

            ExposedDropdownMenuBox(
                expanded = controlTypeDropdownExpanded,
                onExpandedChange = { controlTypeDropdownExpanded = !controlTypeDropdownExpanded },
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

            OutlinedTextField(
                value = uniqueMoves,
                onValueChange = {
                    character = character.copy(uniqueMoves = it)
                    uniqueMoves = it
                                },
                label = { Text("Unique Moves") },
                placeholder = { Text(
                    text = "Write a list of your character's moves, separated by commas.",
                    color = Color.Gray
                ) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    addCharacterViewModel.updateCharacterUiState(character)
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
                                        }
                                    }
                                }
                            }
                            else -> {
                                snackbarHostState.showSnackbar(
                                    message = result,
                                    duration = SnackbarDuration.Short,
                                    withDismissAction = true
                                )
                                setBuffer = false
                            }
                        }
                    } },
                enabled = character.game.isNotEmpty() ||
                character.name.isNotEmpty() ||
                !character.controlType.isNullOrBlank() ||
                !character.uniqueMoves.isNullOrBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if ( setBuffer ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                if (!setBuffer) {
                    Text("Save Character")
                }
            }
        }
    }
}