package com.pedroluis.projects.firechat.features.home.viewmodel.state

import com.pedroluis.projects.firechat.features.home.model.HomeContactModel

sealed class HomeViewModelState {
    object DisplayLoading : HomeViewModelState()
    data class DisplayList(val list: List<HomeContactModel>) : HomeViewModelState()
    object DisplayEmptyList : HomeViewModelState()
    object Error : HomeViewModelState()
}
