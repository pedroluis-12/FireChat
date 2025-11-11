package com.pedroluis.projects.firechat.features.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroluis.projects.firechat.features.login.repository.LoginRepositoryImpl
import com.pedroluis.projects.firechat.features.login.usecase.LoginUseCase
import com.pedroluis.projects.firechat.features.login.usecase.state.LoginUseCaseState
import com.pedroluis.projects.firechat.features.login.viewmodel.state.LoginViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    private val repository = LoginRepositoryImpl()
    private val useCase = LoginUseCase(repository)
    private val _state = MutableStateFlow<LoginViewModelState>(LoginViewModelState.Nothing)
    val state = _state.asStateFlow()

    fun login(email: String, password: String) {
        _state.value = LoginViewModelState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.login(email, password)
            handleLoginState(result)
        }
    }

    private fun handleLoginState(result: LoginUseCaseState) {
        when (result) {
            is LoginUseCaseState.Success ->
                _state.value = LoginViewModelState.Success
            is LoginUseCaseState.Error ->
                _state.value = LoginViewModelState.Error
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
