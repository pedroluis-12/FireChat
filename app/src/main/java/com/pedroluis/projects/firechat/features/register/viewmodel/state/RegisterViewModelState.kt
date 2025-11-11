package com.pedroluis.projects.firechat.features.register.viewmodel.state

import com.pedroluis.projects.firechat.features.login.viewmodel.state.LoginViewModelState

sealed class RegisterViewModelState {
    object Nothing : RegisterViewModelState()
    object Loading : RegisterViewModelState()
    object Success : RegisterViewModelState()
    object Error : RegisterViewModelState()
}