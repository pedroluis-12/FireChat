package com.pedroluis.projects.firechat.features.login.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pedroluis.projects.firechat.R
import com.pedroluis.projects.firechat.commons.redirects.FireChatRoutes
import com.pedroluis.projects.firechat.features.login.viewmodel.LoginViewModel
import com.pedroluis.projects.firechat.features.login.viewmodel.state.LoginViewModelState

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()
    val context = LocalContext.current

    ViewModelState(uiState, navController, context)

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.White)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Email") })
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.size(16.dp))
            if (uiState.value == LoginViewModelState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = { viewModel.login(email, password) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEnabledButton(email, password, uiState)
                ) {
                    Text(text = "Sign In")
                }

                TextButton(onClick = { navController.navigate("register") }) {
                    Text(text = "Don't have an account? Sign Up")
                }
            }
        }
    }
}

@Composable
private fun isEnabledButton(
    email: String, password: String, uiState: State<LoginViewModelState>
): Boolean =
    email.isNotEmpty() && password.isNotEmpty() && (uiState.value == LoginViewModelState.Nothing || uiState.value == LoginViewModelState.Error)

@Composable
private fun ViewModelState(
    uiState: State<LoginViewModelState>, navController: NavController, context: Context
) {
    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is LoginViewModelState.Success ->
                navController.navigate(FireChatRoutes.Home.route)
            is LoginViewModelState.Error ->
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}