package com.example.fightingflow.ui.comboItem

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
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
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.MoveEntry

@Composable
fun ComboInfoTop(
    combo: ComboDisplay,
    uiScale: Float,
    modifier: Modifier = Modifier
) {
    val fontSize = if (uiScale == 2f) 20.sp else 16.sp
    Box(Modifier.fillMaxWidth()) {
        Row(
            modifier = modifier.align(alignment = Alignment.CenterStart)
        ) {
            Text(text = combo.description, fontSize = fontSize)
        }
        Row(modifier.align(Alignment.CenterEnd)) {
            Text(text = combo.character, fontSize = fontSize)
            Spacer(modifier.width((4 * uiScale).dp))
            Text(text = "|")
            Spacer(modifier.width((4 * uiScale).dp))
            Text(text = combo.dateCreated, fontSize = fontSize)
        }
    }
}

@Composable
fun ComboInfoBottom(
    combo: ComboDisplay,
    profile: String,
    fontColor: Color,
    modifier: Modifier = Modifier
) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth()) {
        Row {
            Text(text = "Damage: ", color = fontColor)
            Text(text = combo.damage.toString(), color = Color.Red)
        }
        Row {
            Text(text = "Created by: ", color = fontColor)
            Text(text = combo.createdBy.ifEmpty { profile }, color = fontColor)
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
    val size = 40.dp

    Image(
        painter = painterResource(id = moveId),
        contentDescription = move,
        modifier = modifier
            .padding(horizontal = 4.dp)
            .size(size * uiScale)
    )
}

@Composable
fun TextMove(
    input: MoveEntry,
    color: Color,
    uiScale: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .clip(RoundedCornerShape(25.dp))
            .background(color)
    ) {
        Text(
            text = input.moveName,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                color = if (color == Color.White || color == Color.Green) Color.Black else Color.White,
                shadow = if (color != Color.White)
                    Shadow(color = Color.Black.copy(alpha = 0.5f), offset = Offset(2f, 2f), blurRadius = 4f)
                else
                    Shadow()
            ),
            modifier = modifier
                .padding(horizontal = 1.dp, vertical = 0.dp)
        )
    }
}

@Composable
fun MoveBreak(
    uiScale: Float,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Filled.PlayArrow,
        contentDescription = "",
        tint = Color.Cyan,
        modifier = modifier.size((20 * uiScale).dp)
    )
}

@Composable
fun MiscInput(
    move: MoveEntry,
    modifier: Modifier = Modifier
) {
    Text(
        text = move.notation,
        fontSize = 40.sp,
        color = MaterialTheme.colorScheme.onBackground,

        )
}
