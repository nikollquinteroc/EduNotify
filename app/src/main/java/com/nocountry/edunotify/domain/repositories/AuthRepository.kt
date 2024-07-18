package com.nocountry.edunotify.domain.repositories

import com.nocountry.edunotify.domain.model.AuthDomain
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<AuthDomain>
    suspend fun register(
        name: String,
        lastName: String,
        email: String,
        password: String,
        phone: String,
        school: Int
    ): Flow<AuthDomain>
    suspend fun saveAuthResponseInDatabase(authDomain: AuthDomain): Flow<AuthDomain>
}