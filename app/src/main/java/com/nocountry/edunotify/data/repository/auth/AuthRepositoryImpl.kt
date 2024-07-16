package com.nocountry.edunotify.data.repository.auth

import com.nocountry.edunotify.data.RetrofitService
import com.nocountry.edunotify.data.model.login.LoginResponse
import com.nocountry.edunotify.data.model.register.RegisterResponse
import com.nocountry.edunotify.domain.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AuthRepositoryImpl(private val service: RetrofitService) : AuthRepository {
    override suspend fun login(email: String, password: String): Flow<LoginResponse> {
        val remoteResultLogin = service.createAuthLogin(email = email, password = password)
        return flowOf(remoteResultLogin)
    }

    override suspend fun register(
        name: String,
        lastName: String,
        email: String,
        password: String,
        phone: String,
        school: Int
    ): Flow<RegisterResponse> {
        val remoteResultRegister = service.createAuthRegister(
            name = name,
            lastName = lastName,
            email = email,
            password = password,
            phone = phone,
            school = school
        )
        return flowOf(remoteResultRegister)
    }
}