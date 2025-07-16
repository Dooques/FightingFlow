package com.example.fightingflow.ui.characterScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fightingflow.R

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {
    val gradientColors = listOf(Color.Transparent, MaterialTheme.colorScheme.background)
    NavigationBar(
        containerColor = Color.Transparent,
        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 20.dp),
        modifier = modifier.background(brush = Brush.verticalGradient(gradientColors))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(10.dp),
                        ambientColor = Color.Gray
                    )
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Combo Home Page",
                        modifier = modifier.size(50.dp)
                    )
                }
            }
            Box(
                modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .shadow(elevation = 10.dp, ambientColor = Color.White)
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Combo Search",
                        modifier = modifier.size(50.dp)
                    )
                }
            }
            Box(
                modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .shadow(elevation = 5.dp, ambientColor = Color.Gray)
            ) {
                IconButton(onClick = {}) {
                    Image(
                        painter = painterResource(R.drawable.ff_character_screen_icon),
                        contentDescription = "Characters",
                        modifier = modifier.size(50.dp)
                    )
                }
            }
        }
    }
}