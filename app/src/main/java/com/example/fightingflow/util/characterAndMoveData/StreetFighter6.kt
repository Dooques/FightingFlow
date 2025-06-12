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

val streetFighter6Moves = ImmutableList(
    listOf(
        // Classic Inputs
        MoveEntry(moveName = "lp", notation = "lp", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "mp", notation = "mp", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "hp", notation = "hp", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "lk", notation = "lk", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "mk", notation = "mk", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),
        MoveEntry(moveName = "hk", notation = "hk", moveType = "SF Classic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Classic),

        // Modern Inputs
        MoveEntry(moveName = "l", notation = "light", moveType = "SF Modern", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Modern),
        MoveEntry(moveName = "m", notation = "medium", moveType = "SF Modern", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Modern),
        MoveEntry(moveName = "h", notation = "heavy", moveType = "SF Modern", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Modern),
        MoveEntry(moveName = "s", notation = "special", moveType = "SF Modern", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Modern),


        // Mechanics
        MoveEntry(moveName = "Assist", notation = "assist", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI", controlType = SF6ControlType.Modern),
        MoveEntry(moveName = "Taunt", notation = "taunt", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Throw", notation = "throw", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "DI", notation = "DI", moveType = "Mechanic",character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Parry", notation = "Parry", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "DR", notation = "DR", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "DP", notation = "DP", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "OD", notation = "OD", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Air", notation = "Air", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "Wall Bounce", notation = "WB", moveType = "Mechanic", character = "Generic", game = "Street Fighter VI"),

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
        MoveEntry(moveName = "neutral", notation = "n", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "qcf", notation = "qcf", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "qcb", notation = "qcb", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "hcf", notation = "hcf", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "hcb", notation = "bd", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "fc", notation = "fc", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),
        MoveEntry(moveName = "dp", notation = "dp", moveType = "Movement", character = "Generic", game = "Street Fighter VI"),

        // A.K.I.
        MoveEntry(moveName = "Nightshade Pulse", notation = "Nightshade Pulse", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Orchid Spring", notation = "Orchid Spring", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Toxic Wreath", notation = "Toxic Wreath", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Serpent Lash", notation = "Serpent Lash", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Cruel Fate", notation = "Cruel Fate", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Snake Step", notation = "Snake Step", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sinister Slide", notation = "Sinister Slide", moveType = "Special", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Deadly Implication", notation = "Deadly Implication", moveType = "Super Art", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tainted Talons", notation = "Tainted Talons", moveType = "Super Art", character = "A.K.I.", game = "Street Fighter VI"),
        MoveEntry(moveName = "Claws of Ya Zi", notation = "Claws of Ya Zi", moveType = "Super Art", character = "A.K.I.", game = "Street Fighter VI"),

        // Akuma
        MoveEntry(moveName = "Gou Hadoken", notation = "Gou Hadoken", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Zanku Hadoken", notation = "Zanku Hadoken", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Gou Shoryuken", notation = "Gou Shoryuken", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tatsumaki Zanku-kyaku", notation = "Tatsumaki Zanku-kyaku", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Adamant Flame", notation = "Adamant Flame", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Demon Raid", notation = "Demon Raid", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ashura Senku", notation = "Ashura Senku", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Orobo Throw", notation = "Orobo Throw", moveType = "Special", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Messatsu Gohado", notation = "Messatsu Gohado", moveType = "Super Art", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tenma Gozanku", notation = "Tenma Gozanku", moveType = "Super Art", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Empyrean's End", notation = "Empyrean's End", moveType = "Super Art", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sip of Calamity", notation = "Sip of Calamity", moveType = "Super Art", character = "Akuma", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shun Goku Satsu", notation = "Shun Goku Satsu", moveType = "Super Art", character = "Akuma", game = "Street Fighter VI"),

        // Blanka
        MoveEntry(moveName = "Electric Thunder", notation = "Electric Thunder", moveType = "Special", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Rolling Attack", notation = "Rolling Attack", moveType = "Special", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Vertical Rolling Attack", notation = "Vertical Rolling Attack", moveType = "Special", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Backstep Rolling Attack", notation = "Backstep Rolling Attack", moveType = "Special", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Wild Hunt", notation = "Wild Hunt", moveType = "Special", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Blanka-chan Bomb", notation = "Blanka-chan Bomb", moveType = "Special", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shout of Earth", notation = "Shout of Earth", moveType = "Super Art", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Lightning Beast", notation = "Lightning Beast", moveType = "Super Art", character = "Blanka", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ground Shave Cannonball", notation = "Ground Shave Cannonball", moveType = "Super Art", character = "Blanka", game = "Street Fighter VI"),

        // Cammy
        MoveEntry(moveName = "Spiral Arrow", notation = "Spiral Arrow", moveType = "Special", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Cannon Spike", notation = "Cannon Spike", moveType = "Special", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Quick Spin Knuckle", notation = "Quick Spin Knuckle", moveType = "Special", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Cannon Strike", notation = "Cannon Strike", moveType = "Special", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hooligan Combination", notation = "Hooligan Combination", moveType = "Special", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Spin Drive Smasher", notation = "Spin Drive Smasher", moveType = "Super Art", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Killer Bee Spin", notation = "Killer Bee Spin", moveType = "Super Art", character = "Cammy", game = "Street Fighter VI"),
        MoveEntry(moveName = "Delta Red Assault", notation = "Delta Red Assault", moveType = "Super Art", character = "Cammy", game = "Street Fighter VI"),

        // Chun-Li
        MoveEntry(moveName = "Kikoken", notation = "Kikoken", moveType = "Special", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hundred Lightning Kicks", notation = "Hundred Lightning Kicks", moveType = "Special", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Spinning Bird Kick", notation = "Spinning Bird Kick", moveType = "Special", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hazanshu", notation = "Hazanshu", moveType = "Special", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tensho Kicks", notation = "Tensho Kicks", moveType = "Special", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Kikosho", notation = "Kikosho", moveType = "Super Art", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hoyoku-sen", notation = "Hoyoku-sen", moveType = "Super Art", character = "Chun-Li", game = "Street Fighter VI"),
        MoveEntry(moveName = "Soten Ranka", notation = "Soten Ranka", moveType = "Super Art", character = "Chun-Li", game = "Street Fighter VI"),

        // Dee Jay
        MoveEntry(moveName = "Air Slasher", notation = "Air Slasher", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Jackknife Maximum", notation = "Jackknife Maximum", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Roll Through Feint", notation = "Roll Through Feint", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Quick Rolling Sobat", notation = "Quick Rolling Sobat", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Double Rolling Sobat", notation = "Double Rolling Sobat", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Machine Gun Uppercut", notation = "Machine Gun Uppercut", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Jus Cool", notation = "Jus Cool", moveType = "Special", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "The Greatest Sobat", notation = "The Greatest Sobat", moveType = "Super Art", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Lowkey Sunrise Festival", notation = "Lowkey Sunrise Festival", moveType = "Super Art", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Marvelous Sunrise Festival", notation = "Marvelous Sunrise Festival", moveType = "Super Art", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Headliner Sunrise Festival", notation = "Headliner Sunrise Festival", moveType = "Super Art", character = "Dee Jay", game = "Street Fighter VI"),
        MoveEntry(moveName = "Weekend Pleasure", notation = "Weekend Pleasure", moveType = "Super Art", character = "Dee Jay", game = "Street Fighter VI"),

        // Dhalsim
        MoveEntry(moveName = "Yoga Fire", notation = "Yoga Fire", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Arch", notation = "Yoga Arch", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Flame", notation = "Yoga Flame", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Blast", notation = "Yoga Blast", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Comet", notation = "Yoga Comet", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Float", notation = "Yoga Float", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Teleport", notation = "Yoga Teleport", moveType = "Special", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Inferno", notation = "Yoga Inferno", moveType = "Super Art", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Yoga Sunburst", notation = "Yoga Sunburst", moveType = "Super Art", character = "Dhalsim", game = "Street Fighter VI"),
        MoveEntry(moveName = "Merciless Yoga", notation = "Merciless Yoga", moveType = "Super Art", character = "Dhalsim", game = "Street Fighter VI"),

        // Ed
        MoveEntry(moveName = "Psycho Spark", notation = "Psycho Spark", moveType = "Special", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Uppercut", notation = "Psycho Uppercut", moveType = "Special", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Blitz", notation = "Psycho Blitz", moveType = "Special", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Flicker", notation = "Psycho Flicker", moveType = "Special", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Kill Rush", notation = "Kill Rush", moveType = "Special", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Storm", notation = "Psycho Storm", moveType = "Super Art", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Cannon", notation = "Psycho Cannon", moveType = "Super Art", character = "Ed", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Chamber", notation = "Psycho Chamber", moveType = "Super Art", character = "Ed", game = "Street Fighter VI"),

        // Elena
        MoveEntry(moveName = "Scratch Wheel", notation = "Scratch Wheel", moveType = "Special", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "Mallet Smash", notation = "Mallet Smash", moveType = "Special", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "Spin Scythe", notation = "Spin Scythe", moveType = "Special", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "Healing", notation = "Healing", moveType = "Special", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "Healing Wind", notation = "Healing Wind", moveType = "Super Art", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "Brave Dance", notation = "Brave Dance", moveType = "Super Art", character = "Elena", game = "Street Fighter VI"),
        MoveEntry(moveName = "Skyward Dance", notation = "Skyward Dance", moveType = "Super Art", character = "Elena", game = "Street Fighter VI"),

        // E. Honda
        MoveEntry(moveName = "Hundred Hand Slap", notation = "Hundred Hand Slap", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sumo Headbutt", notation = "Sumo Headbutt", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sumo Smash", notation = "Sumo Smash", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Oicho Throw", notation = "Oicho Throw", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sumo Dash", notation = "Sumo Dash", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Neko Damashi", notation = "Neko Damashi", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sumo Spirit", notation = "Sumo Spirit", moveType = "Special", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Show of Force", notation = "Show of Force", moveType = "Super Art", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ultimate Killer Head Ram", notation = "Ultimate Killer Head Ram", moveType = "Super Art", character = "E. Honda", game = "Street Fighter VI"),
        MoveEntry(moveName = "The Final Bout", notation = "The Final Bout", moveType = "Super Art", character = "E. Honda", game = "Street Fighter VI"),

        // Guile
        MoveEntry(moveName = "Sonic Boom", notation = "Sonic Boom", moveType = "Special", character = "Guile", game = "Street Fighter VI"),
        MoveEntry(moveName = "Somersault Kick", notation = "Somersault Kick", moveType = "Special", character = "Guile", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sonic Blade", notation = "h+u|d p", moveType = "Special", character = "Guile", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sonic Break", notation = "Sonic Break", moveType = "Special", character = "Guile", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sonic Hurricane", notation = "Sonic Hurricane", moveType = "Super Art", character = "Guile", game = "Street Fighter VI"),
        MoveEntry(moveName = "Solid Puncher", notation = "Solid Puncher", moveType = "Super Art", character = "Guile", game = "Street Fighter VI"),
        MoveEntry(moveName = "Crossfire Somersault", notation = "Crossfire Somersault", moveType = "Super Art", character = "Guile", game = "Street Fighter VI"),

        // Jamie
        MoveEntry(moveName = "The Devil Inside", notation = "The Devil Inside", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Freeflow Strikes", notation = "Freeflow Strikes", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Freeflow Kicks", notation = "Freeflow Kicks", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Swagger Step", notation = "Swagger Step", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Arrow Kick", notation = "Arrow Kick", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Luminous Dive Kick", notation = "Luminous Dive Kick", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Bakkai", notation = "Bakkai", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tenshin", notation = "Tenshin", moveType = "Special", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Breakin'", notation = "Breakin", moveType = "Super Art", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "The Devil's Song", notation = "The Devil's Song", moveType = "Super Art", character = "Jamie", game = "Street Fighter VI"),
        MoveEntry(moveName = "Getsuga Saiho", notation = "Getsuga Saiho", moveType = "Super Art", character = "Jamie", game = "Street Fighter VI"),

        // JP
        MoveEntry(moveName = "Triglav", notation = "Triglav", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Departure", notation = "Departure", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Stribog", notation = "Stribog", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Torbalan", notation = "Torbalan", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Amnesia", notation = "Amnesia", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Torbalan", notation = "Torbalan", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Embrace", notation = "Embrace", moveType = "Special", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Chornobog", notation = "Chornobog", moveType = "Super Art", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Lovushka", notation = "Lovushka", moveType = "Super Art", character = "JP", game = "Street Fighter VI"),
        MoveEntry(moveName = "Interdiction", notation = "Interdiction", moveType = "Super Art", character = "JP", game = "Street Fighter VI"),

        // Juri
        MoveEntry(moveName = "Fuhajin", notation = "Fuhajin", moveType = "Special", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Saihasho", notation = "Saihasho", moveType = "Special", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ankensatsu", notation = "Ankensatsu", moveType = "Special", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Go Ohsatsu", notation = "Go Ohsatsu", moveType = "Special", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tensesnrin", notation = "Tensesnrin", moveType = "Special", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shiku-sen", notation = "Shiku-sen", moveType = "Special", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sakkai Fuhazan", notation = "Sakkai Fuhazan", moveType = "Super Art", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Feng Shui Engine", notation = "Feng Shui Engine", moveType = "Super Art", character = "Juri", game = "Street Fighter VI"),
        MoveEntry(moveName = "Kaisen Dankai Raku", notation = "Kaisen Dankai Raku", moveType = "Super Art", character = "Juri", game = "Street Fighter VI"),

        // Ken
        MoveEntry(moveName = "Hadoken", notation = "Hadoken", moveType = "Special", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shoryuken", notation = "Shoryuken", moveType = "Special", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tatsumaki Senpu-kyaku", notation = "Tatsumaki Senpu-kyaku", moveType = "Special", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Dragonlash Kick", notation = "Dragonlash Kick", moveType = "Special", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Jinrai Kick", notation = "Jinrai Kick", moveType = "Special", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Kasai Thrust Kick", notation = "Kasai Thrust Kick", moveType = "Special", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Dragonlash Flame", notation = "Dragonlash Flame", moveType = "Super Art", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shippu Jinrai-kyaku", notation = "Shippu Jinrai-kyaku", moveType = "Super Art", character = "Ken", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shinryu Reppa", notation = "Shinryu Reppa", moveType = "Super Art", character = "Ken", game = "Street Fighter VI"),

        // Kimberly
        MoveEntry(moveName = "Bushin Senpukyaku", notation = "Bushin Senpukyaku", moveType = "Special", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Sprint", notation = "Sprint", moveType = "Special", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Vagabond Edge", notation = "Vagabond Edge", moveType = "Special", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hidden Variable", notation = "Hidden Variable", moveType = "Special", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Genius at Play", notation = "Genius at Play", moveType = "Special", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Nue Twister", notation = "Nue Twister", moveType = "Special", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Bushin Beats", notation = "Bushin Beats", moveType = "Super Art", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Bushin Scramble", notation = "Bushin Scramble", moveType = "Super Art", character = "Kimberly", game = "Street Fighter VI"),
        MoveEntry(moveName = "Bushin Ninjastar Cypher ", notation = "Bushin Ninjastar Cypher", moveType = "Super Art", character = "Kimberly", game = "Street Fighter VI"),

        // Lily
        MoveEntry(moveName = "Condor Wind", notation = "Condor Wind", moveType = "Special", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Condor Spire", notation = "Condor Spire", moveType = "Special", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tomahawk Buster", notation = "Tomahawk Buster", moveType = "Special", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Condor Dive", notation = "Condor Dive", moveType = "Special", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Mexican Typhoon", notation = "Mexican Typhoon", moveType = "Special", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Breezing Hawk", notation = "Breezing Hawk", moveType = "Super Art", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Thunderbird", notation = "Thunderbird", moveType = "Super Art", character = "Lily", game = "Street Fighter VI"),
        MoveEntry(moveName = "Raging Typhoon", notation = "Raging Typhoon", moveType = "Super Art", character = "Lily", game = "Street Fighter VI"),

        // Luke
        MoveEntry(moveName = "Sand Blast", notation = "Sand Blast", moveType = "Special", character = "Luke", game = "Street Fighter VI"),
        MoveEntry(moveName = "Flash Knuckle", notation = "Flash Knuckle", moveType = "Special", character = "Luke", game = "Street Fighter VI"),
        MoveEntry(moveName = "Avenger", notation = "Avenger", moveType = "Special",  character = "Luke", game = "Street Fighter VI"),
        MoveEntry(moveName = "Rising Uppercut", notation = "Rising Uppercut", moveType = "Special", character = "Luke", game = "Street Fighter VI"),
        MoveEntry(moveName = "Vulcan Blast", notation = "Vulcan Blast", moveType = "Super Art", character = "Luke", game = "Street Fighter VI"),
        MoveEntry(moveName = "Eraser", notation = "Eraser", moveType = "Super Art", character = "Luke", game = "Street Fighter VI"),
        MoveEntry(moveName = "Pale Rider", notation = "Pale Rider", moveType = "Super Art", character = "Luke", game = "Street Fighter VI"),

        // Mai
        MoveEntry(moveName = "Kachousen", notation = "Kachousen", moveType = "Special", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ryuuenbu", notation = "Ryuuenbu", moveType = "Special", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hissatsu Shinobi Bachi", notation = "Hissatsu Shinobi Bachi", moveType = "Special", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hishou Ryuuenjin", notation = "Hishou Ryuuenjin", moveType = "Special", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Musasabi no Mai", notation = "Musasabi no Mai", moveType = "Special", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Kagreou no Mai", notation = "Kagreou no Mai", moveType = "Super Art", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Chou Hissatsu Shinobi Bachi", notation = "Chou Hissatsu Shinobi Bachi", moveType = "Super Art", character = "Mai", game = "Street Fighter VI"),
        MoveEntry(moveName = "Enbu Ada Zakura", notation = "Enbu Ada Zakura", moveType = "Super Art", character = "Mai", game = "Street Fighter VI"),

        // Manon
        MoveEntry(moveName = "Manegé Doré", notation = "Manegé Doré", moveType = "Special", character = "Manon", game = "Street Fighter VI"),
        MoveEntry(moveName = "Dégagé", notation = "Dégagé", moveType = "Special", character = "Manon", game = "Street Fighter VI"),
        MoveEntry(moveName = "Renversé", notation = "Renversé", moveType = "Special", character = "Manon", game = "Street Fighter VI"),
        MoveEntry(moveName = "Rond-Point", notation = "Rond-Point", moveType = "Special", character = "Manon", game = "Street Fighter VI"),
        MoveEntry(moveName = "Arabesque", notation = "Arabesque", moveType = "Super Art", character = "Manon", game = "Street Fighter VI"),
        MoveEntry(moveName = "Étoile", notation = "Étoile", moveType = "Super Art", character = "Manon", game = "Street Fighter VI"),
        MoveEntry(moveName = "Pas de Deux", notation = "Pas de Deux", moveType = "Super Art", character = "Manon", game = "Street Fighter VI"),

        // Marisa
        MoveEntry(moveName = "Gladius", notation = "Gladius", moveType = "Special", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Scutum", notation = "Scutum", moveType = "Special", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Phalanx", notation = "Phalanx", moveType = "Special", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Dimachearus", notation = "Dimachearus", moveType = "Special", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Quadriga", notation = "Quadriga", moveType = "Special", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Javelin of Marisa", notation = "Javelin of Marisa", moveType = "Super Art", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Meteorite", notation = "Meteorite", moveType = "Super Art", character = "Marisa", game = "Street Fighter VI"),
        MoveEntry(moveName = "Godess of the Hunt", notation = "Godess of the Hunt", moveType = "Super Art", character = "Marisa", game = "Street Fighter VI"),

        // M. Bison
        MoveEntry(moveName = "Psycho Crusher Attack", notation = "Psycho Crusher Attack", moveType = "Special", character = "M. Bison", game = "Street Fighter VI"),
        MoveEntry(moveName = "Double Knee Press", notation = "Double Knee Press", moveType = "Special", character = "M. Bison", game = "Street Fighter VI"),
        MoveEntry(moveName = "Backfist Combo", notation = "Backfist Combo", moveType = "Special", character = "M. Bison", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shadow Rise", notation = "Shadow Rise", moveType = "Special", character = "M. Bison", game = "Street Fighter VI"),
        MoveEntry(moveName = "Knee Press Nightmare", notation = "Knee Press Nightmare", moveType = "Super Art", character = "M. Bison", game = "Street Fighter VI"),
        MoveEntry(moveName = "Psycho Punisher", notation = "Psycho Punisher", moveType = "Super Art", character = "M. Bison", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ultimate Psycho Crusher", notation = "Ultimate Psycho Crusher", moveType = "Super Art", character = "M. Bison", game = "Street Fighter VI"),

        // Rashid
        MoveEntry(moveName = "Spinning Mixer", notation = "Spinning Mixer", moveType = "Special", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Eagle Spike", notation = "Eagle Spike", moveType = "Special", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Whirlwind Shot", notation = "Whirlwind Shot", moveType = "Special", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Arabian Cyclone", notation = "Arabian Cyclone", moveType = "Special", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Arabian Skyhigh", notation = "Arabian Skyhigh", moveType = "Special", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Super Rashid Kick", notation = "Super Rashid Kick", moveType = "Super Art", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Ysar", notation = "Ysar", moveType = "Super Art", character = "Rashid", game = "Street Fighter VI"),
        MoveEntry(moveName = "Altair", notation = "Altair", moveType = "Super Art", character = "Rashid", game = "Street Fighter VI"),

        // Ryu
        MoveEntry(moveName = "Hadoken", notation = "Hadoken", moveType = "Special", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shoryuken", notation = "Shoryuken", moveType = "Special", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tatsumaki Senpu-kyaku", notation = "Tatsumaki Senpu-kyaku", moveType = "Special", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "High Blade Kick", notation = "High Blade Kick", moveType = "Special", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Hashogeki", notation = "Hashogeki", moveType = "Special", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Denjin Charge", notation = "Denjin Charge", moveType = "Special", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shinku Hadoken", notation = "Shinku Hadoken", moveType = "Super Art", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shin Hashogeki", notation = "Shin Hashogeki", moveType = "Super Art", character = "Ryu", game = "Street Fighter VI"),
        MoveEntry(moveName = "Shin Shoryuken", notation = "Shin Shoryuken", moveType = "Super Art", character = "Ryu", game = "Street Fighter VI"),

        // Terry
        MoveEntry(moveName = "Power Wave", notation = "Power Wave", moveType = "Special", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Round Wave", notation = "Round Wave", moveType = "Special", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Quick Burn", notation = "Quick Burn", moveType = "Special", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Power Charge", notation = "Power Charge", moveType = "Special", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Crack Shoot", notation = "Crack Shoot", moveType = "Special", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Rising Tackle", notation = "Rising Tackle", moveType = "Special", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Buster Wolf", notation = "Buster Wolf", moveType = "Super Art", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Power Geyser", notation = "Power Geyser", moveType = "Super Art", character = "Terry", game = "Street Fighter VI"),
        MoveEntry(moveName = "Rising Fang", notation = "Rising Fang", moveType = "Super Art", character = "Terry", game = "Street Fighter VI"),

        // Zangief
        MoveEntry(moveName = "Double Lariat", notation = "Double Lariat", moveType = "Special", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Screw Piledriver", notation = "Screw Piledriver", moveType = "Special", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Borscht Dynamite", notation = "Borscht Dynamite", moveType = "Special", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Russian Suplex", notation = "Russian Suplex", moveType = "Special", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Siberian Express", notation = "Siberian Express", moveType = "Special", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Tundra Storm", notation = "Tundra Storm", moveType = "Special", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Aerial Russian Slam", notation = "Aerial Russian Slam", moveType = "Super Art", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Cyclone Lariat", notation = "Cyclone Lariat", moveType = "Super Art", character = "Zangief", game = "Street Fighter VI"),
        MoveEntry(moveName = "Bolshoi Storm Buster", notation = "Bolshoi Storm Buster", moveType = "Super Art", character = "Zangief", game = "Street Fighter VI"),
    )
)