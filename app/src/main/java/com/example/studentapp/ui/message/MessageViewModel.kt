package com.example.studentapp.ui.message

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentapp.data.*
import com.example.studentapp.ui.home.TAG
import com.example.studentapp.ui.profile.ProfileViewModel
import com.example.studentapp.ui.profile.UserProjectsState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class MessageViewModel(
    private val messageRepository: MessRepository,
    private val userAuthRepository: AuthRepository,
    private val projectItemsRepository: ItemsRepository,
    private val teamItemsRepository: TeamRepository
) : ViewModel() {
    // технология называется backing property (редачить можем только из этого класса)
    private val _uiState = MutableStateFlow(MessageUiState())
    val uiState: StateFlow<MessageUiState> = _uiState
    var messageList: StateFlow<MessageListState> = MutableStateFlow(MessageListState())

    fun updateDropdownList(teamId: String) {
        uiState.value.currentChats[uiState.value.currentUser]?.remove(teamId)
    }

    fun addMemberAndSubordinateProject(currentUserId: String, teamId: String) =
        viewModelScope.launch {
            val projectId = teamItemsRepository.getTeamById(teamId).project
            userAuthRepository.addSubordinateProject(userId = currentUserId, projectId = projectId)
            projectItemsRepository.addMemberInProject(projectId = projectId, userId = currentUserId)
            teamItemsRepository.deleteTeamById(teamId = teamId)
            userAuthRepository.deleteDialog(teamId = teamId, userId = currentUserId)
        }

    fun setChatsList() = viewModelScope.launch {
        _uiState.update {
            it.copy(currentChats = userAuthRepository.getMessages(UserAuthRepository.getUserId()))
        }
    }

    fun setCurrentMessages(userId: String) {
        messageList = messageRepository.fillMessages(userId)
            .map { MessageListState(it) }
            .stateIn(
                scope = viewModelScope,
                SharingStarted.WhileSubscribed(ProfileViewModel.TIMEOUT_MILLIS),
                initialValue = MessageListState()
            )
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentUserId = userId,
                    currentUser = userAuthRepository.getUserById(userId)
                )
            }
        }
    }

    private fun getCurrentTimeFormatted(): String {
        val currentTime = Timestamp.now().toDate()
        val format = SimpleDateFormat("dd.MM HH:mm:ss", Locale.getDefault())
        return format.format(currentTime)
    }

    fun onClickSendButton(text: String) {
        if (text.isNotBlank()) {
            messageRepository.addMessage(
                text = text,
                send = UserAuthRepository.getUserId(),
                receive = uiState.value.currentUserId,
                time = getCurrentTimeFormatted()
            )
        }
    }

    fun updateCurrentPage() {
        _uiState.update { it.copy(isShowingHomepage = !uiState.value.isShowingHomepage) }
    }
}

data class MessageListState(val messages: List<Message> = listOf())