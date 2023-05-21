package com.example.studentapp.ui.message

import com.example.studentapp.data.Message
import com.example.studentapp.data.User

data class MessageUiState(
    val isShowingHomepage: Boolean = true,
    val currentChats: List<User> = listOf(),
    val currentUserId: String = "",
    val currentUser: User = User()
)