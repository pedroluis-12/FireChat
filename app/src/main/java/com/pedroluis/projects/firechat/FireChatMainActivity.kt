package com.pedroluis.projects.firechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pedroluis.projects.firechat.commons.theme.FireChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FireChatMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FireChatTheme {
                MainApp()
            }
        }
    }
}
