package com.example.fightingflow.ui.comboItem

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fightingflow.data.firebase.GoogleAuthService
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.UserDataForCombos
import com.example.fightingflow.ui.viewmodels.ComboDisplayViewModel
import com.example.fightingflow.ui.viewmodels.UserDetailsState
import com.example.fightingflow.util.CharacterEntryUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ComboInfoTop(
    combo: ComboDisplay,
    uiScale: Float,
    modifier: Modifier = Modifier
) {
    val font = MaterialTheme.typography.bodySmall
    Column {
        Box(Modifier.fillMaxWidth()) {
            Row(
                modifier = modifier.align(Alignment.CenterEnd)
            ) {
                Text(text = combo.character, style = font)
                Spacer(modifier.width((4 * uiScale).dp))
                if (combo.dateCreated.isNotBlank()) {
                    Text(text = "|", style = font)
                    Spacer(modifier.width((4 * uiScale).dp))
                    Text(text = combo.dateCreated, style = font)
                    Spacer(modifier.width((4 * uiScale).dp))
                }
                Text(text = "|", style = font)
                Spacer(modifier.width((4 * uiScale).dp))
                Text(
                    text = "★".repeat(combo.difficulty.toInt()),
                    color = Color.Yellow,
                    style = font
                )
                Text(
                    "★".repeat(5 - combo.difficulty.toInt()),
                    color = Color.Black,
                    style = font
                )
            }
        }
        if (combo.title.isNotBlank()) {
            Box(Modifier.fillMaxWidth()) {
                Row(
                    modifier = modifier.align(alignment = Alignment.CenterStart)
                ) {
                    Text(
                        text = combo.title,
                        style = font
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun ComboInfoBottom(
    scope: CoroutineScope,
    comboDisplayViewModel: ComboDisplayViewModel,
    combo: ComboDisplay,
    characterEntry: CharacterEntryUiState,
    comboCreationState: Boolean,
    currentUser: GoogleAuthService.SignInState,
    userData: UserDataForCombos,
    userDetails: UserDetailsState,
    toShare: Boolean,
    fontColor: Color,
    modifier: Modifier = Modifier
) {
    Timber.d("Current User Details: $userDetails\nUserMap: $userData")
    val font = MaterialTheme.typography.bodySmall

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Row {
                Text(
                    text = "Damage: ",
                    color = fontColor,
                    style = font
                )
                Text(
                    text = combo.damage.toString(),
                    color = Color.Red,
                    style = font
                )
            }
            Row {
                Text(
                    text = "Created by: ",
                    color = fontColor,
                    style = font
                )
                Text(
                    text =
                        if (comboCreationState) {
                            userData.userMap?.get(
                                (currentUser as GoogleAuthService.SignInState.Success).user.userId
                            ) ?: "Invalid User"
                        } else {
                            userData.userMap?.get(combo.createdBy) ?: "Invalid User"
                        },
                    color = fontColor,
                    style = font
                )
            }
        }
        if (!comboCreationState) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (toShare) {
                    Image(
                        painterResource(characterEntry.character.imageId),
                        null,
                        modifier.size(40.dp)
                    )
                }
                IconButton(
                    onClick = {
                        scope.launch {
                            Timber.d("Updating like value of combo and adding combo ID to User Likes")
                            when (userDetails) {
                                is UserDetailsState.Loaded -> {
                                    Timber.d("User Details available: $userDetails")
                                    val comboList = userDetails.user.likedCombos.toMutableList()
                                    comboList.map { it.replace(" ", "") }
                                    if (comboList.size == 1 && comboList.contains("")) {
                                        comboList.remove("")
                                    }
                                    Timber.d("Checking if user has liked the combo...")
                                    Timber.d("Combo List: $comboList")
                                    Timber.d("UserData contains combo: ${comboList.contains(combo.id)}")
                                    Timber.d("Combo ID: ${combo.id}")
                                    if (!comboList.contains(combo.id)) {
                                        Timber.d("User has not liked the combo, adding a like.")
                                        comboList.add(combo.id)
                                        Timber.d("Combo List after adding ID: $comboList")
                                        comboDisplayViewModel.updateCombo(
                                            combo = combo.copy(likes = combo.likes + 1),
                                            user = userDetails.user.copy(likedCombos = comboList)
                                        )
                                    } else {
                                        Timber.d("User has liked the combo, removing like.")
                                        comboList.remove(combo.id)
                                        Timber.d("Combo List after removing ID: $comboList")
                                        comboDisplayViewModel.updateCombo(
                                            combo = combo.copy(likes = combo.likes - 1),
                                            user = userDetails.user.copy(likedCombos = comboList)
                                        )
                                    }
                                }

                                else -> {
                                    Timber.d("User details not loaded")
                                }
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = null,
                        tint = if (
                            userDetails is UserDetailsState.Loaded &&
                            userDetails.user.likedCombos.contains(combo.id)
                        ) Color.Blue else Color.White
                    )
                }
                Text(
                    text = "${combo.likes} Likes",
                    color = fontColor,
                    style = font
                )
            }
        }
    }
}

@Composable
fun InputMove(
    context: Context,
    input: MoveEntry,
    uiScale: Float,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        MoveImage(input.moveName, uiScale = uiScale,  context = context)
    }
}

@Composable
fun MoveImage(
    move: String,
    context: Context,
    uiScale: Float,
    modifier: Modifier = Modifier
) {
    val moveId = remember(move) { context.resources.getIdentifier(move, "drawable", context.packageName) }
    val size = 35.dp

    Image(
        painter = painterResource(id = moveId),
        contentDescription = move,
        modifier = modifier
            .size(size * uiScale)
    )
}

@Composable
fun TextMove(
    move: MoveEntry,
    color: Color,
    uiScale: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .clip(RoundedCornerShape(100.dp))
            .background(color)
            .padding(4.dp)
    ) {
        Text(
            text =
                when (move.moveType) {
                    "Stage", "Mechanic", "Common", "Modifier"->
                    if (!move.notation.contains("/")) {
                        move.notation.replaceFirstChar { it.uppercase() }
                    } else {
                        if (move.moveName.length > 3) {
                            move.notation.replaceFirstChar { it.uppercase() }
                        } else {
                            move.notation.uppercase()
                        }
                    }
                else -> move.moveName },

            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = (14 * uiScale).sp,
                lineHeight = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (color == Color.White || color == Color.Green) Color.Black else Color.White,
                shadow = if (color != Color.White)
                    Shadow(color = Color.Black.copy(alpha = 0.5f), offset = Offset(2f, 2f), blurRadius = 4f)
                else
                    Shadow()
            ),
            modifier = modifier
        )
    }
}

@Composable
fun MoveBreak(
    uiScale: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier.padding(4.dp)) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = "",
            tint = Color.Cyan,
            modifier = modifier.size((15 * uiScale).dp)
        )
    }
}

@Composable
fun MiscInput(
    move: MoveEntry,
    modifier: Modifier = Modifier
) {
    Text(
        text = move.notation,
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.onBackground,

        )
}
