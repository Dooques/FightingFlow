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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dooques.fightingflow.R
import com.dooques.fightingflow.ui.addCharacterScreen.AddCharacterScreen
import com.dooques.fightingflow.ui.addCharacterScreen.ControlSchemeDemoScreen
import com.dooques.fightingflow.ui.characterScreen.CharacterScreen
import com.dooques.fightingflow.ui.comboCreationScreen.ComboCreationScreen
import com.dooques.fightingflow.ui.comboDisplayScreen.ComboDisplayScreen
import com.dooques.fightingflow.ui.userScreen.TitleScreen
import com.dooques.fightingflow.ui.userScreen.UserDetailsScreen
import com.dooques.fightingflow.ui.viewmodels.AddCharacterViewModel
import com.dooques.fightingflow.ui.viewmodels.AuthViewModel
import com.dooques.fightingflow.ui.viewmodels.CharacterViewModel
import com.dooques.fightingflow.ui.viewmodels.ComboCreationViewModel
import com.dooques.fightingflow.ui.viewmodels.ComboDisplayViewModel
import com.dooques.fightingflow.ui.viewmodels.ProfanityViewModel
import com.dooques.fightingflow.ui.viewmodels.UserViewModel
import org.koin.compose.koinInject
import timber.log.Timber

enum class FlowScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    ProfileList(title = R.string.your_details),
    CharSelect(title = R.string.char_select),
    Combos(title = R.string.combos),
    ComboCreation(title = R.string.combo_creation),
    AddCharacter(title = R.string.add_character),
    ControlSchemeDemo(title = R.string.control_scheme_demo)
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    deviceType: WindowSizeClass
) {
    Timber.d("--Initializing NavController--\n Initializing ViewModels...")
    val comboDisplayViewModel = koinInject<ComboDisplayViewModel>()
    val comboCreationViewModel = koinInject<ComboCreationViewModel>()
    val userViewModel = koinInject<UserViewModel>()
    val characterViewModel = koinInject<CharacterViewModel>()
    val addCharacterViewModel = koinInject<AddCharacterViewModel>()
    val authViewModel = koinInject<AuthViewModel>()
    val profanityViewModel = koinInject<ProfanityViewModel>()

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    val profanityData = profanityViewModel.profanityData
    val signInState by authViewModel.signInState.collectAsStateWithLifecycle()

    LaunchedEffect(profanityData) {
        if (profanityData.isEmpty()) {
            profanityViewModel.readJsonFromAssets(context, "profanityFilter.JSON")
        }
    }

    LaunchedEffect(signInState) {
        userViewModel.updateCurrentUser(signInState)
    }

    Timber.d("SignInState: $signInState")
//    LaunchedEffect(signInState) {
//        Timber.d("Sign In State detected, triggering launched effect")
//        when (signInState) {
//            is GoogleAuthService.SignInState.Success -> {
//                userViewModel.updateUserId((signInState as GoogleAuthService.SignInState.Success).user.userId)
//                userDetailsState = userViewModel.userDetailsState(signInState).value
//
//                Timber.d("Sign in successful, returning userDetails Value and updating user state")
//                Timber.d("User Details State: %s",
//                    when (userDetailsState) {
//                        is UserDetailsState.Loaded -> { (userDetailsState as UserDetailsState.Loaded).user }
//                        is UserDetailsState.Error -> { (userDetailsState as UserDetailsState.Error).e }
//                        else -> { "No user details found" }
//                    })
//            }
//            else -> null
//        }
//    }

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
                    authViewModel = authViewModel,
                    userViewModel = userViewModel,
                    profanityViewModel = profanityViewModel,
                    snackbarHostState = snackBarHostState,
                    deviceType = deviceType,
                    onCharSelect = { navController.navigate(FlowScreen.CharSelect.name) },
                    onProfileSelect = { navController.navigate(FlowScreen.ProfileList.name) },
                )
            }

            // Profiles
            composable(route = FlowScreen.ProfileList.name) {
                Timber.d("Loading Profile List Screen...")
                UserDetailsScreen(
                    scope = scope,
                    snackBarHostState = snackBarHostState,
                    userViewModel = userViewModel,
                    authViewModel = authViewModel,
                    profanityViewModel = profanityViewModel,
                    navigateBack =  navController::navigateUp,
                    navigateHome = { navController.navigate(FlowScreen.Start.name) }
                )
            }

            // Character Screen
            composable(route = FlowScreen.CharSelect.name) {
                Timber.d("Loading Character Screen...")
                CharacterScreen(
                    scope = scope,
                    snackbarHostState = snackBarHostState,
                    comboDisplayViewModel = comboDisplayViewModel,
                    characterScreenViewModel = characterViewModel,
                    addCharacterViewModel = addCharacterViewModel,
                    navigateToComboDisplayScreen = { navController.navigate(FlowScreen.Combos.name) },
                    navigateToProfiles = { navController.navigate(FlowScreen.ProfileList.name) },
                    navigateToAddCharacter = { navController.navigate(FlowScreen.AddCharacter.name) },
                    navigateToHome = { navController.navigate(FlowScreen.Start.name) }
                )
            }

            // Add Character Screen
            composable(route = FlowScreen.AddCharacter.name) {
                Timber.d("Loading Add Character Screen...")
                AddCharacterScreen(
                    addCharacterViewModel = addCharacterViewModel,
                    characterViewModel = characterViewModel,
                    comboDisplayViewModel = comboDisplayViewModel,
                    navigateBack =  navController::navigateUp,
                    scope = scope,
                    snackbarHostState = snackBarHostState,
                    navigateToSchemeInfo = { navController.navigate(FlowScreen.ControlSchemeDemo.name) }
                )
            }

            // Control Scheme Demo
            composable(route = FlowScreen.ControlSchemeDemo.name) {
                Timber.d("-- Loading Control Scheme Demo Screen --")
                ControlSchemeDemoScreen(
                    comboCreationViewModel = comboCreationViewModel,
                    comboDisplayViewModel = comboDisplayViewModel,
                    navigateBack = navController::navigateUp
                )
            }

            // Combo Display Screen
            composable(route = FlowScreen.Combos.name) {
                Timber.d("Loading Combo Display Screen...")
                ComboDisplayScreen(
                    deviceType = deviceType,
                    comboDisplayViewModel = comboDisplayViewModel,
                    comboCreationViewModel = comboCreationViewModel,
                    userViewModel = userViewModel,
                    authViewModel = authViewModel,
                    snackbarHostState = snackBarHostState,
                    onNavigateToComboEditor = { navController.navigate(FlowScreen.ComboCreation.name) },
                    navigateBack = { navController.navigate(FlowScreen.CharSelect.name) }
                )
            }

            // Combo Creation Screen
            composable(route = FlowScreen.ComboCreation.name) {
                Timber.d("Loading Combo Creation Screen...")
                ComboCreationScreen(
                    authViewModel = authViewModel,
                    comboDisplayViewModel = comboDisplayViewModel,
                    comboCreationViewModel = comboCreationViewModel,
                    userViewModel = userViewModel,
                    profanityViewModel = profanityViewModel,
                    scope = scope,
                    snackbarHostState = snackBarHostState,
                    onNavigateToComboDisplay = { navController.navigate(FlowScreen.Combos.name) },
                    navigateBack = navController::navigateUp,
                )
            }
        }
    }
}