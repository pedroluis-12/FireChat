package com.pedroluis.projects.firechat.features.chat.repository.mapper

import com.google.firebase.database.DataSnapshot
import com.pedroluis.projects.firechat.features.chat.model.ChatMessageModel

fun Iterable<DataSnapshot>.convertResponseToModel(): List<ChatMessageModel> {
    val list = mutableListOf<ChatMessageModel>()
    this.forEach { data ->
        val message = data.getValue(ChatMessageModel::class.java)
        message?.let {
            list.add(it)
        }
    }
    return list
}
