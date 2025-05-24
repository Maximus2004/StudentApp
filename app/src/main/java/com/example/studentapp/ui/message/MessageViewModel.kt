package com.example.studentapp.ui.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentapp.data.*
import com.example.studentapp.ui.profile.ProfileViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MessageViewModel(
    private val connectRepository: ConnectRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MessageUiState())
    val uiState: StateFlow<MessageUiState> = _uiState
    val messageList = MutableStateFlow(MessageListState())

    fun updateMessagesList(login: String) {
        viewModelScope.launch {
            connectRepository.getConnects(login) { messages ->
                messageList.update { it.copy(messages = messages ?: listOf()) }
            }
        }
    }

    fun setCurrentConnect(connectId: String) {
        viewModelScope.launch {
            connectRepository.getConnect(connectId) { connect ->
                _uiState.value = MessageUiState(
                    isShowingHomepage = !uiState.value.isShowingHomepage,
                    currentConnect = connect
                )
            }
        }
    }

    fun updateCurrentPage() {
        _uiState.update { it.copy(isShowingHomepage = !uiState.value.isShowingHomepage) }
    }
}
