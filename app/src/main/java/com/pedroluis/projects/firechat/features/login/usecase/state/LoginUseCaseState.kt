package com.pedroluis.projects.firechat.features.login.usecase.state

sealed class LoginUseCaseState {
    object Success : LoginUseCaseState()
    object Error : LoginUseCaseState()
}
