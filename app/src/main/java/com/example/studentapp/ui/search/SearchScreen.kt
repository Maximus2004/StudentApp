package com.example.studentapp.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentapp.R
import com.example.studentapp.ui.theme.StudentAppTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.studentapp.data.*
import com.example.studentapp.ui.TeamCard
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.Red

object SearchScreen : NavigationDestination {
    override val route: String = "SearchScreen"
}

@Composable
fun SearchScreen(
    onItemClick: (Int) -> Unit,
    getLeaderById: (Int) -> User,
    getProjectById: (Int) -> Project,
    contentPadding: PaddingValues = PaddingValues(),
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding() + 15.dp)
    ) {
        item {
            SearchCard()
        }
        items(teams) { team ->
            TeamCard(
                team = team,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 10.dp),
                onItemClick = { onItemClick(team.id) },
                leader = getLeaderById(team.leader),
                membersNumber = getProjectById(team.project).members.size
            )
        }
    }
}

@Composable
fun SearchCard() {
    Text(
        text = "Поиск",
        style = MaterialTheme.typography.overline,
        modifier = Modifier.padding(top = 56.dp, start = 16.dp, bottom = 24.dp)
    )
    SearchField(onClickSearchButton = {})
    Row(
        modifier = Modifier
            .padding(start = 24.dp, top = 33.dp, bottom = 32.dp)
            .clickable { }
    ) {
        Image(
            painterResource(id = R.drawable.filters),
            contentDescription = null,
            modifier = Modifier
                .height(14.dp)
                .width(22.dp)
        )
        Text(
            text = "Фильтры",
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.Top),
            style = MaterialTheme.typography.h4
        )
    }
}

@Composable
fun TagItem(text: String) {
    Card(backgroundColor = Color(0xFFC5BAC8), shape = RoundedCornerShape(4.dp)) {
        Row(
            modifier = Modifier
                .padding(2.dp)
                .background(Color(0xFFFFFFFF)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun SearchField(onClickSearchButton: () -> Unit) {
    var search by remember { mutableStateOf("") }
    Surface(
        elevation = 8.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        TextField(
            singleLine = true,
            value = search,
            onValueChange = { search = it },
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(23.dp),
                        tint = Color(0xFF99879D)
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onClickSearchButton() }),
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = Red,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF120E21)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    StudentAppTheme {
        SearchScreen(onItemClick = {}, getLeaderById = { users[0] }, getProjectById = { projects[0] })
    }
}