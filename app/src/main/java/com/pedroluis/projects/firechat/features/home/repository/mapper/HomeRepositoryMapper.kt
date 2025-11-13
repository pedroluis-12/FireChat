package com.pedroluis.projects.firechat.features.home.repository.mapper

import com.google.firebase.database.DataSnapshot
import com.pedroluis.projects.firechat.features.home.model.HomeContactModel

fun Iterable<DataSnapshot>.convertResponseToModel(): List<HomeContactModel> {
    val list = mutableListOf<HomeContactModel>()
    this.forEach { data ->
        if (data.key != null && data.value != null) {
            val channel = HomeContactModel(data.key.toString(), data.value.toString())
            list.add(channel)
        }
    }
    return list
}