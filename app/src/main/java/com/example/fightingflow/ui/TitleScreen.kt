package com.example.fightingflow.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.R
import com.example.fightingflow.util.TITLE_TAG
import java.util.Locale

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun TitleScreen(
    deviceType: WindowSizeClass,
    isLoggedIn: Boolean,
    username: String,
    onCharSelect: () -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d(TITLE_TAG, "")
    Log.d(TITLE_TAG, "Loading title screen...")
    val uiScale =
        if (deviceType.heightSizeClass == WindowHeightSizeClass.Compact) 2 else 1

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Log.d(TITLE_TAG, "Loading logo...")
        Image(
            painter = painterResource(R.drawable.fighting_flow_title_logo),
            contentDescription = "Fighting Flow Logo",
            modifier = Modifier
                .size(if (uiScale == 2) 100.dp else 400.dp)
                .padding(end = 20.dp)
        )
        Log.d(TITLE_TAG, "Checking if user is logged in...")
        if (isLoggedIn) {
            Log.d(TITLE_TAG, "User is logged in.")
            Log.d(TITLE_TAG, "Loading greeting...")
            Text(
                text = "Welcome ${username.replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White
            )
        }
        Spacer(modifier.size(height = 80.dp, width = 0.dp))
        Column {
            Log.d(TITLE_TAG, "Loading buttons...")
            AccessButton(
                buttonText = stringResource(R.string.char_select),
                onClick = onCharSelect,
                modifier = modifier
            )
            Spacer(modifier = modifier.size(16.dp))
            AccessButton(
                buttonText = stringResource(R.string.profiles),
                onClick = onSignUp,
                modifier = modifier
            )
        }
    }
}

@Composable
fun AccessButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color.White),
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth(0.8F)
    ) {
        Text(
            text = buttonText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}