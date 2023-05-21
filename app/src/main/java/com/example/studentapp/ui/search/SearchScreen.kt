package com.example.studentapp.ui.search

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.studentapp.data.*
import com.example.studentapp.ui.TeamCard
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.Red
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object SearchScreen : NavigationDestination {
    override val route: String = "SearchScreen"
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("d.M.yyyy", Locale.getDefault())
    val date = LocalDate.parse(dateString, formatter)
    val monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.getDefault())
    val monthName = date.format(monthFormatter)
    val day = date.dayOfMonth
    return "$day $monthName"
}

@Composable
fun SearchScreen(
    onItemClick: (String) -> Unit,
    teams: List<Team>,
    contentPadding: PaddingValues = PaddingValues(),
    searchText: String,
    onChooseFilter: (Int) -> Unit,
    onSearchChanged: (String) -> Unit,
) {
    var isShowDialog by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding() + 15.dp)
    ) {
        item {
            SearchCard(onFilterClick = { isShowDialog = true }, searchText, onSearchChanged)
        }
        items(teams) { team ->
            TeamCard(
                team = team,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 10.dp)
                    .clickable { onItemClick(team.id) },
                leaderName = team.leaderName,
                leaderAvatar = team.leaderAvatar,
                members = team.members
            )
        }
    }
    if (isShowDialog) {
        AlertDialogFilter(
            onClickButton = { isShowDialog = false },
            onChooseFilter = { onChooseFilter(it) })
    }
}

@Composable
fun AlertDialogFilter(onClickButton: () -> Unit, onChooseFilter: (Int) -> Unit) {
    var firstFilter by remember { mutableStateOf(false) }
    var secondFilter by remember { mutableStateOf(false) }
    var thirdFilter by remember { mutableStateOf(false) }
    AlertDialog(
        shape = RoundedCornerShape(11.dp),
        onDismissRequest = { onClickButton() },
        title = { Text(text = "Выберите фильтр") },
        text = {
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = firstFilter,
                        onClick = {
                            onChooseFilter(1)
                            firstFilter = true
                            secondFilter = false
                            thirdFilter = false
                        }
                    )
                    Text(text = "По имени (по алфавиту)")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = secondFilter,
                        onClick = {
                            onChooseFilter(2)
                            firstFilter = false
                            secondFilter = true
                            thirdFilter = false
                        }
                    )
                    Text(text = "По дате (сначала давние)")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = thirdFilter,
                        onClick = {
                            onChooseFilter(3)
                            firstFilter = false
                            secondFilter = false
                            thirdFilter = true
                        }
                    )
                    Text(text = "По дате (сначала новые)")
                }
            }
        },
        buttons = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Button(onClick = { onClickButton() }, shape = RoundedCornerShape(10.dp)) {
                    Text("Применить")
                }
            }
        }
    )
}

@Composable
fun SearchCard(onFilterClick: () -> Unit, searchText: String, onSearchChanged: (String) -> Unit) {
    Text(
        text = "Поиск",
        style = MaterialTheme.typography.overline,
        modifier = Modifier.padding(top = 56.dp, start = 16.dp, bottom = 24.dp)
    )
    SearchField(searchText, onSearchChanged)
    Row(
        modifier = Modifier
            .padding(start = 24.dp, top = 33.dp, bottom = 32.dp)
            .clickable { onFilterClick() }
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(searchText: String, onSearchChanged: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
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
            value = searchText,
            onValueChange = { onSearchChanged(it) },
            trailingIcon = {
                IconButton(onClick = { keyboardController?.hide() }) {
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
            keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = Red,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF120E21)
            ),
            placeholder = { Text(text = "Поиск по тегам") }
        )
    }
}

@Preview
@Composable
fun SearchPreview() {
    StudentAppTheme {
        SearchField(searchText = "", onSearchChanged = {})
    }
}