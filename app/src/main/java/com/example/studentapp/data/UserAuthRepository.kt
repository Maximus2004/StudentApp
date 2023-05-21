package com.example.studentapp.data

import android.net.Uri
import com.example.studentapp.ui.signinup.Response
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

const val USERS_COLLECTION_REF = "users"

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String,
        onComplete: (Boolean, FirebaseException) -> Unit
    )

    suspend fun signup(
        email: String,
        password: String,
        onComplete: (Boolean, FirebaseException) -> Unit
    )

    fun createNewUser(
        name: String,
        surname: String,
        description: String,
        avatar: String = "https://firebasestorage.googleapis.com/v0/b/studentapp-8b024.appspot.com/o/images%2Funknown_avatar.png?alt=media&token=de909d87-c093-49d9-ae50-406e4e256262",
        portfolio: List<String>
    )

    fun addLeaderProject(projectId: String)
    fun addSubordinateProject(projectId: String)
    fun fillProjects(): Flow<Pair<Deferred<HashMap<Project, Boolean>>, Deferred<HashMap<Project, Boolean>>>>
    suspend fun getUserById(userId: String): User
    suspend fun getUsersList(users: List<String>): List<User>
    suspend fun getListProjects(projects: HashMap<String, Boolean>): HashMap<Project, Boolean>
    suspend fun endProject(projectId: String)
    fun setImage(imageUri: Uri): Flow<Response>
    fun uploadProfilePhotos(uris: List<Uri>): Flow<Pair<String, Boolean>>
    fun addMessage(userId: String)
    fun endSubordinateProjects(projectId: String, userList: List<String>)
    fun setNewFeedback(newRate: Int, userId: String)
    fun listenerToRating(): Flow<Int>
}

class UserAuthRepository(val auth: FirebaseAuth) : AuthRepository {
    private val usersRef: CollectionReference = Firebase.firestore.collection(USERS_COLLECTION_REF)
    private val storage = Firebase.storage.reference

    companion object {
        fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()
    }

    override fun setImage(imageUri: Uri): Flow<Response> = callbackFlow {
        val imageRef = storage.child("images/${imageUri.lastPathSegment}")
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    trySend(Response.Success(uri))
                }
            } else {
                trySend(Response.Error)
            }
        }
        awaitClose {
            uploadTask.cancel()
        }
    }

    override fun uploadProfilePhotos(uris: List<Uri>): Flow<Pair<String, Boolean>> = callbackFlow {
        var uploadTask: UploadTask? = null
        for (imageUri in uris) {
            val imageRef = storage.child("images/${imageUri.lastPathSegment}")
            uploadTask = imageRef.putFile(imageUri)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        if (imageUri == uris.last()) trySend(Pair(uri.toString(), true))
                        else trySend(Pair(uri.toString(), false))
                    }
                }
            }
        }
        awaitClose {
            uploadTask?.cancel()
        }
    }

    override fun listenerToRating(): Flow<Int> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = usersRef.document(getUserId()).addSnapshotListener { snapshot, _ ->
                val response = if (snapshot != null) snapshot.get("rating") else 0
                trySend((response as Long).toInt())
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    override fun fillProjects(): Flow<Pair<Deferred<HashMap<Project, Boolean>>, Deferred<HashMap<Project, Boolean>>>> =
        callbackFlow {
            var snapshotStateListener: ListenerRegistration? = null
            try {
                snapshotStateListener =
                    usersRef.document(getUserId()).addSnapshotListener { snapshot, _ ->
                        val response = if (snapshot != null) {
                            val user = snapshot.toObject(User::class.java)
                            val leaderProjects: Deferred<HashMap<Project, Boolean>> =
                                async { getListProjects(user?.leaderProjects ?: hashMapOf()) }
                            val subordinateProjects: Deferred<HashMap<Project, Boolean>> =
                                async { getListProjects(user?.subordinateProjects ?: hashMapOf()) }
                            Pair(leaderProjects, subordinateProjects)
                        } else {
                            Pair(CompletableDeferred(), CompletableDeferred())
                        }
                        trySend(response)
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            awaitClose {
                snapshotStateListener?.remove()
            }
        }

    override fun addMessage(userId: String) {
        usersRef.document(getUserId()).update("message", FieldValue.arrayUnion(userId))
        usersRef.document(userId).update("message", FieldValue.arrayUnion(getUserId()))
    }

    override suspend fun getUserById(userId: String): User {
        if (userId.isEmpty()) return User()
        var user = User()
        usersRef.document(userId).get()
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

    override suspend fun getListProjects(projects: HashMap<String, Boolean>): HashMap<Project, Boolean> {
        val objects = hashMapOf<Project, Boolean>()
        for (project in projects.keys) {
            val snapshot = ProjectItemsRepository.projectsRef.document(project).get().await()
            val addProject = snapshot.toObject(Project::class.java) ?: Project()
            objects[addProject] = projects[project] ?: true
        }
        return objects
    }

    override fun createNewUser(name: String, surname: String, description: String, avatar: String, portfolio: List<String>) {
        val user = User(
            id = getUserId(),
            name = name,
            surname = surname,
            description = description,
            avatar = avatar,
            portfolio = portfolio
        )
        usersRef.document(getUserId()).set(user)
    }

    override fun addLeaderProject(projectId: String) {
        usersRef.document(getUserId()).get().addOnSuccessListener {
            val newLeaderProjects = it?.toObject(User::class.java)?.leaderProjects
            newLeaderProjects?.put(projectId, true)
            usersRef.document(getUserId()).update("leaderProjects", newLeaderProjects)
        }
    }

    override fun addSubordinateProject(projectId: String) {
        usersRef.document(getUserId()).get().addOnSuccessListener {
            val newSubordinateProjects = it?.toObject(User::class.java)?.subordinateProjects
            newSubordinateProjects?.put(projectId, true)
            usersRef.document(getUserId()).update("subordinateProjects", newSubordinateProjects)
        }
    }

    override suspend fun endProject(projectId: String) {
        usersRef.document(getUserId()).get().addOnSuccessListener {
            val newLeaderProjects = it?.toObject(User::class.java)?.leaderProjects
            newLeaderProjects?.put(projectId, false)
            usersRef.document(getUserId()).update("leaderProjects", newLeaderProjects)
        }
    }

    override fun endSubordinateProjects(projectId: String, userList: List<String>) {
        for (user in userList) {
            usersRef.document(user).get().addOnSuccessListener {
                val newSubordinateProjects = it?.toObject(User::class.java)?.subordinateProjects
                newSubordinateProjects?.put(projectId, false)
                usersRef.document(user).update("subordinateProjects", newSubordinateProjects)
            }
        }
    }

    override fun setNewFeedback(newRate: Int, userId: String) {
        usersRef.document(userId).update("rating", newRate)
    }

    override suspend fun login(
        email: String,
        password: String,
        onComplete: (Boolean, FirebaseException) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    onComplete.invoke(
                        true,
                        FirebaseAuthInvalidCredentialsException(
                            "ERROR_INVALID_CREDENTIAL",
                            "Invalid credentials provided"
                        )
                    )
                else
                    onComplete.invoke(false, it.exception as FirebaseException)
            }
    }

    override suspend fun signup(
        email: String,
        password: String,
        onComplete: (Boolean, FirebaseException) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    onComplete(
                        true,
                        FirebaseAuthInvalidCredentialsException(
                            "ERROR_INVALID_CREDENTIAL",
                            "Invalid credentials provided"
                        )
                    )
                else
                    onComplete(false, it.exception as FirebaseException)
            }
    }
}

sealed interface ImageDownloadStatus {
    object Loading: ImageDownloadStatus
    object Default: ImageDownloadStatus
    object Success: ImageDownloadStatus
}