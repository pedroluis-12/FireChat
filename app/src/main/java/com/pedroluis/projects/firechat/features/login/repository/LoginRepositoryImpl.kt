package com.pedroluis.projects.firechat.features.login.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginRepositoryImpl : LoginRepository {
    override suspend fun executeLogin(email: String, password: String): Task<AuthResult> {
        val firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }
}
