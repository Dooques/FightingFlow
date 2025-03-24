package com.example.fightingflow.ui

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.R
import com.example.fightingflow.ui.theme.FightingFlowTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun TitleScreen(
    onLogin: () -> Unit,
    onSignUp: () -> Unit,
    deviceType: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    val orientation = LocalConfiguration.current.orientation
    val uiScale = if (orientation == ORIENTATION_LANDSCAPE && deviceType.heightSizeClass == WindowHeightSizeClass.Compact) 2 else 1
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            text = "Fighting Flow",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White
        )
        Image(
            painter = painterResource(R.drawable.t8_logo),
            contentDescription = "Tekken 8 Logo",
            modifier = Modifier.size(if (uiScale == 2) 100.dp else 400.dp)
        )
        Column {
            AccessButton(
                buttonText = stringResource(R.string.char_select),
                onClick = onLogin,
                modifier = modifier
            )
            Spacer(modifier = modifier.size(16.dp))
            AccessButton(
                buttonText = stringResource(R.string.sign_up),
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

//@Preview
//@Composable
//fun TitlePreview() {
//    FightingFlowTheme {
//        TitleScreen({}, {}, deviceType = WindowWidthSizeClass.Compact)
//    }
//}