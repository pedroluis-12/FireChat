package com.pedroluis.projects.firechat.features.register.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class RegisterRepositoryImpl : RegisterRepository {
    override suspend fun executeRegister(
        name: String, email: String, password: String
    ): Task<AuthResult> {
        val firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }
}
