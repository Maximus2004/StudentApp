package com.example.studentapp.ui.profile

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentapp.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar

class ProfileViewModel(
    private val projectItemsRepository: ItemsRepository,
    private val teamItemsRepository: TeamRepository,
    private val userAuthRepository: AuthRepository,
    private val feedbackItemsRepository: FeedbackRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    private val _projectPhotos: MutableStateFlow<ImageDownloadStatus> =
        MutableStateFlow(ImageDownloadStatus.Default)
    val projectPhotos: StateFlow<ImageDownloadStatus> = _projectPhotos.asStateFlow()
    var ratingState: StateFlow<Int> = userAuthRepository.listenerToRating()
        .stateIn(
            scope = viewModelScope,
            SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = 0
        )
    var projectsList: StateFlow<UserProjectsState> = userAuthRepository.fillProjects()
        .map { UserProjectsState(Pair(it.first.await(), it.second.await())) }
        .stateIn(
            scope = viewModelScope,
            SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = UserProjectsState()
        )

    fun endProject(projectId: String, context: Context) {
        viewModelScope.launch {
            _projectPhotos.update { ImageDownloadStatus.Loading }
            projectItemsRepository.endProject(projectId, uiState.value.projectPhotos)
            userAuthRepository.endProject(projectId)
            userAuthRepository.endSubordinateProjects(
                projectId,
                projectItemsRepository.getSubordinateUserList(projectId)
            )
            var i = 0
            for (member in uiState.value.currentProject.members) {
                if (!member.value) {
                    feedbackItemsRepository.addFeedback(
                        text = uiState.value.feedbackList[i],
                        rate = uiState.value.ratingList[i],
                        project = uiState.value.currentProject.id,
                        user = member.key
                    )
                    userAuthRepository.setNewFeedback(
                        feedbackItemsRepository.getNewUserFeedback(
                            member.key
                        ), member.key
                    )
                }
                i++
            }
            _uiState.update {
                it.copy(
                    feedbackList = mutableListOf(),
                    projectPhotos = mutableListOf(),
                    ratingList = mutableListOf()
                )
            }
            teamItemsRepository.deleteTeams(projectId)
            Toast.makeText(context, "Проект успешно завершён", Toast.LENGTH_SHORT).show()
            _projectPhotos.update { ImageDownloadStatus.Success }
        }
    }

    fun setProjectPhotos(uris: List<Uri>, context: Context) = viewModelScope.launch {
        _projectPhotos.update { ImageDownloadStatus.Loading }
        userAuthRepository.uploadProfilePhotos(uris).collect { response ->
            if (!response.second) {
                _uiState.update {
                    val temp = uiState.value.projectPhotos.toMutableList()
                    temp.add(response.first)
                    it.copy(projectPhotos = temp)
                }
            } else {
                Toast.makeText(context, "Фото проекта успешно загружены", Toast.LENGTH_SHORT).show()
                _uiState.update {
                    val temp = uiState.value.projectPhotos.toMutableList()
                    temp.add(response.first)
                    it.copy(projectPhotos = temp)
                }
                _projectPhotos.update { ImageDownloadStatus.Success }
            }
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

    fun setProjectById(project: Project) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                currentProject = project,
                currentUsers = userAuthRepository.getUsersList(project.members.keys.toList())
            )
        }
    }

    fun setCurrentFeedback(project: Project) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                currentFeedback = feedbackItemsRepository.getFeedback(
                    projectId = project.id,
                    user = UserAuthRepository.getUserId()
                )
            )
        }
    }

    fun validateProjectForm() =
        uiState.value.projectName.isNotBlank() && uiState.value.projectDescription.isNotBlank()

    fun addProject(userId: String, context: Context, onFinish: (String) -> Unit) {
        try {
            if (!validateProjectForm()) throw IllegalArgumentException("Заполните все поля")
            projectItemsRepository.addProject(
                userId = userId,
                name = uiState.value.projectName,
                description = uiState.value.projectDescription,
                isActive = true,
                members = hashMapOf(userId to true)
            ) { projectAddedStatus, projectId ->
                if (projectAddedStatus) {
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
        } catch (e: Exception) {
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    fun addLeaderProject(projectId: String) {
        userAuthRepository.addLeaderProject(projectId)
    }

    fun validateTeamForm() =
        uiState.value.teamName.isNotBlank() && uiState.value.teamDescription.isNotBlank() && uiState.value.tags.isNotEmpty()

    fun addTeam(
        userId: String,
        projectId: String,
        context: Context,
        onFinish: () -> Unit,
        leaderName: String,
        leaderAvatar: String
    ) {
        try {
            val calendar = Calendar.getInstance()
            if (!validateTeamForm()) throw IllegalArgumentException("Заполните все поля")
            teamItemsRepository.addTeam(
                name = uiState.value.teamName,
                description = uiState.value.teamDescription,
                tags = uiState.value.tags,
                leaderId = userId,
                project = projectId,
                publishDate = "${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH) + 1}.${
                    calendar.get(
                        Calendar.YEAR
                    )
                }",
                leaderName = leaderName,
                members = 0,
                leaderAvatar = leaderAvatar
            ) { teamAddedStatus ->
                if (teamAddedStatus) {
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
        } catch (e: Exception) {
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
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

    companion object {
        const val TIMEOUT_MILLIS = 5_000L
    }
}

data class UserProjectsState(
    val projects: Pair<HashMap<Project, Boolean>, HashMap<Project, Boolean>> = Pair(
        hashMapOf(Project() to true),
        hashMapOf(Project() to true)
    )
)