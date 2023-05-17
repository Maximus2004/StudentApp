package com.example.studentapp.ui.search

import com.example.studentapp.data.Team

sealed interface Response {
    data class Error(val error: String): Response
    object Loading: Response
    data class Success(val teamsList: List<Team>): Response
}
