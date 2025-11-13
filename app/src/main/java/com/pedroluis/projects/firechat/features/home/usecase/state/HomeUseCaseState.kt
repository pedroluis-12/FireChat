package com.pedroluis.projects.firechat.features.home.usecase.state

import com.pedroluis.projects.firechat.features.home.model.HomeContactModel

sealed class HomeListUseCaseState {
    data class ListContacts(val list: List<HomeContactModel>) : HomeListUseCaseState()
    object EmptyList : HomeListUseCaseState()
    object Error : HomeListUseCaseState()
}

sealed class HomeAddUseCaseState {
    object Success : HomeAddUseCaseState()
    object Error : HomeAddUseCaseState()
}
