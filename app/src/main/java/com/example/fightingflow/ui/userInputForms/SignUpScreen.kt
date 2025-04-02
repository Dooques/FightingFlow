package com.example.fightingflow.ui.userInputForms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.model.UserEntry
import com.example.fightingflow.ui.theme.FightingFlowTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    inputViewModel: UserViewModel,
    navigateBack: () -> Unit,
    onSaveUser: () -> Unit,
    updateCurrentUser: (UserEntry) -> Unit,
    user: UserEntry
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Sign up for an account",
            color = Color.White,
            fontSize = 32.sp,
            modifier = modifier.padding(vertical = 16.dp)
        )
        SignUpForm(
            inputViewModel = inputViewModel,
            updateCurrentUser = updateCurrentUser,
            user = user,
            onConfirm = {
                inputViewModel.saveUserData()
                navigateBack()
            }
        )
    }
}

@Composable
fun SignUpForm(
    inputViewModel: UserViewModel,
    updateCurrentUser: (UserEntry) -> Unit,
    user: UserEntry,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
        Spacer(modifier.size(height = 40.dp, width = 0.dp))
        TextInputField("username",
            { username -> updateCurrentUser(user.copy(username = username)) }
        )
        TextInputField("email",
            {email -> updateCurrentUser(user.copy(email = email))}
        )
        TextInputField("password",
            {password -> updateCurrentUser(user.copy(password = password))}
        )
        DobField(
            onValueChange = {dob -> updateCurrentUser(user.copy(dob = dob))},
        )
        Spacer(modifier.size(height = 40.dp, width = 0.dp))
        ConfirmMarketingAndTOS(
            inputViewModel,
            user.tos,
            user.marketing
        )
        Spacer(modifier.size(height = 40.dp, width = 0.dp))
        OutlinedButton(
            onClick = onConfirm,
            colors = ButtonDefaults.buttonColors().copy(containerColor = Color.DarkGray),
            enabled = user.tos,
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
    var inputText by remember { mutableStateOf("") }

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
            value = inputText,
            onValueChange = {
                inputText = it
                onValueChange(inputText)
                            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
    }
}
@Composable
fun DobField(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp)
    ) {
        Text(
            "DOB: ",
            color = Color.White
        )
        TextField(
            value = inputText,
            onValueChange = {
                inputText = it
                onValueChange(inputText)
            },
            placeholder = {Text("dd/mm/yyyy")},
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
    }
}

@Composable
fun ConfirmMarketingAndTOS(
    inputViewModel: UserViewModel,
    tosSelected: Boolean,
    marketingSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable { inputViewModel.updateMarketingSelection(!marketingSelected) }
        ) {
            // Marketing
            RadioButton(
                selected = marketingSelected,
                onClick = {  },
            )
            Text(
                "Would you like to receive marketing and update emails from us?",
                color = Color.White
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable { inputViewModel.updateTosSelection(!tosSelected) }
        ) {
            // TOS
            RadioButton(
                selected = tosSelected,
                onClick = {  },
            )
            Text("Do you agree to our terms of service?", color = Color.White)
        }
    }
}

@Composable
fun DatePicker() {

}