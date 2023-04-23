package com.example.studentapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.R
import com.example.studentapp.data.NavigationItemContent
import com.example.studentapp.data.PageType
import com.example.studentapp.ui.theme.Red

@Composable
fun TopBar(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(top = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(21.dp),
                tint = Color(0xFF99879D)
            )
        }
        Text(text = "Назад", style = MaterialTheme.typography.caption)
    }
}

@Composable
fun MemberCard(member: Member, onProfileClick: () -> Unit) {
    val role = if (member.role) "Руководитель" else "Участник"
    val color = if (member.role) Color(0xFFFFFFFF) else MaterialTheme.colors.background
    Row(
        modifier = Modifier
            .background(color)
            .fillMaxWidth()
            .height(103.dp)
            .clickable { onProfileClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = member.avatar),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(55.dp)
        )
        Column() {
            Text(text = member.name, style = MaterialTheme.typography.h5)
            Text(text = role, style = MaterialTheme.typography.subtitle2)
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.vector),
            contentDescription = null,
            modifier = Modifier.padding(end = 24.dp)
        )
    }
}

@Composable
fun BottomNavigationBar(
    currentTab: PageType,
    onTabPressed: ((PageType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(63.dp),
        containerColor = Color(0xFFFFFFFF),
        tonalElevation = 10.dp
    ) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == navItem.pageType,
                onClick = { onTabPressed(navItem.pageType) },
                icon = {
                    Image(
                        painterResource(id = navItem.icon),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                },
                alwaysShowLabel = false,
            )
        }
    }
}

@Composable
fun TeamCard(team: Team, modifier: Modifier = Modifier, onItemClick: () -> Unit) {
    Card(shape = RoundedCornerShape(8.dp), elevation = 8.dp, modifier = modifier.clickable { onItemClick() }) {
        Column() {
            Row(
                modifier = Modifier
                    .background(Color(0xFFEFEDF0))
                    .height(74.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painterResource(id = R.drawable.avatar2),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 16.dp)
                        .size(29.dp),
                )
                Text(
                    text = "Дмитриев Максим",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = Red,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF120E21)
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                Text(text = "Опубликовано 3 дня назад", style = MaterialTheme.typography.subtitle2)
                Text(
                    text = team.name,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                Text(
                    text = "Описание",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = Red,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF120E21)
                    )
                )
                Text(
                    text = team.description,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(top = 8.dp),
                    lineHeight = 25.sp
                )
                Text(
                    text = "3 участников",
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(vertical = 17.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    items(listOf("UX/UI", "DESIGN", "FIGMA", "PHOTOSHOP")) { tag ->
                        TagItem(tag)
                    }
                }
            }
        }
    }
}