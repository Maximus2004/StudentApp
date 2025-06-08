package com.example.studentapp.ui.search

import com.example.studentapp.data.JobResponse
import com.example.studentapp.data.ProjectResponse

data class SearchUiState(
    val searchText: String = "",
    val currentJob: JobResponse? = null,
    val currentProject: ProjectResponse? = null,
)