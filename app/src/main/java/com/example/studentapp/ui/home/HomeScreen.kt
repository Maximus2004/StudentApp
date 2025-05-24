package com.example.studentapp.ui.home

import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.studentapp.data.PageType
import com.example.studentapp.data.navigationItemContentList
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studentapp.data.UserResponse
import com.example.studentapp.ui.BottomNavigationBar
import com.example.studentapp.ui.ViewModelProvider
import com.example.studentapp.ui.message.MessageViewModel
import com.example.studentapp.ui.navigation.NavGraphMessage
import com.example.studentapp.ui.navigation.NavGraphProfile
import com.example.studentapp.ui.navigation.NavGraphSearch

@Composable
fun HomeScreen(
    user: UserResponse,
    login: String,
    isKeyboardOpen: Boolean = false,
    onClickLogout: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(factory = ViewModelProvider.Factory),
    messageViewModel: MessageViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val homeUiState = homeViewModel.uiState.collectAsState().value
    val messageUiState = messageViewModel.uiState.collectAsState().value
    val messageListState = messageViewModel.messageList.collectAsState().value
    Scaffold(
        bottomBar = {
            if (messageUiState.isShowingHomepage)
                BottomNavigationBar(
                    currentTab = homeUiState.currentPage,
                    onTabPressed = {
                        homeViewModel.updateCurrentPage(pageType = it)
                    },
                    navigationItemContentList = navigationItemContentList
                )
        }
    ) { contentPadding ->
        when (homeUiState.currentPage) {
            PageType.Profile -> NavGraphProfile(
                contentPadding = contentPadding,
                login = login,
                isKeyboardOpen = isKeyboardOpen,
                user = user,
                onClickLogout = onClickLogout
            )
            PageType.Search -> NavGraphSearch(
                contentPadding = contentPadding,
                login = login
            )
            PageType.Message -> NavGraphMessage(
                contentPadding = contentPadding,
                updateMessagesList = { messageViewModel.updateMessagesList(login) },
                messageList = messageListState.messages,
                isShowingHomepage = messageUiState.isShowingHomepage,
                onDialogClick = { connectId ->
                    messageViewModel.setCurrentConnect(connectId)
                },
                currentConnect = messageUiState.currentConnect,
                onNavigateBack = { messageViewModel.updateCurrentPage() }
            )
        }
    }
}