package com.example.fightingflow.ui.comboCreationScreen

import com.example.fightingflow.data.datastore.CharacterDsRepository.Companion.characterName

val mishima = listOf("Reina", "Heihachi", "Jin", "Kazuya", "Devil Jin")

val mishimaSelectorLayout = listOf(
    "Text Combo", "Buttons", "Divider", "Radio Buttons", "Damage", "Divider", "Inputs Title", "Movement", "Input", "Divider",
    "Stances", "Common", "Divider", "Mishima Moves", "Mishima", "Divider", "${characterName}'s Stances", "Character",
    "Divider", "Mechanics", "Mechanics Input", "Stage",
)

val selectorLayout = listOf(
    "Text Combo", "Buttons", "Divider", "Radio Buttons", "Damage", "Divider", "Inputs", "Movement", "Input", "Divider",
    "Stances", "Common", "Divider", "${characterName}'s Stances", "Character",
    "Divider", "Mechanics", "Mechanics Input", "Stage",
)