package com.example.studentapp.data

import android.util.Log
import com.example.studentapp.ui.home.TAG
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val PROJECTS_COLLECTION_REF = "projects"

interface ItemsRepository {
    fun getLeaderProjectList(userId: Int): List<Project>
    fun getSubordinateProjectList(userId: Int): List<Project>
    fun getProjectById(projectId: Int): Project
    fun getUserById(userId: Int): User
    fun addProject(
        userId: String,
        name: String,
        description: String,
        isActive: Boolean,
        members: List<Pair<String, Boolean>>,
        onComplete: (Boolean) -> Unit
    )
}

class ProjectItemsRepository : ItemsRepository {
    private val projectsRef: CollectionReference = Firebase.firestore.collection(PROJECTS_COLLECTION_REF)

    override fun getLeaderProjectList(userId: Int): List<Project> {
        val leaderProjects: MutableList<Project> = mutableListOf()
        for (project in users[userId].leaderProjects) {
            leaderProjects.add(projects[project])
        }
        return leaderProjects
    }

    override fun addProject(
        userId: String,
        name: String,
        description: String,
        isActive: Boolean,
        members: List<Pair<String, Boolean>>,
        onComplete: (Boolean) -> Unit
    ) {
        val documentId = projectsRef.document().id
        val project = Project(
            id = documentId,
            name = name,
            description = description,
            isActive = isActive,
            members = members
        )
        projectsRef
            .document(documentId)
            .set(project)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }
    }

    override fun getSubordinateProjectList(userId: Int): List<Project> {
        val subordinateProjects: MutableList<Project> = mutableListOf()
        for (project in users[userId].subordinateProjects) {
            subordinateProjects.add(projects[project])
        }
        return subordinateProjects
    }

    override fun getProjectById(projectId: Int): Project {
        return projects[projectId]
    }

    override fun getUserById(userId: Int): User {
        return users[userId]
    }
}