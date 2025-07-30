package com.example.fightingflow.model

import kotlinx.serialization.Serializable

@Serializable
class ProfanityData(
    val notes: String,
    val severity: Int,
    val tags: List<String>,
    val dictionary: List<ProfanityWord>
)

@Serializable
class ProfanityWord(
    val id: String,
    val match: String,
    val allowPartial: Boolean? = null,
    val exceptions: List<String>? = null
)