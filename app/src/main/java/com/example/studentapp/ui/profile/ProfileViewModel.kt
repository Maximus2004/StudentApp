package com.example.studentapp.ui.profile

import androidx.lifecycle.ViewModel
import com.example.studentapp.data.ItemsRepository
import com.example.studentapp.data.PageType
import com.example.studentapp.data.Project
import com.example.studentapp.data.User
import com.example.studentapp.ui.home.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class ProfileViewModel(private val projectItemsRepository: ItemsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun fillProjects(userId: Int) {
        _uiState.update {
            it.copy(
                subordinateProjects = projectItemsRepository.getSubordinateProjectList(userId),
                leaderProjects = projectItemsRepository.getLeaderProjectList(userId)
            )
        }
    }

    fun getProjectById(projectId: Int): Project {
        return projectItemsRepository.getProjectById(projectId)
    }

    fun getUserById(userId: Int): User {
        return projectItemsRepository.getUserById(userId)
    }
}