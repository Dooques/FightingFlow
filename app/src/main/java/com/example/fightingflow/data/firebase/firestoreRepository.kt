package com.example.fightingflow.data.firebase

import com.example.fightingflow.model.ComboEntry
import com.example.fightingflow.model.ProfileEntry
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

class FirebaseRepository() {
    val firestore = Firebase.firestore
    val characterCollection: CollectionReference = firestore.collection("characters")
    val userCollection: CollectionReference = firestore.collection("users")

    /* Combo Collection */
    suspend fun addCombo(combo: ComboEntry): String {

        return withContext(Dispatchers.IO) {
            val comboHash = combo.toHashMap()

            val reference = characterCollection
                .document(combo.character)
                .collection("combos")
                .add(comboHash)
                .addOnSuccessListener { documentReference ->
                    Timber.d("Combo added successfully with id: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Timber.e(e, "Error adding combo: ")
                }.await()

            reference.id
        }
    }

    fun updateCombo(combo: ComboEntry) {
        val comboHash = combo.toHashMap()

        characterCollection.document(combo.character)
            .collection("combos").document(combo.id)
            .set(comboHash)
            .addOnSuccessListener { documentReference ->
                Timber.d("Combo added successfully")
            }
            .addOnFailureListener { e ->
                Timber.e(e, "Error adding combo: ")
            }
    }


    fun getComboList(character: String, publicComboDisplayState: Boolean, user: String): Flow<List<ComboEntry>> = callbackFlow {
        if (character.isBlank() || character.contains("/")) {
            Timber.e("Invalid character name provided for Firestore query: $character")
            close(IllegalArgumentException("Invalid character name: $character"))
            return@callbackFlow
        }
        val listenerRegistration: ListenerRegistration =
            characterCollection.document(character)
                .collection("combos")
                .addSnapshotListener { querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
                    /* Error Result */
                    if (e != null) {
                        Timber.e(e, "Error listening to combo collection")
                        close(e)
                        return@addSnapshotListener
                    }

                    /* Query Snapshot null */
                    if (querySnapshot == null) {
                        Timber.w("Query Snapshot was null")
                        return@addSnapshotListener
                    }

                    /* Snapshot success */
                    Timber.d("Sorting combos from the firestore database")
                    val combos = querySnapshot.documents.mapNotNull { document ->
                        try {
                            Timber.d("Combo: ${document.data}")
                            if (publicComboDisplayState) {
                                Timber.d("Showing public combos.")
                                document.toObject(ComboEntry::class.java)?.copy(
                                    id = document.id,
                                    createdBy = document.data?.get("created_by").toString(),
                                    dateCreated = document.data?.get("date_created").toString(),
                                )
                            } else {
                                Timber.d("Showing user combos.")
                                Timber.d("User: %s", user)
                                if (document.data?.get("created_by").toString() == user) {
                                    Timber.d("Combo creator matches current user.")
                                    document.toObject(ComboEntry::class.java)?.copy(
                                        id = document.id,
                                        createdBy = document.data?.get("created_by").toString(),
                                        dateCreated = document.data?.get("date_created").toString(),
                                    )
                                } else {
                                    Timber.d("Combo does not match user, returning null.")
                                    null
                                }
                            }
                        } catch (e: Exception) {
                            Timber.e(e, "Error converting document to Combo: ${document.id}")
                            null
                        }
                    }
                    Timber.d("Firestore combos updated: ${combos.size} items")
                    trySend(combos).isSuccess
                }
        awaitClose {
            Timber.d("Closing Firestore listener for combos")
            listenerRegistration.remove()
        }
    }

    fun getCombo(character: String, comboId: String): Flow<ComboEntry?> = callbackFlow {
        val documentReference = characterCollection.document(character).collection("combos").document(comboId)

        val listenerRegistration = documentReference
            .addSnapshotListener { documentSnapshot: DocumentSnapshot?, e: FirebaseFirestoreException? ->
                /* Error listening for document snapshot */
                if (e != null) {
                    Timber.e(e, "Error listening to combo document: $comboId")
                    close(e)
                    return@addSnapshotListener
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val combo = try {
                        documentSnapshot.toObject(ComboEntry::class.java)?.copy(id = documentSnapshot.id)
                    } catch (e: Exception) {
                        Timber.e(e, "Error converting document to Combo: ${documentSnapshot.id}")
                        null
                    }
                    trySend(combo)
                } else {
                    Timber.d("Combo document $comboId does not exist or is null")
                    trySend(null)
                }
            }
        awaitClose {
            Timber.d("Closing Firestore listener for combo document: $comboId")
            listenerRegistration.remove()
        }
    }

    fun deleteCombo(character: String, comboId: String) {
        Timber.d("Attempting to delete combo document %s from %s's collection", comboId, character)
        val documentReference = characterCollection.document(character).collection("combos").document(comboId)

        try {
            documentReference.delete()
                .addOnSuccessListener { Timber.d("Combo document %s successfully deleted", comboId) }
                .addOnFailureListener { e -> Timber.e(e, "Error deleting combo document %s", comboId) }
        } catch (e: Exception) {
            Timber.e(e, "Error deleting combo document %s.", comboId)
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