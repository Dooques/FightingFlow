package com.example.fightingflow.util.characterAndMoveData

import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.MoveEntry
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

        // Inputs
        MoveEntry(moveName = "light_punch", notation = "lp", moveType = "SF Input", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "medium_punch", notation = "mp", moveType = "SF Input", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "heavy_punch", notation = "hp", moveType = "SF Input", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "light_kick", notation = "lk", moveType = "SF Input", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "medium_kick", notation = "mk", moveType = "SF Input", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "heavy_kick", notation = "hk", moveType = "SF Input", character = "Generic", game = "Street Fighter VI"),

        // Mechanics
        MoveEntry(moveName = "Taunt", notation = "taunt", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "DI", notation = "hp + hk", moveType = "Mechanic",character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Parry", notation = "mp + mk", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Perfect Parry", notation = "mp + mk", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "DR", notation = "dr", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "OD", notation = "od", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Air", notation = "Air", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),

        // A.K.I.
        MoveEntry(moveName = "Nightshade Pulse", notation = "qcb, lp", moveType = "Special", character = "A.K.I."),
        MoveEntry(moveName = "Orchid Spring", notation = "qcb, mp", moveType = "Special", character = "A.K.I."),
        MoveEntry(moveName = "Toxic Wreath", notation = "qcb, hp", moveType = "Special", character = "A.K.I."),
        MoveEntry(moveName = "Serpent Lash", notation = "qcf + p", moveType = "Special", character = "A.K.I."),
        MoveEntry(moveName = "Cruel Fate", notation = "qcb, k", moveType = "Special", character = "A.K.I."),
        MoveEntry(moveName = "Snake Step", notation = "qcf, k", moveType = "Special", character = "A.K.I."),
        MoveEntry(moveName = "Sinister Slide", notation = "d, p, p", moveType = "Special", character = "A.K.I."),
        MoveEntry(moveName = "Deadly Implication", notation = "qcf, qcf + k", moveType = "Super Art", character = "A.K.I."),
        MoveEntry(moveName = "Tainted Talons", notation = "qcb, qcb, p", moveType = "Super Art", character = "A.K.I."),
        MoveEntry(moveName = "Claws of Ya Zi", notation = "qcf, qcf, p", moveType = "Super Art", character = "A.K.I."),

        // Akuma
        MoveEntry(moveName = "Gou Hadoken", notation = "qcf, p", moveType = "Special", character = "Akuma"),
        MoveEntry(moveName = "Zanku Hadoken", notation = "uf, qcf, p", moveType = "Special", character = "Akuma"),
        MoveEntry(moveName = "Gou Shoryuken", notation = "dp, p", moveType = "Special", character = "Akuma"),
        MoveEntry(moveName = "Tatsumaki Zanku-kyaku", notation = "qcb, k", moveType = "Special", character = "Akuma"),
        MoveEntry(moveName = "Adamant Flame", notation = "qcb, p, f, p", moveType = "Special", character = "Akuma"),
        MoveEntry(moveName = "Demon Raid", notation = "qcf, k", moveType = "Special", character = "Akuma"),
        MoveEntry(moveName = "Ashura Senku", notation = "f|b, k, k, k", moveType = "Special", character = "Akuma"),
        MoveEntry(moveName = "Orobo Throw", notation = "f, k, k, k, lp, lk", moveType = "Special", character = "Akuma"),
        MoveEntry(moveName = "Messatsu Gohado", notation = "qcf, qcf, p", moveType = "Super Art", character = "Akuma"),
        MoveEntry(moveName = "Tenma Gozanku", notation = "u, qcf, qcf, k", moveType = "Super Art", character = "Akuma"),
        MoveEntry(moveName = "Empyrean's End", notation = "qcb, qcb, p", moveType = "Super Art", character = "Akuma"),
        MoveEntry(moveName = "Sip of Calamity", notation = "qcf, qcf, k", moveType = "Super Art", character = "Akuma"),
        MoveEntry(moveName = "Shun Goku Satsu", notation = "lp, lp, f, lk, hp", moveType = "Super Art", character = "Akuma"),

        // Blanka
        MoveEntry(moveName = "Electric Thunder", notation = "qcb, p", moveType = "Special", character = "Blanka"),
        MoveEntry(moveName = "Rolling Attack", notation = "h, f|b, p", moveType = "Special", character = "Blanka"),
        MoveEntry(moveName = "Vertical Rolling Attack", notation = "h, u|d, k", moveType = "Special", character = "Blanka"),
        MoveEntry(moveName = "Backstep Rolling Attack", notation = "hcb, k", moveType = "Special", character = "Blanka"),
        MoveEntry(moveName = "Wild Hunt", notation = "qcf, k", moveType = "Special", character = "Blanka"),
        MoveEntry(moveName = "Blanka-chan Bomb", notation = "d, d, p", moveType = "Special", character = "Blanka"),
        MoveEntry(moveName = "Shout of Earth", notation = "qcf, qcf, p", moveType = "Super Art", character = "Blanka"),
        MoveEntry(moveName = "Lightning Beast", notation = "qcb, qcb, p", moveType = "Super Art", character = "Blanka"),
        MoveEntry(moveName = "Ground Shave Cannonball", notation = "qcf, qcf, k", moveType = "Super Art", character = "Blanka"),

        // Cammy
        MoveEntry(moveName = "Spiral Arrow", notation = "qcf, k", moveType = "Special", character = "Cammy"),
        MoveEntry(moveName = "Cannon Spike", notation = "dp, k", moveType = "Special", character = "Cammy"),
        MoveEntry(moveName = "Quick Spin Knuckle", notation = "qcb, p", moveType = "Special", character = "Cammy"),
        MoveEntry(moveName = "Cannon Strike", notation = "qcb, k", moveType = "Special", character = "Cammy"),
        MoveEntry(moveName = "Hooligan Combination", notation = "qcf, p", moveType = "Special", character = "Cammy"),
        MoveEntry(moveName = "Spin Drive Smasher", notation = "qcf, qcf, k", moveType = "Super Art", character = "Cammy"),
        MoveEntry(moveName = "Killer Bee Spin", notation = "qcb, qcb, k", moveType = "Super Art", character = "Cammy"),
        MoveEntry(moveName = "Delta Red Assault", notation = "qcf, qcf, p", moveType = "Super Art", character = "Cammy"),

        // Chun-Li
        MoveEntry(moveName = "Kikoken", notation = "h, f|b, p", moveType = "Special", character = "Chun-Li"),
        MoveEntry(moveName = "Hundred Lightning Kicks", notation = "qcf, k", moveType = "Special", character = "Chun-Li"),
        MoveEntry(moveName = "Spinning Bird Kick", notation = "h, u|d, k", moveType = "Special", character = "Chun-Li"),
        MoveEntry(moveName = "Hazanshu", notation = "qcb, k", moveType = "Special", character = "Chun-Li"),
        MoveEntry(moveName = "Tensho Kicks", notation = "d, d, k", moveType = "Special", character = "Chun-Li"),
        MoveEntry(moveName = "Kikosho", notation = "qcf, qcf, p", moveType = "Super Art", character = "Chun-Li"),
        MoveEntry(moveName = "Hoyoku-sen", notation = "qcf, qcf, k", moveType = "Super Art", character = "Chun-Li"),
        MoveEntry(moveName = "Soten Ranka", notation = "qcb, qcb, k", moveType = "Super Art", character = "Chun-Li"),

        // Dee Jay
        MoveEntry(moveName = "Air Slasher", notation = "h, f|b, p", moveType = "Special", character = "Dee Jay"),
        MoveEntry(moveName = "Jackknife Maximum", notation = "h, u|b, k", moveType = "Special", character = "Dee Jay"),
        MoveEntry(moveName = "Roll Through Feint", notation = "qcf, lp", moveType = "Special", character = "Dee Jay"),
        MoveEntry(moveName = "Quick Rolling Sobat", notation = "qcf, mk", moveType = "Special", character = "Dee Jay"),
        MoveEntry(moveName = "Double Rolling Sobat", notation = "qcf, hk", moveType = "Special", character = "Dee Jay"),
        MoveEntry(moveName = "Machine Gun Uppercut", notation = "qcb, p", moveType = "Special", character = "Dee Jay"),
        MoveEntry(moveName = "Jus Cool", notation = "qcb, k", moveType = "Special", character = "Dee Jay"),
        MoveEntry(moveName = "The Greatest Sobat", notation = "qcf, qcf, k", moveType = "Super Art", character = "Dee Jay"),
        MoveEntry(moveName = "Lowkey Sunrise Festival", notation = "qcf, qcf, lp", moveType = "Super Art", character = "Dee Jay"),
        MoveEntry(moveName = "Marvelous Sunrise Festival", notation = "qcf, qcf, mp", moveType = "Super Art", character = "Dee Jay"),
        MoveEntry(moveName = "Headliner Sunrise Festival", notation = "qcf, qcf, hp", moveType = "Super Art", character = "Dee Jay"),
        MoveEntry(moveName = "Weekend Pleasure", notation = "qcb, qcb, p", moveType = "Super Art", character = "Dee Jay"),

        // Dhalsim
        MoveEntry(moveName = "Yoga Fire", notation = "qcf, p", moveType = "Special", character = "Dhalsim"),
        MoveEntry(moveName = "Yoga Arch", notation = "qcf, k", moveType = "Special", character = "Dhalsim"),
        MoveEntry(moveName = "Yoga Flame", notation = "hcb, p", moveType = "Special", character = "Dhalsim"),
        MoveEntry(moveName = "Yoga Blast", notation = "hcb, k", moveType = "Special", character = "Dhalsim"),
        MoveEntry(moveName = "Yoga Comet", notation = "u, hcb, p", moveType = "Special", character = "Dhalsim"),
        MoveEntry(moveName = "Yoga Float", notation = "d|df, k, k", moveType = "Special", character = "Dhalsim"),
        MoveEntry(moveName = "Yoga Teleport", notation = "f|b, ppp|kkk", moveType = "Special", character = "Dhalsim"),
        MoveEntry(moveName = "Yoga Inferno", notation = "qcf, qcf, p", moveType = "Super Art", character = "Dhalsim"),
        MoveEntry(moveName = "Yoga Sunburst", notation = "qcb, qcb, k", moveType = "Super Art", character = "Dhalsim"),
        MoveEntry(moveName = "Merciless Yoga", notation = "qcf, qcf, k", moveType = "Super Art", character = "Dhalsim"),

        // Ed
        MoveEntry(moveName = "Psycho Spark", notation = "qcf, p", moveType = "Special", character = "Ed"),
        MoveEntry(moveName = "Psycho Uppercut", notation = "dp, p", moveType = "Special", character = "Ed"),
        MoveEntry(moveName = "Psycho Blitz", notation = "qcb, p", moveType = "Special", character = "Ed"),
        MoveEntry(moveName = "Psycho Flicker", notation = "qcf, k", moveType = "Special", character = "Ed"),
        MoveEntry(moveName = "Kill Rush", notation = "b|n|f, k, k", moveType = "Special", character = "Ed"),
        MoveEntry(moveName = "Psycho Storm", notation = "qcb, qcb, p", moveType = "Super Art", character = "Ed"),
        MoveEntry(moveName = "Psycho Cannon", notation = "qcf, qcf, p", moveType = "Super Art", character = "Ed"),
        MoveEntry(moveName = "Psycho Chamber", notation = "qcf, qcf, k", moveType = "Super Art", character = "Ed"),

        // Elena
        MoveEntry(moveName = "Scratch Wheel", notation = "↓ ↘ → + K", moveType = "Special", character = "Elena"),
        MoveEntry(moveName = "OD Scratch Wheel", notation = "↓ ↘ → + KK", moveType = "Overdrive", character = "Elena"),
        MoveEntry(moveName = "Mallet Smash", notation = "→ ↓ ↘ + P", moveType = "Special", character = "Elena"),
        MoveEntry(moveName = "OD Mallet Smash", notation = "→ ↓ ↘ + PP", moveType = "Overdrive", character = "Elena"),
        MoveEntry(moveName = "Spin Scythe", notation = "↓ ↙ ← + K", moveType = "Special", character = "Elena"),
        MoveEntry(moveName = "OD Spin Scythe", notation = "↓ ↙ ← + KK", moveType = "Overdrive", character = "Elena"),
        MoveEntry(moveName = "Healing", notation = "↓ ↓ + P", moveType = "Special", character = "Elena"),
        MoveEntry(moveName = "OD Healing", notation = "↓ ↓ + PP", moveType = "Overdrive", character = "Elena"),
        MoveEntry(moveName = "SA1 - Healing Wind", notation = "↓ ↘ → ↓ ↘ → + P", moveType = "Super Art", character = "Elena"),
        MoveEntry(moveName = "SA2 - Brave Dance", notation = "↓ ↙ ← ↓ ↙ ← + K", moveType = "Super Art", character = "Elena"),
        MoveEntry(moveName = "SA3 - Skyward Dance", notation = "↓ ↘ → ↓ ↘ → + K", moveType = "Super Art", character = "Elena"),

        // E. Honda
        MoveEntry(moveName = "Hundred Hand Slap", notation = "qcb, p", moveType = "Special", character = "E. Honda"),
        MoveEntry(moveName = "Sumo Headbutt", notation = "h, f|b, p", moveType = "Special", character = "E. Honda"),
        MoveEntry(moveName = "Sumo Smash", notation = "hm u|b, k", moveType = "Special", character = "E. Honda"),
        MoveEntry(moveName = "Oicho Throw", notation = "hcb, k", moveType = "Special", character = "E. Honda"),
        MoveEntry(moveName = "Sumo Dash", notation = "qcf, k", moveType = "Special", character = "E. Honda"),
        MoveEntry(moveName = "Neko Damashi", notation = "d, d, p", moveType = "Special", character = "E. Honda"),
        MoveEntry(moveName = "Sumo Spirit", notation = "d, d, k", moveType = "Special", character = "E. Honda"),
        MoveEntry(moveName = "Show of Force", notation = "qcf, qcf, p", moveType = "Super Art", character = "E. Honda"),
        MoveEntry(moveName = "Ultimate Killer Head Ram", notation = "h, f|b, b, f, k", moveType = "Super Art", character = "E. Honda"),
        MoveEntry(moveName = "The Final Bout", notation = "qcb, qcb, p", moveType = "Super Art", character = "E. Honda"),

        // Guile
        MoveEntry(moveName = "Sonic Boom", notation = "h, f|b, p", moveType = "Special", character = "Guile"),
        MoveEntry(moveName = "Somersault Kick", notation = "h, u|d, p", moveType = "Special", character = "Guile"),
        MoveEntry(moveName = "Sonic Blade", notation = "qcb, p", moveType = "Special", character = "Guile"),
        MoveEntry(moveName = "Sonic Break", notation = "super 2, p, p", moveType = "Special", character = "Guile"),
        MoveEntry(moveName = "Sonic Hurricane", notation = "h, f|b, b, f, p", moveType = "Super Art", character = "Guile"),
        MoveEntry(moveName = "Solid Puncher", notation = "qcb, qcb, p", moveType = "Super Art", character = "Guile"),
        MoveEntry(moveName = "Crossfire Somersault", notation = "h, f|b, b, f, k", moveType = "Super Art", character = "Guile"),

        // Jamie
        MoveEntry(moveName = "The Devil Inside", notation = "d, d, p", moveType = "Special", character = "Jamie"),
        MoveEntry(moveName = "Freeflow Strikes", notation = "qcf, p, f, p, f, p", moveType = "Special", character = "Jamie"),
        MoveEntry(moveName = "Freeflow Kicks", notation = "qcf, p, f, k, f, k", moveType = "Special", character = "Jamie"),
        MoveEntry(moveName = "Swagger Step", notation = "qcb, p", moveType = "Special", character = "Jamie"),
        MoveEntry(moveName = "Arrow Kick", notation = "dp, k", moveType = "Special", character = "Jamie"),
        MoveEntry(moveName = "Luminous Dive Kick", notation = "u, qcb, k", moveType = "Special", character = "Jamie"),
        MoveEntry(moveName = "Bakkai", notation = "qcf, k", moveType = "Special", character = "Jamie"),
        MoveEntry(moveName = "Tenshin", notation = "hcb, k", moveType = "Special", character = "Jamie"),
        MoveEntry(moveName = "Breakin'", notation = "qcf, qcf, k", moveType = "Super Art", character = "Jamie"),
        MoveEntry(moveName = "The Devil's Song", notation = "qcb, qcb, p", moveType = "Super Art", character = "Jamie"),
        MoveEntry(moveName = "Getsuga Saiho", notation = "qcf, qcf, p", moveType = "Super Art", character = "Jamie"),

        // JP
        MoveEntry(moveName = "Triglav", notation = "d, d, p", moveType = "Special", character = "JP"),
        MoveEntry(moveName = "Departure", notation = "qcb, p", moveType = "Special", character = "JP"),
        MoveEntry(moveName = "Stribog", notation = "qcf, p", moveType = "Special", character = "JP"),
        MoveEntry(moveName = "Torbalan", notation = "qcf, k", moveType = "Special", character = "JP"),
        MoveEntry(moveName = "Amnesia", notation = "d, d, k", moveType = "Special", character = "JP"),
        MoveEntry(moveName = "Torbalan", notation = "qcf, k", moveType = "Special", character = "JP"),
        MoveEntry(moveName = "Embrace", notation = "qcb, k", moveType = "Special", character = "JP"),
        MoveEntry(moveName = "Chornobog", notation = "qcf, qcf, p", moveType = "Super Art", character = "JP"),
        MoveEntry(moveName = "Lovushka", notation = "qcb, qcb, p", moveType = "Super Art", character = "JP"),
        MoveEntry(moveName = "Interdiction", notation = "qcf, qcf, k", moveType = "Super Art", character = "JP"),

        // Juri
        MoveEntry(moveName = "Fuhajin", notation = "qcb, k", moveType = "Special", character = "Juri"),
        MoveEntry(moveName = "Saihasho", notation = "u, qcb, lk", moveType = "Special", character = "Juri"),
        MoveEntry(moveName = "Ankensatsu", notation = "qcf, mk", moveType = "Special", character = "Juri"),
        MoveEntry(moveName = "Go Ohsatsu", notation = "qcf, hk", moveType = "Special", character = "Juri"),
        MoveEntry(moveName = "Tensesnrin", notation = "dp, p", moveType = "Special", character = "Juri"),
        MoveEntry(moveName = "Shiku-sen", notation = "uf, qcb, k", moveType = "Special", character = "Juri"),
        MoveEntry(moveName = "Sakkai Fuhazan", notation = "qcf, qcf, k", moveType = "Super Art", character = "Juri"),
        MoveEntry(moveName = "Feng Shui Engine", notation = "qcb, qcb, p", moveType = "Super Art", character = "Juri"),
        MoveEntry(moveName = "Kaisen Dankai Raku", notation = "qcb, qcb, k", moveType = "Super Art", character = "Juri"),

        // Ken
        MoveEntry(moveName = "Hadoken", notation = "qcf, p", moveType = "Special", character = "Ken"),
        MoveEntry(moveName = "Shoryuken", notation = "dp, p", moveType = "Special", character = "Ken"),
        MoveEntry(moveName = "Tatsumaki Senpu-kyaku", notation = "qcb, k", moveType = "Special", character = "Ken"),
        MoveEntry(moveName = "Dragonlash Kick", notation = "dp, k", moveType = "Special", character = "Ken"),
        MoveEntry(moveName = "Jinrai Kick", notation = "qcf, k", moveType = "Special", character = "Ken"),
        MoveEntry(moveName = "Kasai Thrust Kick", notation = "f, k", moveType = "Special", character = "Ken"),
        MoveEntry(moveName = "Dragonlash Flame", notation = "qcf, qcf, k", moveType = "Super Art", character = "Ken"),
        MoveEntry(moveName = "Shippu Jinrai-kyaku", notation = "qcf, qcf, k", moveType = "Super Art", character = "Ken"),
        MoveEntry(moveName = "Shinryu Reppa", notation = "qcf, qcf, p", moveType = "Super Art", character = "Ken"),

        // Kimberly
        MoveEntry(moveName = "Bushin Senpukyaku", notation = "qcb, k", moveType = "Special", character = "Kimberly"),
        MoveEntry(moveName = "Sprint", notation = "qcf, k", moveType = "Special", character = "Kimberly"),
        MoveEntry(moveName = "Vagabond Edge", notation = "qcf, p", moveType = "Special", character = "Kimberly"),
        MoveEntry(moveName = "Hidden Variable", notation = "qcb, p", moveType = "Special", character = "Kimberly"),
        MoveEntry(moveName = "Genius at Play", notation = "d, d, p", moveType = "Special", character = "Kimberly"),
        MoveEntry(moveName = "Nue Twister", notation = "u, qcf, p", moveType = "Special", character = "Kimberly"),
        MoveEntry(moveName = "Bushin Beats", notation = "qcf, qcf, k", moveType = "Super Art", character = "Kimberly"),
        MoveEntry(moveName = "Bushin Scramble", notation = "qcb, qcb, p", moveType = "Super Art", character = "Kimberly"),
        MoveEntry(moveName = "Bushin Ninjastar Cypher ", notation = "qcf, qcf, p", moveType = "Super Art", character = "Kimberly"),

        // Lily
        MoveEntry(moveName = "Condor Wind", notation = "qcb, p", moveType = "Special", character = "Lily"),
        MoveEntry(moveName = "Condor Spire", notation = "qcf, k", moveType = "Special", character = "Lily"),
        MoveEntry(moveName = "Tomahawk Buster", notation = "dp, p", moveType = "Special", character = "Lily"),
        MoveEntry(moveName = "Condor Dive", notation = "u, p, p", moveType = "Special", character = "Lily"),
        MoveEntry(moveName = "Mexican Typhoon", notation = "hcf, qcuf, p", moveType = "Special", character = "Lily"),
        MoveEntry(moveName = "Breezing Hawk", notation = "qcf, qcf, p", moveType = "Super Art", character = "Lily"),
        MoveEntry(moveName = "Thunderbird", notation = "qcf, qcf, k", moveType = "Super Art", character = "Lily"),
        MoveEntry(moveName = "Raging Typhoon", notation = "qcb, qcb, p", moveType = "Super Art", character = "Lily"),

        // Luke
        MoveEntry(moveName = "", notation = "qcf, p", moveType = "Special", character = "Luke"),
        MoveEntry(moveName = "Flash Knuckle", notation = "qcb, p", moveType = "Special", character = "Luke"),
        MoveEntry(moveName = "Avenger", notation = "qcf, k", moveType = "Special",  character = "Luke"),
        MoveEntry(moveName = "Rising Uppercut", notation = "dp, p", moveType = "Special", character = "Luke"),
        MoveEntry(moveName = "Vulcan Blast", notation = "wcf, wcf, p", moveType = "Super Art", character = "Luke"),
        MoveEntry(moveName = "Eraser", notation = "qcb, qcb, p", moveType = "Super Art", character = "Luke"),
        MoveEntry(moveName = "Pale Rider", notation = "qcf, qcf, k", moveType = "Super Art", character = "Luke"),

        // Mai
        MoveEntry(moveName = "Kachousen", notation = "qcf, p", moveType = "Special", character = "Mai"),
        MoveEntry(moveName = "Ryuuenbu", notation = "qcb, p", moveType = "Special", character = "Mai"),
        MoveEntry(moveName = "Hissatsu Shinobi Bachi", notation = "qcf, k", moveType = "Special", character = "Mai"),
        MoveEntry(moveName = "Hishou Ryuuenjin", notation = "dp, k", moveType = "Special", character = "Mai"),
        MoveEntry(moveName = "Musasabi no Mai", notation = "u, qcb, p", moveType = "Special", character = "Mai"),
        MoveEntry(moveName = "Kagreou no Mai ", notation = "qcf, qcf, p", moveType = "Super Art", character = "Mai"),
        MoveEntry(moveName = "Chou Hissatsu Shinobi Bachi ", notation = "qcf, qcf, k", moveType = "Super Art", character = "Mai"),
        MoveEntry(moveName = "Enbu Ada Zakura ", notation = "qcb, qcb, p", moveType = "Super Art", character = "Mai"),

        // Manon
        MoveEntry(moveName = "Manegé Doré", notation = "qcb, p", moveType = "Special", character = "Manon"),
        MoveEntry(moveName = "Dégagé", notation = "qcb, k", moveType = "Special", character = "Manon"),
        MoveEntry(moveName = "Renversé", notation = "qcf, p", moveType = "Special", character = "Manon"),
        MoveEntry(moveName = "Rond-Point", notation = "qcf, k", moveType = "Special", character = "Manon"),
        MoveEntry(moveName = "Arabesque", notation = "qcf, qcf, k", moveType = "Super Art", character = "Manon"),
        MoveEntry(moveName = "Étoile", notation = "qcb, qcb, k", moveType = "Super Art", character = "Manon"),
        MoveEntry(moveName = "Pas de Deux", notation = "qcf, qcf, p", moveType = "Super Art", character = "Manon"),

        // Marisa
        MoveEntry(moveName = "Gladius", notation = "qcf, p", moveType = "Special", character = "Marisa"),
        MoveEntry(moveName = "Scutum", notation = "qcb, k", moveType = "Special", character = "Marisa"),
        MoveEntry(moveName = "Phalanx", notation = "dp, p", moveType = "Special", character = "Marisa"),
        MoveEntry(moveName = "Dimachearus", notation = "qcb, p, f, p", moveType = "Special", character = "Marisa"),
        MoveEntry(moveName = "Quadriga", notation = "qcf, k", moveType = "Special", character = "Marisa"),
        MoveEntry(moveName = "Javelin of Marisa", notation = "qcf, qcf, p", moveType = "Super Art", character = "Marisa"),
        MoveEntry(moveName = "Meteorite", notation = "qcb, qcb, p", moveType = "Super Art", character = "Marisa"),
        MoveEntry(moveName = "Godess of the Hunt", notation = "qcf, qcf, k", moveType = "Super Art", character = "Marisa"),

        // M. Bison
        MoveEntry(moveName = "Psycho Crusher Attack", notation = "h, b, f, p", moveType = "Special", character = "M. Bison"),
        MoveEntry(moveName = "Double Knee Press", notation = "qcf, k", moveType = "Special", character = "M. Bison"),
        MoveEntry(moveName = "Backfist Combo", notation = "qcb, p", moveType = "Special", character = "M. Bison"),
        MoveEntry(moveName = "Shadow Rise", notation = "h, u, d, k", moveType = "Special", character = "M. Bison"),
        MoveEntry(moveName = "Knee Press Nightmare", notation = "qcf, qcf, k", moveType = "Super Art", character = "M. Bison"),
        MoveEntry(moveName = "Psycho Punisher", notation = "qcb, qcb, k", moveType = "Super Art", character = "M. Bison"),
        MoveEntry(moveName = "Ultimate Psycho Crusher", notation = "qcf, qcf, p", moveType = "Super Art", character = "M. Bison"),

        // Rashid
        MoveEntry(moveName = "Spinning Mixer", notation = "qcf, p", moveType = "Special", character = "Rashid"),
        MoveEntry(moveName = "Eagle Spike", notation = "qcb, k", moveType = "Special", character = "Rashid"),
        MoveEntry(moveName = "Whirlwind Shot", notation = "qcf, k", moveType = "Special", character = "Rashid"),
        MoveEntry(moveName = "Arabian Cyclone", notation = "qcb, p", moveType = "Special", character = "Rashid"),
        MoveEntry(moveName = "Arabian Skyhigh", notation = "qcb, p", moveType = "Special", character = "Rashid"),
        MoveEntry(moveName = "Super Rashid Kick", notation = "qcf, qcf, k", moveType = "Super Art", character = "Rashid"),
        MoveEntry(moveName = "Ysar", notation = "qcb, qcb, k", moveType = "Super Art", character = "Rashid"),
        MoveEntry(moveName = "Altair", notation = "qcf, qcf, p", moveType = "Super Art", character = "Rashid"),

        // Ryu
        MoveEntry(moveName = "Hadoken", notation = "qcf, p", moveType = "Special", character = "Ryu"),
        MoveEntry(moveName = "Shoryuken", notation = "qcf, p", moveType = "Special", character = "Ryu"),
        MoveEntry(moveName = "Tatsumaki Senpu-kyaku", notation = "qcb, k", moveType = "Special", character = "Ryu"),
        MoveEntry(moveName = "High Blade Kick", notation = "qcf, k", moveType = "Special", character = "Ryu"),
        MoveEntry(moveName = "Hashogeki", notation = "qcb, p", moveType = "Special", character = "Ryu"),
        MoveEntry(moveName = "Denjin Charge", notation = "d, d, p", moveType = "Special", character = "Ryu"),
        MoveEntry(moveName = "Shinku Hadoken", notation = "qcf, qcf, p", moveType = "Super Art", character = "Ryu"),
        MoveEntry(moveName = "Shin Hashogeki", notation = "qcb, qcb, p", moveType = "Super Art", character = "Ryu"),
        MoveEntry(moveName = "Shin Shoryuken", notation = "qcf, qcf, k", moveType = "Super Art", character = "Ryu"),

        // Terry
        MoveEntry(moveName = "Power Wave", notation = "qcf, lp|hp", moveType = "Special", character = "Terry"),
        MoveEntry(moveName = "Round Wave", notation = "qcf, hp", moveType = "Special", character = "Terry"),
        MoveEntry(moveName = "Quick Burn", notation = "qcb, mp|hp", moveType = "Special", character = "Terry"),
        MoveEntry(moveName = "Power Charge", notation = "qcf, k", moveType = "Special", character = "Terry"),
        MoveEntry(moveName = "Crack Shoot", notation = "qcb, k", moveType = "Special", character = "Terry"),
        MoveEntry(moveName = "Rising Tackle", notation = "dp, p", moveType = "Special", character = "Terry"),
        MoveEntry(moveName = "Buster Wolf", notation = "qcf, qcf, k", moveType = "Super Art", character = "Terry"),
        MoveEntry(moveName = "Power Geyser", notation = "qcb, qcb, p", moveType = "Super Art", character = "Terry"),
        MoveEntry(moveName = "Rising Fang", notation = "qcf, qcf, p", moveType = "Super Art", character = "Terry"),

        // Zangief
        MoveEntry(moveName = "Double Lariat", notation = "p, p", moveType = "Special", character = "Zangief"),
        MoveEntry(moveName = "Screw Piledriver", notation = "fc, p", moveType = "Special", character = "Zangief"),
        MoveEntry(moveName = "Borscht Dynamite", notation = "fc, k", moveType = "Special", character = "Zangief"),
        MoveEntry(moveName = "Russian Suplex", notation = "hcb, k", moveType = "Special", character = "Zangief"),
        MoveEntry(moveName = "Siberian Express", notation = "hcb, k", moveType = "Special", character = "Zangief"),
        MoveEntry(moveName = "Tundra Storm", notation = "d, d, hk", moveType = "Special", character = "Zangief"),
        MoveEntry(moveName = "Aerial Russian Slam", notation = "qcf, qcf, k", moveType = "Super Art", character = "Zangief"),
        MoveEntry(moveName = "Cyclone Lariat", notation = "qcf, qcf, p", moveType = "Super Art", character = "Zangief"),
        MoveEntry(moveName = "Bolshoi Storm Buster", notation = "fc, fc, p", moveType = "Super Art", character = "Zangief"),
    )
)