package com.dooques.fightingflow.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.dooques.fightingflow.model.MoveEntry

class SearchFilterViewModel(): ViewModel() {
    fun getKeyByValue(map: Map<String, String>?, value: String): String {
        var key = ""
        map?.forEach { if (it.value.contains(value)) { key = it.key } }
        return key
    }
}

data class ComboFilterObject(
    var user: String = "",
    var description: String = "",
    var moves: MutableList<MoveEntry> = mutableListOf()
)
