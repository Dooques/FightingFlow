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

val moveMap = mapOf(
    "movement" to mapOf(
        "standard" to movement,
        "numpad" to numpadNotationMoves
    ),
    "inputs" to mapOf(
        "standard" to mapOf(
            "Tekken" to tekken8Moves,
            "Mortal Kombat" to mk1Moves,
            "Street Fighter" to streetFighter6Moves,
            "Common" to commonMoves),
        "console" to mapOf(
            "PlayStation" to playstationInputs,
            "Xbox" to xboxInputs,
            "Nintendo" to nintendoInputs
        ),
        "custom" to mapOf(
            "ArcSys" to arcSysMoves,
            "Tag Fighter" to tagFighterMoves,
            "Virtua Fighter" to virtuaFighterMoves
        )
    ),
    "character" to mapOf(
        "Mortal Kombat" to mk1CharacterMoves,
        "Tekken" to tekken8CharacterMoves,
        "Street Fighter" to streetFighter6CharacterMoves
    )
)

val characterMap = mapOf(
    "Tekken 8" to tekken8Characters,
    "Mortal Kombat 1" to mortalKombat1Characters,
    "Street Fighter VI" to streetFighter6Characters
)

