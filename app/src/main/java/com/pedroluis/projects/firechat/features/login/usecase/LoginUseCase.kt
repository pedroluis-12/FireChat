package com.pedroluis.projects.firechat.features.login.usecase

import com.pedroluis.projects.firechat.features.login.repository.LoginRepository
import com.pedroluis.projects.firechat.features.login.usecase.state.LoginUseCaseState
import kotlinx.coroutines.tasks.await

class LoginUseCase(private val repository: LoginRepository) {
    suspend fun login(email: String, password: String): LoginUseCaseState = runCatching {
        val exec = repository.executeLogin(email, password).await()
        return if (exec.user != null) {
            LoginUseCaseState.Success
        } else {
            LoginUseCaseState.Error
        }
    }.onFailure {
        LoginUseCaseState.Error
    }.getOrElse {
        LoginUseCaseState.Error
    }
}
