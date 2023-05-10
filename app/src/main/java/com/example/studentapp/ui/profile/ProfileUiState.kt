package com.example.studentapp.ui.profile

import com.example.studentapp.data.Project
import com.example.studentapp.data.User

data class ProfileUiState(
    val subordinateProjects: MutableList<Project> = mutableListOf(),
    val leaderProjects: MutableList<Project> = mutableListOf(),
    val projectName: String = "",
    val projectDescription: String = "",
    val teamName: String = "",
    val teamDescription: String = "",
    val tags: List<String> = listOf(),
    val currentProject: Project = Project(),
    val currentUsers: List<User> = listOf(),
    val mainUser: User = User()
)