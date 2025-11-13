package com.pedroluis.projects.firechat.features.home.repository

import com.google.android.gms.tasks.Task
import com.pedroluis.projects.firechat.features.home.model.HomeContactModel

interface HomeRepository {
    suspend fun getContactList(): List<HomeContactModel>
    suspend fun addContact(name: String): Task<Void>
}
