package com.example.studentapp.ui.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.studentapp.data.*
import com.example.studentapp.ui.home.HomeUiState
import com.example.studentapp.ui.home.TAG
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

    fun validateProjectForm() =
        uiState.value.projectName.isNotBlank() && uiState.value.projectDescription.isNotBlank()

    fun addProject(userId: String, context: Context) {
        projectItemsRepository.addProject(
            userId = userId,
            name = uiState.value.projectName,
            description = uiState.value.projectDescription,
            isActive = true,
            members = mutableListOf(Pair(userId, true))
        ) { projectAddedStatus ->
            if (projectAddedStatus && validateProjectForm()) {
                Log.d(TAG, "Всё норм")
                Toast.makeText(
                    context,
                    "Проект успешно добавлен",
                    Toast.LENGTH_SHORT
                ).show()
                resetProjectState()
            } else {
                Log.d(TAG, "Всё сломалось")
                Toast.makeText(
                    context,
                    "Проверьте введённые данные",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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
}