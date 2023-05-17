package com.example.studentapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studentapp.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val teamItemsRepository: TeamRepository,
    private val projectItemsRepository: ItemsRepository,
    private val userAuthRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState
    private val _teamsList: MutableStateFlow<Response> = MutableStateFlow(Response.Loading)
    val teamsList: StateFlow<Response> = _teamsList.asStateFlow()

    fun setSearchState(searchText: Pair<String, Int>) = viewModelScope.launch {
        teamItemsRepository.getTeams(searchText).collect { response ->
            _teamsList.update { response }
        }
    }

    fun setTeamById(teamId: String) = viewModelScope.launch {
        _uiState.update { it.copy(currentTeam = teamItemsRepository.getTeamById(teamId)) }
    }

    fun setProjectById(projectId: String) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                currentProject = projectItemsRepository.getProjectById(projectId),
            )
        }
        _uiState.update {
            it.copy(
                currentUsers = userAuthRepository.getUsersList(_uiState.value.currentProject.members.keys.toList())
            )
        }
    }

    fun setCurrentUserDetail(userId: String) = viewModelScope.launch {
        _uiState.update {
            it.copy(currentUserDetail = userAuthRepository.getUserById(userId))
        }
        _uiState.update {
            it.copy(
                currentLastProjectName = projectItemsRepository.getProjectById(uiState.value.currentUserDetail.leaderProjects.keys.last()).name
            )
        }
    }

    fun setProjectList(
        projectLeaderIds: HashMap<String, Boolean>,
        projectSubordinateIds: HashMap<String, Boolean>
    ) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                currentUserLeaderProjects = projectItemsRepository.getProjectList(projectLeaderIds),
                currentUserSubordinateProjects = projectItemsRepository.getProjectList(projectSubordinateIds)
            )
        }
    }

    companion object {
        const val TIMEOUT_MILLIS = 5_000L
    }
}

data class TeamState(val teams: List<Team> = listOf())