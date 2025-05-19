package com.example.fightingflow.ui.comboDisplayScreen

import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fightingflow.data.mediastore.MediaStoreUtil
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.ui.profileScreen.ProfileViewModel
import com.example.fightingflow.util.ActionIcon
import com.example.fightingflow.util.SwipeableItem
import com.example.fightingflow.util.emptyCharacter
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
    onNavigateToComboEditor: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Timber.d("Opening Combo Screen...")

    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    val mediaStoreUtil = koinInject<MediaStoreUtil>()
    val profileViewModel = koinInject<ProfileViewModel>()

    val fontColor = MaterialTheme.colorScheme.onBackground
    val scope = rememberCoroutineScope()
    val uiScale = if (deviceType.widthSizeClass != WindowWidthSizeClass.Compact) 2f else 1f

    var showDialog by remember { mutableStateOf(false) }
    var capturedImage by remember { mutableStateOf<Uri?>(null) }
    var capturedImageFile by remember { mutableStateOf<File?>(null) }
    var shareDataOn by remember { mutableStateOf(false) }

    // Room Flows
    val characterState by comboDisplayViewModel.characterState.collectAsStateWithLifecycle()
    val characterListState by comboDisplayViewModel.characterEntryListState.collectAsStateWithLifecycle()
    val comboDisplayListState by comboDisplayViewModel.comboDisplayListState.collectAsStateWithLifecycle()

    // Datastore Flows
    val username by profileViewModel.username.collectAsStateWithLifecycle()
    val characterNameState by comboDisplayViewModel.characterNameState.collectAsStateWithLifecycle()
    val characterImageState by comboDisplayViewModel.characterImageState.collectAsStateWithLifecycle()

    Timber.d("Updating character data")
    if (characterNameState.name.isNotEmpty()) {
        try {
            Timber.d("Getting ${characterNameState.name} from database...")
            comboDisplayViewModel.updateCharacterState(characterNameState.name)
            Timber.d("Character returned: $characterState")
        } catch (e: NoSuchElementException) {
            Timber.e(e, "Character Error, no element found in character list.")
        }
    }

    Timber.d("Getting combos by character...")
    val combosByCharacter = if (characterState.character != emptyCharacter) {
        Timber.d("Character has been selected, getting combos...")
        comboDisplayViewModel.getComboDisplayListByCharacter()
        comboDisplayListState.comboDisplayList.toMutableList()
    } else {
        Timber.d("No characters selected, returning empty list...")
        emptyList<ComboDisplay>().toMutableList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = characterState.character.name,
                        style = MaterialTheme.typography.displayMedium,
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
                    Image(
                        painter = painterResource(characterState.character.imageId),
                        contentDescription = "",
                        modifier = modifier.size(60.dp)
                    )
                    IconButton(onClick = {
                        scope.launch {
                            comboDisplayViewModel.setEditingState(false)
                            onNavigateToComboEditor()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Combo",
                            modifier = modifier.size(80.dp)
                        )
                    } },
                windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
            )
        }
    ) { contentPadding ->
        Column(Modifier.padding(contentPadding)) {
            Timber.d("Character Details \n Name: ${characterNameState.name} \n Image: ${characterImageState.image}")
            Timber.d("Checking details valid...")
            LazyColumn(modifier.padding(
                start = if (uiScale == 2f) 40.dp else 4.dp,
                end = if (uiScale == 2f) 16.dp else 0.dp
            )
            ) {
                Timber.d("Getting display combos as lazy column with swipeable actions.")
                itemsIndexed(items = combosByCharacter) { index, combo ->
                    val captureController = rememberCaptureController()
                    val isOptionRevealed by remember { mutableStateOf(false) }

                    SwipeableItem(
                        isRevealed = isOptionRevealed,
                        onExpanded = {
                            combosByCharacter[index] = combo.copy(areOptionsRevealed = true)
                            shareDataOn = true
                        },
                        onCollapsed = {
                            combosByCharacter[index] = combo.copy(areOptionsRevealed = false)
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
                                            if (imageObject?.uri != null && imageObject.file != null) {
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
                            // Edit Combo
                            ActionIcon(
                                onclick = {
                                    Timber.d("Preparing to edit combo")
                                    scope.launch {
                                        comboDisplayViewModel.saveComboIdToDs(combo)
                                        comboDisplayViewModel.setEditingState(true)
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
                                        "Combo ${combo.comboId} was deleted.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                tint = Color.Red,
                                icon = Icons.Default.Delete,
                                modifier = modifier.fillMaxHeight()
                            )
                        },
                    ) {
                        ComboDisplay(
                            context = context,
                            captureController = captureController,
                            toShare = shareDataOn,
                            display = true,
                            fontColor = fontColor,
                            characterEntryListUiState = characterListState,
                            combo = combo,
                            username = username,
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