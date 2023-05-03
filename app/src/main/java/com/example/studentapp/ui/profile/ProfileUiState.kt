package com.example.studentapp.ui.profile

import com.example.studentapp.data.Project

data class ProfileUiState(
    val subordinateProjects: List<Project> = listOf(),
    val leaderProjects: List<Project> = listOf()
)