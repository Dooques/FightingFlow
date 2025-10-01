package com.dooques.fightingflow.data.firebase

import com.dooques.fightingflow.model.ComboEntryFb
import com.dooques.fightingflow.model.UserEntry
import com.dooques.fightingflow.model.toHashMap
import com.dooques.fightingflow.ui.viewmodels.ComboResult
import com.dooques.fightingflow.util.emptyUser
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

class FirebaseComboRepository() {
    val firestore = Firebase.firestore
    val characterCollection: CollectionReference = firestore.collection("characters")

    /* Combo Collection */
    suspend fun addCombo(combo: ComboEntryFb): String {
        return withContext(Dispatchers.IO) {
            val comboHash = combo.toHashMap()

            val reference = characterCollection
                .document(combo.character)
                .collection("combos")
                .add(comboHash)
                .addOnSuccessListener { Timber.d(" Combo added successfully with id: ${it.id}") }
                .addOnFailureListener { Timber.e(it, " Error adding combo: ") }
                .await()

            reference.id
        }
    }

    fun updateCombo(combo: ComboEntryFb) {
        val comboHash = combo.toHashMap()

        characterCollection.document(combo.character)
            .collection("combos").document(combo.comboId)
            .set(comboHash)
            .addOnSuccessListener { documentReference ->
                Timber.d(" Combo added successfully")
            }
            .addOnFailureListener { e ->
                Timber.e(e, " Error adding combo: ")
            }
    }

    fun getComboList(
        character: String?,
        publicComboDisplayState: Boolean,
        user: String
    ): Flow<List<ComboEntryFb>> = callbackFlow {
        Timber.d("--Initiating combo list flow from Firebase--")
        if (character?.isBlank() == true || character?.contains("/") == true) {
            Timber.e(" Invalid character name provided for Firestore query: $character")
            close(IllegalArgumentException("Invalid character name: $character"))
            return@callbackFlow
        }

        if (character != null) {
            val listenerRegistration: ListenerRegistration =
                characterCollection.document(character)
                    .collection("combos")
                    .addSnapshotListener { querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
                        /* Error Result */
                        if (e != null) {
                            Timber.e(e, " Error listening to combo collection")
                            close(e)
                            return@addSnapshotListener
                        }

                        /* Query Snapshot null */
                        if (querySnapshot == null) {
                            Timber.w(" Query Snapshot was null")
                            return@addSnapshotListener
                        }

                        /* Snapshot success */
                        Timber.d("Sorting combos from the firestore database by public/user")
                        val combos = querySnapshot.documents.mapNotNull { document ->
                            try {
                                Timber.d("Combo: ${document.data}")
                                if (publicComboDisplayState) {
                                    Timber.d("Showing public combos.")
                                    document.toObject(ComboEntryFb::class.java)?.copy(
                                        comboId = document.id,
                                        createdBy = document.data?.get("created_by").toString(),
                                        dateCreated = document.data?.get("date_created").toString(),
                                    )
                                } else {
                                    Timber.d("Showing user combos.")
                                    Timber.d("User: %s", user)
                                    if (document.data?.get("created_by").toString() == user) {
                                        Timber.d("Combo creator matches current user.")
                                        document.toObject(ComboEntryFb::class.java)?.copy(
                                            comboId = document.id,
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
    }

    fun getCombo(character: String, comboId: String): Flow<ComboEntryFb?> = callbackFlow {
        val documentReference =
            characterCollection.document(character).collection("combos").document(comboId)

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
                        documentSnapshot.toObject(ComboEntryFb::class.java)
                            ?.copy(comboId = documentSnapshot.id)
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

    fun deleteCombo(character: String, comboId: String): ComboResult {
        Timber.d("Attempting to delete combo document %s from %s's collection", comboId, character)
        val documentReference =
            characterCollection.document(character).collection("combos").document(comboId)

        return try {
            documentReference.delete()
            ComboResult.Success
        } catch (e: Exception) {
            Timber.e(e, "Error deleting combo document %s.", comboId)
            ComboResult.Error(e)
        }
    }
}

