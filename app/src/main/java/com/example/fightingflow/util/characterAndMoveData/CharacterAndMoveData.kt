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
            generalMoves,
            tekken8CharacterMoves,
            mk1MoveList,
            streetFighterVIMoves
        )
    )
}

//    val consoleButtons = listOf(
//        MoveEntry(0, "×", "Console", "Playstation"),
//        MoveEntry(0, "○", "Console", "Playstation"),
//        MoveEntry(0, "△", "Console", "Playstation"),
//        MoveEntry(0, "□", "Console", "Playstation"),
//        MoveEntry(0, "R1", "Console", "Playstation"),
//        MoveEntry(0, "R2", "Console", "Playstation"),
//        MoveEntry(0, "R3", "Console", "Playstation"),
//        MoveEntry(0, "L1", "Console", "Playstation"),
//        MoveEntry(0, "L2", "Console", "Playstation"),
//        MoveEntry(0, "L3", "Console", "Playstation"),
//        MoveEntry(0, "A", "Console", "Xbox"),
//        MoveEntry(0, "B", "Console", "Xbox"),
//        MoveEntry(0, "X", "Console", "Xbox"),
//        MoveEntry(0, "Y", "Console", "Xbox"),
//        MoveEntry(0, "RB", "Console", "Xbox"),
//        MoveEntry(0, "RT", "Console", "Xbox"),
//        MoveEntry(0, "RS", "Console", "Xbox"),
//        MoveEntry(0, "LB", "Console", "Xbox"),
//        MoveEntry(0, "LS", "Console", "Xbox"),
//        MoveEntry(0, "LS", "Console", "Xbox")
//    )
