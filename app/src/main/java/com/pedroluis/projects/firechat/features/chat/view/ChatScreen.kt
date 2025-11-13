package com.pedroluis.projects.firechat.features.chat.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.pedroluis.projects.firechat.commons.theme.DarkGrey
import com.pedroluis.projects.firechat.commons.theme.Purple
import com.pedroluis.projects.firechat.features.chat.model.ChatMessageModel
import com.pedroluis.projects.firechat.features.chat.viewmodel.ChatViewModel

@Composable
fun ChatScreen(navController: NavController, channelId: String) {
    Scaffold(
        containerColor = Color.White
    ) {
        val viewModel: ChatViewModel = hiltViewModel()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LaunchedEffect(key1 = true) {
                viewModel.getMessages(channelId)
            }
            val messages = viewModel.messages.collectAsState()
            ChatMessages(
                messages = messages.value,
                onSendMessage = { message ->
                    viewModel.sendMessage(message, channelId)
                }
            )
        }
    }
}

@Composable
fun ChatMessages(
    messages: List<ChatMessageModel>,
    onSendMessage: (String) -> Unit
) {
    val hideKeyboardController = LocalSoftwareKeyboardController.current

    val msg = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items = messages) { message ->
                ChatBubble(message = message)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = msg.value,
                onValueChange = { msg.value = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = "Type a message") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    hideKeyboardController?.hide()
                }),
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = DarkGrey,
                    unfocusedContainerColor = DarkGrey,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.White
                )
            )
            IconButton(onClick = {
                onSendMessage(msg.value)
                msg.value = ""
            }) {
                Image(imageVector = Icons.Filled.Send, contentDescription = "send")
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessageModel) {
    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid
    val bubbleColor = if (isCurrentUser) {
        Purple
    } else {
        DarkGrey
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)

    ) {
        val alignment = if (!isCurrentUser) Alignment.CenterStart else Alignment.CenterEnd
        Row(
            modifier = Modifier
                .padding(8.dp)
                .align(alignment),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isCurrentUser) {
                Image(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Box(
                modifier = Modifier
                    .background(
                        color = bubbleColor, shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ) {

                Text(text = message.message?.trim() ?: "", color = Color.White)
            }

        }

    }
}
