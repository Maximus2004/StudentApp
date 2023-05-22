package com.example.studentapp.data

import com.example.studentapp.R

data class Project(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val members: HashMap<String, Boolean> = hashMapOf("" to true),
    val photos: List<String> = listOf()
)

data class Message(
    val id: String = "",
    val send: String = "",
    val text: String = "",
    val receive: String = "",
    val time: String = ""
)

data class User(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val description: String = "",
    val leaderProjects: HashMap<String, Boolean> = hashMapOf(),
    val subordinateProjects: HashMap<String, Boolean> = hashMapOf(),
    val avatar: String = "",
    val portfolio: List<String> = listOf(),
    val message: HashMap<String, HashMap<String, String>> = hashMapOf(),
    val rating: Int = 0
)

data class Team(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val project: String = "",
    val publishDate: String = "",
    val leaderName: String = "",
    val leaderId: String = "",
    val tags: List<String> = listOf(),
    val members: Int = 0,
    val leaderAvatar: String = ""
)

data class Feedback(
    val id: String = "",
    val user: String = "",
    val text: String = "",
    val rate: Int = 0,
    val project: String = ""
)

val photos = listOf(
    R.drawable.portfolio_example,
    R.drawable.portfolio_example2,
    R.drawable.portfolio_example3,
    R.drawable.portfolio_example4,
    R.drawable.portfolio_example,
    R.drawable.portfolio_example2
)

val projects = listOf(
    Project(
        id = "0",
        name = "Android-приложение для организации мероприятий",
        description = "Мы создаём приложение, которое поможет людям в организации самых различных мероприятий, начиная от гей-парадов и заканчивая научными конференциями. Собираем команду инициативных и обучаемый ребят, которые будут готовы активно работать над общей идеей.",
        members = hashMapOf("0" to true, "1" to false)
    ),
    Project(
        id = "0",
        name = "Backend-приложение для геолокации автомобилей",
        description = "Продукт, который должен будет изменить мир, позволить человечеству сделать ещё один шаг навстречу летающим машинам. Мы получим кучу наград и взорвём этот мир (разработкой имеется в виду).",
        members = hashMapOf("1" to true, "0" to false)
    ),
    Project(
        id = "0",
        name = "Команда для хакатона",
        description = "Собираю команду для участия в хакатоне от Силиконовой долины. Задания будет принимать лично Билл Гейтс и Стив Джобс (да, воскреснет, чтобы поучаствовать в хакатоне)",
        members = hashMapOf("0" to true, "2" to false)
    )
)

val users = listOf(
    User(
        id = "0",
        name = "Максим",
        surname = "Дмитриев",
        description = "Привет, меня зовут Максим, я из Москвы. Опыт работы в районе 4 лет. Буду рад сотрудничесту с вами, всегда вовремя выполняю работу, очень отвественный и вообще я молодец",
        leaderProjects = hashMapOf("0" to true, "2" to false),
        subordinateProjects = hashMapOf("1" to true),
        avatar = "",
        portfolio = listOf()
    ),
    User(
        id = "1",
        name = "Джастин",
        surname = "Вайер",
        description = "Я занимаюсь UI/UX на протяжении нескольких месяцев. Ищу здесь команду для усовершенствования своего скилла. Буду рад любой работе",
        leaderProjects = hashMapOf("0" to true, "2" to false),
        subordinateProjects = hashMapOf("1" to true),
        avatar = "",
        portfolio = listOf()
    ),
    User(
        id = "2",
        name = "Майкл",
        surname = "Джэксон",
        description = "Я прекрасно пою, ищу команду для создания собственной студии. Мы взорвём стадионы и покорим сердца миллионов слушателей",
        leaderProjects = hashMapOf("0" to true, "2" to false),
        subordinateProjects = hashMapOf("1" to true),
        avatar = "",
        portfolio = listOf()
    )
)

val teams = listOf(
    Team(
        id = "0",
        name = "Backend-разработчик",
        description = "В хорошую команду ищём хорошего, инициативного, работоспособного разработчика",
        project = "1",
        publishDate = "23.04.2023",
        tags = listOf("BACKEND", "DJANGO", "REACT")
    ),
    Team(
        id = "1",
        name = "Android-разработчик",
        description = "Требуется android-разработчик, отлично знающий Jetpack Compose. Умение писать многопоточные программы также обязательно.",
        project = "0",
        publishDate = "22.04.2023",
        tags = listOf("ANDROID", "COMPOSE", "MVVM", "DEPENDENCY INJECTION")
    )
)

// для навигации
val navigationItemContentList = listOf(
    NavigationItemContent(pageType = PageType.Search, icon = R.drawable.search_icon),
    NavigationItemContent(pageType = PageType.Message, icon = R.drawable.message_icon),
    NavigationItemContent(pageType = PageType.Profile, icon = R.drawable.profile_icon)
)