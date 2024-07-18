package com.nocountry.edunotify.data.api

import com.nocountry.edunotify.data.api.model.auth.AuthResponse
import com.nocountry.edunotify.data.api.model.schools.SchoolResponse
import com.nocountry.edunotify.data.api.model.user.UserResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "http://192.168.1.4:8080/"

interface RetrofitService {

    @GET("school/all")
    suspend fun getAllSchools(): List<SchoolResponse>

    @POST("auth/register")
    suspend fun createAuthRegister(
        @Query("name") name: String,
        @Query("lastName") lastName: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("phone") phone: String,
        @Query("school") school: Int
    ): AuthResponse

    @POST("auth/login")
    suspend fun createAuthLogin(
        @Query("email") email: String,
        @Query("password") password: String,
    ): AuthResponse

    @POST("message/newMessageCourse/{cursoId}")
    suspend fun createNewMessageCourse(
        @Query("author") author: String,
        @Query("title") title: String,
        @Query("message") message: String,
        @Query("expiration") expiration: Int,
        @Path("cursoId") cursoId: Int
    )

    @POST("user/new/course/{idUser}/{idCourse}")
    suspend fun assignCourseToUser(
        @Path("idUser") idUser: Int,
        @Path("idCourse") idCourse: Int
    ): UserResponse

    @GET("courses/{schoolId}")
    suspend fun getCoursesBySchool(
        @Path("schoolId") schoolId: Int
    )
}

object RetrofitServiceFactory {
    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RetrofitService::class.java)
    }
}