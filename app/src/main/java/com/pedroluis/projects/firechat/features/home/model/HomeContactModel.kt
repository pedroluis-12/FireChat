package com.pedroluis.projects.firechat.features.home.model

data class HomeContactModel(
    val id: String = "",
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)
