package com.example.studentapp.data

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface ItemsRepository {
    suspend fun getProjectList(login: String, onComplete: (List<ProjectResponse>?) -> Unit)
    suspend fun createProject(
        login: String,
        name: String,
        description: String,
    )
    suspend fun getProjectById(projectId: String, onComplete: (ProjectResponse?) -> Unit)
}

class ProjectItemsRepository : ItemsRepository {
    override suspend fun getProjectList(login: String, onComplete: (List<ProjectResponse>?) -> Unit) {
        runCatching {
            ktorClient.get("http://10.0.2.2:8080/user/$login/projects") {
                contentType(ContentType.Application.Json)
            }.body<List<ProjectResponse>>()
        }
        .onSuccess { response ->
            onComplete(response)
        }
        .onFailure { _ ->
            onComplete(null)
        }
    }

    override suspend fun createProject(
        login: String,
        name: String,
        description: String,
    ) {
        ktorClient.post("http://10.0.2.2:8080/user/$login/project") {
            contentType(ContentType.Application.Json)
            setBody(ProjectRequest(name, description))
        }
    }

    override suspend fun getProjectById(projectId: String, onComplete: (ProjectResponse?) -> Unit) {
        runCatching {
            ktorClient.get("http://10.0.2.2:8080/project/${projectId}") {
                contentType(ContentType.Application.Json)
            }.body<ProjectResponse>()
        }
        .onSuccess { response ->
            onComplete(response)
        }
        .onFailure { _ ->
            onComplete(null)
        }
    }
}