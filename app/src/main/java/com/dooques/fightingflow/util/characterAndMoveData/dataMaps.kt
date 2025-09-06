package com.dooques.fightingflow.util.characterAndMoveData

import com.dooques.fightingflow.util.characterAndMoveData.customInputLayouts.arcSysMoves
import com.dooques.fightingflow.util.characterAndMoveData.customInputLayouts.movement
import com.dooques.fightingflow.util.characterAndMoveData.customInputLayouts.numpadNotationMoves
import com.dooques.fightingflow.util.characterAndMoveData.customInputLayouts.tagFighterMoves
import com.dooques.fightingflow.util.characterAndMoveData.customInputLayouts.virtuaFighterMoves

val moveMap = mapOf(
    "movement" to mapOf(
        "standard" to mapOf(
            "standard" to movement,
            "numpad" to numpadNotationMoves
        )),
    "inputs" to mapOf(
        "standard" to mapOf(
            "Tekken" to moveEntriesT8,
            "Mortal Kombat" to moveEntriesMK1,
            "Street Fighter" to moveEntriesSF6,
            "Common" to commonMoves
        ),
        "console" to mapOf(
            "playStation" to playstationInputs,
            "xbox" to xboxInputs,
            "nintendo" to nintendoInputs
        ),
        "custom" to mapOf(
            "ArcSys" to arcSysMoves,
            "Tag Fighter" to tagFighterMoves,
            "Virtua Fighter" to virtuaFighterMoves
        )
    ),
    "character" to mapOf(
        "standard" to mapOf(
            "Mortal Kombat" to characterMoveEntriesMK1,
            "Tekken" to characterMoveEntriesT8,
            "Street Fighter" to characterMoveEntriesSF6
        )
    )
)

val characterMap = mapOf(
    "Tekken 8" to characterEntriesT8,
    "Mortal Kombat 1" to characterEntriesMK1,
    "Street Fighter 6" to characterEntriesSF6
)

