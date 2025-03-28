package com.example.fightingflow.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fightingflow.R
import com.example.fightingflow.di.viewModelModule
import com.example.fightingflow.ui.characterScreen.CharacterScreen
import com.example.fightingflow.ui.comboAddScreen.AddComboScreen
import com.example.fightingflow.ui.comboAddScreen.AddComboViewModel
import com.example.fightingflow.ui.comboViewScreen.ComboScreen
import com.example.fightingflow.ui.comboViewScreen.ComboViewModel
import com.example.fightingflow.ui.userInputForms.InputViewModel
import com.example.fightingflow.ui.userInputForms.SignupScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


enum class FlowScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Signup(title = R.string.sign_up),
    Menu(title = R.string.menu),
    PickChar(title = R.string.char_select),
    Combos(title = R.string.combos),
    AddCombo(title = R.string.add_combo)
}

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun FightingFlowHomeScreen(
    navController: NavHostController = rememberNavController(),
    deviceType: WindowSizeClass
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        FlowScreen.valueOf(backStackEntry?.destination?.route ?: FlowScreen.Start.name)

    val comboViewModel = koinInject<ComboViewModel>()
    val addComboViewModel = koinInject<AddComboViewModel>()
    val scope = rememberCoroutineScope()

    val character by comboViewModel.characterState.collectAsState()
    val allCombos by comboViewModel.allCombos.collectAsState()
    val allComboEntries by comboViewModel.comboEntries.collectAsState()
    val allMoves by comboViewModel.allMoves.collectAsState()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FlowScreen.Start.name,
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.Black)
        ) {
            // Title Screen
            composable(route = FlowScreen.Start.name) {
                TitleScreen(
                    onLogin = { navController.navigate(FlowScreen.PickChar.name) },
                    onSignUp = { navController.navigate(FlowScreen.Signup.name) },
                    deviceType = deviceType
                )
            }
            // Sign Up / Log In Screen
            composable(route = FlowScreen.Signup.name) {
                SignupScreen(
                    navigateBack = navController::navigateUp
                )
            }
            // Character Screen
            composable(route = FlowScreen.PickChar.name) {
                CharacterScreen(
                    comboViewModel = comboViewModel,
                    updateCharacterState = comboViewModel::getCharacterEntry,
                    onClick = { navController.navigate(FlowScreen.Combos.name) },
                )
            }
            // Combo Screen
            composable(route = FlowScreen.Combos.name) {
                ComboScreen(
                    comboViewModel = comboViewModel,
                    deviceType = deviceType,
                    onAddCombo = {
                        addComboViewModel.characterState.value = comboViewModel.characterState.value
                        addComboViewModel.allMoves.value = comboViewModel.allMoves.value
                        addComboViewModel.editing.value = false
                        navController.navigate(FlowScreen.AddCombo.name)
                    },
                    onEditCombo = {
                        addComboViewModel.comboState.value = comboViewModel.comboState.value
                        addComboViewModel.characterState.value = comboViewModel.characterState.value
                        addComboViewModel.allMoves.value = comboViewModel.allMoves.value
                        addComboViewModel.existingCombos.value = comboViewModel.comboEntries.value
                        addComboViewModel.editing.value = true
                        navController.navigate(FlowScreen.AddCombo.name)
                    },
                    navigateBack = navController::navigateUp,
                    character = character,
                    comboDisplayList = allCombos,
                    comboEntryList = allComboEntries,
                    allMoves = allMoves,
                )
            }
            // Add Combo Screen
            composable(route = FlowScreen.AddCombo.name) {
                AddComboScreen(
                    addComboViewModel = addComboViewModel,
                    onConfirm = { navController.navigate(FlowScreen.Combos.name) },
                    navigateBack = navController::navigateUp
                )
            }
        }
    }
}