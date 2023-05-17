package com.example.studentapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.example.studentapp.data.PageType
import com.example.studentapp.data.navigationItemContentList
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studentapp.data.User
import com.example.studentapp.data.UserAuthRepository
import com.example.studentapp.ui.BottomNavigationBar
import com.example.studentapp.ui.ViewModelProvider
import com.example.studentapp.ui.message.MessageViewModel
import com.example.studentapp.ui.message.SendMessage
import com.example.studentapp.ui.navigation.NavGraphMessage
import com.example.studentapp.ui.navigation.NavGraphProfile
import com.example.studentapp.ui.navigation.NavGraphSearch
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.profile.ProfileViewModel

const val TAG = "QWERTY"

@Composable
fun HomeScreen(
    user: User,
    userId: String,
    isKeyboardOpen: Boolean = false,
    homeViewModel: HomeViewModel = viewModel(factory = ViewModelProvider.Factory),
    messageViewModel: MessageViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val homeUiState = homeViewModel.uiState.collectAsState().value
    val messageUiState = messageViewModel.uiState.collectAsState().value
    val chatListState = messageViewModel.messageList.collectAsState().value
    val navStateSearch = remember { mutableStateOf(Bundle()) }
    val navStateProfile = remember { mutableStateOf(Bundle()) }
    Scaffold(
        bottomBar = {
            if (messageUiState.isShowingHomepage) {
                BottomNavigationBar(
                    currentTab = homeUiState.currentPage,
                    onTabPressed = {
                        if (it == PageType.Message) messageViewModel.setChatsList()
                        homeViewModel.updateCurrentPage(pageType = it)
                    },
                    navigationItemContentList = navigationItemContentList
                )
            } else
                SendMessage(onClickSendButton = { messageViewModel.onClickSendButton(it) })
        }
    ) { contentPadding ->
        when (homeUiState.currentPage) {
            PageType.Profile -> NavGraphProfile(
                navState = navStateProfile,
                contentPadding = contentPadding,
                userId = userId,
                isKeyboardOpen = isKeyboardOpen,
                user = user
            )
            PageType.Search -> NavGraphSearch(
                navState = navStateSearch,
                contentPadding = contentPadding
            )
            PageType.Message -> NavGraphMessage(
                contentPadding = contentPadding,
                onDialogClick = {
                    messageViewModel.setCurrentMessages(it)
                    messageViewModel.updateCurrentPage()
                },
                isShowingHomepage = messageUiState.isShowingHomepage,
                onNavigateBack = { messageViewModel.updateCurrentPage() },
                chatList = messageUiState.currentChats,
                messageList = chatListState.messages,
                currentUserId = UserAuthRepository.getUserId(),
                name = user.name,
                surname = user.surname
            )
        }
    }
}