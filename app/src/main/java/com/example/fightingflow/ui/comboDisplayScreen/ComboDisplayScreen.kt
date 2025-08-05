package com.example.fightingflow.ui.comboDisplayScreen

import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.example.fightingflow.data.mediastore.MediaStoreUtil
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.ComboEntryFb
import com.example.fightingflow.ui.viewmodels.ComboCreationViewModel
import com.example.fightingflow.ui.comboItem.ComboItemDisplay
import com.example.fightingflow.ui.components.ProfileAndConsoleInputMenu
import com.example.fightingflow.ui.viewmodels.UserViewModel
import com.example.fightingflow.ui.components.ActionIcon
import com.example.fightingflow.ui.components.SwipeableItem
import com.example.fightingflow.ui.settingsMenus.ShowPublicCombosMenu
import com.example.fightingflow.ui.viewmodels.AuthViewModel
import com.example.fightingflow.ui.viewmodels.ComboDisplayViewModel
import com.example.fightingflow.ui.viewmodels.UserDetailsState
import com.example.fightingflow.util.CharacterEntryUiState
import com.example.fightingflow.util.characterAndMoveData.characterMap
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber
import java.io.File

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeApi::class)
@Composable
fun ComboDisplayScreen(
    deviceType: WindowSizeClass,
    snackbarHostState: SnackbarHostState,
    comboDisplayViewModel: ComboDisplayViewModel,
    comboCreationViewModel: ComboCreationViewModel,
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    onNavigateToComboEditor: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Timber.d("-- Opening Combo Screen --")

    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    val mediaStoreUtil = koinInject<MediaStoreUtil>()

    val fontColor = MaterialTheme.colorScheme.onBackground
    val scope = rememberCoroutineScope()
    val uiScale = if (deviceType.widthSizeClass != WindowWidthSizeClass.Compact) 1.5f else 1f

    var showDialog by remember { mutableStateOf(false) }
    var capturedImage by remember { mutableStateOf<Uri?>(null) }
    var capturedImageFile by remember { mutableStateOf<File?>(null) }
    var shareDataOn by remember { mutableStateOf(false) }
    var combosCollected by remember { mutableStateOf(true) }

    // Room Flows
    val comboDisplayListRoom by comboDisplayViewModel.comboDisplayListRoom.collectAsStateWithLifecycle()
    val comboEntryListRoom by comboDisplayViewModel.comboEntryListRoom.collectAsStateWithLifecycle()

    // Datastore Flows
    val showPublicCombos by comboDisplayViewModel.publicCombosDisplayState.collectAsStateWithLifecycle()
    val characterNameState by comboDisplayViewModel.characterNameState.collectAsStateWithLifecycle()
    val characterState by comboDisplayViewModel.characterState.collectAsStateWithLifecycle()
    val characterImageState by comboDisplayViewModel.characterImageState.collectAsStateWithLifecycle()
    val iconDisplayState by comboDisplayViewModel.showIconState.collectAsStateWithLifecycle()
    val gameSelectedState by comboDisplayViewModel.gameSelectedState.collectAsStateWithLifecycle()

    val textComboState by comboDisplayViewModel.textComboState.collectAsStateWithLifecycle()
    val consoleTypeState by comboDisplayViewModel.consoleTypeState.collectAsStateWithLifecycle()
    val sF6ControlType by comboDisplayViewModel.modernOrClassicState.collectAsStateWithLifecycle()

    // Firebase Flows
    val comboDisplayListFirebase by comboDisplayViewModel.comboDisplayListFb.collectAsStateWithLifecycle()
    val comboEntryListFirestore by comboDisplayViewModel.comboEntryListFb.collectAsStateWithLifecycle()
    val userData by userViewModel.userDataMap.collectAsStateWithLifecycle()

    // Auth Flows
    val currentUser by authViewModel.signInState.collectAsStateWithLifecycle()
    val userDetails by userViewModel.userDetailsState.collectAsStateWithLifecycle()

    val comboEntryList by remember(characterState) {
        derivedStateOf {
            if (!characterState.character.mutable) {
                comboEntryListFirestore.comboEntryFbList.toMutableList()
            } else {
                comboEntryListRoom.comboEntryList.toMutableList()
            }
        }
    }
    val comboDisplayList by remember(characterState) {
        derivedStateOf {
            if (!characterState.character.mutable) {
                comboDisplayListFirebase.comboDisplayList.toMutableList()
            } else {
                comboDisplayListRoom.comboDisplayList.toMutableList()
            }
        }
    }

//    Timber.d("--Flow Values--\n Console: %s\n Combo List: %s\n Collected: %s\n GameSelectedState: %s",
//        consoleTypeState, comboDisplayListFirebase.comboDisplayList, combosCollected, gameSelectedState)

    Timber.d("Updating character data")
    LaunchedEffect(currentUser) {
        Timber.d("Launched Effect triggered by User State Change\nCurrent User: %s",
        when (currentUser) {
            is GoogleAuthService.SignInState.Success -> {"User is signed in: " +
                (currentUser as GoogleAuthService.SignInState.Success).user
            }
            else -> { "User is not signed in yet: $currentUser"}
        })
        when (currentUser) {
            is GoogleAuthService.SignInState.Success -> { comboDisplayViewModel.updateUserState(currentUser) }
            else -> null
        }
    }

    LaunchedEffect(characterNameState, gameSelectedState) {
        Timber.d("Launched Effect triggered by character change")
        if (characterNameState?.name != null && characterNameState?.name?.isNotBlank() == true) {
            try {
                Timber.d("Getting ${characterNameState!!.name} from database...")
                comboDisplayViewModel.updateCharacterState(
                    characterNameState!!.name,
                    gameSelectedState
                )
                Timber.d("Character returned: $characterState")
            } catch (e: NoSuchElementException) {
                Timber.e(e, "Character Error, no element found in character list.")
            }
        }
    }

    Timber.d(
        "Character Details\nName: %s\nImage: %s\nCharacterState: %s\nCombo List: %s",
        characterNameState?.name, characterImageState.image, characterState.character, comboDisplayList
    )
    Timber.d("Checking details valid...")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = characterState.character.name,
                        style =
                            if (characterState.character.name.length > 12)
                                MaterialTheme.typography.displaySmall
                            else
                                MaterialTheme.typography.displayMedium,
                        modifier = modifier.padding(start = 16.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Return to Character Select",
                            modifier = modifier.size(50.dp)
                        )
                    }
                },
                actions = {
                    if (characterState.character.mutable) {
                        AsyncImage(
                            model = characterState.character.imageUri,
                            contentDescription = null,
                            modifier.size(60.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(characterState.character.imageId),
                            contentDescription = "",
                            modifier = modifier.size(60.dp)
                        )
                    }
                    IconButton(onClick = {
                        scope.launch {
                            comboDisplayViewModel.updateEditingState(false)
                            onNavigateToComboEditor()
                        } }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Combo",
                            modifier = modifier.size(80.dp)
                        )
                    }
                    ShowPublicCombosMenu(
                        showPublicComboState = showPublicCombos,
                        updatePublicComboState = {
                            scope.launch {
                                comboDisplayViewModel.updateShowComboDisplayState(!showPublicCombos)
                            }
                        }
                    )
                },
                windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
            )
        }
    ) { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            if (!showPublicCombos) {
                Row(modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)) {
                    Text("Your Combos", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                Row(modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)) {
                    Text("All Combos", style = MaterialTheme.typography.bodyLarge)
                }
            }

            LazyColumn(modifier
                .fillMaxSize()
                .padding(
                    start = if (uiScale == 1.5f) 40.dp else 4.dp,
                    end = if (uiScale == 1.5f) 16.dp else 0.dp
                )
            ) {
                Timber.d("--Loading Combo Display List--\n ComboList: $comboDisplayList")
                if (comboDisplayList.isEmpty()) {
                    Timber.d("Combo list is empty")
                    item {
                        Row(
                            modifier = modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 32.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("Nothing to see here, press the plus icon to create a combo!", textAlign = TextAlign.Center)
                        }
                    }
                }
                Timber.d("Getting display combos as lazy column with swipeable actions.")
                itemsIndexed(
                    items = comboDisplayList,
                    key = {index, item -> item.id}
                ) { index, combo ->
                    Timber.d("Combo: ${combo.id}\nIndex: $index")
                    val captureController = rememberCaptureController()
                    val isOptionRevealed by remember { mutableStateOf(false) }

                    SwipeableItem(
                        isRevealed = isOptionRevealed,
                        onExpanded = {
                            comboDisplayList[index] = combo.copy(areOptionsRevealed = true)
                            shareDataOn = true
                        },
                        onCollapsed = {
                            comboDisplayList[index] = combo.copy(areOptionsRevealed = false)
                            shareDataOn = false
                        },
                        actions = {
                            // Share Combo
                            ActionIcon(
                                onclick = {
                                    Timber.d("Sharing Combo")
                                    scope.launch {
                                        Timber.d("Creating bitmap of composable...")
                                        val bitmapAsync = captureController.captureAsync()
                                        try {
                                            val bitmap = bitmapAsync.await().asAndroidBitmap()
                                            val imageObject = mediaStoreUtil.createAndShareTempImage(bitmap)
                                            if (imageObject.uri != null && imageObject.file != null) {
                                                capturedImage = imageObject.uri
                                                capturedImageFile = imageObject.file
                                                showDialog = true
                                            }
                                        } catch(e: Exception) {
                                            Timber.e(e, "Error sharing image")
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    "Failed to share image: ${e.message}"
                                                )
                                            }
                                        }
                                    }
                                },
                                tint = Color.Blue,
                                icon = Icons.Default.Share,
                                modifier = modifier.fillMaxHeight()
                            )
                            when (userDetails) {
                                is UserDetailsState.Loaded -> {
                                    if ((userDetails as UserDetailsState.Loaded).user.userId == combo.createdBy) {
                                        // Edit Combo
                                        ActionIcon(
                                            onclick = {
                                                Timber.d("Preparing to edit combo")
                                                scope.launch {
                                                    comboDisplayViewModel.updateComboStateInDs(combo)
                                                    comboDisplayViewModel.updateEditingState(true)
                                                    comboCreationViewModel.updateItemIndex(combo.moves.size)
                                                    onNavigateToComboEditor()
                                                }
                                            },
                                            tint = Color.Green,
                                            icon = Icons.Default.Edit,
                                            modifier = modifier.fillMaxHeight()
                                        )
                                        // Delete Combo
                                        ActionIcon(
                                            onclick = {
                                                scope.launch {
                                                    comboDisplayViewModel.deleteCombo(
                                                        combo,
                                                    )
                                                    Timber.d("UI deleted: $combo")
                                                }
                                                Toast.makeText(
                                                    context,
                                                    "Combo ${combo.id} was deleted.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            },
                                            tint = Color.Red,
                                            icon = Icons.Default.Delete,
                                            modifier = modifier.fillMaxHeight()
                                        )
                                    }
                                }
                                else -> null
                            }
                            var settingsMenuExpanded by remember { mutableStateOf(false) }
                            ActionIcon(
                                onclick = {
                                    Timber.d("Configuring Layout")
                                    settingsMenuExpanded = true
                                },
                                tint = Color.White,
                                icon = Icons.Default.Settings,
                                modifier = modifier.fillMaxHeight()
                            )
                            ProfileAndConsoleInputMenu(
                                settingsMenuExpanded = settingsMenuExpanded,
                                onDismissRequest = { settingsMenuExpanded = !settingsMenuExpanded },
                                updateIconSetting = { comboDisplayViewModel.updateShowIconDisplayState(!iconDisplayState) },
                                updateTextComboSetting = { comboDisplayViewModel.updateShowComboTextState(!textComboState) },
                                iconState = iconDisplayState,
                                textComboState = textComboState
                            )
                        },
                    ) {
                        ComboItemDisplay(
                            context = context,
                            scope = scope,
                            captureController = captureController,
                            toShare = shareDataOn,
                            display = true,
                            fontColor = fontColor,
                            comboDisplayViewModel = comboDisplayViewModel,
                            characterEntryUiState = characterState,
                            currentUser = currentUser,
                            userData = userData,
                            userDetails = userDetails,
                            comboCreationState = false,
                            combo = combo,
                            comboAsText =
                                if (comboEntryList.isNotEmpty()) {
                                    if (comboEntryList.first() is ComboEntryFb) {
                                        (comboEntryList[index] as ComboEntryFb).moves.joinToString(",")
                                    } else if (comboEntryList.first() is ComboEntry) {
                                        (comboEntryList[index] as ComboEntry).moves
                                    } else {
                                        ""
                                    }
                                } else {
                                    ""
                                },
                            console = consoleTypeState,
                            sf6ControlType = sF6ControlType,
                            iconDisplayState = iconDisplayState,
                            textComboState = textComboState,
                            uiScale = uiScale,
                            modifier = modifier.padding(vertical = 4.dp, )
                        )
                    }
                }
            }
        }
        SaveOrShareImageDialog(
            showDialog = showDialog,
            scope = scope,
            snackbarHostState = snackbarHostState,
            tempImageUri = capturedImage,
            tempImageFile = capturedImageFile,
            onSave = mediaStoreUtil::saveImageToUri,
            onShare = mediaStoreUtil::shareImage,
            onDismiss = { showDialog = false },
        )
    }
}