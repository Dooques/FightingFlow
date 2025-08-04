package com.example.fightingflow.ui.comboItem

import androidx.lifecycle.ViewModel
import timber.log.Timber

class ComboItemViewModel(

): ViewModel() {
    fun processComboStringForDisplay(combo: String): String {
        Timber.d("Processing combo as display list...")

        val comboAsList = combo.split(", ")
        Timber.d("List: $comboAsList")
        var newString = ""

        comboAsList.forEachIndexed { index, it ->
            val move = it.replace(",", "")
            Timber.d("String: $newString")
            Timber.d("Move: $move, Index: $index")
            if (comboAsList.size > 1 && index < comboAsList.size - 1) {
                Timber.d("List size ${comboAsList.size} greater than 1, checking for symbols")
                newString = when (move) {
                    "{", "+", "[", "/", "►" -> {
                        Timber.d("Adding $move, no comma")
                        "$newString$move "
                    }

                    else -> when (comboAsList[index + 1]) {
                        "{", "}", "+", "[", "]", "/", "►" -> {
                            Timber.d("Adding $move, no comma")
                            "$newString$move "
                        }

                        else -> {
                            Timber.d("Adding $move, with comma")
                            "$newString$move, "
                        }
                    }
                }
            }
            if (index == comboAsList.size - 1) {
                Timber.d("Adding $move, end of List")
                newString = "$newString$move"
            }
        }
        Timber.d("Returning string.")
        return newString.ifEmpty { combo }
    }
}