package com.nocountry.edunotify.ui.screens.login

import androidx.lifecycle.ViewModel
import com.nocountry.edunotify.data.model.login.LoginResponse
import com.nocountry.edunotify.data.model.user.User
import com.nocountry.edunotify.domain.auth.AuthRepository

data class LoginUiState(
    val isLoginUiState: Boolean = false,
    val loginResponse: LoginResponse = LoginResponse(
        jwt = "", user = User(
            id = 0,
            name = "",
            lastName = "",
            email = "",
            phone = "",
            role = "",
            school = 0,
            courses = emptyList()
        )
    ),
    val error: String = ""
)

class LoginViewModel(val repository: AuthRepository) : ViewModel() {

}