package com.example.studentapp.ui.home

import android.os.Bundle
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.example.studentapp.data.PageType
import com.example.studentapp.data.navigationItemContentList
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studentapp.ui.BottomNavigationBar
import com.example.studentapp.ui.message.MessageViewModel
import com.example.studentapp.ui.message.SendMessage
import com.example.studentapp.ui.navigation.NavGraphMessage
import com.example.studentapp.ui.navigation.NavGraphProfile
import com.example.studentapp.ui.navigation.NavGraphSearch
import com.example.studentapp.ui.navigation.NavigationDestination

object HomeScreen : NavigationDestination {
    override val route: String = "HomeScreen"
}

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    messageViewModel: MessageViewModel = viewModel()
) {
    val homeUiState = homeViewModel.uiState.collectAsState().value
    val messageUiState = messageViewModel.uiState.collectAsState().value
    val navStateSearch = remember { mutableStateOf(Bundle()) }
    val navStateProfile = remember { mutableStateOf(Bundle()) }
    Scaffold(
        bottomBar = {
            if (messageUiState.isShowingHomepage) {
                BottomNavigationBar(
                    currentTab = homeUiState.currentPage,
                    onTabPressed = { homeViewModel.updateCurrentPage(pageType = it) },
                    navigationItemContentList = navigationItemContentList
                )
            } else {
                SendMessage(onClickSendButton = {})
            }
        }
    ) { contentPadding ->
        when (homeUiState.currentPage) {
            PageType.Profile -> NavGraphProfile(
                navState = navStateProfile,
                contentPadding = contentPadding
            )
            PageType.Search -> NavGraphSearch(
                navState = navStateSearch,
                contentPadding = contentPadding
            )
            PageType.Message -> NavGraphMessage(
                contentPadding = contentPadding,
                onDialogClick = { messageViewModel.updateCurrentPage() },
                isShowingHomepage = messageUiState.isShowingHomepage,
                onNavigateBack = { messageViewModel.updateCurrentPage() }
            )
        }
    }
}