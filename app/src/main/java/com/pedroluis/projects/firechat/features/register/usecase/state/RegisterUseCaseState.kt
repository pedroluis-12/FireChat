package com.pedroluis.projects.firechat.features.register.usecase.state

sealed class RegisterUseCaseState {
    object Success : RegisterUseCaseState()
    object Error : RegisterUseCaseState()
}