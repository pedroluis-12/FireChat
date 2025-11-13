package com.pedroluis.projects.firechat.features.chat.usecase.state

sealed class ChatSendMessageUseCaseState {
    object Success : ChatSendMessageUseCaseState()
    object Error : ChatSendMessageUseCaseState()
}
