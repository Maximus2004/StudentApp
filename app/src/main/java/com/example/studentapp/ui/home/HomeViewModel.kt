package com.example.studentapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.data.PageType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel() : ViewModel() {
    // технология называется backing property (редачить можем только из этого класса)
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        _uiState.value = HomeUiState()
    }

    fun updateCurrentPage(pageType: PageType) {
        _uiState.update { it.copy(currentPage = pageType) }
    }
}