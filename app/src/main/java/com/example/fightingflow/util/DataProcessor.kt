package com.example.fightingflow.util

import com.example.fightingflow.model.CharacterEntry
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.util.characterAndMoveData.characterMap
import com.example.fightingflow.util.characterAndMoveData.commonMoves
import com.example.fightingflow.util.characterAndMoveData.moveMap
import timber.log.Timber

class DataProcessor {
    fun getMoveListForCharacter(character: CharacterEntry): MoveEntryListUiState {
        Timber.d("--Processing Move Data for ${character.name}")
        val moveList = mutableListOf<MoveEntry>()

        moveList.addAll(commonMoves)

        if (character != emptyCharacter) {
            Timber.d(" Character is valid")
            val movementInputs = if (character.numpadNotation) {
                moveMap["movement"]?.get("standard")?.get("numpad")
            } else {
                moveMap["movement"]?.get("standard")?.get("standard")
            }
            if (movementInputs != null) {
                Timber.d(" Movement: $movementInputs")
                moveList.addAll(movementInputs)
            }

            if (characterMap.keys.contains(character.game)) {
                val gameMoves = moveMap["inputs"]?.get("standard")?.get(character.controlType)
                if (!gameMoves.isNullOrEmpty()) {
                    Timber.d(" Inputs: $gameMoves")
                    moveList.addAll(gameMoves)
                }
            } else {
                val customGameMoves = moveMap["inputs"]?.get("custom")?.get(character.controlType)
                if (!customGameMoves.isNullOrEmpty()) {
                    Timber.d(" Inputs: $customGameMoves")
                    moveList.addAll(customGameMoves)
                }
            }

            val characterMoves = moveMap["character"]?.get("standard")?.get(character.controlType)
                ?.filter { move -> move.character == character.name }
            if (characterMoves != null) {
                Timber.d(" Character moves: $characterMoves")
                moveList.addAll(characterMoves)
            }
        }

        return MoveEntryListUiState(moveList)
    }
}