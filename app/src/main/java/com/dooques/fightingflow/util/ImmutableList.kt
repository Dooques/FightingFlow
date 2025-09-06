package com.dooques.fightingflow.util

import androidx.compose.runtime.Immutable

@Immutable
data class ImmutableList<T>(
    private val list: List<T>
): List<T> by list
