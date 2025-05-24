package com.example.studentapp.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

val ktorClient = HttpClient(Android) {
    install(ContentNegotiation) {
        json()
    }
}