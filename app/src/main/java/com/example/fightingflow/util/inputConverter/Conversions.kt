package com.example.fightingflow.util.inputConverter

import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.characterAndMoveData.moveEntriesMK1
import com.example.fightingflow.util.characterAndMoveData.nintendoInputs
import com.example.fightingflow.util.characterAndMoveData.playstationInputs
import com.example.fightingflow.util.characterAndMoveData.moveEntriesSF6
import com.example.fightingflow.util.characterAndMoveData.characterMoveEntriesT8
import com.example.fightingflow.util.characterAndMoveData.moveEntriesT8
import com.example.fightingflow.util.characterAndMoveData.xboxInputs
import timber.log.Timber

fun mk1ToPlaystation(move: MoveEntry) = when (move.moveName) {
    "one_mk" -> playstationInputs.first { it.moveName == "square" }
    "two_mk" -> playstationInputs.first { it.moveName == "triangle" }
    "three_mk" -> playstationInputs.first { it.moveName == "cross" }
    "four_mk" -> playstationInputs.first { it.moveName == "circle" }
    "Kameo" -> playstationInputs.first { it.moveName == "R1" }
    "Block" -> playstationInputs.first { it.moveName == "R2" }
    "Throw" -> playstationInputs.first { it.moveName == "L1" }
    "Stance" -> playstationInputs.first { it.moveName == "L2" }
    else -> move
}

fun mk1ToXbox(move: MoveEntry) = when (move.moveName) {
    "one_mk" -> xboxInputs.first { it.moveName == "x_xbox" }
    "two_mk" -> xboxInputs.first { it.moveName == "y_xbox" }
    "three_mk" -> xboxInputs.first { it.moveName == "a_xbox" }
    "four_mk" -> xboxInputs.first { it.moveName == "b_xbox" }
    "Kameo" -> xboxInputs.first { it.moveName == "LB" }
    "Block" -> xboxInputs.first { it.moveName == "LT" }
    "Throw" -> xboxInputs.first { it.moveName == "RB" }
    "Stance" -> xboxInputs.first { it.moveName == "RT" }
    else -> move
}

fun mk1ToNintendo(move: MoveEntry) = when (move.moveName) {
    "one_mk" -> nintendoInputs.first { it.moveName == "y_nintendo" }
    "two_mk" -> nintendoInputs.first { it.moveName == "x_nintendo" }
    "three_mk" -> nintendoInputs.first { it.moveName == "b_nintendo" }
    "four_mk" -> nintendoInputs.first { it.moveName == "a_nintendo" }
    "Kameo" -> nintendoInputs.first { it.moveName == "R" }
    "Block" -> nintendoInputs.first { it.moveName == "ZR" }
    "Throw" -> nintendoInputs.first { it.moveName == "L" }
    "Stance" -> nintendoInputs.first { it.moveName == "ZL"}
    else -> move
}

fun sf6ClassicToPlaystationInputs(move: MoveEntry) = when (move.moveName) {
    "lp" -> playstationInputs.first { it.moveName == "square" }
    "mp" -> playstationInputs.first { it.moveName == "triangle" }
    "hp" -> playstationInputs.first { it.moveName == "R1" }
    "lk" -> playstationInputs.first { it.moveName == "cross" }
    "mk" -> playstationInputs.first { it.moveName == "circle" }
    "hk" -> playstationInputs.first { it.moveName == "R2" }
    else -> move
}

fun sf6ClassicToXboxInputs(move: MoveEntry) = when (move.moveName) {
    "lp" -> xboxInputs.first { it.moveName == "x_xbox" }
    "mp" -> xboxInputs.first { it.moveName == "y_xbox" }
    "lk" -> xboxInputs.first { it.moveName == "RB" }
    "mk" -> xboxInputs.first { it.moveName == "a_xbox" }
    "hp" -> xboxInputs.first { it.moveName == "b_xbox" }
    "hk" -> xboxInputs.first { it.moveName == "RT" }
    else -> move
}

fun sf6ClassicToNintendoInputs(move: MoveEntry) = when (move.moveName) {
    "lp" -> nintendoInputs.first { it.moveName == "y_nintendo" }
    "mp" -> nintendoInputs.first { it.moveName == "x_nintendo" }
    "lk" -> nintendoInputs.first { it.moveName == "R" }
    "mk" -> nintendoInputs.first { it.moveName == "b_nintendo" }
    "hp" -> nintendoInputs.first { it.moveName == "a_nintendo" }
    "hk" -> nintendoInputs.first { it.moveName == "ZR" }
    else -> move
}

fun sf6ModernToPlaystationInputs(move: MoveEntry) = when (move.moveName) {
    "light" -> playstationInputs.first { it.moveName == "square" }
    "medium" -> playstationInputs.first { it.moveName == "cross" }
    "heavy" -> playstationInputs.first { it.moveName == "circle" }
    "special" -> playstationInputs.first { it.moveName == "triangle" }
    "Parry" -> playstationInputs.first { it.moveName == "R1" }
    "Assist" -> playstationInputs.first { it.moveName == "R2" }
    "Drive Impact" -> playstationInputs.first { it.moveName == "L1" }
    "Throw" -> playstationInputs.first { it.moveName == "L2" }
    else -> move
}

fun sf6ModernToXboxInputs(move: MoveEntry) = when (move.moveName) {
    "light" -> xboxInputs.first { it.moveName == "x_xbox" }
    "medium" -> xboxInputs.first { it.moveName == "a_xbox" }
    "heavy" -> xboxInputs.first { it.moveName == "b_xbox" }
    "special" -> xboxInputs.first { it.moveName == "y_xbox" }
    "Parry" -> xboxInputs.first { it.moveName == "RB" }
    "Assist" -> xboxInputs.first { it.moveName == "RT" }
    "Drive Impact" -> xboxInputs.first { it.moveName == "LB" }
    "Throw" -> xboxInputs.first { it.moveName == "LT" }
    else -> move
}

fun sf6ModernToNintendoInputs(move: MoveEntry) = when (move.moveName) {
    "light" -> nintendoInputs.first { it.moveName == "y_nintendo" }
    "medium" -> nintendoInputs.first { it.moveName == "b_nintendo" }
    "heavy" -> nintendoInputs.first { it.moveName == "a_nintendo" }
    "special" -> nintendoInputs.first { it.moveName == "x_nintendo" }
    "Parry" -> nintendoInputs.first { it.moveName == "R" }
    "Assist" -> nintendoInputs.first { it.moveName == "ZR" }
    "Drive Impact" -> nintendoInputs.first { it.moveName == "L" }
    "Throw" -> nintendoInputs.first { it.moveName == "ZL" }
    else -> move
}

fun tekkenToPlaystationInputs(move: MoveEntry): MoveEntry {
    return when (move.moveName) {
        "one" -> playstationInputs.first { it.moveName == "square" }
        "two" -> playstationInputs.first { it.moveName == "triangle" }
        "three" -> playstationInputs.first { it.moveName == "cross" }
        "four" -> playstationInputs.first { it.moveName == "circle" }
        "Heat Burst" -> playstationInputs.first { it.moveName == "R1" }
        "Rage Art" -> playstationInputs.first { it.moveName == "R2" }
        else -> move
    }
}

fun tekkenToXboxInputs(move: MoveEntry): MoveEntry {
    return when (move.moveName) {
        "one" -> xboxInputs.first { it.moveName == "x_xbox" }
        "two" -> xboxInputs.first { it.moveName == "y_xbox" }
        "three" -> xboxInputs.first { it.moveName == "a_xbox" }
        "four" -> xboxInputs.first { it.moveName == "b_xbox" }
        "Heat Burst" -> xboxInputs.first { it.moveName == "RB" }
        "Rage Art" -> xboxInputs.first { it.moveName == "RT" }
        else -> move
    }
}

fun tekkenToNintendoInputs(move: MoveEntry): MoveEntry {
    return when (move.moveName) {
        "one" -> nintendoInputs.first { it.moveName == "y_nintendo" }
        "two" -> nintendoInputs.first { it.moveName == "x_nintendo" }
        "three" -> nintendoInputs.first { it.moveName == "b_nintendo" }
        "four" -> nintendoInputs.first { it.moveName == "a_nintendo" }
        "Heat Burst" -> nintendoInputs.first { it.moveName == "R" }
        "Rage Art" -> nintendoInputs.first { it.moveName == "ZR" }
        else -> move
    }
}

fun playStationToT8(move: MoveEntry) = when (move.moveName) {
    "square" -> moveEntriesT8.first { it.moveName == "one" }
    "triangle" -> moveEntriesT8.first { it.moveName == "two" }
    "cross" -> moveEntriesT8.first { it.moveName == "three" }
    "circle" -> moveEntriesT8.first { it.moveName == "four" }
    "R1" -> moveEntriesT8.first { it.moveName == "Heat Burst" }
    "R2" -> moveEntriesT8.first { it.moveName == "Rage Art" }
    else -> move
}

fun nintendoToT8(move: MoveEntry) = when (move.moveName) {
    "y_nintendo" -> moveEntriesT8.first{ it.moveName == "one" }
    "x_nintendo" -> moveEntriesT8.first{ it.moveName == "two" }
    "b_nintendo" -> moveEntriesT8.first{ it.moveName == "three" }
    "a_nintendo" -> moveEntriesT8.first{ it.moveName == "four" }
    "R" -> moveEntriesT8.first{ it.moveName == "Heat Burst" }
    "ZR" -> moveEntriesT8.first{ it.moveName == "Rage Art" }
    else -> move
}

fun xboxToT8(move: MoveEntry) = when (move.moveName) {
    "x_xbox" -> moveEntriesT8.first{ it.moveName == "one" }
    "y_xbox" -> moveEntriesT8.first{ it.moveName == "two" }
    "a_xbox" -> moveEntriesT8.first{ it.moveName == "three" }
    "b_xbox" -> moveEntriesT8.first{ it.moveName == "four" }
    "RB" -> moveEntriesT8.first{ it.moveName == "Heat Burst" }
    "RT" -> moveEntriesT8.first{ it.moveName == "Rage Art" }
    else -> move
}

fun playstationToSF6Classic(move: MoveEntry) = when (move.moveName) {
    "square" -> moveEntriesSF6.first{ it.moveName == "lp" }
    "triangle" -> moveEntriesSF6.first{ it.moveName == "mp" }
    "cross" -> moveEntriesSF6.first{ it.moveName == "lk" }
    "circle" -> moveEntriesSF6.first{ it.moveName == "mk" }
    "R1" -> moveEntriesSF6.first{ it.moveName == "hp" }
    "L1" -> moveEntriesSF6.first{ it.moveName == "hk" }
    else -> move
}

fun playstationToSF6Modern(move: MoveEntry) = when (move.moveName) {
    "square" -> moveEntriesSF6.first{ it.moveName == "l" }
    "triangle" -> moveEntriesSF6.first{ it.moveName == "s" }
    "cross" -> moveEntriesSF6.first{ it.moveName == "m" }
    "circle" -> moveEntriesSF6.first{ it.moveName == "h" }
    "R1" -> moveEntriesSF6.first{ it.moveName == "Parry" }
    "R2" -> moveEntriesSF6.first{ it.moveName == "Assist" }
    "L1" -> moveEntriesSF6.first{ it.moveName == "Drive Impact" }
    "L2" -> moveEntriesSF6.first{ it.moveName == "Throw" }
    else -> move
}

fun xboxToSF6Classic(move: MoveEntry) = when (move.moveName) {
    "x_xbox" -> moveEntriesSF6.first{ it.moveName == "lp" }
    "y_xbox" -> moveEntriesSF6.first{ it.moveName == "mp" }
    "a_xbox" -> moveEntriesSF6.first{ it.moveName == "lk" }
    "b_xbox" -> moveEntriesSF6.first{ it.moveName == "mk" }
    "RB" -> moveEntriesSF6.first{ it.moveName == "hp" }
    "LB" -> moveEntriesSF6.first{ it.moveName == "hk" }
    else -> move
}

fun xboxToSF6Modern(move: MoveEntry) = when (move.moveName) {
    "x_xbox" -> moveEntriesSF6.first{ it.moveName == "l" }
    "y_xbox" -> moveEntriesSF6.first{ it.moveName == "s" }
    "a_xbox" -> moveEntriesSF6.first{ it.moveName == "m" }
    "b_xbox" -> moveEntriesSF6.first{ it.moveName == "h" }
    "RB" -> moveEntriesSF6.first{ it.moveName == "Parry" }
    "RT" -> moveEntriesSF6.first{ it.moveName == "Assist" }
    "LB" -> moveEntriesSF6.first{ it.moveName == "Drive Impact" }
    "LT" -> moveEntriesSF6.first{ it.moveName == "Throw" }
    else -> move
}

fun nintendoToSF6Classic(move: MoveEntry) = when (move.moveName) {
    "y_nintendo" -> moveEntriesSF6.first{ it.moveName == "lp" }
    "x_nintendo" -> moveEntriesSF6.first{ it.moveName == "mp" }
    "b_nintendo" -> moveEntriesSF6.first{ it.moveName == "lk" }
    "a_nintendo" -> moveEntriesSF6.first{ it.moveName == "mk" }
    "R" -> moveEntriesSF6.first{ it.moveName == "hp" }
    "L" -> moveEntriesSF6.first{ it.moveName == "hk" }
    else -> move
}

fun nintendoToSF6Modern(move: MoveEntry) = when (move.moveName) {
    "y_nintendo" -> moveEntriesSF6.first{ it.moveName == "l" }
    "x_nintendo" -> moveEntriesSF6.first{ it.moveName == "s" }
    "b_nintendo" -> moveEntriesSF6.first{ it.moveName == "m" }
    "a_nintendo" -> moveEntriesSF6.first{ it.moveName == "h" }
    "R" -> moveEntriesSF6.first{ it.moveName == "Parry" }
    "ZR" -> moveEntriesSF6.first{ it.moveName == "Assist" }
    "L" -> moveEntriesSF6.first{ it.moveName == "Drive Impact" }
    "ZL" -> moveEntriesSF6.first{ it.moveName == "Throw" }
    else -> move
}

fun playStationToMK1(move: MoveEntry) = when (move.moveName) {
    "square" -> moveEntriesMK1.first{ it.moveName == "one" }
    "triangle" -> moveEntriesMK1.first{ it.moveName == "two" }
    "cross" -> moveEntriesMK1.first{ it.moveName == "three" }
    "circle" -> moveEntriesMK1.first{ it.moveName == "four" }
    "R1" -> moveEntriesMK1.first{ it.moveName == "Kameo" }
    "R2" -> moveEntriesMK1.first{ it.moveName == "Block" }
    "L1" -> moveEntriesMK1.first{ it.moveName == "Throw" }
    "L2" -> moveEntriesMK1.first{ it.moveName == "Stance" }
    else -> move
}

fun xboxToMK1(move: MoveEntry) = when (move.moveName) {
    "x_xbox" -> moveEntriesMK1.first{ it.moveName == "one" }
    "y_xbox" -> moveEntriesMK1.first{ it.moveName == "two" }
    "a_xbox" -> moveEntriesMK1.first{ it.moveName == "three" }
    "b_xbox" -> moveEntriesMK1.first{ it.moveName == "four" }
    "RB" -> moveEntriesMK1.first{ it.moveName == "Kameo" }
    "RT" -> moveEntriesMK1.first{ it.moveName == "Block" }
    "LB" -> moveEntriesMK1.first{ it.moveName == "Throw" }
    "LT" -> moveEntriesMK1.first{ it.moveName == "Stance" }
    else -> move
}

fun nintendoToMK1(move: MoveEntry) = when (move.moveName) {
    "y_nintendo" -> moveEntriesMK1.first{ it.moveName == "one" }
    "x_nintendo" -> moveEntriesMK1.first{ it.moveName == "two" }
    "b_nintendo" -> moveEntriesMK1.first{ it.moveName == "three" }
    "a_nintendo" -> moveEntriesMK1.first{ it.moveName == "four" }
    "R" -> moveEntriesMK1.first{ it.moveName == "Kameo" }
    "ZR" -> moveEntriesMK1.first{ it.moveName == "Block" }
    "L" -> moveEntriesMK1.first{ it.moveName == "Throw" }
    "ZL" -> moveEntriesMK1.first{ it.moveName == "Stance" }
    else -> move
}
