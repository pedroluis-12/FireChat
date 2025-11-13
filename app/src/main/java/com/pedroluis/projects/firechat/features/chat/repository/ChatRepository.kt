package com.pedroluis.projects.firechat.features.chat.repository

import com.google.android.gms.tasks.Task

interface ChatRepository {
    fun sendMessage(channelID: String, messageText: String?): Task<Void>
}