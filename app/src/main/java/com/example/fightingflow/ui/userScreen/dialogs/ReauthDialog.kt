package com.example.fightingflow.ui.userScreen.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fightingflow.ui.userScreen.UserCreationForm
import com.example.fightingflow.viewmodels.AuthViewModel
import com.example.fightingflow.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReauthDialog(
    scope: CoroutineScope,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    showEmailPasswordDialog: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    BasicAlertDialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            colors = CardDefaults.cardColors().copy(containerColor = Color.Black),
            border = CardDefaults.outlinedCardBorder(true)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(24.dp)
            ) {
                Text("Please verify your account", textAlign = TextAlign.Center,
                    modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp))
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            authViewModel.initiateGoogleSignIn()
                            onDismissRequest()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 4.dp)
                ) {
                    Text(
                        "Sign In With Google",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                OutlinedButton(
                    onClick = {
                        showEmailPasswordDialog()
                        onDismissRequest()
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 4.dp)
                ) {
                    Text(
                        "Sign In With Email",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}