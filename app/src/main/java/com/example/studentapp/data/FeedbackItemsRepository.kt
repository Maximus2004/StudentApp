package com.example.studentapp.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Math.ceil

const val FEEDBACK_COLLECTION_REF = "feedbacks"

interface FeedbackRepository {
    fun addFeedback(text: String, user: String, project: String, rate: Int)
    suspend fun getFeedback(projectId: String, user: String): Feedback
    suspend fun getNewUserFeedback(userId: String): Int
}

class FeedbackItemsRepository : FeedbackRepository {
    companion object {
        val feedbacksRef: CollectionReference = Firebase.firestore.collection(FEEDBACK_COLLECTION_REF)
    }

    override fun addFeedback(
        text: String,
        user: String,
        project: String,
        rate: Int
    ) {
        val documentId = feedbacksRef.document().id
        val message = Feedback(
            id = documentId,
            text = text,
            user = user,
            project = project,
            rate = rate
        )
        feedbacksRef.document(documentId).set(message)
    }

    override suspend fun getFeedback(projectId: String, user: String): Feedback {
        val snapshot = feedbacksRef.whereEqualTo("project", projectId).whereEqualTo("user", user).get().await()
        val tempList = snapshot.toObjects(Feedback::class.java)
        return if (tempList.isEmpty()) Feedback() else tempList[0]
    }

    override suspend fun getNewUserFeedback(userId: String): Int {
        val snapshot = feedbacksRef.whereEqualTo("user", userId).get().await()
        var s = 0
        val tempList = snapshot.toObjects(Feedback::class.java)
        tempList.forEach {
            s += it.rate
        }
        return if (tempList.isNotEmpty()) s/tempList.size else 0
    }
}