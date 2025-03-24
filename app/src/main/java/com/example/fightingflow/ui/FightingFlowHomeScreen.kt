package com.example.fightingflow.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fightingflow.R
import com.example.fightingflow.ui.characterScreen.CharacterScreen
import com.example.fightingflow.ui.comboScreen.AddComboScreen
import com.example.fightingflow.ui.comboScreen.ComboScreen
import com.example.fightingflow.ui.comboScreen.ComboViewModel
import com.example.fightingflow.ui.userInputForms.InputViewModel
import com.example.fightingflow.ui.userInputForms.SignupScreen
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
    val inputViewModel = koinInject<InputViewModel>()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FlowScreen.Start.name,
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.Black)
        ) {
            composable(route = FlowScreen.Start.name) {
                TitleScreen(
                    onLogin = { navController.navigate(FlowScreen.PickChar.name) },
                    onSignUp = { navController.navigate(FlowScreen.Signup.name) },
                    deviceType = deviceType
                )
            }
            composable(route = FlowScreen.Signup.name) {
                SignupScreen(
                    navigateBack = {navController.navigateUp()}
                )
            }
            composable(route = FlowScreen.PickChar.name) {
                CharacterScreen(
                    comboViewModel = comboViewModel,
                    onClick = { navController.navigate(FlowScreen.Combos.name) },
                )
            }
            composable(route = FlowScreen.Combos.name) {
                ComboScreen(
                    comboViewModel = comboViewModel,
                    deviceType = deviceType,
                    onAddCombo = { navController.navigate(FlowScreen.AddCombo.name) },
                    navigateBack = { navController.navigateUp() }
                )
            }
            composable(route = FlowScreen.AddCombo.name) {
                AddComboScreen(
                    comboViewModel = comboViewModel,
                    onConfirm = { navController.navigate(FlowScreen.Combos.name) },
                    navigateBack = { navController.navigateUp() }
                )
            }
        }
    }
}