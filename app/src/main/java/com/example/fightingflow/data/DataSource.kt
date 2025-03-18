package com.example.fightingflow.data

import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.CharacterModel
import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.MoveString

object DataSource {
    val menuItems = listOf(
        R.string.combos,
        R.string.flowcharts,
        R.string.share_your_moves,
        R.string.learn_the_notation

    )

    val characterData = listOf(
        CharacterModel(R.drawable.kazuya_sprite, R.string.kazuya),
        CharacterModel(R.drawable.reina_sprite, R.string.reina),
        CharacterModel(R.drawable.victor_sprite, R.string.victor),
        CharacterModel(R.drawable.panda_sprite, R.string.panda),
        CharacterModel(R.drawable.zafina_sprite, R.string.zafina),
        CharacterModel(R.drawable.lee_sprite, R.string.lee),
        CharacterModel(R.drawable.alisa_sprite, R.string.alisa),
        CharacterModel(R.drawable.jin_sprite, R.string.devil_jin),
        CharacterModel(R.drawable.feng_sprite, R.string.feng),
        CharacterModel(R.drawable.dragunov_sprite, R.string.dragunov),
        CharacterModel(R.drawable.shaheen_sprite, R.string.shaheen),
        CharacterModel(R.drawable.steve_sprite, R.string.steve),
        CharacterModel(R.drawable.leo_sprite, R.string.leo),
        CharacterModel(R.drawable.yoshimitsu_sprite, R.string.yoshimitsu),
        CharacterModel(R.drawable.kuma_sprite, R.string.kuma),
        CharacterModel(R.drawable.azucena_sprite, R.string.azucena),
        CharacterModel(R.drawable.raven_sprite, R.string.raven),
        CharacterModel(R.drawable.jin_sprite, R.string.jin),
        CharacterModel(R.drawable.paul_sprite, R.string.paul),
        CharacterModel(R.drawable.law_sprite, R.string.law),
        CharacterModel(R.drawable.nina_sprite, R.string.nina),
        CharacterModel(R.drawable.jack8_sprite, R.string.jack_8),
        CharacterModel(R.drawable.king_sprite, R.string.king),
        CharacterModel(R.drawable.lars_sprite, R.string.lars),
        CharacterModel(R.drawable.jun_sprite, R.string.jun),
        CharacterModel(R.drawable.xiaoyu_sprite, R.string.xiaoyu),
        CharacterModel(R.drawable.asuka_sprite, R.string.asuka),
        CharacterModel(R.drawable.leroy_sprite, R.string.leroy),
        CharacterModel(R.drawable.lili_sprite, R.string.lili),
        CharacterModel(R.drawable.bryan_sprite, R.string.bryan),
        CharacterModel(R.drawable.hwoarang_sprite, R.string.hwoarang),
        CharacterModel(R.drawable.claudio_sprite, R.string.claudio),
        CharacterModel(R.drawable.t8_logo, R.string.eddy),
        CharacterModel(R.drawable.t8_logo, R.string.lidia),
        CharacterModel(R.drawable.t8_logo, R.string.heihachi),
        CharacterModel(R.drawable.t8_logo, R.string.clive)
    )

    val combo = ComboDisplay(
        comboId = "",
        character = "Reina",
        damage = 50,
        createdBy = "Sam",
        moves = listOf(
            MoveString("ewgf", listOf("ewgf"), false, "Mishima"),
            MoveString("b2", listOf("back", "two"), false, "input"),
            MoveString("ff3+4", listOf("forward", "forward", "three_plus_four"), false, "input"),
            MoveString("df3", listOf("down_forward", "one"), false, "input"),
            MoveString("f24", listOf("forward", "two", "four"), false, "input"),
            MoveString("Sentai", listOf("Sentai"), false, "character"),
            MoveString("1+2", listOf("one_plus_two"), false, "input"),
            MoveString("4", listOf("four"), false, "input")
        )
    )
}



