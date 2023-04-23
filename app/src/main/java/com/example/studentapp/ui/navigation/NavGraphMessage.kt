package com.example.studentapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.example.studentapp.ui.message.ChattingScreen
import com.example.studentapp.ui.message.MessagesScreen

@Composable
fun NavGraphMessage(
    contentPadding: PaddingValues,
    onDialogClick: () -> Unit,
    isShowingHomepage: Boolean,
    onNavigateBack: () -> Unit
) {
    if (isShowingHomepage) {
        MessagesScreen(contentPadding = contentPadding, onDialogClick = { onDialogClick() })
    } else {
        ChattingScreen(onNavigateBack = { onNavigateBack() }, contentPadding = contentPadding)
    }
}