package com.example.studentapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.example.studentapp.data.ConnectResponse
import com.example.studentapp.ui.message.ChattingScreen
import com.example.studentapp.ui.message.MessagesScreen

@Composable
fun NavGraphMessage(
    contentPadding: PaddingValues,
    onDialogClick: (String) -> Unit,
    messageList: List<ConnectResponse>,
    updateMessagesList: () -> Unit,
    isShowingHomepage: Boolean,
    currentConnect: ConnectResponse?,
    onNavigateBack: () -> Unit
) {
    if (isShowingHomepage) {
        MessagesScreen(
            contentPadding = contentPadding,
            onDialogClick = { onDialogClick(it) },
            updateMessagesList = updateMessagesList,
            messageList = messageList
        )
    } else {
        ChattingScreen(
            onNavigateBack = { onNavigateBack() },
            currentConnect = currentConnect
        )
    }
}