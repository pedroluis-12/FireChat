package com.pedroluis.projects.firechat.features.chat.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.pedroluis.projects.firechat.features.chat.model.ChatMessageModel
import com.pedroluis.projects.firechat.features.chat.repository.mapper.convertResponseToModel
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ChatRepositoryImpl : ChatRepository {
    override fun sendMessage(channelID: String, messageText: String?): Task<Void> {
        val firebaseDatabase = Firebase.database
        val message = ChatMessageModel(
            firebaseDatabase.reference.push().key ?: UUID.randomUUID().toString(),
            Firebase.auth.currentUser?.uid ?: "",
            messageText,
            System.currentTimeMillis(),
            Firebase.auth.currentUser?.displayName ?: ""
        )
        val result = firebaseDatabase.reference.child("messages")
            .child(channelID).push().setValue(message)
        return result
    }
}