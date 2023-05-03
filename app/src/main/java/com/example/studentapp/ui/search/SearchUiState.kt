package com.example.studentapp.ui.search

import com.example.studentapp.data.Project

data class SearchUiState(
    val subordinateProjects: List<Project> = listOf(),
    val leaderProjects: List<Project> = listOf()
)