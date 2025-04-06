package com.example.fightingflow.ui.profileScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fightingflow.util.PROFILE_SCREEN_TAG
import com.example.fightingflow.util.ProfileListUiState

@Composable
fun ProfileList(
    modifier: Modifier = Modifier,
    profileList: ProfileListUiState,
    editProfileStateChange: () -> Unit,
    navigateBack: () -> Unit
) {
    Log.d(PROFILE_SCREEN_TAG, "")
    Column(
        modifier.fillMaxSize()
    ) {
        Log.d(PROFILE_SCREEN_TAG, "Loading Home Button")
        Box {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Return to Character Select",
                modifier
                    .size(60.dp)
                    .clickable(onClick = navigateBack)
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Existing Profiles")
        }
        Spacer(modifier.size(40.dp))
        LazyColumn {
            items(items = profileList.profileList) { profile ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(profile.username.replaceFirstChar { it.uppercase() })
                    OutlinedButton(onClick = editProfileStateChange) { Text("Choose Profile", color = Color.White) }
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            OutlinedButton(onClick = {}) { Text("Create A New Profile", color = Color.White) }
        }

    }
}