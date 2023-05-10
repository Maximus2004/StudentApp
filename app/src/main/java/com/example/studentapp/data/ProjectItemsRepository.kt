package com.example.studentapp.data

import android.util.Log
import com.example.studentapp.ui.home.TAG
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

const val PROJECTS_COLLECTION_REF = "projects"

interface ItemsRepository {
    suspend fun getProjectList(projectIds: MutableList<String>): MutableList<Project>
    suspend fun getProjectById(projectId: String): Project
    fun addProject(
        userId: String,
        name: String,
        description: String,
        isActive: Boolean,
        members: HashMap<String, Boolean>,
        onComplete: (Boolean, String) -> Unit
    )
    fun endProject(projectId: String)
}

class ProjectItemsRepository : ItemsRepository {
    private val projectsRef: CollectionReference =
        Firebase.firestore.collection(PROJECTS_COLLECTION_REF)

    override suspend fun getProjectList(projectIds: MutableList<String>): MutableList<Project> {
        val leaderList: MutableList<Project> = mutableListOf()
        for (a in projectIds) {
            leaderList.add(getProjectById(a))
        }
        return leaderList
    }

    override fun endProject(projectId: String) {
        projectsRef.document(projectId).update("active", false)
    }

    override fun addProject(
        userId: String,
        name: String,
        description: String,
        isActive: Boolean,
        members: HashMap<String, Boolean>,
        onComplete: (Boolean, String) -> Unit
    ) {
        val documentId = projectsRef.document().id
        val project = Project(
            id = documentId,
            name = name,
            description = description,
            active = isActive,
            members = members
        )
        projectsRef
            .document(documentId)
            .set(project)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful, documentId)
            }
    }

    override suspend fun getProjectById(projectId: String): Project {
        var project = Project()
        projectsRef
            .document(projectId)
            .get()
            .addOnSuccessListener {
                project = it?.toObject(Project::class.java) ?: Project()
            }.await()
        return project
    }
}