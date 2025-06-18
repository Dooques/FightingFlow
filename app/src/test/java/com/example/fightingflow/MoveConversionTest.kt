package com.example.fightingflow

import com.example.fightingflow.model.Console
import com.example.fightingflow.model.Game
import com.example.fightingflow.ui.comboDisplayScreen.inputConverter.convertInputsToConsole
import com.example.fightingflow.ui.comboDisplayScreen.inputConverter.convertInputToStandard
import com.example.fightingflow.util.characterAndMoveData.playstationInputs
import com.example.fightingflow.util.characterAndMoveData.tekken8CharacterMoves
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class MoveConversionTest {
    private val consoleInputs = playstationInputs
    private val gameInputs = tekken8CharacterMoves

    @Test
    @Throws(IOException::class)
    fun moveConversionTest_GameInputToConsole() {
        val gameInput = gameInputs.first { move -> move.moveName == "one"}
        val consoleInput = consoleInputs.first { move -> move.moveName == "square"}
        println("Game Input: $gameInput")
        println("Console Input: $consoleInput")

        val result = convertInputsToConsole(gameInput, Game.T8, Console.PLAYSTATION)
        println("Result: $result\n")

        assertEquals(consoleInput, result)
    }

    @Test
    @Throws(IOException::class)
    fun moveConversionTest_ConsoleInputToGame() {
        val gameInput = gameInputs.first { move -> move.moveName == "one"}
        val consoleInput = consoleInputs.first { move -> move.moveName == "square"}
        println("Game Input: $gameInput")
        println("Console Input: $consoleInput")

        val result = convertInputToStandard(consoleInput, Game.T8, Console.PLAYSTATION)
        println("Result: $result\n")

        assertEquals(gameInput, result)
    }
}