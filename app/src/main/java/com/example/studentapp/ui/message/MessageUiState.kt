package com.example.studentapp.ui.message

import com.example.studentapp.data.ConnectResponse

data class MessageUiState(
    val isShowingHomepage: Boolean = true,
    val currentConnect: ConnectResponse? = null
)

data class MessageListState(val messages: List<ConnectResponse> = listOf())
