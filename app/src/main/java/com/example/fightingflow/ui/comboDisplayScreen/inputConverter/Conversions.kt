package com.example.fightingflow.ui.comboDisplayScreen.inputConverter

import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.characterAndMoveData.mk1Moves
import com.example.fightingflow.util.characterAndMoveData.nintendoInputs
import com.example.fightingflow.util.characterAndMoveData.playstationInputs
import com.example.fightingflow.util.characterAndMoveData.streetFighter6Moves
import com.example.fightingflow.util.characterAndMoveData.tekken8CharacterMoves
import com.example.fightingflow.util.characterAndMoveData.xboxInputs

fun mk1ToPlaystation(move: MoveEntry) = when (move.moveName) {
    "one" -> playstationInputs[0]
    "two" -> playstationInputs[1]
    "three" -> playstationInputs[2]
    "four" -> playstationInputs[3]
    "Kameo" -> playstationInputs[4]
    "Block" -> playstationInputs[5]
    "Throw" -> playstationInputs[6]
    "Stance" -> playstationInputs[7]
    else -> move
}

fun mk1ToXbox(move: MoveEntry) = when (move.moveName) {
    "one" -> xboxInputs[0]
    "two" -> xboxInputs[1]
    "three" -> xboxInputs[2]
    "four" -> xboxInputs[3]
    "Kameo" -> xboxInputs[4]
    "Block" -> xboxInputs[5]
    "Throw" -> xboxInputs[6]
    "Stance" -> xboxInputs[7]
    else -> move
}

fun mk1ToNintendo(move: MoveEntry) = when (move.moveName) {
    "one" -> nintendoInputs[0]
    "two" -> nintendoInputs[1]
    "three" -> nintendoInputs[2]
    "four" -> nintendoInputs[3]
    "Kameo" -> nintendoInputs[4]
    "Block" -> nintendoInputs[5]
    "Throw" -> nintendoInputs[6]
    "Stance" -> nintendoInputs[7]
    else -> move
}

fun sf6ClassicToPlaystationInputs(move: MoveEntry) = when (move.moveName) {
    "lp" -> playstationInputs[0]
    "mp" -> playstationInputs[1]
    "lk" -> playstationInputs[2]
    "mk" -> playstationInputs[3]
    "hp" -> playstationInputs[4]
    "hk" -> playstationInputs[5]
    else -> move
}

fun sf6ClassicToXboxInputs(move: MoveEntry) = when (move.moveName) {
    "lp" -> xboxInputs[0]
    "mp" -> xboxInputs[1]
    "lk" -> xboxInputs[2]
    "mk" -> xboxInputs[3]
    "hp" -> xboxInputs[4]
    "hk" -> xboxInputs[5]
    else -> move
}

fun sf6ClassicToNintendoInputs(move: MoveEntry) = when (move.moveName) {
    "lp" -> nintendoInputs[0]
    "mp" -> nintendoInputs[1]
    "lk" -> nintendoInputs[2]
    "mk" -> nintendoInputs[3]
    "hp" -> nintendoInputs[4]
    "hk" -> nintendoInputs[5]
    else -> move
}

fun sf6ModernToPlaystationInputs(move: MoveEntry) = when (move.moveName) {
    "l" -> playstationInputs[0]
    "s" -> playstationInputs[1]
    "m" -> playstationInputs[2]
    "h" -> playstationInputs[3]
    "Parry" -> playstationInputs[4]
    "Assist" -> playstationInputs[5]
    "Drive Impact" -> playstationInputs[7]
    "Throw" -> playstationInputs[8]
    else -> move
}

fun sf6ModernToXboxInputs(move: MoveEntry) = when (move.moveName) {
    "l" -> xboxInputs[0]
    "s" -> xboxInputs[1]
    "m" -> xboxInputs[2]
    "h" -> xboxInputs[3]
    "Parry" -> xboxInputs[4]
    "Assist" -> xboxInputs[5]
    "Drive Impact" -> xboxInputs[7]
    "Throw" -> xboxInputs[8]
    else -> move
}

fun sf6ModernToNintendoInputs(move: MoveEntry) = when (move.moveName) {
    "l" -> nintendoInputs[0]
    "s" -> nintendoInputs[1]
    "m" -> nintendoInputs[2]
    "h" -> nintendoInputs[3]
    "Parry" -> nintendoInputs[4]
    "Assist" -> nintendoInputs[5]
    "Drive Impact" -> nintendoInputs[7]
    "Throw" -> nintendoInputs[8]
    else -> move
}

fun tekkenToPlaystationInputs(move: MoveEntry): MoveEntry {
    return when (move.moveName) {
        "one" -> playstationInputs[0]
        "two" -> playstationInputs[1]
        "three" -> playstationInputs[2]
        "four" -> playstationInputs[3]
        "two_plus_three" -> playstationInputs[4]
        "three_plus_four" -> playstationInputs[5]
        "Heat Burst" -> playstationInputs[6]
        "one_plus_four" -> playstationInputs[7]
        "one_plus_two" -> playstationInputs[8]
        "Rage Art" -> playstationInputs[9]
        else -> move
    }
}

fun tekkenToXboxInputs(move: MoveEntry): MoveEntry {
    return when (move.moveName) {
        "one" -> xboxInputs[0]
        "two" -> xboxInputs[1]
        "three" -> xboxInputs[2]
        "four" -> xboxInputs[3]
        "two_plus_three" -> xboxInputs[4]
        "three_plus_four" -> xboxInputs[5]
        "Heat Burst" -> xboxInputs[6]
        "one_plus_four" -> xboxInputs[7]
        "one_plus_two" -> xboxInputs[8]
        "Rage Art" -> xboxInputs[9]
        else -> move
    }
}

fun tekkenToNintendoInputs(move: MoveEntry): MoveEntry {
    return when (move.moveName) {
        "one" -> nintendoInputs[0]
        "two" -> nintendoInputs[1]
        "three" -> nintendoInputs[2]
        "four" -> nintendoInputs[3]
        "two_plus_three" -> nintendoInputs[4]
        "three_plus_four" -> nintendoInputs[5]
        "Heat Burst" -> nintendoInputs[6]
        "one_plus_four" -> nintendoInputs[7]
        "one_plus_two" -> nintendoInputs[8]
        "Rage Art" -> nintendoInputs[9]
        else -> move
    }
}

fun playStationToT8(move: MoveEntry) = when (move.moveName) {
    "square" -> tekken8CharacterMoves.first{ it.moveName == "one" }
    "triangle" -> tekken8CharacterMoves.first{ it.moveName == "two" }
    "cross" -> tekken8CharacterMoves.first{ it.moveName == "three" }
    "circle" -> tekken8CharacterMoves.first{ it.moveName == "four" }
    "R1"-> tekken8CharacterMoves.first{ it.moveName == "two_plus_three" }
    "R2" -> tekken8CharacterMoves.first{ it.moveName == "three_plus_four" }
    "R3" -> tekken8CharacterMoves.first{ it.moveName == "Heat Burst" }
    "L1" -> tekken8CharacterMoves.first{ it.moveName == "one_plus_four" }
    "L2" -> tekken8CharacterMoves.first{ it.moveName == "one_plus_two" }
    "L3" -> tekken8CharacterMoves.first{ it.moveName == "Rage Art" }
    else -> move
}

fun nintendoToT8(move: MoveEntry) = when (move.moveName) {
    "y_nintendo" -> tekken8CharacterMoves.first{ it.moveName == "one" }
    "x_nintendo" -> tekken8CharacterMoves.first{ it.moveName == "two" }
    "b_nintendo" -> tekken8CharacterMoves.first{ it.moveName == "three" }
    "a_nintendo" -> tekken8CharacterMoves.first{ it.moveName == "four" }
    "R" -> tekken8CharacterMoves.first{ it.moveName == "two_plus_three" }
    "ZR" -> tekken8CharacterMoves.first{ it.moveName == "three_plus_four" }
    "RS" -> tekken8CharacterMoves.first{ it.moveName == "Heat Burst" }
    "L" -> tekken8CharacterMoves.first{ it.moveName == "one_plus_four" }
    "ZL" -> tekken8CharacterMoves.first{ it.moveName == "one_plus_two" }
    "LS" -> tekken8CharacterMoves.first{ it.moveName == "Rage Art" }
    else -> move
}

fun xboxToT8(move: MoveEntry) = when (move.moveName) {
    "x_xbox" -> tekken8CharacterMoves.first{ it.moveName == "one" }
    "y_xbox" -> tekken8CharacterMoves.first{ it.moveName == "two" }
    "a_xbox" -> tekken8CharacterMoves.first{ it.moveName == "three" }
    "b_xbox" -> tekken8CharacterMoves.first{ it.moveName == "four" }
    "RB" -> tekken8CharacterMoves.first{ it.moveName == "two_plus_three" }
    "RT" -> tekken8CharacterMoves.first{ it.moveName == "three_plus_four" }
    "RS" -> tekken8CharacterMoves.first{ it.moveName == "Heat Burst" }
    "LB" -> tekken8CharacterMoves.first{ it.moveName == "one_plus_four" }
    "LT" -> tekken8CharacterMoves.first{ it.moveName == "one_plus_two" }
    "LS" -> tekken8CharacterMoves.first{ it.moveName == "Rage Art" }
    else -> move
}

fun playstationToSF6Classic(move: MoveEntry) = when (move.moveName) {
    "square" -> streetFighter6Moves.first{ it.moveName == "lp" }
    "triangle" -> streetFighter6Moves.first{ it.moveName == "mp" }
    "cross" -> streetFighter6Moves.first{ it.moveName == "lk" }
    "circle" -> streetFighter6Moves.first{ it.moveName == "mk" }
    "R1" -> streetFighter6Moves.first{ it.moveName == "hp" }
    "L1" -> streetFighter6Moves.first{ it.moveName == "hk" }
    else -> move
}

fun playstationToSF6Modern(move: MoveEntry) = when (move.moveName) {
    "square" -> streetFighter6Moves.first{ it.moveName == "l" }
    "triangle" -> streetFighter6Moves.first{ it.moveName == "s" }
    "cross" -> streetFighter6Moves.first{ it.moveName == "m" }
    "circle" -> streetFighter6Moves.first{ it.moveName == "h" }
    "R1" -> streetFighter6Moves.first{ it.moveName == "Parry" }
    "R2" -> streetFighter6Moves.first{ it.moveName == "Assist" }
    "L1" -> streetFighter6Moves.first{ it.moveName == "Drive Impact" }
    "L2" -> streetFighter6Moves.first{ it.moveName == "Throw" }
    else -> move
}

fun xboxToSF6Classic(move: MoveEntry) = when (move.moveName) {
    "x_xbox" -> streetFighter6Moves.first{ it.moveName == "lp" }
    "y_xbox" -> streetFighter6Moves.first{ it.moveName == "mp" }
    "a_xbox" -> streetFighter6Moves.first{ it.moveName == "lk" }
    "b_xbox" -> streetFighter6Moves.first{ it.moveName == "mk" }
    "RB" -> streetFighter6Moves.first{ it.moveName == "hp" }
    "LB" -> streetFighter6Moves.first{ it.moveName == "hk" }
    else -> move
}

fun xboxToSF6Modern(move: MoveEntry) = when (move.moveName) {
    "x_xbox" -> streetFighter6Moves.first{ it.moveName == "l" }
    "y_xbox" -> streetFighter6Moves.first{ it.moveName == "s" }
    "a_xbox" -> streetFighter6Moves.first{ it.moveName == "m" }
    "b_xbox" -> streetFighter6Moves.first{ it.moveName == "h" }
    "RB" -> streetFighter6Moves.first{ it.moveName == "Parry" }
    "RT" -> streetFighter6Moves.first{ it.moveName == "Assist" }
    "LB" -> streetFighter6Moves.first{ it.moveName == "Drive Impact" }
    "LT" -> streetFighter6Moves.first{ it.moveName == "Throw" }
    else -> move
}

fun nintendoToSF6Classic(move: MoveEntry) = when (move.moveName) {
    "y_nintendo" -> streetFighter6Moves.first{ it.moveName == "lp" }
    "x_nintendo" -> streetFighter6Moves.first{ it.moveName == "mp" }
    "b_nintendo" -> streetFighter6Moves.first{ it.moveName == "lk" }
    "a_nintendo" -> streetFighter6Moves.first{ it.moveName == "mk" }
    "R" -> streetFighter6Moves.first{ it.moveName == "hp" }
    "L" -> streetFighter6Moves.first{ it.moveName == "hk" }
    else -> move
}

fun nintendoToSF6Modern(move: MoveEntry) = when (move.moveName) {
    "y_nintendo" -> streetFighter6Moves.first{ it.moveName == "l" }
    "x_nintendo" -> streetFighter6Moves.first{ it.moveName == "s" }
    "b_nintendo" -> streetFighter6Moves.first{ it.moveName == "m" }
    "a_nintendo" -> streetFighter6Moves.first{ it.moveName == "h" }
    "R" -> streetFighter6Moves.first{ it.moveName == "Parry" }
    "ZR" -> streetFighter6Moves.first{ it.moveName == "Assist" }
    "L" -> streetFighter6Moves.first{ it.moveName == "Drive Impact" }
    "ZL" -> streetFighter6Moves.first{ it.moveName == "Throw" }
    else -> move
}

fun playStationToMK1(move: MoveEntry) = when (move.moveName) {
    "square" -> mk1Moves.first{ it.moveName == "one" }
    "triangle" -> mk1Moves.first{ it.moveName == "two" }
    "cross" -> mk1Moves.first{ it.moveName == "three" }
    "circle" -> mk1Moves.first{ it.moveName == "four" }
    "R1" -> mk1Moves.first{ it.moveName == "Kameo" }
    "R2" -> mk1Moves.first{ it.moveName == "Block" }
    "L1" -> mk1Moves.first{ it.moveName == "Throw" }
    "L2" -> mk1Moves.first{ it.moveName == "Stance" }
    else -> move
}

fun xboxToMK1(move: MoveEntry) = when (move.moveName) {
    "x_xbox" -> mk1Moves.first{ it.moveName == "one" }
    "y_xbox" -> mk1Moves.first{ it.moveName == "two" }
    "a_xbox" -> mk1Moves.first{ it.moveName == "three" }
    "b_xbox" -> mk1Moves.first{ it.moveName == "four" }
    "RB" -> mk1Moves.first{ it.moveName == "Kameo" }
    "RT" -> mk1Moves.first{ it.moveName == "Block" }
    "LB" -> mk1Moves.first{ it.moveName == "Throw" }
    "LT" -> mk1Moves.first{ it.moveName == "Stance" }
    else -> move
}

fun nintendoToMK1(move: MoveEntry) = when (move.moveName) {
    "y_nintendo" -> mk1Moves.first{ it.moveName == "one" }
    "x_nintendo" -> mk1Moves.first{ it.moveName == "two" }
    "b_nintendo" -> mk1Moves.first{ it.moveName == "three" }
    "a_nintendo" -> mk1Moves.first{ it.moveName == "four" }
    "R" -> mk1Moves.first{ it.moveName == "Kameo" }
    "ZR" -> mk1Moves.first{ it.moveName == "Block" }
    "L" -> mk1Moves.first{ it.moveName == "Throw" }
    "ZL" -> mk1Moves.first{ it.moveName == "Stance" }
    else -> move
}
