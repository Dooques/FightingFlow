package com.example.fightingflow.ui.comboCreationScreen

import com.example.fightingflow.model.ComboDisplay
import com.example.fightingflow.model.Console
import com.example.fightingflow.model.Game
import com.example.fightingflow.model.MoveEntry
import com.example.fightingflow.model.SF6ControlType
import com.example.fightingflow.ui.comboDisplayScreen.inputConverter.convertInputToStandard
import com.example.fightingflow.util.ComboDisplayUiState
import com.example.fightingflow.util.ImmutableList
import com.example.fightingflow.util.characterAndMoveData.consoleInputs
import timber.log.Timber

fun processComboAsStringAbstract(moveList: ImmutableList<MoveEntry>): String {
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
    game: Game? = null,
    console: Console? = null,
    sf6ControlTypeState: SF6ControlType? = null,
    itemIndexState: Int,
    moveList: List<MoveEntry>
): ComboDisplay {
    var moveToAdd = moveList.first { it.moveName == moveName}
    Timber.d("MoveToAdd: $moveToAdd")
    if (moveToAdd.moveName in consoleInputs) {
        Timber.d("Converting console input to standard...")
        moveToAdd = convertInputToStandard(
            move = moveToAdd,
            game = game,
            console = console,
            classic = sf6ControlTypeState == SF6ControlType.Classic,
        )
    } else {
        moveList.first { it.moveName == moveName }
    }
    Timber.d("${moveToAdd.moveName} found.")
    val updatedList = comboDisplayState.comboDisplay.moves.toMutableList()
    Timber.d("Index: $itemIndexState")
    val index =
        if (itemIndexState == comboDisplayState.comboDisplay.moves.size)
            itemIndexState else itemIndexState + 1
    updatedList.add(index, moveToAdd)
    return comboDisplayState.comboDisplay.copy(moves = ImmutableList(list = updatedList))
}