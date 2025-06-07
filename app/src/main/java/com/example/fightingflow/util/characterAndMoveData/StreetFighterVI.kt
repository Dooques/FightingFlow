package com.example.fightingflow.util.characterAndMoveData

import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.util.ImmutableList

val streetFighter6Characters = ImmutableList(
    listOf(
        CharacterEntry(
            name = "A.K.I.",
            imageId = R.drawable.aki,
            fightingStyle = "Snake Kung Fu",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Akuma",
            imageId = R.drawable.akuma,
            fightingStyle = " Satsui no Hado, Ansatsuken",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Blanka",
            imageId = R.drawable.blanka,
            fightingStyle = "Feral movement, electric attacks",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Cammy",
            imageId = R.drawable.cammy,
            fightingStyle = "Shadaloo fighting techniques (Shadaloo)" +
                    "Special Forces training (Delta Red)",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Chun-Li",
            imageId = R.drawable.chunli,
            fightingStyle = "Chinese martial arts/Kung Fu and Tai Chi",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Dee Jay",
            imageId = R.drawable.deejay,
            fightingStyle = "Kickboxing and break dancing",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Dhalsim",
            imageId = R.drawable.dhalsim,
            fightingStyle = "Esoteric Yoga",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "E. Honda",
            imageId = R.drawable.ehonda,
            fightingStyle = "Sumo Wrestler Chef",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Ed",
            imageId = R.drawable.ed,
            fightingStyle = "Psycho Boxing",
            game = "Street Fighter VI",
        ),CharacterEntry(
            name = "Elena",
            imageId = R.drawable.elena,
            fightingStyle = "Capoeira Master",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Guile",
            imageId = R.drawable.guile,
            fightingStyle = "Martial arts and professional wrestling",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Jamie",
            imageId = R.drawable.jamie,
            fightingStyle = "Zui Quan (Drunken Fist) & Breakdancing",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "JP",
            imageId = R.drawable.jp,
            fightingStyle = "Bartitsu & Psycho Power",
            game = "Street Fighter VI 6",
        ),
        CharacterEntry(
            name = "Juri",
            imageId = R.drawable.juri,
            fightingStyle = "Taekwondo, ki attacks",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Ken",
            imageId = R.drawable.ken,
            fightingStyle = "Karate based martial arts, Ansatsuken",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Kimberly",
            imageId = R.drawable.kimberly,
            fightingStyle = "Bushin-ryu Ninjutsu",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Lily",
            imageId = R.drawable.lily,
            fightingStyle = "Thunderfoot Martial Arts",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Luke",
            imageId = R.drawable.luke,
            fightingStyle = "Military MMA",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "M.Bison",
            imageId = R.drawable.m_bison,
            fightingStyle = "Psycho Power & Lerdrit",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Mai",
            imageId = R.drawable.mai,
            fightingStyle = "Shiranui-ryuu Ninjitsu",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Manon",
            imageId = R.drawable.manon,
            fightingStyle = "Judo and ballet dancing",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Marisa",
            imageId = R.drawable.marisa,
            fightingStyle = "Pankration",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Rashid",
            imageId = R.drawable.rashid,
            fightingStyle = "Wind and parkour",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Ryu",
            imageId = R.drawable.ryu,
            fightingStyle = "Ansatsuken",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Terry",
            imageId = R.drawable.terry_bogard,
            fightingStyle = "Martial arts and Hakkyokuseiken chi techniques",
            game = "Street Fighter VI",
        ),
        CharacterEntry(
            name = "Zangief",
            imageId = R.drawable.zangief,
            fightingStyle = "Mix of Russian and American pro wrestling",
            game = "Street Fighter VI",
        ),
    )
)

val streetFighterVIMoves = ImmutableList(
    listOf(
        // Inputs
        MoveEntry(moveName = "LP", notation = "lp", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "MP", notation = "mp", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "HP", notation = "hp", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "LK", notation = "lk", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "MK", notation = "mk", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "HK", notation = "hk", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),

        // Classic Inputs
        MoveEntry(moveName = "L", notation = "l", moveType = "SF Modern", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Modern),
        MoveEntry(moveName = "M", notation = "m", moveType = "SF Modern", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Modern),
        MoveEntry(moveName = "H", notation = "h", moveType = "SF Modern", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Modern),
        MoveEntry(moveName = "S", notation = "special", moveType = "SF Modern", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Modern),
        MoveEntry(moveName = "Assist", notation = "assist", moveType = "SF Modern", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Modern),

        // Mechanics
        MoveEntry(moveName = "Taunt", notation = "taunt", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Throw", notation = "throw", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Drive Impact", notation = "hp + hk", moveType = "Mechanic",character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Parry", notation = "mp + mk", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Perfect Parry", notation = "mp + mk", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Drive Rush", notation = "d/r", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Drive Reversal", notation = "d/r", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Overdrive", notation = "o/d", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Air", notation = "air", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),

        // Movements
        MoveEntry(moveName = "forward", notation = "f", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "up", notation = "u", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "up_forward", notation = "u/f", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "down_forward", notation = "d/f", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "forward_dash", notation = "F", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "back", notation = "b", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "down", notation = "d", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "up_back", notation = "u/b", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "down_back", notation = "d/b", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "qcf", notation = "qcf", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "qcb", notation = "qcb", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "hcf", notation = "hcf", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "hcb", notation = "bd", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "fc", notation = "fc", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "dp", notation = "dp", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),

        // A.K.I.
        MoveEntry(moveName = "Nightshade Pulse", notation = "qcb, lp", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Orchid Spring", notation = "qcb, mp", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Toxic Wreath", notation = "qcb, hp", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Serpent Lash", notation = "qcf + p", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Cruel Fate", notation = "qcb, k", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Snake Step", notation = "qcf, k", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sinister Slide", notation = "d, p, p", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Deadly Implication", notation = "qcf, qcf + k", moveType = "Super Art", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tainted Talons", notation = "qcb, qcb, p", moveType = "Super Art", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Claws of Ya Zi", notation = "qcf, qcf, p", moveType = "Super Art", character = "A.K.I.", game = "Street Fighter VI"),

        // Akuma
        MoveEntry(moveName = "Gou Hadoken", notation = "qcf, p", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Zanku Hadoken", notation = "uf, qcf, p", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Gou Shoryuken", notation = "dp, p", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tatsumaki Zanku-kyaku", notation = "qcb, k", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Adamant Flame", notation = "qcb, p, f, p", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Demon Raid", notation = "qcf, k", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ashura Senku", notation = "f|b, k, k, k", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Orobo Throw", notation = "f, k, k, k, lp, lk", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Messatsu Gohado", notation = "qcf, qcf, p", moveType = "Super Art", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tenma Gozanku", notation = "u, qcf, qcf, k", moveType = "Super Art", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Empyrean's End", notation = "qcb, qcb, p", moveType = "Super Art", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sip of Calamity", notation = "qcf, qcf, k", moveType = "Super Art", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shun Goku Satsu", notation = "lp, lp, f, lk, hp", moveType = "Super Art", character = "Akuma", game = "Street Fighter VI"),

        // Blanka
        MoveEntry(moveName = "Electric Thunder", notation = "qcb, p", moveType = "Special", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Rolling Attack", notation = "h, f|b, p", moveType = "Special", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Vertical Rolling Attack", notation = "h, u|d, k", moveType = "Special", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Backstep Rolling Attack", notation = "hcb, k", moveType = "Special", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Wild Hunt", notation = "qcf, k", moveType = "Special", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Blanka-chan Bomb", notation = "d, d, p", moveType = "Special", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shout of Earth", notation = "qcf, qcf, p", moveType = "Super Art", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Lightning Beast", notation = "qcb, qcb, p", moveType = "Super Art", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ground Shave Cannonball", notation = "qcf, qcf, k", moveType = "Super Art", character = "Blanka", game = "Street Fighter VI"),

        // Cammy
        MoveEntry(moveName = "Spiral Arrow", notation = "qcf, k", moveType = "Special", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Cannon Spike", notation = "dp, k", moveType = "Special", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Quick Spin Knuckle", notation = "qcb, p", moveType = "Special", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Cannon Strike", notation = "qcb, k", moveType = "Special", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hooligan Combination", notation = "qcf, p", moveType = "Special", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Spin Drive Smasher", notation = "qcf, qcf, k", moveType = "Super Art", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Killer Bee Spin", notation = "qcb, qcb, k", moveType = "Super Art", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Delta Red Assault", notation = "qcf, qcf, p", moveType = "Super Art", character = "Cammy", game = "Street Fighter VI"),

        // Chun-Li
        MoveEntry(moveName = "Kikoken", notation = "h, f|b, p", moveType = "Special", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hundred Lightning Kicks", notation = "qcf, k", moveType = "Special", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Spinning Bird Kick", notation = "h, u|d, k", moveType = "Special", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hazanshu", notation = "qcb, k", moveType = "Special", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tensho Kicks", notation = "d, d, k", moveType = "Special", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Kikosho", notation = "qcf, qcf, p", moveType = "Super Art", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hoyoku-sen", notation = "qcf, qcf, k", moveType = "Super Art", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Soten Ranka", notation = "qcb, qcb, k", moveType = "Super Art", character = "Chun-Li", game = "Street Fighter VI"),

        // Dee Jay
        MoveEntry(moveName = "Air Slasher", notation = "h, f|b, p", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Jackknife Maximum", notation = "h, u|b, k", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Roll Through Feint", notation = "qcf, lp", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Quick Rolling Sobat", notation = "qcf, mk", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Double Rolling Sobat", notation = "qcf, hk", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Machine Gun Uppercut", notation = "qcb, p", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Jus Cool", notation = "qcb, k", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "The Greatest Sobat", notation = "qcf, qcf, k", moveType = "Super Art", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Lowkey Sunrise Festival", notation = "qcf, qcf, lp", moveType = "Super Art", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Marvelous Sunrise Festival", notation = "qcf, qcf, mp", moveType = "Super Art", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Headliner Sunrise Festival", notation = "qcf, qcf, hp", moveType = "Super Art", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Weekend Pleasure", notation = "qcb, qcb, p", moveType = "Super Art", character = "Dee Jay", game = "Street Fighter VI"),

        // Dhalsim
        MoveEntry(moveName = "Yoga Fire", notation = "qcf, p", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Arch", notation = "qcf, k", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Flame", notation = "hcb, p", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Blast", notation = "hcb, k", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Comet", notation = "u, hcb, p", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Float", notation = "d|df, k, k", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Teleport", notation = "f|b, ppp|kkk", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Inferno", notation = "qcf, qcf, p", moveType = "Super Art", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Sunburst", notation = "qcb, qcb, k", moveType = "Super Art", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Merciless Yoga", notation = "qcf, qcf, k", moveType = "Super Art", character = "Dhalsim", game = "Street Fighter VI"),

        // Ed
        MoveEntry(moveName = "Psycho Spark", notation = "qcf, p", moveType = "Special", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Uppercut", notation = "dp, p", moveType = "Special", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Blitz", notation = "qcb, p", moveType = "Special", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Flicker", notation = "qcf, k", moveType = "Special", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Kill Rush", notation = "b|n|f, k, k", moveType = "Special", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Storm", notation = "qcb, qcb, p", moveType = "Super Art", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Cannon", notation = "qcf, qcf, p", moveType = "Super Art", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Chamber", notation = "qcf, qcf, k", moveType = "Super Art", character = "Ed", game = "Street Fighter VI"),

        // Elena
        MoveEntry(moveName = "Scratch Wheel", notation = "↓ ↘ → + K", moveType = "Special", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "OD Scratch Wheel", notation = "↓ ↘ → + KK", moveType = "Overdrive", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "Mallet Smash", notation = "→ ↓ ↘ + P", moveType = "Special", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "OD Mallet Smash", notation = "→ ↓ ↘ + PP", moveType = "Overdrive", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "Spin Scythe", notation = "↓ ↙ ← + K", moveType = "Special", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "OD Spin Scythe", notation = "↓ ↙ ← + KK", moveType = "Overdrive", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "Healing", notation = "↓ ↓ + P", moveType = "Special", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "OD Healing", notation = "↓ ↓ + PP", moveType = "Overdrive", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "SA1 - Healing Wind", notation = "↓ ↘ → ↓ ↘ → + P", moveType = "Super Art", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "SA2 - Brave Dance", notation = "↓ ↙ ← ↓ ↙ ← + K", moveType = "Super Art", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "SA3 - Skyward Dance", notation = "↓ ↘ → ↓ ↘ → + K", moveType = "Super Art", character = "Elena", game = "Street Fighter VI"),

        // E. Honda
        MoveEntry(moveName = "Hundred Hand Slap", notation = "qcb, p", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sumo Headbutt", notation = "h, f|b, p", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sumo Smash", notation = "hm u|b, k", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Oicho Throw", notation = "hcb, k", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sumo Dash", notation = "qcf, k", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Neko Damashi", notation = "d, d, p", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sumo Spirit", notation = "d, d, k", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Show of Force", notation = "qcf, qcf, p", moveType = "Super Art", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ultimate Killer Head Ram", notation = "h, f|b, b, f, k", moveType = "Super Art", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "The Final Bout", notation = "qcb, qcb, p", moveType = "Super Art", character = "E. Honda", game = "Street Fighter VI"),

        // Guile
        MoveEntry(moveName = "Sonic Boom", notation = "h, f|b, p", moveType = "Special", character = "Guile", game = "Street Fighter VI"),
        MoveEntry(moveName = "Somersault Kick", notation = "h, u|d, p", moveType = "Special", character = "Guile", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sonic Blade", notation = "qcb, p", moveType = "Special", character = "Guile", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sonic Break", notation = "super 2, p, p", moveType = "Special", character = "Guile", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sonic Hurricane", notation = "h, f|b, b, f, p", moveType = "Super Art", character = "Guile", game = "Street Fighter VI"),
        MoveEntry(moveName = "Solid Puncher", notation = "qcb, qcb, p", moveType = "Super Art", character = "Guile", game = "Street Fighter VI"),
        MoveEntry(moveName = "Crossfire Somersault", notation = "h, f|b, b, f, k", moveType = "Super Art", character = "Guile", game = "Street Fighter VI"),

        // Jamie
        MoveEntry(moveName = "The Devil Inside", notation = "d, d, p", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Freeflow Strikes", notation = "qcf, p, f, p, f, p", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Freeflow Kicks", notation = "qcf, p, f, k, f, k", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Swagger Step", notation = "qcb, p", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Arrow Kick", notation = "dp, k", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Luminous Dive Kick", notation = "u, qcb, k", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Bakkai", notation = "qcf, k", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tenshin", notation = "hcb, k", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Breakin'", notation = "qcf, qcf, k", moveType = "Super Art", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "The Devil's Song", notation = "qcb, qcb, p", moveType = "Super Art", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Getsuga Saiho", notation = "qcf, qcf, p", moveType = "Super Art", character = "Jamie", game = "Street Fighter VI"),

        // JP
        MoveEntry(moveName = "Triglav", notation = "d, d, p", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Departure", notation = "qcb, p", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Stribog", notation = "qcf, p", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Torbalan", notation = "qcf, k", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Amnesia", notation = "d, d, k", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Torbalan", notation = "qcf, k", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Embrace", notation = "qcb, k", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Chornobog", notation = "qcf, qcf, p", moveType = "Super Art", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Lovushka", notation = "qcb, qcb, p", moveType = "Super Art", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Interdiction", notation = "qcf, qcf, k", moveType = "Super Art", character = "JP", game = "Street Fighter VI"),

        // Juri
        MoveEntry(moveName = "Fuhajin", notation = "qcb, k", moveType = "Special", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Saihasho", notation = "u, qcb, lk", moveType = "Special", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ankensatsu", notation = "qcf, mk", moveType = "Special", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Go Ohsatsu", notation = "qcf, hk", moveType = "Special", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tensesnrin", notation = "dp, p", moveType = "Special", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shiku-sen", notation = "uf, qcb, k", moveType = "Special", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sakkai Fuhazan", notation = "qcf, qcf, k", moveType = "Super Art", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Feng Shui Engine", notation = "qcb, qcb, p", moveType = "Super Art", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Kaisen Dankai Raku", notation = "qcb, qcb, k", moveType = "Super Art", character = "Juri", game = "Street Fighter VI"),

        // Ken
        MoveEntry(moveName = "Hadoken", notation = "qcf, p", moveType = "Special", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shoryuken", notation = "dp, p", moveType = "Special", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tatsumaki Senpu-kyaku", notation = "qcb, k", moveType = "Special", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Dragonlash Kick", notation = "dp, k", moveType = "Special", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Jinrai Kick", notation = "qcf, k", moveType = "Special", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Kasai Thrust Kick", notation = "f, k", moveType = "Special", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Dragonlash Flame", notation = "qcf, qcf, k", moveType = "Super Art", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shippu Jinrai-kyaku", notation = "qcf, qcf, k", moveType = "Super Art", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shinryu Reppa", notation = "qcf, qcf, p", moveType = "Super Art", character = "Ken", game = "Street Fighter VI"),

        // Kimberly
        MoveEntry(moveName = "Bushin Senpukyaku", notation = "qcb, k", moveType = "Special", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sprint", notation = "qcf, k", moveType = "Special", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Vagabond Edge", notation = "qcf, p", moveType = "Special", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hidden Variable", notation = "qcb, p", moveType = "Special", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Genius at Play", notation = "d, d, p", moveType = "Special", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Nue Twister", notation = "u, qcf, p", moveType = "Special", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Bushin Beats", notation = "qcf, qcf, k", moveType = "Super Art", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Bushin Scramble", notation = "qcb, qcb, p", moveType = "Super Art", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Bushin Ninjastar Cypher ", notation = "qcf, qcf, p", moveType = "Super Art", character = "Kimberly", game = "Street Fighter VI"),

        // Lily
        MoveEntry(moveName = "Condor Wind", notation = "qcb, p", moveType = "Special", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Condor Spire", notation = "qcf, k", moveType = "Special", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tomahawk Buster", notation = "dp, p", moveType = "Special", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Condor Dive", notation = "u, p, p", moveType = "Special", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Mexican Typhoon", notation = "hcf, qcuf, p", moveType = "Special", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Breezing Hawk", notation = "qcf, qcf, p", moveType = "Super Art", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Thunderbird", notation = "qcf, qcf, k", moveType = "Super Art", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Raging Typhoon", notation = "qcb, qcb, p", moveType = "Super Art", character = "Lily", game = "Street Fighter VI"),

        // Luke
        MoveEntry(moveName = "", notation = "qcf, p", moveType = "Special", character = "Luke", game = "Street Fighter VI"),
        MoveEntry(moveName = "Flash Knuckle", notation = "qcb, p", moveType = "Special", character = "Luke", game = "Street Fighter VI"),
        MoveEntry(moveName = "Avenger", notation = "qcf, k", moveType = "Special",  character = "Luke", game = "Street Fighter VI"),
        MoveEntry(moveName = "Rising Uppercut", notation = "dp, p", moveType = "Special", character = "Luke", game = "Street Fighter VI"),
        MoveEntry(moveName = "Vulcan Blast", notation = "wcf, wcf, p", moveType = "Super Art", character = "Luke", game = "Street Fighter VI"),
        MoveEntry(moveName = "Eraser", notation = "qcb, qcb, p", moveType = "Super Art", character = "Luke", game = "Street Fighter VI"),
        MoveEntry(moveName = "Pale Rider", notation = "qcf, qcf, k", moveType = "Super Art", character = "Luke", game = "Street Fighter VI"),

        // Mai
        MoveEntry(moveName = "Kachousen", notation = "qcf, p", moveType = "Special", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ryuuenbu", notation = "qcb, p", moveType = "Special", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hissatsu Shinobi Bachi", notation = "qcf, k", moveType = "Special", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hishou Ryuuenjin", notation = "dp, k", moveType = "Special", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Musasabi no Mai", notation = "u, qcb, p", moveType = "Special", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Kagreou no Mai ", notation = "qcf, qcf, p", moveType = "Super Art", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Chou Hissatsu Shinobi Bachi ", notation = "qcf, qcf, k", moveType = "Super Art", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Enbu Ada Zakura ", notation = "qcb, qcb, p", moveType = "Super Art", character = "Mai", game = "Street Fighter VI"),

        // Manon
        MoveEntry(moveName = "Manegé Doré", notation = "qcb, p", moveType = "Special", character = "Manon", game = "Street Fighter VI"),
        MoveEntry(moveName = "Dégagé", notation = "qcb, k", moveType = "Special", character = "Manon", game = "Street Fighter VI"),
        MoveEntry(moveName = "Renversé", notation = "qcf, p", moveType = "Special", character = "Manon", game = "Street Fighter VI"),
        MoveEntry(moveName = "Rond-Point", notation = "qcf, k", moveType = "Special", character = "Manon", game = "Street Fighter VI"),
        MoveEntry(moveName = "Arabesque", notation = "qcf, qcf, k", moveType = "Super Art", character = "Manon", game = "Street Fighter VI"),
        MoveEntry(moveName = "Étoile", notation = "qcb, qcb, k", moveType = "Super Art", character = "Manon", game = "Street Fighter VI"),
        MoveEntry(moveName = "Pas de Deux", notation = "qcf, qcf, p", moveType = "Super Art", character = "Manon", game = "Street Fighter VI"),

        // Marisa
        MoveEntry(moveName = "Gladius", notation = "qcf, p", moveType = "Special", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Scutum", notation = "qcb, k", moveType = "Special", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Phalanx", notation = "dp, p", moveType = "Special", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Dimachearus", notation = "qcb, p, f, p", moveType = "Special", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Quadriga", notation = "qcf, k", moveType = "Special", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Javelin of Marisa", notation = "qcf, qcf, p", moveType = "Super Art", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Meteorite", notation = "qcb, qcb, p", moveType = "Super Art", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Godess of the Hunt", notation = "qcf, qcf, k", moveType = "Super Art", character = "Marisa", game = "Street Fighter VI"),

        // M. Bison
        MoveEntry(moveName = "Psycho Crusher Attack", notation = "h, b, f, p", moveType = "Special", character = "M. Bison", game = "Street Fighter VI"),
        MoveEntry(moveName = "Double Knee Press", notation = "qcf, k", moveType = "Special", character = "M. Bison", game = "Street Fighter VI"),
        MoveEntry(moveName = "Backfist Combo", notation = "qcb, p", moveType = "Special", character = "M. Bison", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shadow Rise", notation = "h, u, d, k", moveType = "Special", character = "M. Bison", game = "Street Fighter VI"),
        MoveEntry(moveName = "Knee Press Nightmare", notation = "qcf, qcf, k", moveType = "Super Art", character = "M. Bison", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Punisher", notation = "qcb, qcb, k", moveType = "Super Art", character = "M. Bison", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ultimate Psycho Crusher", notation = "qcf, qcf, p", moveType = "Super Art", character = "M. Bison", game = "Street Fighter VI"),

        // Rashid
        MoveEntry(moveName = "Spinning Mixer", notation = "qcf, p", moveType = "Special", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Eagle Spike", notation = "qcb, k", moveType = "Special", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Whirlwind Shot", notation = "qcf, k", moveType = "Special", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Arabian Cyclone", notation = "qcb, p", moveType = "Special", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Arabian Skyhigh", notation = "qcb, p", moveType = "Special", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Super Rashid Kick", notation = "qcf, qcf, k", moveType = "Super Art", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ysar", notation = "qcb, qcb, k", moveType = "Super Art", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Altair", notation = "qcf, qcf, p", moveType = "Super Art", character = "Rashid", game = "Street Fighter VI"),

        // Ryu
        MoveEntry(moveName = "Hadoken", notation = "qcf, p", moveType = "Special", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shoryuken", notation = "qcf, p", moveType = "Special", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tatsumaki Senpu-kyaku", notation = "qcb, k", moveType = "Special", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "High Blade Kick", notation = "qcf, k", moveType = "Special", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hashogeki", notation = "qcb, p", moveType = "Special", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Denjin Charge", notation = "d, d, p", moveType = "Special", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shinku Hadoken", notation = "qcf, qcf, p", moveType = "Super Art", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shin Hashogeki", notation = "qcb, qcb, p", moveType = "Super Art", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shin Shoryuken", notation = "qcf, qcf, k", moveType = "Super Art", character = "Ryu", game = "Street Fighter VI"),

        // Terry
        MoveEntry(moveName = "Power Wave", notation = "qcf, lp|hp", moveType = "Special", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Round Wave", notation = "qcf, hp", moveType = "Special", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Quick Burn", notation = "qcb, mp|hp", moveType = "Special", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Power Charge", notation = "qcf, k", moveType = "Special", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Crack Shoot", notation = "qcb, k", moveType = "Special", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Rising Tackle", notation = "dp, p", moveType = "Special", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Buster Wolf", notation = "qcf, qcf, k", moveType = "Super Art", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Power Geyser", notation = "qcb, qcb, p", moveType = "Super Art", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Rising Fang", notation = "qcf, qcf, p", moveType = "Super Art", character = "Terry", game = "Street Fighter VI"),

        // Zangief
        MoveEntry(moveName = "Double Lariat", notation = "p, p", moveType = "Special", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Screw Piledriver", notation = "fc, p", moveType = "Special", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Borscht Dynamite", notation = "fc, k", moveType = "Special", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Russian Suplex", notation = "hcb, k", moveType = "Special", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Siberian Express", notation = "hcb, k", moveType = "Special", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tundra Storm", notation = "d, d, hk", moveType = "Special", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Aerial Russian Slam", notation = "qcf, qcf, k", moveType = "Super Art", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Cyclone Lariat", notation = "qcf, qcf, p", moveType = "Super Art", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Bolshoi Storm Buster", notation = "fc, fc, p", moveType = "Super Art", character = "Zangief", game = "Street Fighter VI"),
    )
)