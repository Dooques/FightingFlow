package com.example.fightingflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fightingflow.ui.FightingFlowHomeScreen
import com.example.fightingflow.ui.theme.FightingFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FightingFlowTheme {
                FightingFlowApp()
            }
        }
    }
}

@Composable
fun FightingFlowApp(modifier: Modifier = Modifier) {
    FightingFlowHomeScreen()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FightingFlowTheme {
    }
}