package com.pedroluis.projects.firechat.features.home.usecase

import com.pedroluis.projects.firechat.features.home.repository.HomeRepository
import com.pedroluis.projects.firechat.features.home.usecase.state.HomeAddUseCaseState
import com.pedroluis.projects.firechat.features.home.usecase.state.HomeListUseCaseState

class HomeUseCase(private val repository: HomeRepository) {

    suspend fun getListContacts(): HomeListUseCaseState = runCatching {
        val exec = repository.getContactList()
        return when {
            exec.isNotEmpty() -> HomeListUseCaseState.ListContacts(exec)
            exec.isEmpty() -> HomeListUseCaseState.EmptyList
            else -> HomeListUseCaseState.Error
        }
    }.onFailure {
        HomeListUseCaseState.Error
    }.getOrElse {
        HomeListUseCaseState.Error
    }

    suspend fun addContact(name: String): HomeAddUseCaseState = runCatching {
        repository.addContact(name)
        HomeAddUseCaseState.Success
    }.onFailure {
        HomeAddUseCaseState.Error
    }.getOrElse {
        HomeAddUseCaseState.Error
    }
}