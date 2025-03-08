package com.example.fightingflow.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fightingflow.FightingFlowApp
import com.example.fightingflow.R
import com.example.fightingflow.ui.CharacterScreen.CharacterScreen
import com.example.fightingflow.ui.UserInputForms.SignUpScreen
import com.example.fightingflow.ui.theme.FightingFlowTheme


enum class FlowScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Signup(title = R.string.sign_up),
    Menu(title = R.string.menu),
    PickChar(title = R.string.char_select),
    Combos(title = R.string.combos)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlowAppBar(
    currentScreen: FlowScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Color.White
                )
            }
                },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.DarkGray
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        }
    )
}

@Composable
fun FightingFlowHomeScreen(
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = FlowScreen.valueOf(
        backStackEntry?.destination?.route ?: FlowScreen.Start.name
    )

    Scaffold(
        topBar = {
            if (currentScreen != FlowScreen.Start) {
                FlowAppBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            }
        }
    ) {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FlowScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = FlowScreen.Start.name) {
                TitleScreen(
                    onLogin = {navController.navigate(FlowScreen.PickChar.name)},
                    onSignUp = {navController.navigate(FlowScreen.Signup.name)}
                )
            }
            composable(route = FlowScreen.PickChar.name) {
                CharacterScreen()
            }
            composable(route = FlowScreen.Signup.name,) {
                SignUpScreen({ navController.navigate(FlowScreen.PickChar.name) })
            }
        }
    }
}

@Preview
@Composable
fun FightingFlowPreview() {
    FightingFlowTheme {
        FightingFlowApp()
    }
}