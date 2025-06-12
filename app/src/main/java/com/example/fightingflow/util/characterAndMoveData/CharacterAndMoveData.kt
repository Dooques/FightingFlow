package com.example.fightingflow.util.characterAndMoveData

import androidx.compose.runtime.Immutable
import com.example.fightingflow.util.ImmutableList

@Immutable
class CharacterAndMoveData {
    val characterEntries = ImmutableList(
        listOf(
            tekken8Characters,
            streetFighter6Characters,
            mortalKombat1Characters,
        )
    )

    val moveEntries = ImmutableList(
        listOf(
            commonMoves,
            playstationInputs,
            xboxInputs,
            nintendoInputs,
            tekken8CharacterMoves,
            mk1MoveList,
            streetFighter6Moves
        )
    )
}


