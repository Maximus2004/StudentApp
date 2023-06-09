package com.example.studentapp.ui.profile

import com.example.studentapp.data.Feedback
import com.example.studentapp.data.Project
import com.example.studentapp.data.User

data class ProfileUiState(
    val projectName: String = "",
    val projectDescription: String = "",
    val teamName: String = "",
    val teamDescription: String = "",
    val tags: List<String> = listOf(),
    val currentProject: Project = Project(),
    val currentUsers: List<User> = listOf(),
    val currentUserDetail: User = User(),
    val currentLastProjectName: String = "",
    val currentUserLeaderProjects: HashMap<Project, Boolean> = hashMapOf(),
    val currentUserLeaderProjectIds: HashMap<String, Boolean> = hashMapOf(),
    val currentUserSubordinateProjects: HashMap<Project, Boolean> = hashMapOf(),
    val currentUserSubordinateProjectIds: HashMap<String, Boolean> = hashMapOf(),
    val projectPhotos: List<String> = listOf(),
    val feedbackList: MutableList<String> = mutableListOf(),
    val ratingList: MutableList<Int> = mutableListOf(),
    val currentFeedback: Feedback = Feedback()
)