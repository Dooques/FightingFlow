package com.dooques.fightingflow.data.firebase

import com.dooques.fightingflow.model.ComboEntryFb
import com.dooques.fightingflow.model.UserDataForCombos
import com.dooques.fightingflow.model.UserEntry
import com.dooques.fightingflow.model.toHashMap
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.collections.forEach

class FirebaseRepository() {
    val firestore = Firebase.firestore
    val characterCollection: CollectionReference = firestore.collection("characters")
    val userCollection: CollectionReference = firestore.collection("users")

    /* Combo Collection */
    suspend fun addCombo(combo: ComboEntryFb): String {
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

    fun updateCombo(combo: ComboEntryFb) {
        val comboHash = combo.toHashMap()

        characterCollection.document(combo.character)
            .collection("combos").document(combo.comboId)
            .set(comboHash)
            .addOnSuccessListener { documentReference ->
                Timber.d("Combo added successfully")
            }
            .addOnFailureListener { e ->
                Timber.e(e, "Error adding combo: ")
            }
    }

    fun getComboList(
        character: String?,
        publicComboDisplayState: Boolean,
        user: String
    ): Flow<List<ComboEntryFb>> = callbackFlow {
        Timber.d("--Initiating combo list flow from Firebase--")
        if (character?.isBlank() == true || character?.contains("/") == true) {
            Timber.e("Invalid character name provided for Firestore query: $character")
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

    fun deleteCombo(character: String, comboId: String) {
        Timber.d("Attempting to delete combo document %s from %s's collection", comboId, character)
        val documentReference =
            characterCollection.document(character).collection("combos").document(comboId)

        try {
            documentReference.delete()
                .addOnSuccessListener {
                    Timber.d(
                        "Combo document %s successfully deleted",
                        comboId
                    )
                }
                .addOnFailureListener { e ->
                    Timber.e(
                        e,
                        "Error deleting combo document %s",
                        comboId
                    )
                }
        } catch (e: Exception) {
            Timber.e(e, "Error deleting combo document %s.", comboId)
        }
    }

    /* User Collection */
    suspend fun addUserToStore(user: UserEntry): UserFbResult {
        Timber.d("--Adding new user entry to firestore--")
        val userHash = user.toHashMap()

        try {
            Timber.d("Initiating the transaction")
            val newDocument = firestore.runTransaction { transaction ->
                val query = userCollection.document(user.userId)
                val snapshot = transaction.get(query)

                if (snapshot.exists()) {
                    // User exists
                    val existingDocument = snapshot.get("username")
                    Timber.w("$existingDocument already exists.")
                    UserFbResult.Error(Exception("User Exists"))
                } else {
                    Timber.d("User Details: $userHash")
                    transaction.set(query, userHash)
                    Timber.d("Transaction: Adding ${user.username} as a new user to db.")
                    UserFbResult.Success
                }
            }.await()
            return newDocument
        } catch (e: Exception) {
            Timber.e(e, "Error processing transaction to add ${user.username} to db.")
            return UserFbResult.Error(error = e)
        }
    }

    fun getUserDetailsById(userId: String): Flow<UserEntry?> {
        if (userId.isBlank()) {
            Timber.d("User ID is blank")
            return flowOf(emptyUser)
        }
        try {
            return callbackFlow {
                Timber.d("--Repo: Getting user details from the firestore.--")
                Timber.d("UserID: $userId")

                val userDocumentReference = userCollection.document(userId)
                Timber.d("UserDocumentRef: $userDocumentReference")

                val listenerRegistration = userDocumentReference
                    .addSnapshotListener { documentSnapshot: DocumentSnapshot?, e: FirebaseFirestoreException? ->
                        if (e != null) {
                            Timber.e(e, "Error listening for combo document.")
                            close(e)
                            return@addSnapshotListener
                        }

                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            val user =
                                try {
                                    Timber.d("Document: %s", documentSnapshot)
                                    documentSnapshot.toObject(UserEntry::class.java)
                                } catch (e: Exception) {
                                    Timber.e(e, "Error converting user to User: $userId")
                                    null
                                }
                            trySend(user)
                        } else {
                            Timber.d("User $userId does not exist or is null: $documentSnapshot")
                            trySend(null)
                        }
                    }
                awaitClose {
                    Timber.d("Closing firestore listener for User document: $userId")
                    listenerRegistration.remove()
                }

            }
        } catch (e: Exception) {
            Timber.e(e, "An Error occurred during listenerRegistration")
            return flowOf(emptyUser)
        }
    }

    fun getUserMap(): Flow<UserDataForCombos> = callbackFlow {
        Timber.d("--Starting callback flow for User Data collection--")
        val userMap = mutableMapOf<String, String>()
        val listenerRegistration = userCollection
            .addSnapshotListener { querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
                Timber.d("User Snapshot: ${querySnapshot?.query.toString()}")
                if (e != null) {
                    Timber.e(e, "Error listening for user document.")
                    close(e)
                }
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    try {
                        querySnapshot.forEach { user ->
                            userMap[user.id] = user.data["username"] as String
                        }
                    } catch (e: Exception) {
                        Timber.e(e, "Error getting data from user collection")
                        close(e)
                    }
                    Timber.d("UserMap: $userMap")
                    trySend(UserDataForCombos(userMap))
                }
            }
        awaitClose {
            Timber.d("Closing firestore listener for User Collection")
            listenerRegistration.remove()
        }
    }

    fun updateUser(user: UserEntry) {
        val userHash = user.toHashMap()
        Timber.d("User Details: $userHash")


        try {
            Timber.d("Attempting to update firestore database user")
            userCollection.document(user.userId)
                .update(userHash)
                .addOnSuccessListener { Timber.d("${user.username} updated successfully") }
                .addOnFailureListener { e -> Timber.e(e, "Error updating user: $user") }
        } catch (e: Exception) {
            Timber.e(e, "Error updating ${user.username}'s details")
        }
    }

    fun deleteUser(userId: String): UserFbResult {
        Timber.d("--Deleting user profile from firestore--")
        val documentReference = userCollection.document(userId)

        try {
            documentReference.delete()
            Timber.d("Successfully deleted $userId from firestore.")
            return UserFbResult.Success
        } catch (e: Exception) {
            Timber.e(e, "Error deleting user profile from datastore")
            return UserFbResult.Error(e)
        }
    }
}



sealed class UserFbResult {
    data object Success: UserFbResult()
    data class Error(val error: Exception): UserFbResult()
}