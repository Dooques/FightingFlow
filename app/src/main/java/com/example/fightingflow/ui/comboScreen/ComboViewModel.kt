package com.example.fightingflow.ui.comboScreen

import androidx.lifecycle.ViewModel
import com.example.fightingflow.data.DataSource
import com.example.fightingflow.data.database.TekkenDataRepository

class ComboViewModel(
    tekkenDataRepository: TekkenDataRepository
): ViewModel() {
    val reinaCombo = DataSource.combo
    val data = DataSource


}