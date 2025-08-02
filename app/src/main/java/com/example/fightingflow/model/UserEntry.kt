package com.example.fightingflow.model

import com.google.firebase.firestore.PropertyName

data class UserEntry(
    var userId: String = "",
    val username: String = "",
    val email: String = "",
    @get:PropertyName("profile_pic")
    @set:PropertyName("profile_pic")
    var profilePic: String = "",
    @get:PropertyName("date_created")
    @set:PropertyName("date_created")
    var dateCreated: String = "",
    var dob: String = "",
    var name: String = "",
    @get: PropertyName("liked_combos")
    @set: PropertyName("liked_combos")
    var likedCombos: List<String> = emptyList()
)

data class UserDataForCombos(
    val userMap: Map<String, String>?
)

fun UserEntry.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        "userId" to userId,
        "username" to username,
        "name" to name,
        "email" to email,
        "profile_pic" to profilePic,
        "date_created" to dateCreated,
        "dob" to dob,
        "liked_combos" to likedCombos
    )
