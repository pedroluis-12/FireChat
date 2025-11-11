package com.pedroluis.projects.firechat.features.login.viewmodel.state

sealed class LoginViewModelState {
    object Nothing : LoginViewModelState()
    object Loading : LoginViewModelState()
    object Success : LoginViewModelState()
    object Error : LoginViewModelState()
}