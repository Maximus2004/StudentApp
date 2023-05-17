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
    suspend fun getProjectList(projectIds: HashMap<String, Boolean>): HashMap<Project, Boolean>
    suspend fun getProjectById(projectId: String): Project
    fun addProject(
        userId: String,
        name: String,
        description: String,
        isActive: Boolean,
        members: HashMap<String, Boolean>,
        onComplete: (Boolean, String) -> Unit
    )
    fun addMemberInProject(projectId: String)
    fun endProject(projectId: String, photos: List<String>)
}

class ProjectItemsRepository : ItemsRepository {
    companion object{
        val projectsRef: CollectionReference = Firebase.firestore.collection(PROJECTS_COLLECTION_REF)
    }

    override suspend fun getProjectList(projectIds: HashMap<String, Boolean>): HashMap<Project, Boolean> {
        val leaderList: HashMap<Project, Boolean> = hashMapOf()
        for (a in projectIds) {
            leaderList[getProjectById(a.key)] = a.value
        }
        return leaderList
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
            members = members
        )
        projectsRef
            .document(documentId)
            .set(project)
            .addOnCompleteListener { result ->
                onComplete(result.isSuccessful, documentId)
            }
    }

    override fun addMemberInProject(projectId: String) {
        projectsRef.document(projectId).get().addOnSuccessListener {
            val newMembers = it?.toObject(Project::class.java)?.members
            newMembers?.put(UserAuthRepository.getUserId(), false)
            projectsRef.document(projectId).update("members", newMembers)
        }
    }

    override fun endProject(projectId: String, photos: List<String>) {
        projectsRef.document(projectId).get().addOnSuccessListener {
            projectsRef.document(projectId).update("photos", photos)
        }
    }

    override suspend fun getProjectById(projectId: String): Project {
        val snapshot = projectsRef.document(projectId).get().await()
        return snapshot.toObject(Project::class.java) ?: Project()
    }
}