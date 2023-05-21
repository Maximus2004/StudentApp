package com.example.studentapp.ui.search

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.data.*
import com.example.studentapp.ui.TeamCard
import com.example.studentapp.ui.TopBar
import com.example.studentapp.ui.navigation.NavigationDestination
import com.example.studentapp.ui.theme.Red
import com.example.studentapp.ui.theme.StudentAppTheme

object DetailTeammateScreen : NavigationDestination {
    override val route: String = "DetailTeammateScreen"
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailTeammateScreen(
    team: Team,
    onClickShowProject: (String) -> Unit,
    onClickReply: () -> Unit,
    onNavigateBack: () -> Unit,
    members: Int,
    currentUser: String,
    contentPadding: PaddingValues = PaddingValues()
) {
    Scaffold(
        topBar = { TopBar(onNavigateBack = { onNavigateBack() }) },
        modifier = Modifier.fillMaxSize()
    ) {
        Box() {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                contentPadding = PaddingValues(bottom = contentPadding.calculateBottomPadding() + 77.dp)
            ) {
                item {
                    TeamCard(
                        team = team,
                        modifier = Modifier
                            .padding(bottom = 8.dp, top = 18.dp)
                            .padding(horizontal = 10.dp),
                        leaderName = team.name,
                        leaderAvatar = team.leaderAvatar,
                        members = members
                    )
                    Card(
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { onClickShowProject(team.project) },
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(26.dp)
                        ) {
                            Text(
                                text = "Посмотреть проект",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = Red,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF120E21)
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            if (team.leaderId != currentUser) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    ExtendedFloatingActionButton(
                        text = {
                            Text(
                                text = "Откликнуться",
                                style = MaterialTheme.typography.button
                            )
                        },
                        onClick = { onClickReply() },
                        backgroundColor = Color(0xFF9378FF),
                        modifier = Modifier
                            .padding(bottom = 15.dp + contentPadding.calculateBottomPadding())
                            .height(54.dp)
                            .width(263.dp),
                    )
                }
            }
        }
    }
}