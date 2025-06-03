package com.example.fightingflow.ui.comboDisplayScreen.inputConverter

import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.characterAndMoveData.mk1MoveList
import com.example.fightingflow.util.characterAndMoveData.nintendoInputs
import com.example.fightingflow.util.characterAndMoveData.playstationInputs
import com.example.fightingflow.util.characterAndMoveData.streetFighterVIMoves
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
    "LP" -> playstationInputs[0]
    "MP" -> playstationInputs[1]
    "LK" -> playstationInputs[2]
    "MK" -> playstationInputs[3]
    "HP" -> playstationInputs[4]
    "HK" -> playstationInputs[5]
    else -> move
}

fun sf6ClassicToXboxInputs(move: MoveEntry) = when (move.moveName) {
    "LP" -> xboxInputs[0]
    "MP" -> xboxInputs[1]
    "LK" -> xboxInputs[2]
    "MK" -> xboxInputs[3]
    "HP" -> xboxInputs[4]
    "HK" -> xboxInputs[5]
    else -> move
}

fun sf6ClassicToNintendoInputs(move: MoveEntry) = when (move.moveName) {
    "LP" -> nintendoInputs[0]
    "MP" -> nintendoInputs[1]
    "LK" -> nintendoInputs[2]
    "MK" -> nintendoInputs[3]
    "HP" -> nintendoInputs[4]
    "HK" -> nintendoInputs[5]
    else -> move
}

fun sf6ModernToPlaystationInputs(move: MoveEntry) = when (move.moveName) {
    "L" -> playstationInputs[0]
    "S" -> playstationInputs[1]
    "M" -> playstationInputs[2]
    "H" -> playstationInputs[3]
    "Parry" -> playstationInputs[4]
    "Assist" -> playstationInputs[5]
    "Drive Impact" -> playstationInputs[7]
    "Throw" -> playstationInputs[8]
    else -> move
}

fun sf6ModernToXboxInputs(move: MoveEntry) = when (move.moveName) {
    "L" -> xboxInputs[0]
    "S" -> xboxInputs[1]
    "M" -> xboxInputs[2]
    "H" -> xboxInputs[3]
    "Parry" -> xboxInputs[4]
    "Assist" -> xboxInputs[5]
    "Drive Impact" -> xboxInputs[7]
    "Throw" -> xboxInputs[8]
    else -> move
}

fun sf6ModernToNintendoInputs(move: MoveEntry) = when (move.moveName) {
    "L" -> nintendoInputs[0]
    "S" -> nintendoInputs[1]
    "M" -> nintendoInputs[2]
    "H" -> nintendoInputs[3]
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
    "square" -> streetFighterVIMoves.first{ it.moveName == "LP" }
    "triangle" -> streetFighterVIMoves.first{ it.moveName == "MP" }
    "cross" -> streetFighterVIMoves.first{ it.moveName == "LK" }
    "circle" -> streetFighterVIMoves.first{ it.moveName == "MK" }
    "R1" -> streetFighterVIMoves.first{ it.moveName == "HP" }
    "L1" -> streetFighterVIMoves.first{ it.moveName == "HK" }
    else -> move
}

fun playstationToSF6Modern(move: MoveEntry) = when (move.moveName) {
    "square" -> streetFighterVIMoves.first{ it.moveName == "L" }
    "triangle" -> streetFighterVIMoves.first{ it.moveName == "S" }
    "cross" -> streetFighterVIMoves.first{ it.moveName == "M" }
    "circle" -> streetFighterVIMoves.first{ it.moveName == "H" }
    "R1" -> streetFighterVIMoves.first{ it.moveName == "Parry" }
    "R2" -> streetFighterVIMoves.first{ it.moveName == "Assist" }
    "L1" -> streetFighterVIMoves.first{ it.moveName == "Drive Impact" }
    "L2" -> streetFighterVIMoves.first{ it.moveName == "Throw" }
    else -> move
}

fun xboxToSF6Classic(move: MoveEntry) = when (move.moveName) {
    "x_xbox" -> streetFighterVIMoves.first{ it.moveName == "LP" }
    "y_xbox" -> streetFighterVIMoves.first{ it.moveName == "MP" }
    "a_xbox" -> streetFighterVIMoves.first{ it.moveName == "LK" }
    "b_xbox" -> streetFighterVIMoves.first{ it.moveName == "MK" }
    "RB" -> streetFighterVIMoves.first{ it.moveName == "HP" }
    "LB" -> streetFighterVIMoves.first{ it.moveName == "HK" }
    else -> move
}

fun xboxToSF6Modern(move: MoveEntry) = when (move.moveName) {
    "x_xbox" -> streetFighterVIMoves.first{ it.moveName == "L" }
    "y_xbox" -> streetFighterVIMoves.first{ it.moveName == "S" }
    "a_xbox" -> streetFighterVIMoves.first{ it.moveName == "M" }
    "b_xbox" -> streetFighterVIMoves.first{ it.moveName == "H" }
    "RB" -> streetFighterVIMoves.first{ it.moveName == "Parry" }
    "RT" -> streetFighterVIMoves.first{ it.moveName == "Assist" }
    "LB" -> streetFighterVIMoves.first{ it.moveName == "Drive Impact" }
    "LT" -> streetFighterVIMoves.first{ it.moveName == "Throw" }
    else -> move
}

fun nintendoToSF6Classic(move: MoveEntry) = when (move.moveName) {
    "y_nintendo" -> streetFighterVIMoves.first{ it.moveName == "LP" }
    "x_nintendo" -> streetFighterVIMoves.first{ it.moveName == "MP" }
    "b_nintendo" -> streetFighterVIMoves.first{ it.moveName == "LK" }
    "a_nintendo" -> streetFighterVIMoves.first{ it.moveName == "MK" }
    "R" -> streetFighterVIMoves.first{ it.moveName == "HP" }
    "L" -> streetFighterVIMoves.first{ it.moveName == "HK" }
    else -> move
}

fun nintendoToSF6Modern(move: MoveEntry) = when (move.moveName) {
    "y_nintendo" -> streetFighterVIMoves.first{ it.moveName == "L" }
    "x_nintendo" -> streetFighterVIMoves.first{ it.moveName == "S" }
    "b_nintendo" -> streetFighterVIMoves.first{ it.moveName == "M" }
    "a_nintendo" -> streetFighterVIMoves.first{ it.moveName == "H" }
    "R" -> streetFighterVIMoves.first{ it.moveName == "Parry" }
    "ZR" -> streetFighterVIMoves.first{ it.moveName == "Assist" }
    "L" -> streetFighterVIMoves.first{ it.moveName == "Drive Impact" }
    "ZL" -> streetFighterVIMoves.first{ it.moveName == "Throw" }
    else -> move
}

fun playStationToMK1(move: MoveEntry) = when (move.moveName) {
    "square" -> mk1MoveList.first{ it.moveName == "one" }
    "triangle" -> mk1MoveList.first{ it.moveName == "two" }
    "cross" -> mk1MoveList.first{ it.moveName == "three" }
    "circle" -> mk1MoveList.first{ it.moveName == "four" }
    "R1" -> mk1MoveList.first{ it.moveName == "Kameo" }
    "R2" -> mk1MoveList.first{ it.moveName == "Block" }
    "L1" -> mk1MoveList.first{ it.moveName == "Throw" }
    "L2" -> mk1MoveList.first{ it.moveName == "Stance" }
    else -> move
}

fun xboxToMK1(move: MoveEntry) = when (move.moveName) {
    "x_xbox" -> mk1MoveList.first{ it.moveName == "one" }
    "y_xbox" -> mk1MoveList.first{ it.moveName == "two" }
    "a_xbox" -> mk1MoveList.first{ it.moveName == "three" }
    "b_xbox" -> mk1MoveList.first{ it.moveName == "four" }
    "RB" -> mk1MoveList.first{ it.moveName == "Kameo" }
    "RT" -> mk1MoveList.first{ it.moveName == "Block" }
    "LB" -> mk1MoveList.first{ it.moveName == "Throw" }
    "LT" -> mk1MoveList.first{ it.moveName == "Stance" }
    else -> move
}

fun nintendoToMK1(move: MoveEntry) = when (move.moveName) {
    "y_nintendo" -> mk1MoveList.first{ it.moveName == "one" }
    "x_nintendo" -> mk1MoveList.first{ it.moveName == "two" }
    "b_nintendo" -> mk1MoveList.first{ it.moveName == "three" }
    "a_nintendo" -> mk1MoveList.first{ it.moveName == "four" }
    "R" -> mk1MoveList.first{ it.moveName == "Kameo" }
    "ZR" -> mk1MoveList.first{ it.moveName == "Block" }
    "L" -> mk1MoveList.first{ it.moveName == "Throw" }
    "ZL" -> mk1MoveList.first{ it.moveName == "Stance" }
    else -> move
}
