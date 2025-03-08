package com.example.fightingflow.ui.UserInputForms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.ui.theme.FightingFlowTheme

@Composable
fun SignUpScreen(
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Sign up for an account",
            color = Color.White,
            fontSize = 32.sp,
            modifier = modifier.padding(vertical = 16.dp)
        )
        SignUpForm(onConfirm)
    }
}

@Composable
fun SignUpForm(
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
        Spacer(modifier.size(height = 40.dp, width = 0.dp))
        TextInputField("username", {})
        TextInputField("email", {})
        TextInputField("password", {})
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Text("DOB ", color = Color.White)
            TextField("31/05/1993", onValueChange = {})
        }
        Spacer(modifier.size(height = 40.dp, width = 0.dp))
        ConfirmMarketingAndTOS()
        Spacer(modifier.size(height = 40.dp, width = 0.dp))
        OutlinedButton(
            onClick = onConfirm,
            colors = ButtonDefaults.buttonColors().copy(containerColor = Color.DarkGray),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) { Text("Confirm") }
        Spacer(modifier.size(height = 100.dp, width = 0.dp))

    }
}

@Composable
fun TextInputField(
    type: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val typeCap = type.replaceFirstChar { it.uppercaseChar() }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = typeCap,
            color = Color.White
        )
        TextField(
            value = "",
            onValueChange = onValueChange,
            placeholder = { Text("enter your $type...") },
        )
    }
}

@Composable
fun ConfirmMarketingAndTOS() {
    val marketingSelected by remember { mutableStateOf(false) }
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = marketingSelected,
                onClick = { !marketingSelected },
            )
            Text(
                "Would you like to receive marketing and update emails from us?",
                color = Color.White
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = marketingSelected,
                onClick = { !marketingSelected },
            )
            Text("Do you agree to our terms of service?", color = Color.White)
        }
    }
}

@Composable
fun DatePicker() {

}

@Preview
@Composable
fun LoginScreenPreview() {
    FightingFlowTheme {
        Surface(color = Color.Black, modifier = Modifier.fillMaxSize()) {
            SignUpScreen({})
        }
    }
}