package com.example.fightingflow.model

import androidx.lifecycle.ViewModel
import com.example.fightingflow.data.FlowUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FlowViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(FlowUiState(
        character = TODO()
    ))
    val uiState: StateFlow<FlowUiState> = _uiState.asStateFlow()
}