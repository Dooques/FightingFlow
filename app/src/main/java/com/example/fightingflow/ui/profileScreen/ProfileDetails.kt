package com.example.fightingflow.ui.profileScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.R
import com.example.fightingflow.util.ComboDisplayListUiState
import com.example.fightingflow.util.PROFILE_SCREEN_TAG
import com.example.fightingflow.util.ProfileUiState
import timber.log.Timber

@Composable
fun ProfileDetailsUi(
    profileViewModel: ProfileViewModel,
    isProfileLoggedIn: Boolean,
    currentProfile: ProfileUiState,
    combosByProfile: ComboDisplayListUiState,
    navigateBack: () -> Unit
) {
    Header(
        existingProfile = currentProfile,
        navigateBack = navigateBack
    )
}

@Composable
fun Header(
    existingProfile: ProfileUiState,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()

    ) {
        Timber.d("")
        Timber.d("Loading Home Button")
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Return to Character Select",
            modifier
                .size(65.dp)
                .clickable(onClick = navigateBack)

        )
        Timber.d("Loading Character Name: ${existingProfile.profile.username}...")
        Text(
            text = existingProfile.profile.username,
            fontSize = if (existingProfile.profile.username.length > 9) 50.sp else 70.sp,
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier
        )
        Timber.d("Loading Character Image ${existingProfile.profile.profilePic}...")
        Image(
            painter = painterResource(R.drawable.t8_logo),
            contentDescription = "profile pic",
            modifier = Modifier
                .size(60.dp)
        )
        Timber.d("Loading Icon image")
    }
}