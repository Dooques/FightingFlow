package com.dooques.fightingflow.ui.userScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dooques.fightingflow.model.UserEntry
import com.dooques.fightingflow.ui.viewmodels.AuthViewModel

@Composable
fun GoogleSignIn(
    authViewModel: AuthViewModel,
    checkProfileFields: () -> Unit,
    onConfirm: (String, String) -> Unit,
    profile: UserEntry
) {
    val signInState by authViewModel.signInState.collectAsStateWithLifecycle()
    val textAlignment = TextAlign.Center

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        checkProfileFields()
    }
}