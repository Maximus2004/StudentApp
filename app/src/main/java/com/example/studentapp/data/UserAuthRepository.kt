package com.example.studentapp.data

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.studentapp.R
import com.example.studentapp.ui.home.TAG
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

const val USERS_COLLECTION_REF = "users"

interface AuthRepository {
    suspend fun login(email: String, password: String, onComplete:(Boolean) -> Unit)
    suspend fun signup(email: String, password: String, onComplete:(Boolean) -> Unit)
    fun createNewUser(name: String, surname: String, description: String)
    fun addLeaderProject(projectId: String)
    fun addSubordinateProject(projectId: String)
    fun fillProjects(onSuccess: (MutableList<String>, MutableList<String>) -> Unit)
    suspend fun getUserById(userId: String): User
    suspend fun getUsersList(users: List<String>): List<User>
}

class UserAuthRepository(val auth: FirebaseAuth) : AuthRepository {
    private val usersRef: CollectionReference = Firebase.firestore.collection(USERS_COLLECTION_REF)
    companion object {
        fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()
    }

    override fun fillProjects(onSuccess: (MutableList<String>, MutableList<String>) -> Unit) {
        usersRef.document(getUserId()).get()
            .addOnSuccessListener {
                onSuccess.invoke(
                    it?.toObject(User::class.java)?.leaderProjects ?: mutableListOf(),
                    it?.toObject(User::class.java)?.subordinateProjects ?: mutableListOf()
                )
            }
    }

    override suspend fun getUserById(userId: String): User {
        var user = User()
        usersRef.document(getUserId()).get()
            .addOnSuccessListener {
                user = it?.toObject(User::class.java) ?: User()
            }.await()
        return user
    }

    override suspend fun getUsersList(users: List<String>): List<User> {
        val temp: MutableList<User> = mutableListOf()
        for (a in users) {
            temp.add(getUserById(a))
        }
        return temp
    }

    override fun createNewUser(name: String, surname: String, description: String) {
        val user = User(
            id = getUserId(),
            name = name,
            surname = surname,
            description = description,
            avatar = R.drawable.avatar,
            leaderProjects = mutableListOf(),
            subordinateProjects = mutableListOf(),
        )
        usersRef.document(getUserId()).set(user)
    }

    override fun addLeaderProject(projectId: String) {
        usersRef.document(getUserId()).get().addOnSuccessListener {
            val newLeaderProjects = it?.toObject(User::class.java)?.leaderProjects
            newLeaderProjects?.add(projectId)
            usersRef.document(getUserId()).update("leaderProjects", newLeaderProjects)
        }
    }

    override fun addSubordinateProject(projectId: String) {
        usersRef.document(getUserId()).get().addOnSuccessListener {
            val newSubordinateProjects = it?.toObject(User::class.java)?.subordinateProjects
            newSubordinateProjects?.add(projectId)
            usersRef.document(getUserId()).update("subordinateProjects", newSubordinateProjects )
        }
    }

    override suspend fun login(email: String, password: String, onComplete: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }

    override suspend fun signup(email: String, password: String, onComplete:(Boolean) -> Unit) {
        if (password.length < 6) {
            onComplete.invoke(false)
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onComplete.invoke(true)
                    } else {
                        onComplete.invoke(false)
                    }
                }.await()
        }
    }
}