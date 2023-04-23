package com.example.studentapp.ui.message

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MessageViewModel() : ViewModel() {
    // технология называется backing property (редачить можем только из этого класса)
    private val _uiState = MutableStateFlow(MessageUiState())
    val uiState: StateFlow<MessageUiState> = _uiState

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        _uiState.value = MessageUiState()
    }

    fun updateCurrentPage() {
        _uiState.update { it.copy(isShowingHomepage = !uiState.value.isShowingHomepage) }
    }
}