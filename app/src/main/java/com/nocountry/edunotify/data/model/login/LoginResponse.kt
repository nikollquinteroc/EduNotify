package com.nocountry.edunotify.data.model.login

import com.nocountry.edunotify.data.model.user.User

data class LoginResponse(
    val jwt: String,
    val user: User
)
