package com.example.studentapp.data

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.studentapp.ui.home.TAG
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface AuthRepository {
    suspend fun login(email: String, password: String, onComplete:(Boolean) -> Unit)
    suspend fun signup(email: String, password: String, onComplete:(Boolean) -> Unit)
}

class UserAuthRepository(val auth: FirebaseAuth) : AuthRepository {
    companion object {
        fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()
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