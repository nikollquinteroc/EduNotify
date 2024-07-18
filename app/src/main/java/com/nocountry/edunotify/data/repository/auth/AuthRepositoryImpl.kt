package com.nocountry.edunotify.data.repository.auth

import com.nocountry.edunotify.data.api.RetrofitService
import com.nocountry.edunotify.data.database.dao.AuthDao
import com.nocountry.edunotify.domain.mappers.AuthMapper
import com.nocountry.edunotify.domain.model.AuthDomain
import com.nocountry.edunotify.domain.model.CourseDomain
import com.nocountry.edunotify.domain.model.NotificationDomain
import com.nocountry.edunotify.domain.model.UserDomain
import com.nocountry.edunotify.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AuthRepositoryImpl(
    private val service: RetrofitService,
    private val authMapper: AuthMapper
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
                            messageDate = "12 de Julio 2024",
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
                            messageDate = "06 de Agosto 2024",
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
                            messageDate = "30 de Abril 2024",
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
        //val remoteResultLogin = service.createAuthLogin(email = email, password = password)
        //val authDomain = authMapper.mapAuthResponseToAuthDomain(remoteResultLogin)
        //saveAuthResponseInDatabase(authDomain)
        //return flowOf(authDomain)

        saveAuthResponseInDatabase(fakeResponse)
        return flowOf(fakeResponse)
    }

    override suspend fun register(
        name: String,
        lastName: String,
        email: String,
        password: String,
        phone: String,
        school: Int
    ): Flow<AuthDomain> {
        /*val remoteResultRegister = service.createAuthRegister(
            name = name,
            lastName = lastName,
            email = email,
            password = password,
            phone = phone,
            school = school
        )
        //val authDomain = authMapper.mapAuthResponseToAuthDomain(remoteResultRegister)
        //saveAuthResponseInDatabase(authDomain)
        return flowOf(authDomain)
        */

        saveAuthResponseInDatabase(fakeResponse)
        return flowOf(fakeResponse)
    }

    override suspend fun saveAuthResponseInDatabase(authDomain: AuthDomain): Flow<AuthDomain> {
        // crear un mapper que converta de authDomain a authEntity
        //val result = database.insert()
        return flowOf(fakeResponse)
    }
}