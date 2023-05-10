package com.example.studentapp.ui.search

import androidx.lifecycle.ViewModel
import com.example.studentapp.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SearchViewModel(private val teamItemsRepository: TeamRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    init {
        getTeamsList()
    }

//    fun fillProjects(userId: Int) {
//        _uiState.update {
//            it.copy(
//                subordinateProjects = teamItemsRepository.getSubordinateProjectList(userId),
//                leaderProjects = teamItemsRepository.getLeaderProjectList(userId)
//            )
//        }
//    }
    fun getTeamsList() {
        _uiState.update { it.copy(currentTeamsList = teamItemsRepository.getTeams()) }
    }
    fun getTeamById(teamId: Int): Team {
        return teamItemsRepository.getTeamById(teamId)
    }
    fun getUserById(leaderId: Int): User {
        return teamItemsRepository.getLeaderById(leaderId)
    }
    fun getProjectById(projectId: Int): Project {
        return teamItemsRepository.getProjectById(projectId)
    }
}