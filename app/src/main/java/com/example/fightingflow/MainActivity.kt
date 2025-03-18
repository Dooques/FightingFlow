package com.example.fightingflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fightingflow.data.database.initData.InitViewModel
import com.example.fightingflow.ui.FightingFlowHomeScreen
import com.example.fightingflow.ui.theme.FightingFlowTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FightingFlowTheme {
                KoinAndroidContext {
                    FightingFlowApp()
                }
            }
        }
    }
}

@Composable
fun FightingFlowApp(
    initViewModel: InitViewModel = koinViewModel<InitViewModel>(),
    modifier: Modifier = Modifier
) {
    initViewModel.addDataToDb()
    FightingFlowHomeScreen()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FightingFlowTheme {
    }
}