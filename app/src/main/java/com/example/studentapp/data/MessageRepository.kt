package com.example.studentapp.data

import android.util.Log
import com.example.studentapp.ui.home.TAG
import com.example.studentapp.ui.search.Response
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

const val MESSAGE_COLLECTION_REF = "messages"

interface MessRepository {
    fun addMessage(text: String, send: String, receive: String, time: String)
    fun fillMessages(dialog: String): Flow<List<Message>>
}

class MessageRepository : MessRepository {
    companion object {
        val messagesRef: CollectionReference = Firebase.firestore.collection(MESSAGE_COLLECTION_REF)
    }

    override fun addMessage(
        text: String,
        send: String,
        receive: String,
        time: String
    ) {
        val documentId = messagesRef.document().id
        val message = Message(
            id = documentId,
            text = text,
            send = send,
            receive = receive,
            time = time
        )
        messagesRef.document(documentId).set(message)
    }

    override fun fillMessages(dialog: String): Flow<List<Message>> =
        callbackFlow {
            var snapshotStateListener: ListenerRegistration? = null
            try {
                snapshotStateListener =
                    messagesRef.orderBy("time", Query.Direction.ASCENDING).addSnapshotListener { snapshot, e ->
                        val response = if (snapshot != null) {
                            val messages = snapshot.toObjects(Message::class.java)
                            messages
                        } else {
                            listOf()
                        }
                        trySend(response)
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            awaitClose {
                snapshotStateListener?.remove()
            }
        }
}