package com.nocountry.edunotify.domain.auth

import com.nocountry.edunotify.data.model.login.LoginResponse
import com.nocountry.edunotify.data.model.register.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<LoginResponse>

    suspend fun register(
        name: String,
        lastName: String,
        email: String,
        password: String,
        phone: String,
        school: Int
    ): Flow<RegisterResponse>
}