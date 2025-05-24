package com.example.studentapp.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface ConnectRepository {
    suspend fun getConnects(login: String, onComplete: (List<ConnectResponse>?) -> Unit)
    suspend fun createConnect(jobId: String, text: String, login: String)
    suspend fun getConnect(connectId: String, onComplete: (ConnectResponse?) -> Unit)
}

class ConnectItemsRepository : ConnectRepository {
    override suspend fun getConnects(login: String, onComplete: (List<ConnectResponse>?) -> Unit) {
        runCatching {
            ktorClient.get("http://10.0.2.2:8080/user/$login/connects") {
                contentType(ContentType.Application.Json)
            }.body<List<ConnectResponse>>()
        }
        .onSuccess { response ->
            onComplete(response)
        }
        .onFailure { _ ->
            onComplete(null)
        }
    }

    override suspend fun createConnect(jobId: String, text: String, login: String) {
        ktorClient.post("http://10.0.2.2:8080/job/$jobId/connect") {
            contentType(ContentType.Application.Json)
            setBody(ConnectRequest(login, text))
        }
    }

    override suspend fun getConnect(connectId: String, onComplete: (ConnectResponse?) -> Unit) {
        runCatching {
            ktorClient.get("http://10.0.2.2:8080/connect/${connectId}") {
                contentType(ContentType.Application.Json)
            }.body<ConnectResponse>()
        }
        .onSuccess { response ->
            onComplete(response)
        }
        .onFailure { _ ->
            onComplete(null)
        }
    }
}