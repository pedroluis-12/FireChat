package com.pedroluis.projects.firechat.features.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.pedroluis.projects.firechat.features.chat.model.ChatMessageModel
import com.pedroluis.projects.firechat.features.chat.repository.ChatRepositoryImpl
import com.pedroluis.projects.firechat.features.chat.usecase.ChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {
    private val repository = ChatRepositoryImpl()
    private val useCase = ChatUseCase(repository)
    private val _messages = MutableStateFlow<List<ChatMessageModel>>(emptyList())
    val messages = _messages.asStateFlow()
    val firebaseDatabase = Firebase.database

    fun sendMessage(message: String, channelID: String) {
        useCase.sendMessage(channelID, message)
    }

    fun getMessages(channelID: String) {
        firebaseDatabase.getReference("messages").child(channelID).orderByChild("createdAt")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<ChatMessageModel>()
                    snapshot.children.forEach { data ->
                        val message = data.getValue(ChatMessageModel::class.java)
                        message?.let {
                            list.add(it)
                        }
                    }
                    _messages.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
