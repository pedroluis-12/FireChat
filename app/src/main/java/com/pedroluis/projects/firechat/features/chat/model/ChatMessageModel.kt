package com.pedroluis.projects.firechat.features.chat.model

data class ChatMessageModel(
    val id: String = "",
    val senderId: String = "",
    val message: String? = "",
    val createdAt: Long = System.currentTimeMillis(),
    val senderName: String = ""
)
