package com.nocountry.edunotify.data.repository.auth

import com.nocountry.edunotify.data.api.RetrofitService
import com.nocountry.edunotify.data.api.body.LoginBody
import com.nocountry.edunotify.data.api.body.RegisterBody
import com.nocountry.edunotify.data.database.mappers.AuthMapperDb
import com.nocountry.edunotify.domain.mappers.AuthMapper
import com.nocountry.edunotify.domain.model.AuthDomain
import com.nocountry.edunotify.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class AuthRepositoryImpl(
    private val service: RetrofitService,
    private val authMapper: AuthMapper,
    private val authMapperDb: AuthMapperDb,
) : AuthRepository {

    override suspend fun login(email: String, password: String): Flow<AuthDomain> {
        val loginBody = LoginBody(email, password)
        val remoteResultLogin = service.createAuthLogin(loginBody)
        val authDomain = authMapper.mapAuthResponseToAuthDomain(remoteResultLogin)
        saveAuthResponseInDatabase(authDomain)
        return flowOf(authDomain)
    }

    override suspend fun register(
        name: String,
        lastName: String,
        email: String,
        password: String,
        phone: String,
        school: Int
    ): Flow<AuthDomain> {

        val registerBody = RegisterBody(name, lastName, email, password, phone, school)
        val remoteResultRegister = service.createAuthRegister(registerBody)
        val authDomain = authMapper.mapAuthResponseToAuthDomain(remoteResultRegister)
        saveAuthResponseInDatabase(authDomain)
        return flowOf(authDomain)
    }

    override suspend fun saveAuthResponseInDatabase(authDomain: AuthDomain): Flow<AuthDomain> {

        return flow {
            val authEntity = authMapperDb.mapAuthDomainToAuthEntity(authDomain)
            //val result = EduNotifyRoomDatabase.getDatabase().getAuthDao().insert(authEntity)
        }
        // crear un mapper que converta de authDomain a authEntity
        //val result = database.insert()
        //return flowOf(fakeResponse)
    }
}