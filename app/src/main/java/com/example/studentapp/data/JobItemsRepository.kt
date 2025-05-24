package com.example.studentapp.data

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface JobRepository {
    suspend fun getJobById(jobId: String, onComplete: (JobResponse?) -> Unit)
    suspend fun getJobs(onComplete: (List<JobResponse>?) -> Unit)
    suspend fun createJob(projectId: String, name: String, description: String)
}

class JobItemsRepository : JobRepository {
    override suspend fun getJobById(jobId: String, onComplete: (JobResponse?) -> Unit) {
        runCatching {
            ktorClient.get("http://10.0.2.2:8080/job/${jobId}") {
                contentType(ContentType.Application.Json)
            }.body<JobResponse>()
        }
        .onSuccess { response ->
            onComplete(response)
        }
        .onFailure { _ ->
            onComplete(null)
        }
    }

    override suspend fun getJobs(onComplete: (List<JobResponse>?) -> Unit) {
        runCatching {
            ktorClient.get("http://10.0.2.2:8080/jobs") {
                contentType(ContentType.Application.Json)
            }.body<List<JobResponse>>()
        }
        .onSuccess { response ->
            onComplete(response)
        }
        .onFailure { _ ->
            onComplete(null)
        }
    }

    override suspend fun createJob(projectId: String, name: String, description: String) {
        ktorClient.post("http://10.0.2.2:8080/project/$projectId/job") {
            contentType(ContentType.Application.Json)
            setBody(JobRequest(name, description))
        }
    }
}
