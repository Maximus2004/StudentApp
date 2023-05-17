package com.example.studentapp.ui.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentapp.data.*
import com.example.studentapp.ui.profile.ProfileViewModel
import com.example.studentapp.ui.profile.UserProjectsState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class MessageViewModel(
    private val messageRepository: MessRepository,
    private val userAuthRepository: AuthRepository
) : ViewModel() {
    // технология называется backing property (редачить можем только из этого класса)
    private val _uiState = MutableStateFlow(MessageUiState())
    val uiState: StateFlow<MessageUiState> = _uiState
    var messageList: StateFlow<MessageListState> = messageRepository.fillMessages(uiState.value.currentUserId)
        .map { MessageListState(it) }
        .stateIn(
            scope = viewModelScope,
            SharingStarted.WhileSubscribed(ProfileViewModel.TIMEOUT_MILLIS),
            initialValue = MessageListState()
        )

    fun setChatsList() = viewModelScope.launch {
        _uiState.update {
            it.copy(
                currentChats = userAuthRepository.getUsersList(userAuthRepository.getUserById(UserAuthRepository.getUserId()).message)
            )
        }
    }

    fun setCurrentMessages(userId: String) {
        _uiState.update { it.copy(currentUserId = userId) }
    }

    private fun getCurrentTimeFormatted(): String {
        val currentTime = Timestamp.now().toDate()
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        return format.format(currentTime)
    }

    fun onClickSendButton(text: String) {
        messageRepository.addMessage(
            text = text,
            send = UserAuthRepository.getUserId(),
            receive = uiState.value.currentUserId,
            time = getCurrentTimeFormatted()
        )
    }

    fun updateCurrentPage() {
        _uiState.update { it.copy(isShowingHomepage = !uiState.value.isShowingHomepage) }
    }
}

data class MessageListState(val messages: List<Message> = listOf())