package com.pedroluis.projects.firechat.features.register.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface RegisterRepository {
    suspend fun executeRegister(name: String, email: String, password: String): Task<AuthResult>
}
