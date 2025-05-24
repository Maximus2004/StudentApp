package com.example.studentapp.ui.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.data.ProjectResponse
import com.example.studentapp.ui.TopBar
import com.example.studentapp.ui.navigation.NavigationDestination

object ActiveProjectScreen : NavigationDestination {
    override val route: String = "ActiveProjectScreen"
    const val projectId = "projectId"
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ActiveProjectScreen(
    contentPadding: PaddingValues = PaddingValues(),
    onNavigateBack: () -> Unit,
    project: ProjectResponse?,
) {
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = contentPadding) {
            item {
                if (project != null) {
                    Text(
                        text = "Опубликовано 17.5.2023",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp)
                    )
                    Text(
                        text = project.name,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(start = 25.dp, top = 4.dp, end = 24.dp)
                    )
                    Text(
                        text = project.description,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(
                            start = 24.dp,
                            top = 16.dp,
                            end = 24.dp,
                            bottom = 16.dp
                        ),
                        lineHeight = 27.sp
                    )
                }
            }
        }
    }
}