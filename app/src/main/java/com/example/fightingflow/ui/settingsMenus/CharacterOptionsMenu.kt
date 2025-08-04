package com.example.fightingflow.ui.settingsMenus

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.ui.viewmodels.CharacterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun CharacterOptionsMenu(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    characterScreenViewModel: CharacterViewModel,
    characterOptionsMenuExpanded: Boolean,
    characterEntry: CharacterEntry,
    navigateToAddCharacter: () -> Unit,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        expanded = characterOptionsMenuExpanded,
        onDismissRequest = { onDismiss() }
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.edit_character)) },
            onClick = navigateToAddCharacter
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.delete_character)) },
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Are you sure you want to delete this character?",
                        actionLabel = "Yes",
                        withDismissAction = true,
                        duration = SnackbarDuration.Indefinite
                    ).run {
                        when (this) {
                            SnackbarResult.ActionPerformed -> {
                                scope.launch {
                                    characterScreenViewModel.deleteCustomCharacter(characterEntry)
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "${characterEntry.name} has been deleted."
                                        )
                                    }
                                }
                            }
                            SnackbarResult.Dismissed -> Timber.d("Character deletion cancelled.")
                        }
                    }
                }
                onDismiss()
            }
        )
    }
}
