package com.nocountry.edunotify.data.api.body

data class RegisterBody(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String,
    val school: Int,
)
