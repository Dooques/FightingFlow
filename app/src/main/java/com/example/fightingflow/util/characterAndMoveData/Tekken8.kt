package com.example.fightingflow.util.characterAndMoveData

import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.ImmutableList

val tekken8Characters: ImmutableList<CharacterEntry> = ImmutableList(
    listOf(
        CharacterEntry(name = "Alisa", imageId = R.drawable.alisa, fightingStyle = "Thruster-Based High-Mobility Fighting Style", game = "Tekken 8"),
        CharacterEntry(name = "Anna", imageId = R.drawable.anna, fightingStyle = "Aikido based techniques and Koppojutsu based Assassination Arts", game = "Tekken 8"),
        CharacterEntry(name = "Asuka", imageId = R.drawable.asuka, fightingStyle = "Kazama Style Traditional Martial Arts", game = "Tekken 8"),
        CharacterEntry(name = "Azucena", imageId = R.drawable.azucena, fightingStyle = "Mixed Martial Arts (Striker)", game = "Tekken 8"),
        CharacterEntry(name = "Bryan", imageId = R.drawable.bryan, fightingStyle = "Kickboxing", game = "Tekken 8"),
        CharacterEntry(name = "Claudio", imageId = R.drawable.claudio, fightingStyle = "Sirius Exorcist Arts", game = "Tekken 8"),
        CharacterEntry(name = "Clive", imageId = R.drawable.clive, fightingStyle = "Dominant", game = "Tekken 8"),
        CharacterEntry(name = "Devil Jin", imageId = R.drawable.devil_jin, fightingStyle = "Unknown", game = "Tekken 8"),
        CharacterEntry(name = "Dragunov", imageId = R.drawable.dragunov, fightingStyle = "White Reaper", game = "Tekken 8"),
        CharacterEntry(name = "Eddy", imageId = R.drawable.eddy, fightingStyle = "Capoeira", game = "Tekken 8"),
        CharacterEntry(name = "Feng", imageId = R.drawable.feng, fightingStyle = "Taijiquan", game = "Tekken 8"),
        CharacterEntry(name = "Heihachi", imageId = R.drawable.heihachi, fightingStyle = "Mishima Style Fighting Karate", game = "Tekken 8"),
        CharacterEntry(name = "Hwoarang", imageId = R.drawable.hwoarang, fightingStyle = "Taekwondo", game = "Tekken 8"),
        CharacterEntry(name = "Jack-8", imageId = R.drawable.jack_8, fightingStyle = "High Tech Annihilator", game = "Tekken 8"),
        CharacterEntry(name = "Jin", imageId = R.drawable.jin, fightingStyle = "Karate based on Kyokushin style infused with Devil Gene", game = "Tekken 8"),
        CharacterEntry(name = "Jun", imageId = R.drawable.jun, fightingStyle = "Kazama Style Traditional Martial Arts", game = "Tekken 8"),
        CharacterEntry(name = "Kazuya", imageId = R.drawable.kazuya, fightingStyle = "Mishima Style Fighting Karate", game = "Tekken 8"),
        CharacterEntry(name = "King", imageId = R.drawable.king, fightingStyle = "Pro Wrestling", game = "Tekken 8"),
        CharacterEntry(name = "Kuma", imageId = R.drawable.kuma, fightingStyle = "Heihachi-style Improved Kuma Shinken", game = "Tekken 8"),
        CharacterEntry(name = "Lars", imageId = R.drawable.lars, fightingStyle = "Tekken Forces Martial Arts", game = "Tekken 8"),
        CharacterEntry(name = "Law", imageId = R.drawable.law, fightingStyle = "Martial Arts", game = "Tekken 8"),
        CharacterEntry(name = "Lee", imageId = R.drawable.lee, fightingStyle = "Martial Arts", game = "Tekken 8"),
        CharacterEntry(name = "Leo", imageId = R.drawable.leo, fightingStyle = "Baji Quan", game = "Tekken 8"),
        CharacterEntry(name = "Leroy", imageId = R.drawable.leroy, fightingStyle = "Wing Chun", game = "Tekken 8"),
        CharacterEntry(name = "Lidia", imageId = R.drawable.lidia, fightingStyle = "Traditional Karate", game = "Tekken 8"),
        CharacterEntry(name = "Lili", imageId = R.drawable.lili, fightingStyle = "Self-Taught Style", game = "Tekken 8"),
        CharacterEntry(name = "Nina", imageId = R.drawable.nina, fightingStyle = "Assassination Arts", game = "Tekken 8"),
        CharacterEntry(name = "Panda", imageId = R.drawable.panda, fightingStyle = "Heihachi-style Improved Kuma Shinken", game = "Tekken 8"),
        CharacterEntry(name = "Paul", imageId = R.drawable.paul, fightingStyle = "Integrated Martial Arts", game = "Tekken 8"),
        CharacterEntry(name = "Raven", imageId = R.drawable.raven, fightingStyle = "Ninjutsu", game = "Tekken 8"),
        CharacterEntry(name = "Reina", imageId = R.drawable.reina, fightingStyle = "Taido and Mishima-Style Karate", game = "Tekken 8"),
        CharacterEntry(name = "Shaheen", imageId = R.drawable.shaheen, fightingStyle = "Close Quarters Combat", game = "Tekken 8"),
        CharacterEntry(name = "Steve", imageId = R.drawable.steve, fightingStyle = "Boxing", game = "Tekken 8"),
        CharacterEntry(name = "Victor", imageId = R.drawable.victor, fightingStyle = "Super Spy CQB", game = "Tekken 8"),
        CharacterEntry(name = "Xiaoyu", imageId = R.drawable.xiaoyu, fightingStyle = "Chinese Martial Arts", game = "Tekken 8"),
        CharacterEntry(name = "Yoshimitsu", imageId = R.drawable.yoshimitsu, fightingStyle = "Advanced Manji Ninjutsu", game = "Tekken 8"),
        CharacterEntry(name = "Zafina", imageId = R.drawable.zafina, fightingStyle = "Ancient Assassination Arts", game = "Tekken 8")
    )
)

val tekken8CharacterMoves = ImmutableList(
    listOf(

        // Movements
        MoveEntry(moveName = "forward", notation = "f", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "up", notation = "u", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "up_forward", notation = "u/f", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "down_forward", notation = "d/f", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "forward_dash", notation = "F", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "back", notation = "b", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "down", notation = "d", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "up_back", notation = "u/b", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "down_back", notation = "d/b", moveType = "Movement", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "neutral", notation = "n", moveType = "Movement", character = "Generic", game = "Tekken 8"),

        // Inputs
        MoveEntry(moveName = "one", notation = "1", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "two", notation = "2", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "three", notation = "3", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "four", notation = "4", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "one_plus_two", notation = "1+2", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "three_plus_four", notation = "3+4", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "one_plus_three", notation = "1+3", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "two_plus_four", notation = "2+4", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "one_plus_four", notation = "1+4", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "two_plus_three", notation = "2+3", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "one_plus_two_plus_three", notation = "1+2+3", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "one_plus_two_plus_four", notation = "1+2+4", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "one_plus_three_plus_four", notation = "1+3+4", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "two_plus_three_plus_four", notation = "2+3+4", moveType = "Input", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "one_plus_two_plus_three_plus_four", notation = "1+2+3+4", moveType = "Input", character = "Generic", game = "Tekken 8"),

        // Modifiers
        MoveEntry(moveName = "Hold", notation = "h", moveType = "Modifier", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Hold Max", notation = "H", moveType = "Modifier", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Delay", notation = "delay", moveType = "Modifier", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Just Frame", notation = "j/f", moveType = "Modifier", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Slide", notation = "(s)", moveType = "Modifier", character = "Generic", game = "Tekken 8"),

        // Common Stances
        MoveEntry(moveName = "Side Switch", notation = "s/sw", moveType = "Common", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Back Turned", notation = "b/t", moveType = "Common", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "While Standing", notation = "w/s", moveType = "Common", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Crouch", notation = "cr", moveType = "Common", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Dash", notation = "dash", moveType = "Common", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Sidestep", notation = "s/st", moveType = "Common", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Low Parry", notation = "l/p", moveType = "Common", character = "Generic", game = "Tekken 8"),

        // Mechanics
        MoveEntry(moveName = "Heat", notation = "heat", moveType = "Mechanic", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Heat Burst", notation = "h/burst", moveType = "Mechanic", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Heat Dash", notation = "h/dash", moveType = "Mechanic", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Rage Art", notation = "rage", moveType = "Mechanic", character = "Generic", game = "Tekken 8"),

        // Stage
        MoveEntry(moveName = "Wall Splat", notation = "w/splat", moveType = "Stage", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Wall Break", notation = "w/break", moveType = "Stage", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Wall Blast", notation = "w/blast", moveType = "Stage", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Wall Bounce", notation = "w/bounce", moveType = "Stage", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Floor Break", notation = "f/break", moveType = "Stage", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Floor Blast", notation = "f/blast", moveType = "Stage", character = "Generic", game = "Tekken 8"),
        MoveEntry(moveName = "Balcony Break", notation = "b/break", moveType = "Stage", character = "Generic", game = "Tekken 8"),

        // Alisa
        MoveEntry(moveName = "Destructive Form", notation = "Destructive Form", moveType = "Character", character = "Alisa", game = "Tekken 8"),
        MoveEntry(moveName = "Boot", notation = "Boot", moveType = "Character", character = "Alisa", game = "Tekken 8"),
        MoveEntry(moveName = "Dual Boot", notation = "Dual Boot", moveType = "Character", character = "Alisa", game = "Tekken 8"),

        // Anna
        MoveEntry(moveName = "Hammer Chance", notation = "Hammer Chance", moveType = "Character", character = "Anna", game = "Tekken 8"),
        MoveEntry(moveName = "Chaos Judgement", notation = "Chaos Judgement", moveType = "Character", character = "Anna", game = "Tekken 8"),

        //Asuka
        MoveEntry(moveName = "Naniwa Gusto", notation = "Naniwa Gusto", moveType = "Character", character = "Asuka", game = "Tekken 8"),
        MoveEntry(moveName = "Leg Cutter", notation = "Leg Cutter", moveType = "Character", character = "Asuka", game = "Tekken 8"),

        // Azucena
        MoveEntry(moveName = "Libertador", notation = "Libertador", moveType = "Character", character = "Azucena", game = "Tekken 8"),
        MoveEntry(moveName = "Taking High Attack", notation = "Taking High Attack", moveType = "Character", character = "Azucena", game = "Tekken 8"),
        MoveEntry(moveName = "Taking Low Attack", notation = "Taking Low Attack", moveType = "Character", character = "Azucena", game = "Tekken 8"),

        // Bryan
        MoveEntry(moveName = "Snake Eyes", notation = "Snake Eyes", moveType = "Character", character = "Bryan", game = "Tekken 8"),
        MoveEntry(moveName = "Slither Step", notation = "Slither Step", moveType = "Character", character = "Bryan", game = "Tekken 8"),

        // Claudio
        MoveEntry(moveName = "Starburst", notation = "Starburst", moveType = "Character", character = "Claudio"),

        // Clive
        MoveEntry(moveName = "Phoenix Shift", notation = "Phoenix Shift", moveType = "Character", character = "Clive", game = "Tekken 8"),
        MoveEntry(moveName = "Wings Of Light", notation = "Wings Of Light", moveType = "Character", character = "Clive", game = "Tekken 8"),
        MoveEntry(moveName = "Updraft", notation = "Updraft", moveType = "Character", character = "Clive", game = "Tekken 8"),
        MoveEntry(moveName = "Zantetsuken", notation = "Zantetsuken", moveType = "Character", character = "Clive", game = "Tekken 8"),

        // Devil Jin
        MoveEntry(moveName = "Mourning Crow", notation = "Mourning Crow", moveType = "Character", character = "Devil Jin", game = "Tekken 8"),
        MoveEntry(moveName = "Fly", notation = "Fly", moveType = "Character", character = "Devil Jin", game = "Tekken 8"),
        MoveEntry(moveName = "WGS", notation = "WGS", moveType = "Mishima", character = "Devil Jin", game = "Tekken 8"),
        MoveEntry(moveName = "WGF", notation = "WGF", moveType = "Mishima", character = "Devil Jin", game = "Tekken 8"),
        MoveEntry(moveName = "EWGF", notation = "EWGF", moveType = "Mishima", character = "Devil Jin", game = "Tekken 8"),

        // Dragunov
        MoveEntry(moveName = "Sneak", notation = "Sneak", moveType = "Character", character = "Dragunov", game = "Tekken 8"),

        // Eddy
        MoveEntry(moveName = "Ginga", notation = "Ginga", moveType = "Character", character = "Eddy", game = "Tekken 8"),
        MoveEntry(moveName = "Bananeira", notation = "Bananeira", moveType = "Character", character = "Eddy", game = "Tekken 8"),
        MoveEntry(moveName = "Negativa", notation = "Negativa", moveType = "Character", character = "Eddy", game = "Tekken 8"),
        MoveEntry(moveName = "Mandinga", notation = "Mandinga", moveType = "Character", character = "Eddy", game = "Tekken 8"),

        // Feng
        MoveEntry(moveName = "Deceptive Step", notation = "Deceptive Step", moveType = "Character", character = "Feng", game = "Tekken 8"),
        MoveEntry(moveName = "Shifting Clouds", notation = "Shifting Clouds", moveType = "Character", character = "Feng", game = "Tekken 8"),

        // Heihachi
        MoveEntry(moveName = "Wind God's Kamae", notation = "Wind God's Kamae", moveType = "Character", character = "Heihachi", game = "Tekken 8"),
        MoveEntry(moveName = "Thunder God's Kamae", notation = "Thunder God's Kamae", moveType = "Character", character = "Heihachi", game = "Tekken 8"),
        MoveEntry(moveName = "Warrior Instinct", notation = "Warrior Instinct", moveType = "Character", character = "Heihachi", game = "Tekken 8"),
        MoveEntry(moveName = "WGS", notation = "WGS", moveType = "Mishima", character = "Heihachi", game = "Tekken 8"),
        MoveEntry(moveName = "WGF", notation = "WGF", moveType = "Mishima", character = "Heihachi", game = "Tekken 8"),
        MoveEntry(moveName = "EWGF", notation = "EWGF", moveType = "Mishima", character = "Heihachi", game = "Tekken 8"),
        MoveEntry(moveName = "TGF", notation = "TGF", moveType = "Mishima", character = "Heihachi", game = "Tekken 8"),
        MoveEntry(moveName = "ETGF", notation = "ETGF", moveType = "Mishima", character = "Heihachi", game = "Tekken 8"),
        MoveEntry(moveName = "BSK", notation = "BSK", moveType = "Mishima", character = "Heihachi", game = "Tekken 8"),
        MoveEntry(moveName = "ESK", notation = "ESK", moveType = "Mishima", character = "Heihachi", game = "Tekken 8"),
        MoveEntry(moveName = "SD", notation = "SD", moveType = "Mishima", character = "Heihachi", game = "Tekken 8"),

        // Hwoarang
        MoveEntry(moveName = "Right Stance", notation = "Right Stance", moveType = "Character", character = "Hwoarang", game = "Tekken 8"),
        MoveEntry(moveName = "Left Stance", notation = "Left Stance", moveType = "Character", character = "Hwoarang", game = "Tekken 8"),
        MoveEntry(moveName = "Right Flamingo", notation = "Right Flamingo", moveType = "Character", character = "Hwoarang", game = "Tekken 8"),
        MoveEntry(moveName = "Left Flamingo", notation = "Left Flamingo", moveType = "Character", character = "Hwoarang", game = "Tekken 8"),

        // Jack-8
        MoveEntry(moveName = "Sit Down", notation = "Sit Down", moveType = "Character", character = "Jack-8", game = "Tekken 8"),
        MoveEntry(moveName = "Gamma Howl", notation = "Gamma Howl", moveType = "Character", character = "Jack-8", game = "Tekken 8"),

        // Jin
        MoveEntry(moveName = "Zenshin", notation = "Zenshin", moveType = "Character", character = "Jin", game = "Tekken 8"),
        MoveEntry(moveName = "WGS", notation = "WGS", moveType = "Mishima", character = "Jin", game = "Tekken 8"),
        MoveEntry(moveName = "WHF", notation = "WGF", moveType = "Mishima", character = "Jin", game = "Tekken 8"),
        MoveEntry(moveName = "EWHF", notation = "EWGF", moveType = "Mishima", character = "Jin", game = "Tekken 8"),

        // Jun
        MoveEntry(moveName = "Izumo", notation = "Izumo", moveType = "Character", character = "Jun", game = "Tekken 8"),
        MoveEntry(moveName = "Genjutsu", notation = "Genjutsu", moveType = "Character", character = "Jun", game = "Tekken 8"),
        MoveEntry(moveName = "Miare", notation = "Miare", moveType = "Character", character = "Jun", game = "Tekken 8"),

        // Kazuya
        MoveEntry(moveName = "Devil Form", notation = "Devil Form", moveType = "Character", character = "Kazuya", game = "Tekken 8"),
        MoveEntry(moveName = "WGS", notation = "WGS", moveType = "Mishima", character = "Kazuya", game = "Tekken 8"),
        MoveEntry(moveName = "WGF", notation = "WGF", moveType = "Mishima", character = "Kazuya", game = "Tekken 8"),
        MoveEntry(moveName = "EWGF", notation = "EWGF", moveType = "Mishima", character = "Kazuya", game = "Tekken 8"),

        // Kuma & Panda
        MoveEntry(moveName = "Hunting", notation = "Hunting", moveType = "Character", character = "Kuma & Panda", game = "Tekken 8"),
        MoveEntry(moveName = "Bear Sit", notation = "Bear Sit", moveType = "Character", character = "Kuma & Panda", game = "Tekken 8"),
        MoveEntry(moveName = "Bear Roll", notation = "Bear Roll", moveType = "Character", character = "Kuma & Panda", game = "Tekken 8"),

        // Lars
        MoveEntry(moveName = "Dynamic Entry", notation = "Dynamic Entry", moveType = "Character", character = "Lars", game = "Tekken 8"),
        MoveEntry(moveName = "Silent Entry", notation = "Silent Entry", moveType = "Character", character = "Lars", game = "Tekken 8"),
        MoveEntry(moveName = "Limited Entry", notation = "Limited Entry", moveType = "Character", character = "Lars", game = "Tekken 8"),

        // Law
        MoveEntry(moveName = "Dragon Charge", notation = "Dragon Charge", moveType = "Character", character = "Law", game = "Tekken 8"),

        // Lee
        MoveEntry(moveName = "Hitman", notation = "Hitman", moveType = "Character", character = "Lee", game = "Tekken 8"),

        // Leo
        MoveEntry(moveName = "Jin Ji Du Li", notation = "Jin Ji Du Li", moveType = "Character", character = "Leo", game = "Tekken 8"),
        MoveEntry(moveName = "Fo Bu", notation = "Fo Bu", moveType = "Character", character = "Leo", game = "Tekken 8"),

        // Leroy
        MoveEntry(moveName = "Hermit", notation = "Hermit", moveType = "Character", character = "Leroy", game = "Tekken 8"),

        // Lidia
        MoveEntry(moveName = "Horse Stance", notation = "Horse Stance", moveType = "Character", character = "Lidia", game = "Tekken 8"),
        MoveEntry(moveName = "Cat Stance", notation = "Cat Stance", moveType = "Character", character = "Lidia", game = "Tekken 8"),
        MoveEntry(moveName = "Heaven and Earth", notation = "Heaven and Earth", moveType = "Character", character = "Lidia", game = "Tekken 8"),
        MoveEntry(moveName = "Stalking Wolf Stance", notation = "Stalking Wolf Stance", moveType = "Character", character = "Lidia", game = "Tekken 8"),

        // Lili
        MoveEntry(moveName = "Dew Glide", notation = "Dew Glide", moveType = "Character", character = "Lili", game = "Tekken 8"),

        // Raven
        MoveEntry(moveName = "Soulzone", notation = "Soulzone", moveType = "Character", character = "Raven", game = "Tekken 8"),

        // Reina
        MoveEntry(moveName = "Unsoku", notation = "Unsoku", moveType = "Character", character = "Reina", game = "Tekken 8"),
        MoveEntry(moveName = "Sentai", notation = "Sentai", moveType = "Character", character = "Reina", game = "Tekken 8"),
        MoveEntry(moveName = "Heaven's Wrath", notation = "Heaven's Wrath", moveType = "Character", character = "Reina", game = "Tekken 8"),
        MoveEntry(moveName = "WGS", notation = "WGS", moveType = "Mishima", character = "Reina", game = "Tekken 8"),
        MoveEntry(moveName = "WGF", notation = "WGF", moveType = "Mishima", character = "Reina", game = "Tekken 8"),
        MoveEntry(moveName = "EWGF", notation = "EWGF", moveType = "Mishima", character = "Reina", game = "Tekken 8"),
        MoveEntry(moveName = "WGK", notation = "WGK", moveType = "Mishima", character = "Reina", game = "Tekken 8"),
        MoveEntry(moveName = "EWGK", notation = "EWGK", moveType = "Mishima", character = "Reina", game = "Tekken 8"),

        // Shaheen
        MoveEntry(moveName = "Stealth Step", notation = "Stealth Step", moveType = "Character", character = "Shaheen", game = "Tekken 8"),

        // Steve
        MoveEntry(moveName = "Quick Spin", notation = "Quick Spin", moveType = "Character", character = "Steve", game = "Tekken 8"),
        MoveEntry(moveName = "Ducking", notation = "Ducking", moveType = "Character", character = "Steve", game = "Tekken 8"),
        MoveEntry(moveName = "Peekaboo", notation = "Peekaboo", moveType = "Character", character = "Steve", game = "Tekken 8"),
        MoveEntry(moveName = "Ducking In", notation = "Ducking In", moveType = "Character", character = "Steve", game = "Tekken 8"),
        MoveEntry(moveName = "Ducking Left", notation = "Ducking Left", moveType = "Character", character = "Steve", game = "Tekken 8"),
        MoveEntry(moveName = "Ducking Right", notation = "Ducking Right", moveType = "Character", character = "Steve", game = "Tekken 8"),
        MoveEntry(moveName = "Flicker Stance", notation = "Flicker Stance", moveType = "Character", character = "Steve", game = "Tekken 8"),
        MoveEntry(moveName = "Swaying", notation = "Swaying", moveType = "Character", character = "Steve", game = "Tekken 8"),
        MoveEntry(moveName = "Lion Heart", notation = "Lion Heart", moveType = "Character", character = "Steve", game = "Tekken 8"),

        // Victor
        MoveEntry(moveName = "Iai", notation = "Iai", moveType = "Character", character = "Victor", game = "Tekken 8"),
        MoveEntry(moveName = "Perfumer", notation = "Perfumer", moveType = "Character", character = "Victor", game = "Tekken 8"),

        // Xiaoyu
        MoveEntry(moveName = "Phoenix", notation = "Phoenix", moveType = "Character", character = "Xiaoyu", game = "Tekken 8"),
        MoveEntry(moveName = "Hypnotist", notation = "Hypnotist", moveType = "Character", character = "Xiaoyu", game = "Tekken 8"),

        // Yoshimitsu
        MoveEntry(moveName = "Kincho", notation = "Kincho", moveType = "Character", character = "Yoshimitsu", game = "Tekken 8"),
        MoveEntry(moveName = "Mutou No Kiwami", notation = "Mutou No Kiwami", moveType = "Character", character = "Yoshimitsu", game = "Tekken 8"),
        MoveEntry(moveName = "Manji Dragonfly", notation = "Manji Dragonfly", moveType = "Character", character = "Yoshimitsu", game = "Tekken 8"),
        MoveEntry(moveName = "Flea", notation = "Flea", moveType = "Character", character = "Yoshimitsu", game = "Tekken 8"),
        MoveEntry(moveName = "Indian Stance", notation = "Indian Stance", moveType = "Character", character = "Yoshimitsu", game = "Tekken 8"),

        // Zafina
        MoveEntry(moveName = "Tarantula", notation = "Tarantula", moveType = "Character", character = "Zafina", game = "Tekken 8"),
        MoveEntry(moveName = "Scarecrow", notation = "Scarecrow", moveType = "Character", character = "Zafina", game = "Tekken 8"),
        MoveEntry(moveName = "Mantis", notation = "Mantis", moveType = "Character", character = "Zafina", game = "Tekken 8"),)
)