package com.example.fightingflow.ui.characterScreen

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.ui.comboViewScreen.ComboViewModel
import kotlinx.coroutines.launch

@Composable
fun CharacterScreen(
    comboViewModel: ComboViewModel,
    updateCharacterState: (String) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val characterList by comboViewModel.allCharacters.collectAsState()
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        columns = GridCells.Fixed(3),
        modifier = modifier
            .background(Color.Black)
            .padding(16.dp)
    ) {
        items(items = characterList.sortedBy { it.name }) { character ->
            CharacterCard(
                updateCharacterState = updateCharacterState,
                onClick = onClick,
                character = character,
                modifier = modifier
            )
        }
    }
}

@Composable
fun CharacterCard(
    updateCharacterState: (String) -> Unit,
    onClick: () -> Unit,
    character: CharacterEntry,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    Card(
        colors = CardDefaults.cardColors(
            containerColor = when (character.name) {
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
                    updateCharacterState(character.name)
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
                painter = painterResource(character.imageId),
                contentDescription = character.name,
                contentScale = ContentScale.Fit,
                modifier = modifier.size(100.dp)
            )
            Text(
                text = character.name,
                style = MaterialTheme.typography.labelLarge,
                color = when (character.name) {
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