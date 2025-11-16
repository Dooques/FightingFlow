package com.dooques.fightingflow.ui.userScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.dooques.fightingflow.data.firebase.GoogleAuthService
import com.dooques.fightingflow.model.CharacterEntry
import com.dooques.fightingflow.ui.characterScreen.CharacterCard
import com.dooques.fightingflow.ui.settingsMenus.UserDetailsSettingsMenu
import com.dooques.fightingflow.ui.userScreen.dialogs.ConfirmDeleteDialog
import com.dooques.fightingflow.ui.userScreen.dialogs.EmailSignInDialog
import com.dooques.fightingflow.ui.userScreen.dialogs.ReauthDialog
import com.dooques.fightingflow.ui.viewmodels.AuthViewModel
import com.dooques.fightingflow.ui.viewmodels.CharacterViewModel
import com.dooques.fightingflow.ui.viewmodels.ComboDisplayViewModel
import com.dooques.fightingflow.ui.viewmodels.ProfanityViewModel
import com.dooques.fightingflow.ui.viewmodels.UserDetailsState
import com.dooques.fightingflow.ui.viewmodels.UserViewModel
import com.dooques.fightingflow.util.CharacterEntryListUiState
import com.dooques.fightingflow.util.CharacterEntryUiState
import com.dooques.fightingflow.util.characterAndMoveData.characterMap
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    characterViewModel: CharacterViewModel,
    comboDisplayViewModel: ComboDisplayViewModel,
    navigateToComboDisplayScreen: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d("--Loading user details--")

    var showReauthDialog by remember { mutableStateOf(false) }
    var showEmailPasswordDialog by remember { mutableStateOf(false) }
    var showConfirmDeleteDialog by remember { mutableStateOf(false) }
    var usernamesConfirmed by remember { mutableStateOf(false) }

    val currentUser by authViewModel.signInState.collectAsStateWithLifecycle()
    val userDetails by userViewModel.userDetailsState.collectAsStateWithLifecycle()


    val userCharacters = if (userDetails is UserDetailsState.Loaded) {
        val processedList = mutableListOf<CharacterEntry>()
        (userDetails as UserDetailsState.Loaded).user.characterList.forEach { characterName ->
            var character: CharacterEntry? = null

            characterMap.entries.forEach { game ->
                game.value.forEach { gameCharacter ->
                    if (gameCharacter.name == characterName) {
                        character = gameCharacter
                    }
                }
            }

            if (character != null) { processedList.add(character) }
        }
        CharacterEntryListUiState(processedList.toList())
    } else {
        CharacterEntryListUiState()
    }

    var menuExpanded by remember { mutableStateOf(false) }

    Timber.d(" Flows Collected: \n Current User: %s\n User Details: %s",
        currentUser, userDetails)

    when (currentUser) {
        is GoogleAuthService.SignInState.Idle -> navigateBack()
        else -> null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Your Details",
                        style = MaterialTheme.typography.displayMedium,
                        modifier = modifier.padding(start = 16.dp)
                    ) },
                windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),

                navigationIcon = {
                    IconButton(
                        onClick = { navigateBack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Return to Character Select",
                            modifier = modifier.size(50.dp)
                        ) } },

                actions = {
                    UserDetailsSettingsMenu(
                        scope = scope,
                        authViewModel = authViewModel,
                        navigateBack = navigateBack,
                        showConfirmDialog = { showConfirmDeleteDialog = true },
                    )
                }
            ) },
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) { contentPadding ->

        Column(
            modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 32.dp)
        ) {

            UserDetailsForm(
                currentUser = currentUser,
                userDetails = userDetails,
                menuExpanded = menuExpanded,
                showProfilePhotoOptions = { menuExpanded = !menuExpanded},
            )

            Spacer(modifier.size(20.dp))

            Text(
                text = "Your Characters",
                style = MaterialTheme.typography.displayMedium,
            )
            if (userCharacters.characterList.isNotEmpty()) {
                UserCharacterList(
                    scope = scope,
                    snackbarHostState = snackBarHostState,
                    characterViewModel = characterViewModel,
                    comboDisplayViewModel = comboDisplayViewModel,
                    characterList = userCharacters,
                    navigateToComboDisplayScreen = navigateToComboDisplayScreen
                )
            } else {
                Text("No combos created yet...", modifier.padding(top = 8.dp))
            }
        }

        if (showReauthDialog) {
            ReauthDialog(
                scope = scope,
                authViewModel = authViewModel,
                showConfirmDialog = { showConfirmDeleteDialog = true },
                showEmailPasswordDialog = { showEmailPasswordDialog = true },
                onDismissRequest = { showReauthDialog = false },
            )
        }

        if (showConfirmDeleteDialog) {
            ConfirmDeleteDialog(
                scope = scope,
                snackbarHostState = snackBarHostState,
                userDetailsState = userDetails,
                userViewModel = userViewModel,
                authViewModel = authViewModel,
                currentUser = currentUser,
                showReauthDialog = { showReauthDialog = true },
                navigateBack = navigateBack,
                onDismissRequest = { showConfirmDeleteDialog = false },
                modifier = modifier
            )
        }

        if (showEmailPasswordDialog) {
            EmailSignInDialog(
                userViewModel = userViewModel,
                authViewModel = authViewModel,
                createAccount = false,
                showConfirmDialog = {showConfirmDeleteDialog = true },
                onDismissRequest = { showEmailPasswordDialog = false },
            )
        }
    }
}

@Composable
fun ChangeProfileImage(menuExpanded: Boolean, onDismissRequest: () -> Unit) {
    DropdownMenu(expanded = menuExpanded, onDismissRequest = onDismissRequest) {
        DropdownMenuItem(
            text = { Text("Change Profile Photo") },
            onClick = {}
        )
    }
}

@Composable
fun UserDetailsForm(
    currentUser: GoogleAuthService.SignInState,
    userDetails: UserDetailsState,
    menuExpanded: Boolean,
    showProfilePhotoOptions: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.d(" User Details\n currentUser: $currentUser\n userDetails: $userDetails")
    Timber.d(" Checking user is authenticated")

    when (currentUser) {
        is GoogleAuthService.SignInState.Success -> {
            Timber.d(" User is authenticated")
            when (userDetails) {
                is UserDetailsState.Loaded -> {
                    Timber.d(" User details are loaded")
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Box(Modifier.fillMaxWidth()) {
                            Text(
                                text = userDetails.user.username,
                                style = MaterialTheme.typography.displayMedium,
                                modifier = modifier.align(CenterStart)
                            )
                            Box(
                                modifier = modifier.align(CenterEnd)
                            ) {
                                AsyncImage(
                                    model = currentUser.user.photo,
                                    contentDescription = "User Image",
                                    modifier = modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .clickable(onClick = showProfilePhotoOptions)
                                )
                                ChangeProfileImage(
                                    menuExpanded = menuExpanded,
                                    onDismissRequest = showProfilePhotoOptions
                                )
                            }
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Text(
                            "Name: ${userDetails.user.name}",
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Text(
                            "Email: ${currentUser.user.email.toString()}",
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Text(
                            "Date of Birth: ${userDetails.user.dob}",
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Text(
                            "Date Created: ${userDetails.user.dateCreated}",
                        )
                    }
                    Spacer(modifier.size(20.dp))
                }

                else -> {
                    Timber.d(" User is not authenticated")
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Text(
                            "Name: ${currentUser.user.displayName}",
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Text(
                            "Email: ${currentUser.user.email.toString()}",
                        )
                    }
                }
            }
        }
        else -> {
            Timber.d(" User is not authenticated")
            Row(Modifier.padding(horizontal = 16.dp)) {
                Text("Not currently logged in")
            }
        }
    }
}

@Composable
fun UserCharacterList(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    characterViewModel: CharacterViewModel,
    comboDisplayViewModel: ComboDisplayViewModel,
    characterList: CharacterEntryListUiState,
    navigateToComboDisplayScreen: () -> Unit
) {

    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth()
    ) {
        Timber.d("Loading Character Grid...")
        items(items = characterList.characterList.sortedBy { character -> character.name }) { character ->
            CharacterCard(
                scope = scope,
                snackbarHostState = snackbarHostState,
                characterViewModel = characterViewModel,
                updateCharacterState = comboDisplayViewModel::updateCharacterState,
                setCharacterToDS = comboDisplayViewModel::updateCharacterInDS,
                navigateToComboDisplayScreen = navigateToComboDisplayScreen,
                characterState = CharacterEntryUiState(character),
            )
        }
        Timber.d("Character Grid Finished.")
    }
}
