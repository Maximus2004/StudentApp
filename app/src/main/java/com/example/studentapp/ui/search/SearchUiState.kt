package com.example.studentapp.ui.search

import com.example.studentapp.data.Project
import com.example.studentapp.data.Team
import com.example.studentapp.data.User

data class SearchUiState(
    val currentTeam: Team = Team(),
    val currentProject: Project = Project(),
    val currentUsers: List<User> = listOf(),
    val currentUserDetail: User = User(),
    val currentLastProjectName: String = "",
    val currentUserLeaderProjects: HashMap<Project, Boolean> = hashMapOf(),
    val currentUserLeaderProjectIds: HashMap<String, Boolean> = hashMapOf(),
    val currentUserSubordinateProjects: HashMap<Project, Boolean> = hashMapOf(),
    val currentUserSubordinateProjectIds: HashMap<String, Boolean> = hashMapOf()
)