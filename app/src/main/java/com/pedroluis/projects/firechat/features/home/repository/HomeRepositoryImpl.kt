package com.pedroluis.projects.firechat.features.home.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.database
import com.pedroluis.projects.firechat.features.home.model.HomeContactModel
import com.pedroluis.projects.firechat.features.home.repository.mapper.convertResponseToModel
import kotlinx.coroutines.tasks.await

class HomeRepositoryImpl : HomeRepository {

    override suspend fun getContactList(): List<HomeContactModel> {
        val firebaseDatabase = Firebase.database
        val list = firebaseDatabase.getReference("channel").get().await()
        return list.children.convertResponseToModel()
    }

    override suspend fun addContact(name: String): Task<Void> {
        val firebaseDatabase = Firebase.database
        val key = firebaseDatabase.getReference("channel").push().key
        if (key != null) {
            val result = firebaseDatabase.getReference("channel").child(key).setValue(name)
            return result
        } else {
            throw Exception("Key is null")
        }
    }
}