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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.fightingflow.ui.InitViewModel
import com.example.fightingflow.ui.NavGraph
import com.example.fightingflow.ui.theme.FightingFlowTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import timber.log.Timber
import timber.log.Timber.DebugTree

const val TAG = "Main Activity"

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FightingFlowTheme {
                KoinAndroidContext {
                    Timber.d("Starting Application")
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
    val scope = rememberCoroutineScope()
    val databaseExists = remember { mutableStateOf(false) }

    Timber.d("Checking data in database...")
    LaunchedEffect(scope) {
        if (initViewModel.addDataToDb()) { databaseExists.value = true}
    }
    if (databaseExists.value) {
        NavGraph(deviceType = deviceType)
    }
}