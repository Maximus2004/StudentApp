package com.example.studentapp.ui.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
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
import androidx.compose.ui.layout.ContentScale
import com.example.studentapp.ui.navigation.NavigationDestination

object ProfileScreen : NavigationDestination {
    override val route: String = "ProfileScreen"
}

@Composable
fun ProfileScreen(
    onClickShowProjects: () -> Unit,
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 18.dp),
        contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding() + 9.dp)
    ) {
        header {
            Column() {
                Text(
                    text = "Профиль",
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier.padding(top = 56.dp)
                )
                UserCard(
                    name = "Головач Лена",
                    position = "Дизайнер",
                    modifier = Modifier.padding(vertical = 25.dp)
                )
                Text(text = "Описание", style = MaterialTheme.typography.h5)
                Text(
                    text = "Привет, меня зовут Дастин, я из Дублина. Опыт работы в районе 4 лет. Буду рад сотрудничесту с вами, всегда вовремя выполняю работу, очень отвественный и вообще я молодец",
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
                    onClickShowProjects = onClickShowProjects
                )
                Text(text = "Портфолио", style = MaterialTheme.typography.h5)
            }
        }
        items(photos) { photo ->
            PhotoItem(photo, modifier = Modifier.aspectRatio(1.5f))
        }
    }
}


fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}

@Composable
fun PhotoItem(photo: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier, elevation = 10.dp, shape = RoundedCornerShape(10.dp)) {
        Image(
            painter = painterResource(id = photo),
            contentDescription = null,
            modifier = Modifier.padding(10.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun InfoCard(modifier: Modifier = Modifier, onClickShowProjects: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column() {
            Text(
                text = "3 проекта",
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
                text = "Android-приложение дл...",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color(0xFF120E21),
                    fontFamily = Red
                )
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(top = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Средняя оценка",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 4.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.stars),
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
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

@Composable
fun UserCard(modifier: Modifier = Modifier, name: String, position: String) {
    Row(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = name, style = MaterialTheme.typography.h3)
            Text(text = position, style = MaterialTheme.typography.h4)
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = false)
fun ProfileScreenPreview() {
    StudentAppTheme {
        ProfileScreen(onClickShowProjects = {})
    }
}