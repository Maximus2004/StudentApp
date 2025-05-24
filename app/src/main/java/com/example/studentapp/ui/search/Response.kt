package com.example.studentapp.ui.search

import com.example.studentapp.data.JobResponse

sealed interface Response {
    data class Error(val error: String): Response
    object Loading: Response
    data class Success(val teamsList: List<JobResponse>): Response
}
