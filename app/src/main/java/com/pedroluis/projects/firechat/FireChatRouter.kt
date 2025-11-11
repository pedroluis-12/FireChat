package com.pedroluis.projects.firechat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.pedroluis.projects.firechat.commons.redirects.FireChatRoutes
import com.pedroluis.projects.firechat.features.home.view.HomeScreen
import com.pedroluis.projects.firechat.features.login.view.LoginScreen
import com.pedroluis.projects.firechat.features.register.view.RegisterScreen

@Composable
fun MainApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        NavHost(navController = rememberNavController(), startDestination = getStartRoute()) {
            composable(FireChatRoutes.Login.route) {
                LoginScreen(navController)
            }
            composable(FireChatRoutes.Register.route) {
                RegisterScreen(navController)
            }
            composable(FireChatRoutes.Home.route) {
                HomeScreen(navController)
            }
        }
    }
}

private fun getStartRoute() = if (FirebaseAuth.getInstance().currentUser != null) {
    FireChatRoutes.Home.route
} else {
    FireChatRoutes.Login.route
}
