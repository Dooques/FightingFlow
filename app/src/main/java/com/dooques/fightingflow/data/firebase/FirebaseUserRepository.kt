package com.dooques.fightingflow.data.firebase

import com.dooques.fightingflow.model.UserDataForCombos
import com.dooques.fightingflow.model.UserEntry
import com.dooques.fightingflow.model.toHashMap
import com.dooques.fightingflow.util.emptyUser
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import kotlin.collections.forEach

class FirebaseUserRepository() {

    val firestore = Firebase.firestore
    val userCollection: CollectionReference = firestore.collection("users")

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

    suspend fun updateUser(user: UserEntry): UserFbResult {
        val userHash = user.toHashMap()
        Timber.d("User Details: $userHash")

        return try {
            Timber.d("Attempting to update firestore database user")
            userCollection.document(user.userId)
                .update(userHash)
                .await()
            UserFbResult.Success
        } catch (e: Exception) {
            Timber.e(e, "Error updating ${user.username}'s details \n User: $user\n Hash: $userHash")
            UserFbResult.Error(e)
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
