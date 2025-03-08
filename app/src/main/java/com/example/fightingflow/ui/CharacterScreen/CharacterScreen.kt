package com.example.fightingflow.ui.CharacterScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fightingflow.R
import com.example.fightingflow.data.DataSource.characterData
import com.example.fightingflow.model.CharacterModel
import com.example.fightingflow.ui.theme.FightingFlowTheme

@Composable
fun CharacterScreen(
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        columns = GridCells.Fixed(3),
        modifier = modifier
            .background(Color.Black)
            .padding(16.dp)
    ) {
        items(items = characterData) {
            CharacterCard(
                it,
                modifier = modifier
            )
        }
    }
}

@Composable
fun CharacterCard(
    character: CharacterModel,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = when (character.name) {
                R.string.heihachi -> Color.DarkGray
                R.string.lidia -> Color.DarkGray
                R.string.eddy -> Color.DarkGray
                else -> Color.White
            }
        ),
        modifier = modifier

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(character.imageId),
                contentDescription = stringResource(R.string.kazuya),
                contentScale = ContentScale.Fit,
                modifier = modifier.size(100.dp)
            )
            Text(
                text = stringResource(character.name),
                style = MaterialTheme.typography.labelLarge,
                color = when (character.name) {
                    R.string.heihachi -> Color.White
                    R.string.lidia -> Color.White
                    R.string.eddy -> Color.White
                    else -> Color.Black
                }
            )
        }
    }
}

@Preview
@Composable
fun CharacterPreview() {
    FightingFlowTheme {
        CharacterScreen()
    }
}