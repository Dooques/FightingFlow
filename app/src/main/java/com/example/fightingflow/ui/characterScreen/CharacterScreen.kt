package com.example.fightingflow.ui.characterScreen

import android.content.Context
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fightingflow.R
import com.example.fightingflow.data.DataSource.characterData
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.CharacterModel
import com.example.fightingflow.ui.comboScreen.ComboViewModel
import com.example.fightingflow.ui.theme.FightingFlowTheme

@Composable
fun CharacterScreen(
    comboViewModel: ComboViewModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val characterList = comboViewModel.allCharacters.collectAsState()
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        columns = GridCells.Fixed(3),
        modifier = modifier
            .background(Color.Black)
            .padding(16.dp)
    ) {
        items(items = characterList.value.sortedBy { it.name }) { character ->
            CharacterCard(
                onClick = onClick,
                character = character,
                comboViewModel = comboViewModel,
                modifier = modifier
            )
        }
    }
}

@Composable
fun CharacterCard(
    onClick: () -> Unit,
    comboViewModel: ComboViewModel,
    character: CharacterEntry,
    modifier: Modifier = Modifier
) {
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
                    comboViewModel.getCharacterEntry(character.name)
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