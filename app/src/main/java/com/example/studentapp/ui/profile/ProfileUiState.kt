package com.example.studentapp.ui.profile

import com.example.studentapp.data.ProjectResponse

data class ProfileUiState(
    val projectName: String = "",
    val projectDescription: String = "",
    val teamName: String = "",
    val teamDescription: String = "",
    val tags: List<String> = listOf(),
)

data class UserProjectsUiState(
    val projects: List<ProjectResponse> = listOf()
)
