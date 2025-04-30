package com.example.fightingflow

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import com.example.fightingflow.ui.InitViewModel
import com.example.fightingflow.ui.NavGraph
import com.example.fightingflow.ui.theme.FightingFlowTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import timber.log.Timber

const val TAG = "Main Activity"

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FightingFlowTheme {
                KoinAndroidContext {
                    val deviceType = calculateWindowSizeClass(this)
                    FightingFlowApp(deviceType = deviceType)
                }
            }
        }
    }
}

@Composable
fun FightingFlowApp(
    initViewModel: InitViewModel = koinViewModel<InitViewModel>(),
    deviceType: WindowSizeClass
) {
    Timber.d("Starting Application")
    initViewModel.addDataToDb()
    NavGraph(deviceType = deviceType)
}