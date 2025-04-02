package com.example.fightingflow.data

import com.example.fightingflow.R
import com.example.fightingflow.data.database.initData.DataToAdd
import com.example.fightingflow.model.CharacterEntry

val characterEntry = CharacterEntry(
    id = 0,
    name = "Reina",
    imageId = R.drawable.reina_sprite,
    fightingStyle = "Mishima",
    uniqueMoves = DataToAdd().tekkenCharacterEntries.first { it.name == "Reina" }.toString(),
    combosById = ""
)


