package com.example.fightingflow.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fightingflow.R
import com.example.fightingflow.ui.characterScreen.CharacterScreen
import com.example.fightingflow.ui.comboCreationScreen.ComboCreationScreen
import com.example.fightingflow.ui.comboCreationScreen.ComboCreationViewModel
import com.example.fightingflow.ui.comboDisplayScreen.ComboDisplayScreen
import com.example.fightingflow.ui.comboDisplayScreen.ComboDisplayViewModel
import com.example.fightingflow.ui.profileScreen.ProfileList
import com.example.fightingflow.ui.profileScreen.ProfileViewModel
import com.example.fightingflow.ui.profileScreen.TitleScreen
import org.koin.compose.koinInject
import timber.log.Timber


enum class FlowScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    ProfileList(title = R.string.profile_select),
    CharSelect(title = R.string.char_select),
    Combos(title = R.string.combos),
    ComboCreation(title = R.string.combo_creation)
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    deviceType: WindowSizeClass
) {
    Timber.d("Initializing NavController")
    val backStackEntry by navController.currentBackStackEntryAsState()
//    val currentScreen = FlowScreen.valueOf(value = backStackEntry?.destination?.route ?: FlowScreen.Start.name)

    Timber.d("Initializing ViewModels")
    val comboDisplayViewModel = koinInject<ComboDisplayViewModel>()
    val comboCreationViewModel = koinInject<ComboCreationViewModel>()
    val profileViewModel = koinInject<ProfileViewModel>()

    // ProfileViewModel Flows
    val loggedInState by profileViewModel.loggedInState.collectAsStateWithLifecycle()
    val username by profileViewModel.username.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        Timber.d("Loading NavHost...")
        NavHost(
            navController = navController,
            startDestination = FlowScreen.Start.name,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Timber.d("Getting Composable Routes...")
            // Title Screen
            composable(route = FlowScreen.Start.name) {
                Timber.d("Loading Title Screen...")
                TitleScreen(
                    profileViewModel = profileViewModel,
                    snackbarHostState = snackBarHostState,
                    deviceType = deviceType,
                    username = username,
                    isLoggedIn = loggedInState,
                    onCharSelect = { navController.navigate(FlowScreen.CharSelect.name) },
                    onProfileSelect = { navController.navigate(FlowScreen.ProfileList.name) },
                )
            }

            // Profiles
            composable(route = FlowScreen.ProfileList.name) {
                Timber.d("Loading Profile List Screen")
                ProfileList(
                    profileViewModel = profileViewModel,
                    username = username,
                    loggedInState = loggedInState,
                    snackbarHostState = snackBarHostState,
                    scope = scope,
                    navigateBack =  navController::navigateUp,
                )
            }

            // Character Screen
            composable(route = FlowScreen.CharSelect.name) {
                Timber.d("Loading Character Screen")
                CharacterScreen(
                    comboDisplayViewModel = comboDisplayViewModel,
                    onClick = { navController.navigate(FlowScreen.Combos.name) },
                    navigateBack = { navController.navigate(FlowScreen.Start.name)},
                    navigateToProfiles = { navController.navigate(FlowScreen.ProfileList.name) }
                )
            }

            // Combo Display Screen
            composable(route = FlowScreen.Combos.name) {
                Timber.d("Loading Combo Display Screen")
                ComboDisplayScreen(
                    deviceType = deviceType,
                    comboDisplayViewModel = comboDisplayViewModel,
                    snackbarHostState = snackBarHostState,
                    updateCharacterState = comboDisplayViewModel::updateCharacterState,
                    onNavigateToComboEditor = { navController.navigate(FlowScreen.ComboCreation.name) },
                    navigateBack = { navController.navigate(FlowScreen.CharSelect.name) }
                )
            }

            // Combo Creation Screen
            composable(route = FlowScreen.ComboCreation.name) {
                Timber.d("Loading Combo Creation Screen")
                ComboCreationScreen(
                    comboDisplayViewModel = comboDisplayViewModel,
                    comboCreationViewModel = comboCreationViewModel,
                    scope = scope,
                    snackbarHostState = snackBarHostState,
                    onNavigateToComboDisplay = { navController.navigate(FlowScreen.Combos.name) },
                    navigateBack = navController::navigateUp,
                    navigateHome = { navController.navigate(FlowScreen.CharSelect.name) }
                )
            }
        }
    }
}