package com.example.studentapp.data

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String,
        onComplete: (AuthResponse?, Throwable?) -> Unit
    )

    suspend fun signup(
        name: String,
        surname: String,
        description: String,
        email: String,
        password: String,
        onComplete: (AuthResponse?, Throwable?) -> Unit
    )

    suspend fun getUserById(userId: String): UserResponse
}

class UserAuthRepository : AuthRepository {
    override suspend fun getUserById(userId: String) =
        runCatching {
            ktorClient.get("http://10.0.2.2:8080/user/$userId") {
                contentType(ContentType.Application.Json)
            }.body<UserResponse>()
        }
        .getOrNull() ?: UserResponse()

    override suspend fun login(
        email: String,
        password: String,
        onComplete: (AuthResponse?, Throwable?) -> Unit
    ) {
        runCatching {
            ktorClient.post("http://10.0.2.2:8080/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email, password))
            }.body<AuthResponse>()
        }
            .onSuccess { response ->
                onComplete(response, null)
            }
            .onFailure { exception ->
                onComplete(null, exception)
            }
    }

    override suspend fun signup(
        name: String,
        surname: String,
        description: String,
        email: String,
        password: String,
        onComplete: (AuthResponse?, Throwable?) -> Unit
    ) {
        runCatching {
            ktorClient.post("http://10.0.2.2:8080/register") {
                contentType(ContentType.Application.Json)
                setBody(SignUpRequest(name, surname, description, email, password))
            }.body<AuthResponse>()
        }
            .onSuccess { response ->
                onComplete(response, null)
            }
            .onFailure { exception ->
                onComplete(null, exception)
            }
    }
}
