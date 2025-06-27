package com.example.fightingflow.util.characterAndMoveData

import androidx.compose.runtime.Immutable
import com.example.fightingflow.util.ImmutableList
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.arcSysMoves
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.movement
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.numpadNotationMoves
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.tagFighterMoves
import com.example.fightingflow.util.characterAndMoveData.customInputLayouts.virtuaFighterMoves

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
            movement,
            playstationInputs,
            xboxInputs,
            nintendoInputs,
            tekken8Moves,
            tekken8CharacterMoves,
            mk1Moves,
            mk1CharacterMoves,
            streetFighter6Moves,
            streetFighter6CharacterMoves,
            arcSysMoves,
            numpadNotationMoves,
            tagFighterMoves,
            virtuaFighterMoves
        )
    )
}


