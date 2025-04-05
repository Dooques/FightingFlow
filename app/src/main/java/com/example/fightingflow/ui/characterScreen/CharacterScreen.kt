package com.example.fightingflow.ui.characterScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.ui.comboScreen.ComboViewModel
import com.example.fightingflow.util.CharacterUiState

const val TAG = "CharacterScreen"

@Composable
fun CharacterScreen(
    comboViewModel: ComboViewModel,
    updateCharacterState: (String) -> Unit,
    setCharacterToDS: (CharacterEntry) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d(TAG, "")
    Log.d(TAG,"\nLoading Character Screen")

    val characterListState by comboViewModel.characterEntryListState.collectAsState()
    Log.d(TAG, "Flows Collected")
    Log.d(TAG, "Character List: ${characterListState.characterList}")

    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        columns = GridCells.Fixed(3),
        modifier = modifier
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Log.d(TAG, "Loading Character Grid...")
        items(items = characterListState.characterList.sortedBy { it.name }) { character ->
            CharacterCard(
                updateCharacterState = updateCharacterState,
                setCharacterToDS = setCharacterToDS,
                onClick = onClick,
                characterState = CharacterUiState(character),
                modifier = modifier
            )
        }
        Log.d(TAG, "Character Grid Finished.")
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
    Log.d(TAG, "Loading Card: ${characterState.character.name}")
    Card(
        colors = CardDefaults.cardColors(
            containerColor = when (characterState.character.name) {
                "Heihachi" -> Color.DarkGray
                "Lidia" -> Color.DarkGray
                "Eddy" -> Color.DarkGray
                "Clive" -> Color.DarkGray
                else -> Color.White
            }
        ),
        modifier = modifier
            .clickable(
                onClick = {
                    Log.d(TAG, "")
                    Log.d(TAG, "Preparing to open Combo Screen...")
                    updateCharacterState(characterState.character.name)
                    Log.d(TAG, "Updated Character State in Combo View Model")
                    Log.d(TAG, "Preparing to add ${characterState.character.name} to datastore")
                    setCharacterToDS(characterState.character)
                    Log.d(TAG, "Opening Combo Screen")
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
                color = when (characterState.character.name) {
                    "Heihachi" -> Color.White
                    "Lidia" -> Color.White
                    "Eddy" -> Color.White
                    "Clive" -> Color.White
                    else -> Color.Black
                }
            )
        }
    }
}