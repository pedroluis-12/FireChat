package com.pedroluis.projects.firechat.features.chat.usecase

import com.pedroluis.projects.firechat.features.chat.repository.ChatRepository
import com.pedroluis.projects.firechat.features.chat.usecase.state.ChatSendMessageUseCaseState

class ChatUseCase(val repository: ChatRepository) {
    fun sendMessage(
        channelID: String, messageText: String?
    ): ChatSendMessageUseCaseState = runCatching {
        repository.sendMessage(channelID, messageText)
        ChatSendMessageUseCaseState.Success
    }.onFailure {
        ChatSendMessageUseCaseState.Error
    }.getOrElse {
        ChatSendMessageUseCaseState.Error
    }
}