package com.example.studentapp.ui.home

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.data.PageType

data class HomeUiState(
    val currentPage: PageType = PageType.Search,
)