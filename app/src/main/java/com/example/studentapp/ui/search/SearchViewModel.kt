package com.example.studentapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studentapp.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class SearchViewModel(
    private val teamItemsRepository: TeamRepository,
    private val projectItemsRepository: ItemsRepository,
    private val userAuthRepository: AuthRepository,
    private val messageRepository: MessRepository
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

    fun setProjectByIdForTeam(projectId: String) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                currentProject = projectItemsRepository.getProjectById(projectId)
            )
        }
        _uiState.update {
            it.copy(
                currentUsers = userAuthRepository.getUsersList(_uiState.value.currentProject.members.keys.toList())
            )
        }
    }

    fun setProjectById(project: Project) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                currentProject = project,
                currentUsers = userAuthRepository.getUsersList(project.members.keys.toList())
            )
        }
    }

    fun setCurrentUserDetail(userId: String) = viewModelScope.launch {
        _uiState.update {
            it.copy(currentUserDetail = userAuthRepository.getUserById(userId))
        }
        _uiState.update {
            var temp: String? = null
            if (uiState.value.currentUserDetail.leaderProjects.isNotEmpty())
                temp = uiState.value.currentUserDetail.leaderProjects.keys.last()
            it.copy(
                currentLastProjectName = if (temp != null) projectItemsRepository.getProjectById(
                    temp
                ).name else "Пока нет проектов"
            )
        }
    }

    private fun getCurrentTimeFormatted(): String {
        val currentTime = Timestamp.now().toDate()
        val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return format.format(currentTime)
    }

    fun addMessage(text: String, send: String, receive: String) = viewModelScope.launch {
        if (text.isNotBlank()) {
            messageRepository.addMessage(
                text = text,
                receive = receive,
                send = send,
                time = getCurrentTimeFormatted()
            )
            userAuthRepository.addMessage(
                leaderUser = receive,
                currentUser = send,
                teamName = uiState.value.currentTeam.name,
                teamId = uiState.value.currentTeam.id
            )
        }
    }

    fun increaseTeamNumber(teamId: String) {
        teamItemsRepository.increaseTeamNumber(teamId)
    }

    fun setProjectList(
        projectLeaderIds: HashMap<String, Boolean>,
        projectSubordinateIds: HashMap<String, Boolean>
    ) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                currentUserLeaderProjects = projectItemsRepository.getProjectList(projectLeaderIds),
                currentUserSubordinateProjects = projectItemsRepository.getProjectList(
                    projectSubordinateIds
                )
            )
        }
    }
}