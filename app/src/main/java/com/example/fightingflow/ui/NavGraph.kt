package com.example.fightingflow.ui

import android.annotation.SuppressLint
import android.util.Log
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
import com.example.fightingflow.ui.comboAddScreen.AddComboScreen
import com.example.fightingflow.ui.comboAddScreen.AddComboViewModel
import com.example.fightingflow.ui.comboScreen.ComboScreen
import com.example.fightingflow.ui.comboScreen.ComboViewModel
import com.example.fightingflow.ui.comboScreen.TAG
import com.example.fightingflow.ui.profileScreen.ProfileScreen
import com.example.fightingflow.ui.profileScreen.ProfileViewModel
import kotlinx.coroutines.flow.update
import org.koin.compose.koinInject

const val TAG = "Navigation Map"

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
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    deviceType: WindowSizeClass
) {
    Log.d(TAG, "")
    Log.d(TAG, "Initializing NavController")
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = FlowScreen.valueOf(backStackEntry?.destination?.route ?: FlowScreen.Start.name)

    Log.d(TAG, "Initializing ViewModels")
    val comboViewModel = koinInject<ComboViewModel>()
    val addComboViewModel = koinInject<AddComboViewModel>()
    val profileViewModel = koinInject<ProfileViewModel>()

    // ComboViewModel Collection
    val characterState by comboViewModel.characterState.collectAsState()
    val comboEntryListState by comboViewModel.comboEntryListSate.collectAsState()
    val comboState by comboViewModel.comboDisplayState.collectAsState()

    // AddComboViewModel Collection
    val comboStateAddCombo by addComboViewModel.comboState.collectAsState()
    val comboEntryListStateAddCombo by addComboViewModel.comboEntryListState.collectAsState()

    // UserViewModel collection
    val isUserLoggedIn by profileViewModel.loggedInState.collectAsState()
    val profile by profileViewModel.profileState.collectAsState()

    Log.d(TAG, "Flows collected" +
            "\nCharacter: ${characterState.character}" +
            "\n\nCombo Entry List: ${comboEntryListState.comboEntryList}" +
            "\n\nCombo Display: ${comboStateAddCombo.comboDisplay}"
    )

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
                ProfileScreen(
                    profileViewModel = profileViewModel,
                    navigateBack = navController::navigateUp,
                    updateCurrentUser = profileViewModel::updateProfileCreation,
                )
            }

            // Character Screen
            composable(route = FlowScreen.PickChar.name) {
                CharacterScreen(
                    comboViewModel = comboViewModel,
                    updateCharacterState = comboViewModel::updateCharacterState,
                    setCharacterToDS = comboViewModel::setCharacterToDS,
                    onClick = { navController.navigate(FlowScreen.Combos.name) },
                )
            }

            // Combo Screen
            composable(route = FlowScreen.Combos.name) {
                ComboScreen(
                    deviceType = deviceType,
                    comboViewModel = comboViewModel,
                    updateCharacterState = comboViewModel::updateCharacterState,
                    getMoveEntryData = comboViewModel::getMoveEntryData,
                    onAddCombo = {
                        Log.d(TAG, "")
                        Log.d(TAG, "Preparing to create new combo...")
                        addComboViewModel.characterState.update { characterState }
                        Log.d(TAG, "$characterState's data added to AddComboViewModel")
                        addComboViewModel.editingState.value = false
                        navController.navigate(FlowScreen.AddCombo.name)
                    },
//                    onEditCombo = {
//                        Log.d(TAG, "")
//                        Log.d(TAG, "Preparing to edit selected combo")
//                        Log.d(TAG, "Saving selected combo to AddComboViewModel...")
//                        addComboViewModel.comboState.update { it }
//                        Log.d(TAG, "AddComboViewModel Combo state: ${comboStateAddCombo.comboDisplay}")
//
//                        Log.d(TAG, "Updating character state of AddComboViewModel")
//                        addComboViewModel.characterState.update { characterState }
//
//                        Log.d(TAG, "Updating Combo List of AddComboViewModel")
//                        addComboViewModel.comboEntryListState.update { comboEntryListState }
//                        Log.d(TAG, "Updated Combo List: ${comboEntryListStateAddCombo.comboEntryList}")
//                        addComboViewModel.editingState.value = true
//                        navController.navigate(FlowScreen.AddCombo.name)
//                    },
                    navigateBack = {navController.navigate(FlowScreen.PickChar.name) }
                )
            }

            // Add Combo Screen
            composable(route = FlowScreen.AddCombo.name) {
                AddComboScreen(
                    addComboViewModel = addComboViewModel,
                    comboViewModel = comboViewModel,
                    updateCharacterState = comboViewModel::updateCharacterState,
                    saveComboDetailsToDs = addComboViewModel::saveComboDetailsToDs,
                    getComboDetailsFromDs = addComboViewModel::getComboDetailsFromDs,
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