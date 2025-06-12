package com.example.fightingflow.util.characterAndMoveData

import com.example.fightingflow.model.MoveEntry

val playstationInputs = listOf(
    MoveEntry(moveName = "square", notation = "□", moveType = "Console", character = "Character"),
    MoveEntry(moveName = "triangle", notation = "△", moveType = "Console", character = "Character"),
    MoveEntry( moveName = "cross", notation = "×", moveType = "Console", character = "Character"),
    MoveEntry(moveName = "circle", notation = "○", moveType = "Console", character = "Character"),
    MoveEntry(moveName = "R1", notation = "R1", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "R2", notation = "R2", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "R3", notation = "R3", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "L1", notation = "L1", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "L2", notation = "L2", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "L3", notation = "L3", moveType = "Console Text", character = "Character"),
)

val xboxInputs = listOf(
    MoveEntry(moveName = "x_xbox", notation = "X", moveType = "Console", character = "Character"),
    MoveEntry(moveName = "y_xbox", notation = "Y", moveType = "Console", character = "Character"),
    MoveEntry(moveName = "a_xbox", notation = "A", moveType = "Console", character = "Character"),
    MoveEntry(moveName = "b_xbox", notation = "B", moveType = "Console", character = "Character"),
    MoveEntry(moveName = "RB", notation = "RB", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "RT", notation = "RT", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "RS", notation = "RS", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "LB", notation = "LB", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "LT", notation = "LT", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "LS", notation = "LS", moveType = "Console Text", character = "Character"),
)

val nintendoInputs = listOf(
    MoveEntry(moveName = "y_nintendo", notation = "Y", moveType = "Console", character = "Character"),
    MoveEntry(moveName = "x_nintendo", notation = "X", moveType = "Console", character = "Character"),
    MoveEntry(moveName = "b_nintendo", notation = "B", moveType = "Console", character = "Character"),
    MoveEntry(moveName = "a_nintendo", notation = "A", moveType = "Console", character = "Character"),
    MoveEntry(moveName = "R", notation = "R", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "ZR", notation = "ZR", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "RS", notation = "RS", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "L", notation = "L", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "ZL", notation = "ZL", moveType = "Console Text", character = "Character"),
    MoveEntry(moveName = "LS", notation = "LS", moveType = "Console Text", character = "Character"),
)

val convertibleInputs = listOf(
    "one", "two", "three", "four", "two_plus_three", "three_plus_four", "Heat Burst", "one_plus_four",
    "one_plus_two", "Rage Art", "one", "two", "three", "four", "Kameo", "Block", "Throw", "Stance",
    "lp", "mp", "lk", "mk", "hp", "hk", "l", "s", "m", "h", "Parry", "Assist", "Drive Impact",
    "Throw"
)

val consoleInputs = listOf(
    "square", "triangle", "cross", "circle", "R1", "R2", "R3", "L1", "L2", "L3", "x_xbox", "y_xbox",
    "a_xbox", "b_xbox", "RB", "RT",  "RS",  "LB",  "LT",  "LS", "y_nintendo",  "x_nintendo",
    "b_nintendo",  "a_nintendo",  "R",  "ZR",  "RS",  "L",  "ZL",  "LS",
)