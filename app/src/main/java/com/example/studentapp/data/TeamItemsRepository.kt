package com.example.studentapp.data

import android.net.Uri
import android.util.Log
import com.example.studentapp.ui.home.TAG
import com.example.studentapp.ui.search.Response
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.WriteBatch
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


const val TEAMS_COLLECTION_REF = "teams"

interface TeamRepository {
    suspend fun getTeamById(teamId: String): Team
    fun addTeam(
        name: String,
        description: String,
        project: String,
        publishDate: String,
        leaderId: String,
        leaderName: String,
        leaderAvatar: String,
        members: Int,
        tags: List<String>,
        onComplete: (Boolean) -> Unit
    )
    fun increaseTeamNumber(teamId: String)
    fun getTeams(searchText: Pair<String, Int>): Flow<Response>
    fun deleteTeams(projectId: String)
    fun deleteTeamById(teamId: String)
}

class TeamItemsRepository : TeamRepository {
    private val teamsRef: CollectionReference = Firebase.firestore.collection(TEAMS_COLLECTION_REF)

    override fun getTeams(searchText: Pair<String, Int>): Flow<Response> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            var productsQuery: Query = teamsRef
            if (searchText.first.isNotEmpty()) {
                productsQuery = productsQuery.whereArrayContainsAny("tags", searchText.first.split(" "))
            }
            // 2 - сначала давние
            when (searchText.second) {
                1 -> productsQuery = productsQuery.orderBy("name", Query.Direction.ASCENDING)
                2 -> productsQuery = productsQuery.orderBy("publishDate", Query.Direction.ASCENDING)
                else -> productsQuery = productsQuery.orderBy("publishDate", Query.Direction.DESCENDING)
            }

            snapshotStateListener = productsQuery.addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val teams = snapshot.toObjects(Team::class.java)
                    Response.Success(teams)
                } else {
                    Log.d(TAG, e?.message ?: e.toString())
                    Response.Error(e?.message ?: e.toString())
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

    override fun increaseTeamNumber(teamId: String) {
        teamsRef.document(teamId).get().addOnSuccessListener {
            val newTeamNumber = it?.toObject(Team::class.java)?.members ?: 0
            it.reference.update("members", newTeamNumber + 1)
        }
    }

    override fun deleteTeams(projectId: String) {
        teamsRef.whereEqualTo("project", projectId).get().addOnSuccessListener {
            for (document in it.documents) {
                document.reference.delete()
            }
        }
    }

    override fun deleteTeamById(teamId: String) {
        teamsRef.document(teamId).delete()
    }

    override suspend fun getTeamById(teamId: String): Team {
        val snapshot = teamsRef.document(teamId).get().await()
        return snapshot.toObject(Team::class.java) ?: Team()
    }

    override fun addTeam(
        name: String,
        description: String,
        project: String,
        publishDate: String,
        leaderId: String,
        leaderName: String,
        leaderAvatar: String,
        members: Int,
        tags: List<String>,
        onComplete: (Boolean) -> Unit
    ) {
        val documentId = teamsRef.document().id
        val team = Team(
            id = documentId,
            name = name,
            description = description,
            project = project,
            leaderName = leaderName,
            leaderId = leaderId,
            tags = tags,
            publishDate = publishDate,
            leaderAvatar = leaderAvatar,
            members = members
        )
        teamsRef
            .document(documentId)
            .set(team)
            .addOnCompleteListener { result ->
                onComplete(result.isSuccessful)
            }
    }
}
