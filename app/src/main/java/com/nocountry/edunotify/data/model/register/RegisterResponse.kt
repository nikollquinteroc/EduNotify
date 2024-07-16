package com.nocountry.edunotify.data.model.register

import com.nocountry.edunotify.data.model.user.User

data class RegisterResponse(
    val jwt: String,
    val user: User
)
