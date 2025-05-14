package com.example.fightingflow.ui.comboDisplayScreen

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import androidx.compose.ui.platform.LocalContext
import kotlin.reflect.KFunction2
import kotlin.reflect.KSuspendFunction3

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveOrShareImageDialog(
    showDialog: Boolean,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    tempImageUri: Uri?,
    tempImageFile: File?,
    onSave: KSuspendFunction3<Activity, Uri, Uri, Unit>,
    onShare: KFunction2<Activity, Uri, Unit>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val saveImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("image/*"),
        onResult = { uri: Uri? ->
            uri?.let { saveLocationUri ->
                tempImageUri?.let { tempUri ->
                    (context as? Activity)?.let { act ->
                        scope.launch {
                            onSave(act, tempUri, saveLocationUri)
                        }
                    }
                }
            }
        }
    )

    if (showDialog && activity != null && tempImageUri != null && tempImageFile != null) {
        BasicAlertDialog(
            onDismissRequest = onDismiss,
        ) {
            Column(
                modifier = modifier.padding(24.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Save or Share Image")
                Spacer(modifier.height(16.dp))
                Row {
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                saveImageLauncher.launch("combo_image.jpg")
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Image saved.",
                                        withDismissAction = true
                                    )
                                }
                                onDismiss()
                            }
                        }) { Text("Save") }
                    Spacer(modifier.width(24.dp))
                    OutlinedButton(onClick = {
                        scope.launch {
                            onShare(activity, tempImageUri); onDismiss()
                        }
                    }) { Text("Share") }
                }
            }
        }
    }
}