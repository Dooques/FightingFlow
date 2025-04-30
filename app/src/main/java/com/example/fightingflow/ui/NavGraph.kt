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
import com.example.fightingflow.ui.comboAddScreen.ComboCreationScreen
import com.example.fightingflow.ui.comboAddScreen.ComboCreationViewModel
import com.example.fightingflow.ui.comboScreen.ComboDisplayScreen
import com.example.fightingflow.ui.comboScreen.ComboDisplayViewModel
import com.example.fightingflow.ui.profileScreen.ProfileList
import com.example.fightingflow.ui.profileScreen.ProfileViewModel
import kotlinx.coroutines.flow.update
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
    val comboViewModel = koinInject<ComboDisplayViewModel>()
    val addComboViewModel = koinInject<ComboCreationViewModel>()
    val profileViewModel = koinInject<ProfileViewModel>()

    // ComboViewModel Flows
    val characterState by comboViewModel.characterState.collectAsStateWithLifecycle()
    val comboEntryListState by comboViewModel.comboEntryListSate.collectAsStateWithLifecycle()

    // AddComboViewModel Flows
    val comboStateAddCombo by addComboViewModel.comboDisplayState.collectAsStateWithLifecycle()
    val comboEntryListStateAddCombo by addComboViewModel.comboEntryListState.collectAsStateWithLifecycle()

    // ProfileViewModel Flows
    val loggedInState by profileViewModel.loggedInState.collectAsStateWithLifecycle()
    val username by profileViewModel.username.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Timber.d(
        "Flows collected" +

                "\nComboViewModel Flows" +
                "\nCharacter: ${characterState.character}" +
                "\nCombo Entry List: ${comboEntryListState.comboEntryList}" +
                "\nCombo Display: ${comboStateAddCombo.comboDisplay}" +

                "\n\nAddComboViewModel Flows" +
                "\nComboStateAddCombo: ${comboStateAddCombo.comboDisplay}" +
                "\ncomboEntryListStateAddCombo: ${comboEntryListStateAddCombo.comboEntryList}" +

                "\n\nProfileViewModel Flows" +
                "\nIsUserLoggedIn: $loggedInState" +
                "\nUsername: $username"
    )

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
                    comboViewModel = comboViewModel,
                    updateCharacterState = comboViewModel::updateCharacterState,
                    setCharacterToDS = comboViewModel::setCharacterToDS,
                    onClick = { navController.navigate(FlowScreen.Combos.name) },
                    navigateBack = { navController.navigate(FlowScreen.Start.name)}
                )
            }

            // Combo Screen
            composable(route = FlowScreen.Combos.name) {
                ComboDisplayScreen(
                    deviceType = deviceType,
                    comboViewModel = comboViewModel,
                    updateCharacterState = comboViewModel::updateCharacterState,
                    getMoveEntryData = comboViewModel::getMoveEntryData,
                    onAddCombo = {
                        Timber.d("")
                        Timber.d("Preparing to create new combo...")
                        Timber.d("Setting edit state to false...")
                        addComboViewModel.editingState.value = false
                        Timber.d("Moving to AddComboScreen")
                        navController.navigate(FlowScreen.AddCombo.name)
                    },
                    onEditCombo = {
                        Timber.d("")
                        Timber.d("Preparing to edit selected combo")
                        Timber.d("Saving selected combo to AddComboViewModel...")
                        addComboViewModel.comboDisplayState.update { it }
                        Timber.d("AddComboViewModel Combo state: ${comboStateAddCombo.comboDisplay}")
//
//                        Log.d(NAV_TAG, "Updating character state of AddComboViewModel")
//                        addComboViewModel.characterState.update { characterState }
//
                        Timber.d("Updating Combo List of AddComboViewModel")
                        addComboViewModel.comboEntryListState.update { comboEntryListState }
                        Timber.d("Updated Combo List: ${comboEntryListStateAddCombo.comboEntryList}")
                        addComboViewModel.editingState.value = true
                        navController.navigate(FlowScreen.AddCombo.name)
                    },
                    navigateBack = {navController.navigate(FlowScreen.PickChar.name) }
                )
            }

            // Add Combo Screen
            composable(route = FlowScreen.AddCombo.name) {
                ComboCreationScreen(
                    addComboViewModel = addComboViewModel,
                    comboViewModel = comboViewModel,
                    saveComboDetailsToDs = addComboViewModel::saveComboDetailsToDs,
                    updateComboData = addComboViewModel::updateComboDetails,
                    updateMoveList = addComboViewModel::updateMoveList,
                    saveCombo = addComboViewModel::saveCombo,
                    deleteLastMove = addComboViewModel::deleteLastMove,
                    clearMoveList = addComboViewModel::clearMoveList,
                    onConfirm = { navController.navigate(FlowScreen.Combos.name) },
                    navigateBack = navController::navigateUp
                )
            }
        }
    }
}