package com.pedroluis.projects.firechat.features.register.usecase

import com.google.firebase.auth.UserProfileChangeRequest
import com.pedroluis.projects.firechat.features.register.repository.RegisterRepository
import com.pedroluis.projects.firechat.features.register.usecase.state.RegisterUseCaseState
import kotlinx.coroutines.tasks.await

class RegisterUseCase(private val repository: RegisterRepository) {
    suspend fun register(
        name: String, email: String, password: String
    ): RegisterUseCaseState = runCatching {
        val exec = repository.executeRegister(name, email, password).await()
        return if (exec.user != null) {
            exec.user?.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
            )?.await()
            RegisterUseCaseState.Success
        } else {
            RegisterUseCaseState.Error
        }
    }.onFailure {
        RegisterUseCaseState.Error
    }.getOrElse {
        RegisterUseCaseState.Error
    }
}
