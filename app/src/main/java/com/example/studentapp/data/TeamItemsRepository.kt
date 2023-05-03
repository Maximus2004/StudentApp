package com.example.studentapp.data

import android.util.Log
import com.example.studentapp.ui.home.TAG

interface TeamRepository {
    fun getTeamById(teamId: Int): Team
    fun getLeaderById(leaderId: Int): User
    fun getProjectById(projectId: Int) : Project
    fun getLeaderProjectList(userId: Int): List<Project>
    fun getSubordinateProjectList(userId: Int): List<Project>
}

class TeamItemsRepository : TeamRepository {
    override fun getTeamById(teamId: Int): Team {
        return teams[teamId]
    }

    override fun getLeaderById(leaderId: Int): User {
        return users[leaderId]
    }

    override fun getProjectById(projectId: Int): Project {
        return projects[projectId]
    }

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
}