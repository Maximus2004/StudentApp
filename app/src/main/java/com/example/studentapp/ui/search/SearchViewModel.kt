package com.example.studentapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentapp.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val jobRepository: JobRepository,
    private val projectItemsRepository: ItemsRepository,
    private val connectRepository: ConnectRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState
    private val _jobsList: MutableStateFlow<Response> = MutableStateFlow(Response.Loading)
    val jobsList: StateFlow<Response> = _jobsList.asStateFlow()

    fun updateJobList() {
        viewModelScope.launch {
            jobRepository.getJobs { jobs ->
                _jobsList.value =
                    if (jobs != null)
                        Response.Success(teamsList = jobs)
                    else
                        Response.Error("Ошибка")
            }
        }
    }

    fun onSearchTextChanged(searchText: String) {
        _uiState.update { it.copy(searchText = searchText) }
        viewModelScope.launch {
            jobRepository.searchJobs(searchText) { jobs ->
                _jobsList.value =
                    if (jobs != null)
                        Response.Success(teamsList = jobs)
                    else
                        Response.Error("Ошибка")
            }
        }
    }

    fun setTeamById(jobId: String) {
        viewModelScope.launch {
            jobRepository.getJobById(jobId) { job ->
                _uiState.update { it.copy(currentJob = job) }
            }
        }
    }

    fun createConnect(jobId: String, text: String, login: String) {
        viewModelScope.launch {
            connectRepository.createConnect(jobId, text, login)
        }
    }

    fun setProjectById(projectId: String) {
        viewModelScope.launch {
            projectItemsRepository.getProjectById(projectId) { project ->
                _uiState.update { it.copy(currentProject = project) }
            }
        }
    }
}