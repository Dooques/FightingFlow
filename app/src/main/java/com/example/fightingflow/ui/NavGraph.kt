package com.example.fightingflow.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber


enum class FlowScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    ProfileList(title = R.string.profile_select),
    CreateProfile(title = R.string.create_profile),
    ProfileDetails(title = R.string.profile_details),
    Menu(title = R.string.menu),
    PickChar(title = R.string.char_select),
    Combos(title = R.string.combos),
    AddCombo(title = R.string.add_combo)
}

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    deviceType: WindowSizeClass
) {
    Timber.d("")
    Timber.d("Initializing NavController")
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = FlowScreen.valueOf(backStackEntry?.destination?.route ?: FlowScreen.Start.name)

    Timber.d("Initializing ViewModels")
    val comboDisplayViewModel = koinInject<ComboDisplayViewModel>()
    val comboCreationViewModel = koinInject<ComboCreationViewModel>()
    val profileViewModel = koinInject<ProfileViewModel>()

    // ComboDisplayViewModel Flows
    val comboEntryListState by comboDisplayViewModel.comboEntryListState.collectAsStateWithLifecycle()

    // Combo Creation Flows
    val comboStateAddCombo by comboCreationViewModel.comboDisplayState.collectAsStateWithLifecycle()
    val comboEntryListStateAddCombo by comboCreationViewModel.comboEntryListState.collectAsStateWithLifecycle()

    // ProfileViewModel Flows
    val loggedInState by profileViewModel.loggedInState.collectAsStateWithLifecycle()
    val username by profileViewModel.username.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

//    Timber.d("")
//    Timber.d("Flows collected")
//    Timber.d("ComboViewModel Flows")
//    Timber.d("Character: ${characterState.character}")
//    Timber.d("Combo Entry List: ${comboEntryListState.comboEntryList}")
//    Timber.d("Character Entry List: ${characterEntryList.characterList}")
//    Timber.d("")
//    Timber.d("AddComboViewModel Flows")
//    Timber.d("ComboStateAddCombo: ${comboStateAddCombo.comboDisplay}")
//    Timber.d("comboEntryListStateAddCombo: ${comboEntryListStateAddCombo.comboEntryList}")
//    Timber.d("")
//    Timber.d("ProfileViewModel Flows")
//    Timber.d("IsUserLoggedIn: $loggedInState")
//    Timber.d("Username: $username")

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        Timber.d("")
        Timber.d("Loading NavHost...")
        NavHost(
            navController = navController,
            startDestination = FlowScreen.Start.name,
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.Black)
        ) {
            Timber.d("Getting Composable Routes...")
            // Title Screen
            composable(route = FlowScreen.Start.name) {
                Timber.d("")
                Timber.d("Loading Title Screen...")
                TitleScreen(
                    profileViewModel = profileViewModel,
                    snackbarHostState = snackBarHostState,
                    deviceType = deviceType,
                    username = username,
                    isLoggedIn = loggedInState,
                    onCharSelect = { navController.navigate(FlowScreen.PickChar.name) },
                    onProfileSelect = { navController.navigate(FlowScreen.ProfileList.name) },
                )
            }

            // Profiles
            composable(route = FlowScreen.ProfileList.name) {
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
            composable(route = FlowScreen.PickChar.name) {
                CharacterScreen(
                    comboViewModel = comboDisplayViewModel,
                    updateCharacterState = comboDisplayViewModel::updateCharacterState,
                    setCharacterToDS = comboDisplayViewModel::updateCharacterInDS,
                    onClick = { navController.navigate(FlowScreen.Combos.name) },
                    navigateBack = { navController.navigate(FlowScreen.Start.name)}
                )
            }

            // Combo Display Screen
            composable(route = FlowScreen.Combos.name) {
                ComboDisplayScreen(
                    deviceType = deviceType,
                    comboViewModel = comboDisplayViewModel,
                    snackbarHostState = snackBarHostState,
                    updateCharacterState = comboDisplayViewModel::updateCharacterState,
                    getMoveEntryData = comboDisplayViewModel::getMoveEntryData,
                    onAddCombo = {
                        Timber.d("")
                        Timber.d("Preparing to create new combo...")
                        Timber.d("Setting edit state to false...")
                        comboCreationViewModel.editingState.value = false
                        Timber.d("Moving to AddComboScreen")
                        navController.navigate(FlowScreen.AddCombo.name)
                    },
                    onEditCombo = {
                        scope.launch {
                            Timber.d("")
                            Timber.d("Preparing to edit selected combo")
                            Timber.d("Saving selected combo to AddComboViewModel...")
                            comboCreationViewModel.comboDisplayState.update { it }
                            Timber.d("AddComboViewModel Combo state: ${comboStateAddCombo.comboDisplay}")
                            Timber.d("Updated Combo List: ${comboEntryListStateAddCombo.comboEntryList}")
                            comboCreationViewModel.editingState.value = true
                            navController.navigate(FlowScreen.AddCombo.name)
                        }
                    },
                    navigateBack = {navController.navigate(FlowScreen.PickChar.name) }
                )
            }

            // Add Combo Screen
            composable(route = FlowScreen.AddCombo.name) {
                ComboCreationScreen(
                    comboCreationViewModel = comboCreationViewModel,
                    comboDisplayViewModel = comboDisplayViewModel,
                    saveComboDetailsToDs = comboCreationViewModel::saveComboDetailsToDs,
                    updateComboData = comboCreationViewModel::updateComboDetails,
                    updateMoveList = comboCreationViewModel::updateMoveList,
                    saveCombo = comboCreationViewModel::saveCombo,
                    deleteLastMove = comboCreationViewModel::deleteLastMove,
                    clearMoveList = comboCreationViewModel::clearMoveList,
                    onConfirm = { navController.navigate(FlowScreen.Combos.name) },
                    navigateBack = navController::navigateUp
                )
            }
        }
    }
}