package com.pedroluis.projects.firechat.features.register.view

import android.content.Context
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
import com.pedroluis.projects.firechat.commons.theme.FireChatTheme
import com.pedroluis.projects.firechat.features.register.viewmodel.RegisterViewModel
import com.pedroluis.projects.firechat.features.register.viewmodel.state.RegisterViewModelState

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirm by remember {
        mutableStateOf("")
    }
    val viewModel: RegisterViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()
    val context = LocalContext.current

    ViewModelStates(uiState, navController, context)

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
                .padding(16.dp), verticalArrangement = Arrangement.Center,
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
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Full Name") })

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Email") })
            OutlinedTextField(
                value = password, onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = confirm, onValueChange = { confirm = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = password.isNotEmpty() && confirm.isNotEmpty() && password != confirm
            )
            Spacer(modifier = Modifier.size(16.dp))
            if (uiState.value == RegisterViewModelState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = { viewModel.register(name, email, password) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty() && password == confirm
                ) {
                    Text(text = "Sign Up")
                }
                TextButton(onClick = { navController.popBackStack() }) {
                    Text(text = "Already have an account? Sign In")
                }
            }
        }
    }
}

@Composable
private fun ViewModelStates(
    uiState: State<RegisterViewModelState>,
    navController: NavController,
    context: Context
) {
    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is RegisterViewModelState.Success ->
                navController.navigate(FireChatRoutes.Home.route)
            is RegisterViewModelState.Error ->
                Toast.makeText(context, "Register failed", Toast.LENGTH_SHORT).show()
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}
