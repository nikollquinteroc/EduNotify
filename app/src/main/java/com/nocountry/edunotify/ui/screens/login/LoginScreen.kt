package com.nocountry.edunotify.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

@Composable
fun LoginScreen(onLoginClicked: () -> Unit, onRegisterClicked: () -> Unit) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Login")
            Button(onClick = onLoginClicked) {
                Text(text = "login")
            }
            Button(onClick = onRegisterClicked) {
                Text(text = "crear cuenta")
            }
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    EduNotifyTheme {
        LoginScreen(onLoginClicked = {}, onRegisterClicked = {})
    }
}