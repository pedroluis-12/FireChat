package com.pedroluis.projects.firechat.features.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroluis.projects.firechat.features.register.repository.RegisterRepository
import com.pedroluis.projects.firechat.features.register.repository.RegisterRepositoryImpl
import com.pedroluis.projects.firechat.features.register.usecase.RegisterUseCase
import com.pedroluis.projects.firechat.features.register.usecase.state.RegisterUseCaseState
import com.pedroluis.projects.firechat.features.register.viewmodel.state.RegisterViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    private val repository: RegisterRepository = RegisterRepositoryImpl()
    private val useCase: RegisterUseCase = RegisterUseCase(repository)
    private val _state = MutableStateFlow<RegisterViewModelState>(RegisterViewModelState.Nothing)
    val state = _state.asStateFlow()

    fun register(name: String, email: String, password: String) {
        _state.value = RegisterViewModelState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.register(name, email, password)
            handleRegisterState(result)
        }
    }

    private fun handleRegisterState(result: RegisterUseCaseState) {
        when (result) {
            is RegisterUseCaseState.Success ->
                _state.value = RegisterViewModelState.Success
            is RegisterUseCaseState.Error ->
                _state.value = RegisterViewModelState.Error
        }
    }
}