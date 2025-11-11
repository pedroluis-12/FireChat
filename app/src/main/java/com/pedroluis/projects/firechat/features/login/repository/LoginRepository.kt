package com.pedroluis.projects.firechat.features.login.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface LoginRepository {
    suspend fun executeLogin(email: String, password: String): Task<AuthResult>
}
