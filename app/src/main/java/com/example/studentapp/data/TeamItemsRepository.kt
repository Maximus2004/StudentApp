package com.example.studentapp.data

import android.util.Log
import com.example.studentapp.ui.home.TAG
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


const val TEAMS_COLLECTION_REF = "teams"

interface TeamRepository {
    fun getTeamById(teamId: Int): Team
    fun getLeaderById(leaderId: Int): User
    fun getProjectById(projectId: Int): Project
    fun addTeam(
        name: String,
        description: String,
        project: String,
        publishDate: String,
        leader: String,
        tags: List<String>,
        onComplete: (Boolean) -> Unit
    )
    fun getTeams(): List<Team>
}

class TeamItemsRepository : TeamRepository {
    private val teamsRef: CollectionReference = Firebase.firestore.collection(TEAMS_COLLECTION_REF)

    override fun getTeams(): List<Team> {
        val teams: MutableList<Team> = mutableListOf()
        teamsRef.get().addOnCompleteListener { task ->
            Log.d(TAG, "Зашёл")
            if (task.isSuccessful) {
                for (document in task.result) {
                    Log.d(TAG, document.get("tags").toString())
                    teams.add(
                        Team(
                            name = document.get("name").toString(),
                            description = document.get("description").toString(),
                            leader = document.get("leader").toString(),
                            project = document.get("project").toString(),
                            publishDate = document.get("publishDate").toString(),
                            tags = document.get("tags") as List<String>,
                            id = document.id
                        )
                    )
                }
            }
        }
        return teams
    }


    override fun getTeamById(teamId: Int): Team {
        return teams[teamId]
    }

    override fun getLeaderById(leaderId: Int): User {
        return users[leaderId]
    }

    override fun getProjectById(projectId: Int): Project {
        return projects[projectId]
    }

    override fun addTeam(
        name: String,
        description: String,
        project: String,
        publishDate: String,
        leader: String,
        tags: List<String>,
        onComplete: (Boolean) -> Unit
    ) {
        val documentId = teamsRef.document().id
        val team = Team(
            id = documentId,
            name = name,
            description = description,
            project = project,
            leader = leader,
            tags = tags,
            publishDate = publishDate
        )
        teamsRef
            .document(documentId)
            .set(team)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }
            .addOnFailureListener { result ->
                Log.d(TAG, result.cause.toString())
            }
    }
}
