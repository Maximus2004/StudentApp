package com.example.studentapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.example.studentapp.data.Message
import com.example.studentapp.data.User
import com.example.studentapp.ui.message.ChattingScreen
import com.example.studentapp.ui.message.MessagesScreen

@Composable
fun NavGraphMessage(
    contentPadding: PaddingValues,
    onDialogClick: (String) -> Unit,
    isShowingHomepage: Boolean,
    onNavigateBack: () -> Unit,
    chatList: List<User>,
    messageList: List<Message>,
    currentUserId: String,
    name: String,
    surname: String
) {
    if (isShowingHomepage) {
        MessagesScreen(
            contentPadding = contentPadding,
            onDialogClick = { onDialogClick(it) },
            messageList = chatList
        )
    } else {
        ChattingScreen(
            currentUserId = currentUserId,
            onNavigateBack = { onNavigateBack() },
            contentPadding = contentPadding,
            chatList = messageList,
            currentUserName = name,
            currentUserSurname = surname
        )
    }
}