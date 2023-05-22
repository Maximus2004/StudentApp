package com.example.studentapp.ui.profile

import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.R
import com.example.studentapp.data.photos
import com.example.studentapp.ui.theme.Red
import com.example.studentapp.ui.theme.StudentAppTheme
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Logout
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.studentapp.data.User
import com.example.studentapp.data.users
import com.example.studentapp.ui.StarRatingInfo
import com.example.studentapp.ui.navigation.NavigationDestination

object ProfileScreen : NavigationDestination {
    override val route: String = "ProfileScreen"
}

@Composable
fun ProfileScreen(
    isAlienProfile: Boolean,
    onClickShowProjects: () -> Unit,
    onClickCreateTeam: () -> Unit,
    user: User,
    textLastProject: String,
    contentPadding: PaddingValues = PaddingValues(),
    numberOfProjects: Int,
    rating: Int,
    onClickLogout: () -> Unit
) {
    Box() {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 140.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 18.dp),
            contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding() + 77.dp)
        ) {
            header {
                Column() {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 56.dp)) {
                        Text(text = "Профиль", style = MaterialTheme.typography.overline)
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = { onClickLogout() },
                            modifier = Modifier.padding(top = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                    UserCard(
                        name = user.name + " " + user.surname,
                        modifier = Modifier.padding(vertical = 25.dp),
                        avatar = user.avatar
                    )
                    Text(text = "Описание", style = MaterialTheme.typography.h5)
                    Text(
                        text = user.description,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color(0xFF99879D),
                            lineHeight = 23.sp
                        ),
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    InfoCard(
                        modifier = Modifier.padding(top = 19.dp),
                        onClickShowProjects = onClickShowProjects,
                        numberOfProjects = numberOfProjects,
                        textLastProject = textLastProject,
                        isAlienProfile = isAlienProfile,
                        rating = rating
                    )
                    Text(text = "Портфолио", style = MaterialTheme.typography.h5)
                }
            }
            items(user.portfolio) { photo ->
                PhotoItem(photo, modifier = Modifier.aspectRatio(1.5f))
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = "Создать вакансию",
                        style = MaterialTheme.typography.button
                    )
                },
                onClick = { onClickCreateTeam() },
                backgroundColor = Color(0xFF9378FF),
                modifier = Modifier
                    .padding(bottom = 15.dp + contentPadding.calculateBottomPadding())
                    .height(54.dp)
                    .width(263.dp),
            )
        }
    }
}

fun LazyGridScope.header(content: @Composable LazyGridItemScope.() -> Unit) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}

@Composable
fun PhotoItem(photo: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, elevation = 10.dp, shape = RoundedCornerShape(10.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Uri.parse(photo))
                .crossfade(true)
                .build(),
            contentScale = ContentScale.FillWidth,
            placeholder = rememberVectorPainter(image = Icons.Filled.Autorenew),
            contentDescription = null,
            modifier = Modifier.clip(RectangleShape),
        )
    }
}

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    onClickShowProjects: () -> Unit,
    numberOfProjects: Int = 0,
    textLastProject: String,
    isAlienProfile: Boolean,
    rating: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(Modifier.weight(0.62f)) {
            Text(
                text = "$numberOfProjects проекта",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Red,
                    color = Color(0xFF120E21)
                )
            )
            Text(
                text = "Последний проект:",
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = textLastProject,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color(0xFF120E21),
                    fontFamily = Red
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(top = 5.dp)
                .weight(0.38f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Средняя оценка",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 4.dp)
            )
            StarRatingInfo(rating = rating)
            if (isAlienProfile) {
                Button(
                    onClick = { onClickShowProjects() },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD8CEFF)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 14.dp)
                        .padding(horizontal = 5.dp)
                ) {
                    Text(
                        text = "Смотреть",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = Color(0xFF120E21),
                            fontFamily = Red
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun UserCard(modifier: Modifier = Modifier, name: String, avatar: String) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Uri.parse(avatar))
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(64.dp)
                .clip(CircleShape)
        )
        Text(text = name, style = MaterialTheme.typography.h3)
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = false)
fun ProfileScreenPreview() {
    StudentAppTheme {
        ProfileScreen(
            onClickShowProjects = {},
            onClickCreateTeam = {},
            user = users[0],
            textLastProject = "Android-приложение для организаци",
            isAlienProfile = false,
            numberOfProjects = 0,
            rating = 0,
            onClickLogout = {}
        )
    }
}