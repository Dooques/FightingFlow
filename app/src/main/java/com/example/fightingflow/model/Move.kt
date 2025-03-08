package com.example.fightingflow.model

import androidx.annotation.StringRes

data class Move(
    val moveImage: Int,
    @StringRes val moveName: Int,
)