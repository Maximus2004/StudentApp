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
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
    project: Project,
    feedback: Feedback
) {
    val state = rememberPagerState()
    Scaffold(topBar = { TopBar(onNavigateBack = { onNavigateBack() }) }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding())
        ) {
            item {
                Column() {
                    HorizontalPager(
                        state = state,
                        count = project.photos.size,
                        modifier = Modifier.wrapContentSize(),
                    ) { page ->
                        Card(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(16.dp),
                            elevation = 8.dp
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(project.photos[page])
                                    .crossfade(true)
                                    .build(),
                                contentScale = ContentScale.FillWidth,
                                contentDescription = null,
                                placeholder = rememberVectorPainter(image = Icons.Filled.Autorenew)
                            )
                        }
                    }
                    DotsIndicator(totalDots = state.pageCount, selectedIndex = state.currentPage)
                }
            }
            item {
                Card(
                    elevation = 50.dp,
                    modifier = Modifier.padding(top = 16.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column() {
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
                            modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp),
                            lineHeight = 27.sp
                        )
                        Divider(
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 21.dp)
                        )
                        if (feedback.text.isNotBlank()) FeedBackCard(feedback)
                    }
                }
            }
        }
    }
}

@Composable
fun FeedBackCard(feedback: Feedback) {
    Column(modifier = Modifier.padding(horizontal = 21.dp).padding(bottom = 20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Отзыв от руководителя", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Участник",
                modifier = Modifier,
                style = MaterialTheme.typography.subtitle2
            )
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
        StarRatingInfo(rating = feedback.rate)
    }
}

@Composable
fun StarRatingInfo(modifier: Modifier = Modifier, rating: Int) {
    val maxRating = 5
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        for (rate in 1..maxRating) {
            val isSelected = rate <= rating
            val starIcon = if (isSelected) Icons.Default.Star else Icons.Default.StarBorder
            val tint = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
            Icon(
                imageVector = starIcon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(20.dp)
            )
        }
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
        DetailProjectScreen(onNavigateBack = {}, project = projects[0], feedback = Feedback())
    }
}