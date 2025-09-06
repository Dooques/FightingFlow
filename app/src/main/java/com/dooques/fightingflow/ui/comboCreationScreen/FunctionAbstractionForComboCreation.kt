package com.dooques.fightingflow.ui.comboCreationScreen

import com.dooques.fightingflow.model.ComboDisplay
import com.dooques.fightingflow.model.Console
import com.dooques.fightingflow.model.Game
import com.dooques.fightingflow.model.MoveEntry
import com.dooques.fightingflow.model.SF6ControlType
import com.dooques.fightingflow.util.inputConverter.convertInputToStandard
import com.dooques.fightingflow.util.ComboDisplayUiState
import com.dooques.fightingflow.util.ImmutableList
import com.dooques.fightingflow.util.characterAndMoveData.consoleInputs
import com.dooques.fightingflow.util.characterAndMoveData.nintendoInputs
import com.dooques.fightingflow.util.characterAndMoveData.playstationInputs
import com.dooques.fightingflow.util.characterAndMoveData.xboxInputs
import timber.log.Timber

fun processComboAsStringAbstract(moveList: List<MoveEntry>): String {
    val comboIterator = moveList.iterator()
    var moveAsString = ""
    while (comboIterator.hasNext()) {
        val move = comboIterator.next().notation
        if (move == "Break") {
            moveAsString += " $move "
        }
        moveAsString += move
        if (comboIterator.hasNext()) {
            moveAsString += ", "
        }
    }
    Timber.d("Processed combo: $moveAsString")
    return moveAsString
}

fun updateMoveListAbstract(
    moveName: String,
    comboDisplayState: ComboDisplayUiState,
    game: String,
    console: Console? = null,
    sf6ControlTypeState: SF6ControlType? = null,
    itemIndexState: Int,
    moveList: List<MoveEntry>
): ComboDisplay {
    Timber.d("-- Adding move to Combo --\n MoveToAdd: %s\n Game: %s\n Console: %s",
        moveName, game, console)

    var moveToAdd =
        if (console != null) {
            when (console) {
                Console.PLAYSTATION -> playstationInputs.first { it.moveName == moveName}

                Console.XBOX -> xboxInputs.first { it.moveName == moveName}

                Console.NINTENDO -> nintendoInputs.first { it.moveName == moveName}

                Console.STANDARD -> moveList.first { it.moveName == moveName}
            }
        }
        else moveList.first { it.moveName == moveName }
    Timber.d(" Move: $moveToAdd")

    val gameSelected =
        if (game.contains("Tekken")) {
            Game.T8
        } else if (game.contains("Mortal Kombat")) {
            Game.MK1
        } else if (game.contains("Street Fighter")) {
            Game.SF6
        } else {
            Game.CUSTOM
        }

    if (moveToAdd.moveName in consoleInputs) {
        Timber.d(" Converting console input to standard...")
        moveToAdd = convertInputToStandard(
            move = moveToAdd,
            game = gameSelected,
            console = console,
            classic = sf6ControlTypeState == SF6ControlType.Classic,
        )
    } else if (
        moveToAdd.moveType == "Break" ||
        moveToAdd.moveType == "Misc" ||
        moveToAdd.moveType == "Movement"
    ) {
        Timber.d("Adding Break to Combo")
        moveToAdd = moveList.first { it.moveName == moveName }
    } else {
        Timber.d("Adding $moveName to Combo")
        moveToAdd =
            if (moveToAdd.moveType == "Unique Move") {
                moveList.first {
                    it.moveName == moveName
                }
            } else {
                moveList.first {
                    it.moveName == moveName
                }
            }
    }

    Timber.d("$moveToAdd found.")
    val updatedList = comboDisplayState.comboDisplay.moves.toMutableList()

    Timber.d("Index: $itemIndexState")
    val index = if (itemIndexState == comboDisplayState.comboDisplay.moves.size)
        itemIndexState else itemIndexState + 1
    updatedList.add(index, moveToAdd)
    return comboDisplayState.comboDisplay.copy(moves = ImmutableList(list = updatedList))
}