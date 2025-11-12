package com.pedroluis.projects.firechat.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroluis.projects.firechat.features.home.repository.HomeRepositoryImpl
import com.pedroluis.projects.firechat.features.home.usecase.HomeUseCase
import com.pedroluis.projects.firechat.features.home.usecase.state.HomeAddUseCaseState
import com.pedroluis.projects.firechat.features.home.usecase.state.HomeListUseCaseState
import com.pedroluis.projects.firechat.features.home.viewmodel.state.HomeViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val repository = HomeRepositoryImpl()
    private val useCase = HomeUseCase(repository)
    private val _state = MutableStateFlow<HomeViewModelState>(
        HomeViewModelState.DisplayEmptyList
    )
    val state = _state.asStateFlow()

    init {
        getListContacts()
    }

    fun addContact(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.addContact(name)
            handleAddContactState(result)
        }
    }

    private fun handleAddContactState(result: HomeAddUseCaseState) {
        when (result) {
            is HomeAddUseCaseState.Success -> getListContacts()
            is HomeAddUseCaseState.Error -> _state.value = HomeViewModelState.Error
        }
    }

    private fun getListContacts() {
        _state.value = HomeViewModelState.DisplayLoading
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.getListContacts()
            handleHomeState(result)
        }
    }

    private fun handleHomeState(result: HomeListUseCaseState) {
        when (result) {
            is HomeListUseCaseState.ListContacts ->
                _state.value = HomeViewModelState.DisplayList(result.list)
            is HomeListUseCaseState.EmptyList ->
                _state.value = HomeViewModelState.DisplayEmptyList
            is HomeListUseCaseState.Error ->
                _state.value = HomeViewModelState.Error
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
