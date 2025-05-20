package com.example.fightingflow.util.characterAndMoveData

import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.ImmutableList

val generalMoves = ImmutableList(
    listOf(

        // Break
        MoveEntry(moveName = "break", notation = "►", moveType = "Break", associatedCharacter = "Generic"),

        // Movements
        MoveEntry(moveName = "forward", notation = "f", moveType = "Movement", associatedCharacter = "Generic"),
        MoveEntry(moveName = "forward_dash", notation = "F", moveType = "Movement", associatedCharacter = "Generic"),
        MoveEntry(moveName = "down_forward", notation = "d/f", moveType = "Movement", associatedCharacter = "Generic"),
        MoveEntry(moveName = "down", notation = "d", moveType = "Movement", associatedCharacter = "Generic"),
        MoveEntry(moveName = "down_back", notation = "d/b", moveType = "Movement", associatedCharacter = "Generic"),
        MoveEntry(moveName = "back", notation = "b", moveType = "Movement", associatedCharacter = "Generic"),
        MoveEntry(moveName = "up_back", notation = "u/b", moveType = "Movement", associatedCharacter = "Generic"),
        MoveEntry(moveName = "up", notation = "u", moveType = "Movement", associatedCharacter = "Generic"),
        MoveEntry(moveName = "up_forward", notation = "u/f", moveType = "Movement", associatedCharacter = "Generic"),
        MoveEntry(moveName = "neutral", notation = "n", moveType = "Movement", associatedCharacter = "Generic"),

        // Inputs
        MoveEntry(moveName = "one", notation = "1", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "two", notation = "2", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "three", notation = "3", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "four", notation = "4", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "one_plus_two", notation = "1+2", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "three_plus_four", notation = "3+4", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "one_plus_three", notation = "1+3", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "two_plus_four", notation = "2+4", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "one_plus_four", notation = "1+4", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "two_plus_three", notation = "2+3", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "one_plus_two_plus_three", notation = "1+2+3", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "one_plus_two_plus_four", notation = "1+2+4", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "one_plus_three_plus_four", notation = "1+3+4", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "two_plus_three_plus_four", notation = "2+3+4", moveType = "Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "one_plus_two_plus_three_plus_four", notation = "1+2+3+4", moveType = "Input", associatedCharacter = "Generic"),

        // Modifiers
        MoveEntry(moveName = "Hold", notation = "h", moveType = "Modifier", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Hold Max", notation = "H", moveType = "Modifier", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Delay", notation = "delay", moveType = "Modifier", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Just Frame", notation = "j/f", moveType = "Modifier", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Slide", notation = "(s)", moveType = "Modifier", associatedCharacter = "Generic"),

        // Common Stances
        MoveEntry(moveName = "Side Switch", notation = "s/s", moveType = "Common", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Back Turned", notation = "b/t", moveType = "Common", associatedCharacter = "Generic"),
        MoveEntry(moveName = "While Standing", notation = "w/s", moveType = "Common", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Crouch", notation = "crouch", moveType = "Common", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Dash", notation = "dash", moveType = "Common", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Sidestep Left", notation = "s/l", moveType = "Common", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Sidestep Right", notation = "s/r", moveType = "Common", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Low Parry", notation = "l/p", moveType = "Common", associatedCharacter = "Generic"),

        // Mechanics
        MoveEntry(moveName = "Heat Burst", notation = "h/burst", moveType = "Mechanics Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Heat Dash", notation = "h/dash", moveType = "Mechanic Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "During Heat", notation = "in heat", moveType = "Mechanics Input", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Rage Art", notation = "rage", moveType = "Mechanics Input", associatedCharacter = "Generic"),

        // Stage
        MoveEntry(moveName = "Wall Splat", notation = "w/splat", moveType = "Stage", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Wall Break", notation = "w/break", moveType = "Stage", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Wall Blast", notation = "w/blast", moveType = "Stage", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Wall Bounce", notation = "w/bounce", moveType = "Stage", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Floor Break", notation = "f/break", moveType = "Stage", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Floor Blast", notation = "f/blast", moveType = "Stage", associatedCharacter = "Generic"),
        MoveEntry(moveName = "Balcony Break", notation = "b/break", moveType = "Stage", associatedCharacter = "Generic"),

        // Mishima
        MoveEntry(moveName = "Wind God Step", notation = "WGS", moveType = "Mishima", associatedCharacter = "Mishima"),
        MoveEntry(moveName = "Wind God Fist", notation = "WGF", moveType = "Mishima", associatedCharacter = "Mishima"),
        MoveEntry(moveName = "Thunder God Fist", notation = "TGF", moveType = "Mishima", associatedCharacter = "Mishima"),
        MoveEntry(moveName = "Electric Wind God Fist", notation = "EWGF", moveType = "Mishima", associatedCharacter = "Mishima"),
        MoveEntry(moveName = "Omen Thunder God Fist", notation = "OTGF", moveType = "Mishima", associatedCharacter = "Mishima"),
        MoveEntry(moveName = "Electric Thunder God Fist", notation = "ETGF", moveType = "Mishima", associatedCharacter = "Mishima"),
    )
)