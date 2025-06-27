package com.example.fightingflow.data.firebase

import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.ProfileEntry
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import timber.log.Timber

class FirebaseRepository() {
    val firestore = Firebase.firestore
    val characterCollection: CollectionReference = firestore.collection("characters")
    val comboCollection: CollectionReference = firestore.collection("combos")
    val userCollection: CollectionReference = firestore.collection("users")

    /* Combo Collection */
    fun addComboToStore(combo: ComboEntry) {
        val comboHash = combo.toHashMap()

        characterCollection.document(combo.character)
            .collection("combos").document()

            .set(comboHash)
            .addOnSuccessListener { documentReference ->
                Timber.d("Combo added successfully")
            }
            .addOnFailureListener { e ->
                Timber.e(e, "Error adding combo: ")
            }
    }

    fun getCombosFromStore(character: String) {
       characterCollection.document(character)
           .collection("combos")
            .get()
            .addOnSuccessListener { result ->
                result.forEach { document ->
                    Timber.d("${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { e ->
                Timber.e(e, "Error getting documents: ")
            }
    }

    /* User Collection */

    fun addUserToStore(profile: ProfileEntry) {
        val userHash = profile.toHashMap()

        userCollection.document(profile.username)
            .set(userHash)
            .addOnSuccessListener { Timber.d("User added successfully") }
            .addOnFailureListener { e -> Timber.e(e, "Error adding user: ") }
    }

    fun getUsersFromStore() {
       userCollection
            .get()
            .addOnSuccessListener { result ->
                result.forEach { document ->
                    Timber.d("${document.id} => ${document.data}")
                } }
            .addOnFailureListener { e -> Timber.e(e, "Error getting documents: ") }
    }
}

fun ProfileEntry.toHashMap(): HashMap<String, Any> =
    hashMapOf(
        "username" to username,
        "profile_pic" to profilePic,
        "password" to password,
    )

fun ComboEntry.toHashMap(): HashMap<String, Any> =
    hashMapOf(
        "title" to title,
        "character" to character,
        "damage" to damage,
        "created_by" to createdBy,
        "date_created" to dateCreated,
        "difficulty" to difficulty,
        "likes" to likes,
        "tags" to tags.let { "" },
        "moves" to moves
    )