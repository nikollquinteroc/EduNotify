package com.nocountry.edunotify.data.repository.auth

import android.content.Context
import android.util.Log
import com.nocountry.edunotify.data.api.RetrofitService
import com.nocountry.edunotify.data.api.body.LoginBody
import com.nocountry.edunotify.data.api.body.RegisterBody
import com.nocountry.edunotify.data.database.EduNotifyRoomDatabase
import com.nocountry.edunotify.data.database.mappers.AuthMapperDb
import com.nocountry.edunotify.domain.mappers.AuthMapper
import com.nocountry.edunotify.domain.model.AuthDomain
import com.nocountry.edunotify.domain.model.CourseDomain
import com.nocountry.edunotify.domain.model.NotificationDomain
import com.nocountry.edunotify.domain.model.UserDomain
import com.nocountry.edunotify.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import okio.IOException
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val service: RetrofitService,
    private val authMapper: AuthMapper,
    private val authMapperDb: AuthMapperDb,
) : AuthRepository {

    private val fakeResponse = AuthDomain(
        jwt = "hgiudshgiudhfgouidsrguisd",
        user = UserDomain(
            id = 1,
            name = "Nikoll",
            lastName = "Quintero",
            email = "nikollquinteroc@gmail.com",
            phone = "3057878326",
            role = "parent",
            school = 1,
            courses = listOf(
                CourseDomain(
                    course = "Primero de primaria",
                    courseId = 1,
                    notifications = listOf(
                        NotificationDomain(
                            messageId = 1,
                            messageDate = emptyList(),
                            author = "Carlos Morales",
                            title = "Anuncio importante",
                            message = "Mañana no hay clases",
                            expiration = 2
                        )
                    )
                ),
                CourseDomain(
                    course = "Tercero de primaria",
                    courseId = 2,
                    notifications = listOf(
                        NotificationDomain(
                            messageId = 2,
                            messageDate = emptyList(),
                            author = "Carlos Morales",
                            title = "Jean Day",
                            message = "La próxima semana deben venir vestidos como BadBunny",
                            expiration = 2
                        )
                    )
                ),
                CourseDomain(
                    course = "Cuarto de bachillerato",
                    courseId = 3,
                    notifications = listOf(
                        NotificationDomain(
                            messageId = 3,
                            messageDate = emptyList(),
                            author = "Carlos Morales",
                            title = "Icfes",
                            message = "en Agosto es el icfes, ponganse vergas",
                            expiration = 2
                        )
                    )
                )
            )
        )
    )

    override suspend fun login(email: String, password: String): Flow<AuthDomain> {
        val loginBody = LoginBody(email, password)
        val remoteResultLogin = service.createAuthLogin(loginBody)
        val authDomain = authMapper.mapAuthResponseToAuthDomain(remoteResultLogin)
        saveAuthResponseInDatabase(authDomain)
        return flowOf(authDomain)

        /*saveAuthResponseInDatabase(fakeResponse)
        return flowOf(fakeResponse)*/
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

        /*saveAuthResponseInDatabase(fakeResponse)
        return flowOf(fakeResponse)*/
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