package com.nocountry.edunotify.data

import com.nocountry.edunotify.data.model.login.LoginResponse
import com.nocountry.edunotify.data.model.notifications.NotificationResponse
import com.nocountry.edunotify.data.model.register.RegisterResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

const val BASE_URL = "http://localhost:8080/"

interface RetrofitService {
    @POST("auth/register")
    suspend fun createAuthRegister(
        @Query("name") name: String,
        @Query("lastName") lastName: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("phone") phone: String,
        @Query("school") school: Int
    ): RegisterResponse

    @POST("auth/login")
    suspend fun createAuthLogin(
        @Query("email") email: String,
        @Query("password") password: String,
    ): LoginResponse

    @POST("message/newMessageSchool")
    suspend fun createNewMessageSchool(
        @Query("author") author: String,
        @Query("title") title: String,
        @Query("message") message: String,
        @Query("expiration") expiration: Int,
    ): NotificationResponse

}

object RetrofitServiceFactory {
    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RetrofitService::class.java)
    }
}