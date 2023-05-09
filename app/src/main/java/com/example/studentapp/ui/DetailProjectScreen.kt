package com.example.studentapp.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.studentapp.R
import com.example.studentapp.ui.theme.StudentAppTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import com.example.studentapp.data.*
import com.example.studentapp.ui.navigation.NavigationDestination

object DetailProjectScreen : NavigationDestination {
    override val route: String = "DetailProjectScreen"
    const val projectId = "projectId"
    val routeWithArgs: String = "${route}/{$projectId}"

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailProjectScreen(
    onNavigateBack: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
    project: Project
) {
    val state = rememberPagerState()
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        Column(verticalArrangement = Arrangement.Top) {
            HorizontalPager(
                state = state, count = 3, modifier = Modifier.wrapContentSize(),
            ) { page ->
                Card(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = 8.dp
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.portfolio_example),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
            DotsIndicator(totalDots = state.pageCount, selectedIndex = state.currentPage)
            Card(
                elevation = 50.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(top = 16.dp),
                shape = MaterialTheme.shapes.large
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding() + 15.dp)) {
                    item {
                        Text(
                            text = "Опубликовано 3 дня назад",
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
                            modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp),
                            lineHeight = 27.sp
                        )
                        Divider(
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 21.dp)
                        )
                        FeedBackCard(feedbacks[0])
                    }
                }
            }
        }
    }
}

@Composable
fun FeedBackCard(feedback: FeedbackInfo) {
    Column(modifier = Modifier.padding(horizontal = 21.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = feedback.name, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.weight(1f))
            if (feedback.role) {
                Text(
                    text = "Руководитель",
                    modifier = Modifier,
                    style = MaterialTheme.typography.subtitle2
                )
            } else {
                Text(
                    text = "Участник",
                    modifier = Modifier,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
        Text(
            text = feedback.text,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Оценка",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.stars),
            contentDescription = null,
            alignment = Alignment.CenterStart,
            modifier = Modifier
                .width(100.dp)
                .height(16.dp)
        )
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center
    ) {
        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(color = Color.DarkGray)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(color = Color.LightGray)
                )
            }
            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun DetailScreenPreview() {
    StudentAppTheme {
        DetailProjectScreen(onNavigateBack = {}, project = projects[0])
    }
}