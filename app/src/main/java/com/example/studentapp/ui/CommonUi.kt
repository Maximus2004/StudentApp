package com.example.studentapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentapp.data.JobResponse
import com.example.studentapp.data.NavigationItemContent
import com.example.studentapp.data.PageType
import com.example.studentapp.ui.theme.Red

@Composable
fun TopBar(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
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
        Text(
            text = "Назад",
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = Color(0xFF99879D)
            )
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
fun TeamCard(
    job: JobResponse,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Text(text = "Опубликовано ${job.publishDate}", style = MaterialTheme.typography.subtitle2)
            Text(
                text = job.name,
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
                text = job.description,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                lineHeight = 25.sp
            )
        }
    }
}

@Composable
fun ProjectCard(
    name: String,
    projectId: String,
    onClickProject: (String) -> Unit,
    isLeader: Boolean = false,
    isActive: Boolean = true
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClickProject(projectId) },
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 25.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 25.dp, bottom = 25.dp)
                    .weight(0.90f)
            ) {
                Text(
                    text = name,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = Color(0xFF120E21)
                    ),
                    modifier = Modifier.padding(bottom = 9.dp)
                )
                Text(
                    text = if (isLeader) "Руководитель" else "Участник",
                    style = MaterialTheme.typography.h4
                )
            }
            if (isActive) {
                Row(Modifier.weight(0.10f)) {
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Filled.Circle,
                        contentDescription = null,
                        tint = Color(0xFF25C92C),
                        modifier = Modifier
                            .size(15.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TextInput(
    onDataChanged: (String) -> Unit,
    currentValue: String,
    inputHint: String,
    focusRequester: FocusRequester,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    keyboardTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    TextField(
        value = currentValue,
        onValueChange = { onDataChanged(it) },
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 25.dp)
            .focusRequester(focusRequester),
        label = { Text(text = inputHint) },
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.secondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = keyboardTransformation,
        keyboardActions = keyboardActions,
        isError = isError
    )
}