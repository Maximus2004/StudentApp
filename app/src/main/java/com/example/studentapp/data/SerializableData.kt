package com.example.studentapp.data

import com.example.studentapp.R
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val name: String,
    val surname: String,
    val description: String,
    val email: String,
    val password: String
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val login: String
)

@Serializable
data class ProjectResponse(
    val projectId: String,
    val name: String,
    val description: String
)

@Serializable
data class ConnectResponse(
    val connectId: String,
    val login: String,
    val text: String
)

@Serializable
data class JobResponse(
    val jobId: String,
    val projectId: String,
    val name: String,
    val description: String,
    val publishDate: String
)

@Serializable
data class UserResponse(
    val name: String = "",
    val surname: String = "",
    val description: String = "",
    val email: String = "",
    val password: String = ""
)

@Serializable
data class ProjectRequest(
    val name: String,
    val description: String
)

@Serializable
data class JobRequest(
    val name: String,
    val description: String
)

@Serializable
data class ConnectRequest(
    val login: String,
    val text: String,
)

// для навигации
val navigationItemContentList = listOf(
    NavigationItemContent(pageType = PageType.Search, icon = R.drawable.search_icon),
    NavigationItemContent(pageType = PageType.Message, icon = R.drawable.message_icon),
    NavigationItemContent(pageType = PageType.Profile, icon = R.drawable.profile_icon)
)