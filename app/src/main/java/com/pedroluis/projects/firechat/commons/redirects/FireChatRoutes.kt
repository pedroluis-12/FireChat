package com.pedroluis.projects.firechat.commons.redirects

sealed class FireChatRoutes(val route: String) {
    object Login : FireChatRoutes("login")
    object Register : FireChatRoutes("register")
    object Home : FireChatRoutes("home")
}
