package com.example.fightingflow.util

import androidx.compose.runtime.Immutable
import com.example.fightingflow.R
import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.MoveEntry

@Immutable
class CharacterAndMoveData {
    val tekkenCharacterEntries: List<CharacterEntry> = listOf(
        CharacterEntry(
            id = 0,
            name = "Alisa",
            imageId = R.drawable.alisa_sprite,
            fightingStyle = "Thruster-Based High-Mobility Fighting Style",
            uniqueMoves = listOf(
                "Clockwork",
                "Destructive Form",
                "Boot",
                "Dual Boot",
                "Backup"
            ).toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Anna",
            imageId = R.drawable.anna_sprite,
            fightingStyle = "Aikido based techniques and Koppojutsu based Assassination Arts",
            uniqueMoves = listOf("Backhand Slap", "Hammer Chance", "Chaos Judgement").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Asuka",
            imageId = R.drawable.asuka_sprite,
            fightingStyle = "Kazama Style Traditional Martial Arts",
            uniqueMoves = listOf("Naniwa Gusto").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Azucena",
            imageId = R.drawable.azucena_sprite,
            fightingStyle = "Mixed Martial Arts (Striker)",
            uniqueMoves = listOf("Back Turned", "Libertador", "Nuevo Libertador").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Bryan",
            imageId = R.drawable.bryan_sprite,
            fightingStyle = "Kickboxing",
            uniqueMoves = listOf("Slither Step", "Snake Eyes", "Sway").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Claudio",
            imageId = R.drawable.claudio_sprite,
            fightingStyle = "Sirius Exorcist Arts",
            uniqueMoves = listOf("Starburst").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Clive",
            imageId = R.drawable.clive_sprite,
            fightingStyle = "Dominant",
            uniqueMoves = listOf("Stance1", "Stance2").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Devil Jin",
            imageId = R.drawable.jin_sprite,
            fightingStyle = "Unknown",
            uniqueMoves = listOf(
                "Mishima Crouch Dash (Wind God Step)",
                "Fly",
                "Mourning Crow"
            ).toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Dragunov",
            imageId = R.drawable.dragunov_sprite,
            fightingStyle = "White Reaper",
            uniqueMoves = listOf("Sneak").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Eddy",
            imageId = R.drawable.eddy_sprite,
            fightingStyle = "Capoeira",
            uniqueMoves = listOf("Ginga", "Bananeira", "Negativa", "Mandinga").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Feng",
            imageId = R.drawable.feng_sprite,
            fightingStyle = "Taijiquan",
            uniqueMoves = listOf(
                "Back Turned",
                "Lingering Shadow",
                "Shifting Clouds",
                "Deceptive Step"
            ).toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Heihachi",
            imageId = R.drawable.heihachi_sprite,
            fightingStyle = "Mishima Style Fighting Karate",
            uniqueMoves = listOf("Raijin Stance").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Hwoarang",
            imageId = R.drawable.hwoarang_sprite,
            fightingStyle = "Taekwondo",
            uniqueMoves = listOf(
                "Crouch Step",
                "Back Turned",
                "Right Stance",
                "Left Stance",
                "Right Flamingo",
                "Left Flamingo"
            ).toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Jack-8",
            imageId = R.drawable.jack8_sprite,
            fightingStyle = "High Tech Annihilator",
            uniqueMoves = listOf("Gamma Howl", "Gamma Charge", "Sit Down").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Jin",
            imageId = R.drawable.jin_sprite,
            fightingStyle = "Karate based on Kyokushin style infused with Devil Gene",
            uniqueMoves = listOf(
                "Awakened Power Stance",
                "Zenshin and Zanshin",
                "Breaking Step"
            ).toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Jun",
            imageId = R.drawable.jun_sprite,
            fightingStyle = "Kazama Style Traditional Martial Arts",
            uniqueMoves = listOf("Izumo", "Genjutsu", "Miare").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Kazuya",
            imageId = R.drawable.kazuya_sprite,
            fightingStyle = "Mishima Style Fighting Karate",
            uniqueMoves = listOf("Devil Form", "Wind God Step").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "King",
            imageId = R.drawable.king_sprite,
            fightingStyle = "Pro Wrestling",
            uniqueMoves = listOf(
                "Beast Step",
                "Back Turned",
                "Jaguar Step",
                "Jaguar Sprint"
            ).toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Kuma",
            imageId = R.drawable.kuma_sprite,
            fightingStyle = "Heihachi-style Improved Kuma Shinken",
            uniqueMoves = listOf("Hunting", "Bear Sit", "Bear Roll").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Lars", imageId = R.drawable.lars_sprite,
            fightingStyle = "Tekken Forces Martial Arts",
            uniqueMoves = listOf(
                "Avalanche Flip",
                "Dynamic Entry",
                "Silent Entry",
                "Limited Entry"
            ).toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Law",
            imageId = R.drawable.law_sprite,
            fightingStyle = "Martial Arts",
            uniqueMoves = listOf("Back Turned", "Dragon Charge").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Lee",
            imageId = R.drawable.lee_sprite,
            fightingStyle = "Martial Arts",
            uniqueMoves = listOf("Mist Step", "Hitman").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Leo",
            imageId = R.drawable.leo_sprite,
            fightingStyle = "Baji Quan",
            uniqueMoves = listOf("Lightning Glare", "Jin Bu", "Jin Ji Du Li", "Fo Bu").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Leroy",
            imageId = R.drawable.leroy_sprite,
            fightingStyle = "Wing Chun",
            uniqueMoves = listOf("Hermit").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Lidia",
            imageId = R.drawable.lidia_sprite,
            fightingStyle = "Traditional Karate",
            uniqueMoves = listOf(
                "Horse Stance",
                "Cat Stance",
                "Heaven and Earth",
                "Stalking Wolf Stance"
            ).toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Lili",
            imageId = R.drawable.lili_sprite,
            fightingStyle = "Self-Taught Style",
            uniqueMoves = listOf(
                "Feisty Rabbit",
                "Powered Up Feisty Rabbit",
                "Back Turned",
                "Dew Glide"
            ).toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Nina",
            imageId = R.drawable.nina_sprite,
            fightingStyle = "Assassination Arts",
            uniqueMoves = listOf("Ducking Step", "Sway").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Panda",
            imageId = R.drawable.panda_sprite,
            fightingStyle = "Heihachi-style Improved Kuma Shinken",
            uniqueMoves = listOf("Hunting", "Bear Sit", "Bear Roll").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Paul",
            imageId = R.drawable.paul_sprite,
            fightingStyle = "Integrated Martial Arts",
            uniqueMoves = listOf("Cormorant Step", "Deep Dive", "Sway").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Raven",
            imageId = R.drawable.raven_sprite,
            fightingStyle = "Ninjutsu",
            uniqueMoves = listOf("Soulzone", "Shadow Sprint", "Back Turned").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Reina",
            imageId = R.drawable.reina_sprite,
            fightingStyle = "Taido and Mishima-Style Karate",
            uniqueMoves = listOf("Senshin", "Unsoku", "Sentai", "Heaven's Wrath").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Shaheen",
            imageId = R.drawable.shaheen_sprite,
            fightingStyle = "Close Quarters Combat",
            uniqueMoves = listOf("Stealth Step").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Steve",
            imageId = R.drawable.steve_sprite,
            fightingStyle = "Boxing",
            uniqueMoves = listOf(
                "Peekaboo", "Flicker Stance", "Swaying", "Ducking", "Ducking Left", "Ducking Right",
                "Two Faced", "Quick Spin", "Cyclone", "Ducking In", "Lion Heart"
            ).toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Victor",
            imageId = R.drawable.victor_sprite,
            fightingStyle = "Super Spy CQB",
            uniqueMoves = listOf("Iai", "Perfumer").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Xiaoyu",
            imageId = R.drawable.xiaoyu_sprite,
            fightingStyle = "Chinese Martial Arts",
            uniqueMoves = listOf("Phoenix", "Hypnotist", "Rain Dance").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Yoshimitsu",
            imageId = R.drawable.yoshimitsu_sprite,
            fightingStyle = "Advanced Manji Ninjutsu",
            uniqueMoves = listOf(
                "Bad Stomach", "Mutou No Kiwami", "Death Copter", "Back Turned",
                "Kincho", "Meditation", "Flea", "Flea Step", "Indian Stance", "Healing",
                "Manji Dragonfly"
            ).toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
        CharacterEntry(
            id = 0,
            name = "Zafina",
            imageId = R.drawable.zafina_sprite,
            fightingStyle = "Ancient Assassination Arts",
            uniqueMoves = listOf("Tarantula", "Scarecrow", "Mantis").toString(),
            gameFranchise = "Tekken",
            gameEntry = "8"
        ),
    )

    val moveEntries =
        listOf(
            MoveEntry(
                0, "break", "►", "Break",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "forward",
                notation = "f",
                moveType = "Movement",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "forward_dash",
                notation = "F",
                moveType = "Movement",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "down_forward",
                notation = "d/f",
                moveType = "Movement",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "down",
                notation = "d",
                moveType = "Movement",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "down_back",
                notation = "d/b",
                moveType = "Movement",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "back",
                notation = "b",
                moveType = "Movement",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "up_back",
                notation = "u/b",
                moveType = "Movement",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "up",
                notation = "u",
                moveType = "Movement",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "up_forward",
                notation = "u/f",
                moveType = "Movement",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "neutral",
                notation = "n",
                moveType = "Movement",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "one",
                notation = "1",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "two",
                notation = "2",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "three",
                notation = "3",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "four",
                notation = "4",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "one_plus_two",
                notation = "1+2",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "three_plus_four",
                notation = "3+4",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "one_plus_three",
                notation = "1+3",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "two_plus_four",
                notation = "2+4",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "one_plus_four",
                notation = "1+4",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "two_plus_three",
                notation = "2+3",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "one_plus_two_plus_three",
                notation = "1+2+3",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "one_plus_two_plus_four",
                notation = "1+2+4",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "one_plus_three_plus_four",
                notation = "1+3+4",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "two_plus_three_plus_four",
                notation = "2+3+4",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "one_plus_two_plus_three_plus_four",
                notation = "1+2+3+4",
                moveType = "Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Hold",
                notation = "h",
                moveType = "Common",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Hold Max",
                notation = "H",
                moveType = "Common",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Delay",
                notation = "delay",
                moveType = "Common",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Just Frame",
                notation = "j/f",
                moveType = "Common",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Slide",
                notation = "(s)",
                moveType = "Common",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Side Switch",
                notation = "s/s",
                moveType = "Common",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Back Turned",
                notation = "b/t",
                moveType = "Common",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "While Standing",
                notation = "w/s",
                moveType = "Common",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Crouch",
                notation = "crouch",
                moveType = "Common",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Dash",
                notation = "dash",
                moveType = "Common",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Sidestep Left",
                notation = "s/l",
                moveType = "Common",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Sidestep Right",
                notation = "s/r",
                moveType = "Common",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Low Parry",
                notation = "l/p",
                moveType = "Common",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Heat Burst",
                notation = "h/burst",
                moveType = "Mechanics Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Heat Dash",
                notation = "h/dash",
                moveType = "Mechanic Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "During Heat",
                notation = "in heat",
                moveType = "Mechanics Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Rage Art",
                notation = "rage",
                moveType = "Mechanics Input",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Wall Splat",
                notation = "w/splat",
                moveType = "Stage",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Wall Break",
                notation = "w/break",
                moveType = "Stage",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Wall Blast",
                notation = "w/blast",
                moveType = "Stage",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Wall Bounce",
                notation = "w/bounce",
                moveType = "Stage",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Floor Break",
                notation = "f/break",
                moveType = "Stage",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Floor Blast",
                notation = "f/blast",
                moveType = "Stage",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Balcony Break",
                notation = "b/break",
                moveType = "Stage",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Generic"
            ),
            MoveEntry(
                id = 0,
                moveName = "Wind God Step",
                notation = "WGS",
                moveType = "Mishima",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Mishima"
            ),
            MoveEntry(
                id = 0,
                moveName = "Wind God Fist",
                notation = "WGF",
                moveType = "Mishima",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Mishima"
            ),
            MoveEntry(
                id = 0,
                moveName = "Thunder God Fist",
                notation = "TGF",
                moveType = "Mishima",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Mishima"
            ),
            MoveEntry(
                id = 0,
                moveName = "Electric Wind God Fist",
                notation = "EWGF",
                moveType = "Mishima",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Mishima"
            ),
            MoveEntry(
                id = 0,
                moveName = "Omen Thunder God Fist",
                notation = "OTGF",
                moveType = "Mishima",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Mishima"
            ),
            MoveEntry(
                id = 0,
                moveName = "Electric Thunder God Fist",
                notation = "ETGF",
                moveType = "Mishima",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Mishima"
            ),
            MoveEntry(
                id = 0,
                moveName = "Destructive Form",
                notation = "Destructive Form",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Alisa"
            ),
            MoveEntry(
                id = 0,
                moveName = "Boot",
                notation = "Boot",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Alisa"
            ),
            MoveEntry(
                id = 0,
                moveName = "Dual Boot",
                notation = "Dual Boot",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Alisa"
            ),
            MoveEntry(
                id = 0,
                moveName = "Naniwa Gusto",
                notation = "Naniwa Gusto",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Asuka"
            ),
            MoveEntry(
                id = 0,
                moveName = "Leg Cutter",
                notation = "Leg Cutter",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Asuka"
            ),
            MoveEntry(
                id = 0,
                moveName = "Libertador",
                notation = "Libertador",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Azucena"
            ),
            MoveEntry(
                id = 0,
                moveName = "Taking High Attack",
                notation = "Taking High Attack",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Azucena"
            ),
            MoveEntry(
                id = 0,
                moveName = "Taking Low Attack",
                notation = "Taking Low Attack",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Azucena"
            ),
            MoveEntry(
                id = 0,
                moveName = "Snake Eyes",
                notation = "Snake Eyes",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Bryan"
            ),
            MoveEntry(
                id = 0,
                moveName = "Slither Step",
                notation = "Slither Step",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Bryan"
            ),
            MoveEntry(
                id = 0,
                moveName = "Starburst",
                notation = "Starburst",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Claudio"
            ),
            MoveEntry(
                id = 0,
                moveName = "Phoenix Shift",
                notation = "Phoenix Shift",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Clive"
            ),
            MoveEntry(
                id = 0,
                moveName = "Wings Of Light",
                notation = "Wings Of Light",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Clive"
            ),
            MoveEntry(
                id = 0,
                moveName = "Updraft",
                notation = "Updraft",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Clive"
            ),
            MoveEntry(
                id = 0,
                moveName = "Zantetsuken",
                notation = "Zantetsuken",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Clive"
            ),
            MoveEntry(
                id = 0,
                moveName = "Mourning Crow",
                notation = "Mourning Crow",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Devil Jin"
            ),
            MoveEntry(
                id = 0,
                moveName = "Fly",
                notation = "Fly",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Devil Jin"
            ),
            MoveEntry(
                id = 0,
                moveName = "Sneak",
                notation = "Sneak",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Dragunov"
            ),
            MoveEntry(
                id = 0,
                moveName = "Ginga",
                notation = "Ginga",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Eddy"
            ),
            MoveEntry(
                id = 0,
                moveName = "Bananeira",
                notation = "Bananeira",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Eddy"
            ),
            MoveEntry(
                id = 0,
                moveName = "Negativa",
                notation = "Negativa",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Eddy"
            ),
            MoveEntry(
                id = 0,
                moveName = "Mandinga",
                notation = "Mandinga",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Eddy"
            ),
            MoveEntry(
                id = 0,
                moveName = "Deceptive Step",
                notation = "Deceptive Step",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Feng"
            ),
            MoveEntry(
                id = 0,
                moveName = "Shifting Clouds",
                notation = "Shifting Clouds",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Feng"
            ),
            MoveEntry(
                id = 0,
                moveName = "Wind God's Kamae",
                notation = "Wind God's Kamae",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Heihachi"
            ),
            MoveEntry(
                id = 0,
                moveName = "Thunder God's Kamae",
                notation = "Thunder God's Kamae",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Heihachi"
            ),
            MoveEntry(
                id = 0,
                moveName = "Warrior Instinct",
                notation = "Warrior Instinct",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Heihachi"
            ),
            MoveEntry(
                id = 0,
                moveName = "Right Stance",
                notation = "Right Stance",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Hwoarang"
            ),
            MoveEntry(
                id = 0,
                moveName = "Left Stance",
                notation = "Left Stance",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Hwoarang"
            ),
            MoveEntry(
                id = 0,
                moveName = "Right Flamingo",
                notation = "Right Flamingo",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Hwoarang"
            ),
            MoveEntry(
                id = 0,
                moveName = "Left Flamingo",
                notation = "Left Flamingo",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Hwoarang"
            ),
            MoveEntry(
                id = 0,
                moveName = "Sit Down",
                notation = "Sit Down",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Jack-8"
            ),
            MoveEntry(
                id = 0,
                moveName = "Gamma Howl",
                notation = "Gamma Howl",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Jack-8"
            ),
            MoveEntry(
                id = 0,
                moveName = "Zenshin",
                notation = "Zenshin",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Jin"
            ),
            MoveEntry(
                id = 0,
                moveName = "Izumo",
                notation = "Izumo",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Jun"
            ),
            MoveEntry(
                id = 0,
                moveName = "Genjutsu",
                notation = "Genjutsu",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Jun"
            ),
            MoveEntry(
                id = 0,
                moveName = "Miare",
                notation = "Miare",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Jun"
            ),
            MoveEntry(
                id = 0,
                moveName = "Hunting",
                notation = "Hunting",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Kuma & Panda"
            ),
            MoveEntry(
                id = 0,
                moveName = "Bear Sit",
                notation = "Bear Sit",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Kuma & Panda"
            ),
            MoveEntry(
                id = 0,
                moveName = "Bear Roll",
                notation = "Bear Roll",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Kuma & Panda"
            ),
            MoveEntry(
                id = 0,
                moveName = "Dynamic Entry",
                notation = "Dynamic Entry",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Lars"
            ),
            MoveEntry(
                id = 0,
                moveName = "Silent Entry",
                notation = "Silent Entry",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Lars"
            ),
            MoveEntry(
                id = 0,
                moveName = "Limited Entry",
                notation = "Limited Entry",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Lars"
            ),
            MoveEntry(
                id = 0,
                moveName = "Dragon Charge",
                notation = "Dragon Charge",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Law"
            ),
            MoveEntry(
                id = 0,
                moveName = "Hitman",
                notation = "Hitman",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Lee"
            ),
            MoveEntry(
                id = 0,
                moveName = "Jin Ji Du Li",
                notation = "Jin Ji Du Li",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Leo"
            ),
            MoveEntry(
                id = 0,
                moveName = "Fo Bu",
                notation = "Fo Bu",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Leo"
            ),
            MoveEntry(
                id = 0,
                moveName = "Hermit",
                notation = "Hermit",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Leroy"
            ),
            MoveEntry(
                id = 0,
                moveName = "Horse Stance",
                notation = "Horse Stance",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Lidia"
            ),
            MoveEntry(
                id = 0,
                moveName = "Cat Stance",
                notation = "Cat Stance",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Lidia"
            ),
            MoveEntry(
                id = 0,
                moveName = "Heaven and Earth",
                notation = "Heaven and Earth",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Lidia"
            ),
            MoveEntry(
                id = 0,
                moveName = "Stalking Wolf Stance",
                notation = "Stalking Wolf Stance",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Lidia"
            ),
            MoveEntry(
                id = 0,
                moveName = "Dew Glide",
                notation = "Dew Glide",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Lili"
            ),
            MoveEntry(
                id = 0,
                moveName = "Soulzone",
                notation = "Soulzone",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Raven"
            ),
            MoveEntry(
                id = 0,
                moveName = "Unsoku",
                notation = "Unsoku",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Reina"
            ),
            MoveEntry(
                id = 0,
                moveName = "Sentai",
                notation = "Sentai",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Reina"
            ),
            MoveEntry(
                id = 0,
                moveName = "Heaven's Wrath",
                notation = "Heaven's Wrath",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Reina"
            ),
            MoveEntry(
                id = 0,
                moveName = "Stealth Step",
                notation = "Stealth Step",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Shaheen"
            ),
            MoveEntry(
                id = 0,
                moveName = "Quick Spin",
                notation = "Quick Spin",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Steve"
            ),
            MoveEntry(
                id = 0,
                moveName = "Ducking",
                notation = "Ducking",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Steve"
            ),
            MoveEntry(
                id = 0,
                moveName = "Peekaboo",
                notation = "Peekaboo",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Steve"
            ),
            MoveEntry(
                id = 0,
                moveName = "Ducking In",
                notation = "Ducking In",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Steve"
            ),
            MoveEntry(
                0,
                "Ducking Left",
                "Ducking Left",
                "Character",
                false,
                false,
                false,
                "Steve"
            ),
            MoveEntry(
                0,
                "Ducking Right",
                "Ducking Right",
                "Character",
                false,
                false,
                false,
                "Steve"
            ),
            MoveEntry(
                0,
                "Flicker Stance",
                "Flicker Stance",
                "Character",
                false,
                false,
                false,
                "Steve"
            ),
            MoveEntry(
                id = 0,
                moveName = "Swaying",
                notation = "Swaying",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Steve"
            ),
            MoveEntry(
                id = 0,
                moveName = "Lion Heart",
                notation = "Lion Heart",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Steve"
            ),
            MoveEntry(
                id = 0,
                moveName = "Iai",
                notation = "Iai",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Victor"
            ),
            MoveEntry(
                id = 0,
                moveName = "Perfumer",
                notation = "Perfumer",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Victor"
            ),
            MoveEntry(
                id = 0,
                moveName = "Phoenix",
                notation = "Phoenix",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Xiaoyu"
            ),
            MoveEntry(
                id = 0,
                moveName = "Hypnotist",
                notation = "Hypnotist",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Xiaoyu"
            ),
            MoveEntry(
                id = 0,
                moveName = "Kincho",
                notation = "Kincho",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Yoshimitsu"
            ),
            MoveEntry(
                0,
                "Mutou No Kiwami",
                "Mutou No Kiwami",
                "Character",
                false,
                false,
                false,
                "Yoshimitsu"
            ),
            MoveEntry(
                0,
                "Manji Dragonfly",
                "Manji Dragonfly",
                "Character",
                false,
                false,
                false,
                "Yoshimitsu"
            ),
            MoveEntry(
                id = 0,
                moveName = "Flea",
                notation = "Flea",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Yoshimitsu"
            ),
            MoveEntry(
                0,
                "Indian Stance",
                "Indian Stance",
                "Character",
                false,
                false,
                false,
                "Yoshimitsu"
            ),
            MoveEntry(
                id = 0,
                moveName = "Tarantula",
                notation = "Tarantula",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Zafina"
            ),
            MoveEntry(
                id = 0,
                moveName = "Scarecrow",
                notation = "Scarecrow",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Zafina"
            ),
            MoveEntry(
                id = 0,
                moveName = "Mantis",
                notation = "Mantis",
                moveType = "Character",
                counterHit = false,
                hold = false,
                justFrame = false,
                associatedCharacter = "Zafina"
            ),
        )

//    val consoleButtons = listOf(
//        MoveEntry(0, "×", "Console", "Playstation"),
//        MoveEntry(0, "○", "Console", "Playstation"),
//        MoveEntry(0, "△", "Console", "Playstation"),
//        MoveEntry(0, "□", "Console", "Playstation"),
//        MoveEntry(0, "R1", "Console", "Playstation"),
//        MoveEntry(0, "R2", "Console", "Playstation"),
//        MoveEntry(0, "R3", "Console", "Playstation"),
//        MoveEntry(0, "L1", "Console", "Playstation"),
//        MoveEntry(0, "L2", "Console", "Playstation"),
//        MoveEntry(0, "L3", "Console", "Playstation"),
//        MoveEntry(0, "A", "Console", "Xbox"),
//        MoveEntry(0, "B", "Console", "Xbox"),
//        MoveEntry(0, "X", "Console", "Xbox"),
//        MoveEntry(0, "Y", "Console", "Xbox"),
//        MoveEntry(0, "RB", "Console", "Xbox"),
//        MoveEntry(0, "RT", "Console", "Xbox"),
//        MoveEntry(0, "RS", "Console", "Xbox"),
//        MoveEntry(0, "LB", "Console", "Xbox"),
//        MoveEntry(0, "LS", "Console", "Xbox"),
//        MoveEntry(0, "LS", "Console", "Xbox")
//    )
}

