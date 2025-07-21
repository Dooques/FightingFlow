package com.example.fightingflow.model

import com.google.firebase.firestore.PropertyName

data class UserEntry(
    val userId: String? = "",
    val username: String? = "",
    val email: String? = "",
    @get:PropertyName("profile_pic")
    @set:PropertyName("profile_pic")
    var profilePic: String = "",
    @get:PropertyName("date_created")
    @set:PropertyName("date_created")
    var dob: String = "",
    var dateCreated: String = "",
)