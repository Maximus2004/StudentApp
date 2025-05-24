package com.example.studentapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentapp.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val projectItemsRepository: ItemsRepository,
    private val jobItemsRepository: JobRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    val projectsUiState = MutableStateFlow(UserProjectsUiState())

    fun getProjects(login: String) {
        viewModelScope.launch {
            projectItemsRepository.getProjectList(login) { projects ->
                projectsUiState.update { it.copy(projects = projects ?: emptyList()) }
            }
        }
    }

    fun createProject(login: String, name: String, description: String) {
        viewModelScope.launch {
            projectItemsRepository.createProject(login, name, description)
        }
    }

    fun createJob(projectId: String, name: String, description: String) {
        viewModelScope.launch {
            jobItemsRepository.createJob(projectId, name, description)
        }
    }

    fun onNameChanged(name: String) {
        _uiState.update { it.copy(projectName = name) }
    }

    fun onDescriptionChanged(description: String) {
        _uiState.update { it.copy(projectDescription = description) }
    }

    fun onTeamNameChanged(name: String) {
        _uiState.update { it.copy(teamName = name) }
    }

    fun onTeamDescriptionChanged(description: String) {
        _uiState.update { it.copy(teamDescription = description) }
    }

    fun onTagsChanged(tags: List<String>) {
        _uiState.update { it.copy(tags = tags) }
    }
}