package com.example.studentapp.ui.search

import com.example.studentapp.data.Project
import com.example.studentapp.data.Team

data class SearchUiState(
    val subordinateProjects: List<Project> = listOf(),
    val leaderProjects: List<Project> = listOf(),
    val currentTeamsList: List<Team> = listOf()
)