package com.example.fightingflow.ui.addCharacterScreen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fightingflow.model.Console
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.ui.comboCreationScreen.ComboCreationViewModel
import com.example.fightingflow.ui.comboCreationScreen.IconMoves
import com.example.fightingflow.ui.comboCreationScreen.TextMoves
import com.example.fightingflow.ui.comboDisplayScreen.ComboDisplayViewModel
import com.example.fightingflow.ui.components.ConsoleInputsMenu
import com.example.fightingflow.util.MoveEntryListUiState
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.arcSysMoves
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.movement
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.numpadNotationMoves
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.tagFighterMoves
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.virtuaFighterMoves
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlSchemeDemoScreen(
    comboDisplayViewModel: ComboDisplayViewModel,
    comboCreationViewModel: ComboCreationViewModel,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val consoleTypeState by comboDisplayViewModel.consoleTypeState.collectAsStateWithLifecycle()

//    var consoleInputMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Control Schemes",
                        style = MaterialTheme.typography.displaySmall,
                    )
                },
                actions = {
//                    IconButton(onClick = { consoleInputMenuExpanded = !consoleInputMenuExpanded}) {
//                        Icon(
//                            imageVector = Icons.Default.Settings,
//                            contentDescription = "Settings"
//                        )
//                        if (consoleInputMenuExpanded) {
//                            ConsoleInputsMenu(
//                                optionSelected = { comboDisplayViewModel.updateConsoleType(it) },
//                                onDismiss = { consoleInputMenuExpanded = false },
//                                onDismissParent = {}
//                            )
//                        }
//                    }
                    IconButton(
                        onClick = { navigateBack() },
                        modifier = modifier.fillMaxHeight()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Return to Combo screen",
                            tint = Color.White,
                            modifier = modifier.size(100.dp)
                        )
                    }
                },
                windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
            )
        }
    ) { contentPadding ->



        val context = LocalContext.current
        Box(
            modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            LazyColumn {
                item{Spacer(Modifier.size(20.dp))}

                item{
                    Box(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                        Text("This is how your combo creation screen will look if you pick a custom control type.")
                    }
                }

                items(items = controlDemoLayout, key = { it }) { scheme ->
                    SchemeDisplay(
                        context = context,
                        comboCreationViewModel = comboCreationViewModel,
                        schemeName = scheme,
                        movementInputs = MoveEntryListUiState(
                            schemeList.first { it.name == scheme }
                                .list.filter { it.moveType == "Movement" }
                        ),
                        iconInputs = MoveEntryListUiState(
                            schemeList.first { it.name == scheme }
                                .list.filter { it.moveType == "Input" }),
                        textInputs = MoveEntryListUiState(
                            schemeList.first { it.name == scheme }
                                .list.filter { it.moveType == "Text Input" }),
                        console = consoleTypeState,
                        lastItem = scheme == controlDemoLayout.last()
                    )
                }
            }
        }
    }
}

@Composable
fun SchemeDisplay(
    context: Context,
    comboCreationViewModel: ComboCreationViewModel,
    schemeName: String,
    movementInputs: MoveEntryListUiState,
    iconInputs: MoveEntryListUiState,
    textInputs: MoveEntryListUiState,
    console: Console,
    lastItem: Boolean,
    modifier: Modifier = Modifier
) {
    Column {
            Text(
                text = schemeName,
                style = MaterialTheme.typography.displaySmall.copy(fontSize = 25.sp),
                modifier = modifier.padding(horizontal = 16.dp).padding(top = 24.dp)
            )
        Text(text = "Movement", style = MaterialTheme.typography.bodyLarge, modifier = modifier.padding(horizontal = 16.dp))
        IconMoves(
            comboCreationViewModel = comboCreationViewModel,
            moveType = "Movement",
            moveList = if (schemeName == "Numpad Notation") movementInputs else MoveEntryListUiState(movement),
            console = console,
            context = context,
            maxItems = 5
        )
        HorizontalDivider(modifier.padding(vertical = 8.dp))
        Text(text = "Inputs", style = MaterialTheme.typography.bodyLarge, modifier = modifier.padding(horizontal = 16.dp))
        IconMoves(
            comboCreationViewModel = comboCreationViewModel,
            moveType = "Input",
            moveList = iconInputs,
            console = console,
            context = context,
            maxItems = 5
        )
        if (textInputs.moveList.isNotEmpty()) {
            TextMoves(
                comboCreationViewModel = comboCreationViewModel,
                moveType = "Text Input",
                moveList = textInputs,
                maxItems = 6,
                console = console,
            )
        }
        if (lastItem){
            Spacer(modifier.height(80.dp))
        }
    }
}

val controlDemoLayout = listOf("ArcSys",  "Numpad Notation", "Tag Fighter", "Virtua Fighter")

val schemeList = listOf(
    SchemeListObject("ArcSys", arcSysMoves),
    SchemeListObject("Numpad Notation", numpadNotationMoves),
    SchemeListObject("Tag Fighter", tagFighterMoves),
    SchemeListObject("Virtua Fighter", virtuaFighterMoves)
)

data class SchemeListObject(val name: String, val list: List<MoveEntry>)