package com.nocountry.edunotify.data.api.model.auth

import com.nocountry.edunotify.data.api.model.user.UserResponse

data class AuthResponse(
    val jwt: String,
    val user: UserResponse
)
