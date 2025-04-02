package com.example.fightingflow

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.fightingflow.data.database.initData.InitViewModel
import com.example.fightingflow.ui.FightingFlowHomeScreen
import com.example.fightingflow.ui.theme.FightingFlowTheme
import com.example.fightingflow.util.emptyCharacter
import com.example.fightingflow.util.emptyMove
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

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
    Log.d(TAG, "Starting Application")
    initViewModel.addDataToDb()
    FightingFlowHomeScreen(deviceType = deviceType)
}