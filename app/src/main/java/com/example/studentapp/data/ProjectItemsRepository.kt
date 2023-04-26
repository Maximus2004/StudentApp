package com.example.studentapp.data

import android.util.Log
import com.example.studentapp.ui.home.TAG

interface ItemsRepository {
    fun getLeaderProjectList(userId: Int): List<Project>
    fun getSubordinateProjectList(userId: Int): List<Project>
    fun getProjectById(projectId: Int): Project
    fun getUserById(userId: Int): User
}

class ProjectItemsRepository : ItemsRepository {
    override fun getLeaderProjectList(userId: Int): List<Project> {
        val leaderProjects: MutableList<Project> = mutableListOf()
        for (project in users[userId].leaderProjects) {
            Log.d(TAG, project.toString())
            leaderProjects.add(projects[project])
        }
        return leaderProjects
    }

    override fun getSubordinateProjectList(userId: Int): List<Project> {
        val subordinateProjects: MutableList<Project> = mutableListOf()
        for (project in users[userId].subordinateProjects) {
            subordinateProjects.add(projects[project])
        }
        return subordinateProjects
    }

    override fun getProjectById(projectId: Int): Project {
        return projects[projectId]
    }

    override fun getUserById(userId: Int): User {
        return users[userId]
    }
}