package com.example.fightingflow.data

import com.example.fightingflow.R
import com.example.fightingflow.util.CharacterAndMoveData
import com.example.fightingflow.model.CharacterEntry

val characterEntry = CharacterEntry(
    id = 0,
    name = "Reina",
    imageId = R.drawable.reina_sprite,
    fightingStyle = "Mishima",
    uniqueMoves = CharacterAndMoveData().tekkenCharacterEntries.first { it.name == "Reina" }.toString(),
    combosById = ""
)


