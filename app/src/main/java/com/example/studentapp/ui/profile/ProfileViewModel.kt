package com.example.studentapp.ui.profile

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentapp.data.*
import com.example.studentapp.ui.home.HomeUiState
import com.example.studentapp.ui.home.TAG
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Calendar

class ProfileViewModel(
    private val projectItemsRepository: ItemsRepository,
    private val teamItemsRepository: TeamRepository,
    private val userAuthRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun endProject(projectId: String) {
        projectItemsRepository.endProject(projectId)
    }

    fun fillProjects() {
        userAuthRepository.fillProjects { leaderProjects, subordinateProjects ->
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        leaderProjects = projectItemsRepository.getProjectList(leaderProjects),
                        subordinateProjects = projectItemsRepository.getProjectList(subordinateProjects)
                    )
                }
            }
        }
    }

    fun getUserById(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(mainUser = userAuthRepository.getUserById(userId)) }
        }
    }

    fun resetUsersAndProject() {
        _uiState.update { it.copy(currentProject = Project(), currentUsers = listOf()) }
    }

    fun setProjectById(projectId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentProject = projectItemsRepository.getProjectById(projectId),
                    currentUsers = userAuthRepository.getUsersList(uiState.value.currentProject.members.keys.toList())
                )
            }
        }
    }

    fun validateProjectForm() =
        uiState.value.projectName.isNotBlank() && uiState.value.projectDescription.isNotBlank()

    fun addProject(userId: String, context: Context, onFinish: (String) -> Unit) {
        projectItemsRepository.addProject(
            userId = userId,
            name = uiState.value.projectName,
            description = uiState.value.projectDescription,
            isActive = true,
            members = hashMapOf(userId to true)
        ) { projectAddedStatus, projectId ->
            if (projectAddedStatus && validateProjectForm()) {
                Toast.makeText(
                    context,
                    "Проект успешно добавлен",
                    Toast.LENGTH_SHORT
                ).show()
                resetProjectState()
                onFinish(projectId)
            } else {
                Toast.makeText(
                    context,
                    "Проверьте введённые данные",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun addSubordinateProject(projectId: String) {
        userAuthRepository.addSubordinateProject(projectId)
    }

    fun addLeaderProject(projectId: String) {
        userAuthRepository.addLeaderProject(projectId)
    }

    fun validateTeamForm() =
        uiState.value.teamName.isNotBlank() && uiState.value.teamDescription.isNotBlank() && uiState.value.tags.isNotEmpty()

    fun addTeam(userId: String, projectId: String, context: Context, onFinish: () -> Unit) {
        val calendar = Calendar.getInstance()
        teamItemsRepository.addTeam(
            name = uiState.value.teamName,
            description = uiState.value.teamDescription,
            tags = uiState.value.tags,
            leader = userId,
            project = projectId,
            publishDate = "${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH) + 1}.${
                calendar.get(
                    Calendar.YEAR
                )
            }"
        ) { teamAddedStatus ->
            if (teamAddedStatus && validateTeamForm()) {
                Toast.makeText(
                    context,
                    "Вакансия успешно опбликована",
                    Toast.LENGTH_SHORT
                ).show()
                onFinish()
                resetTeamState()
            } else {
                Toast.makeText(
                    context,
                    "Проверьте введённые данные",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun resetTeamState() {
        _uiState.update { it.copy(teamName = "", teamDescription = "", tags = listOf()) }
    }

    fun resetProjectState() {
        _uiState.update { it.copy(projectName = "", projectDescription = "") }
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