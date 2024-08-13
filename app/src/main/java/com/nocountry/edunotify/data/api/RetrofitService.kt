package com.nocountry.edunotify.data.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.nocountry.edunotify.data.api.body.CreateNewMessageBody
import com.nocountry.edunotify.data.api.body.LoginBody
import com.nocountry.edunotify.data.api.body.RegisterBody
import com.nocountry.edunotify.data.api.model.auth.AuthResponse
import com.nocountry.edunotify.data.api.model.schools.SchoolInfoResponse
import com.nocountry.edunotify.data.api.model.schools.SchoolResponse
import com.nocountry.edunotify.data.api.model.user.UserResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

const val BASE_URL = "https://back-edunotify-production.up.railway.app/"

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val stethoInterceptor = StethoInterceptor()

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .addNetworkInterceptor(stethoInterceptor)
    .build()

interface RetrofitService {

    @GET("school/all")
    suspend fun getAllSchools(): List<SchoolResponse>

    @GET("school/{schoolId}")
    suspend fun getSchoolInfo(
        @Path("schoolId") schoolId: Int
    ): SchoolInfoResponse

    @POST("auth/register")
    suspend fun createAuthRegister(
        @Body registerBody: RegisterBody
    ): AuthResponse

    @POST("auth/login")
    suspend fun createAuthLogin(
        @Body loginBody: LoginBody
    ): AuthResponse

    @POST("message/newMessageCourse/{cursoId}")
    suspend fun createNewMessageCourse(
        @Body createNewMessageBody: CreateNewMessageBody,
        @Path("cursoId") cursoId: Int
    ): Response<Unit>

    @POST("message/newMessageSchool/{schoolId}")
    suspend fun createNewMessageSchool(
        @Body createNewMessageBody: CreateNewMessageBody,
        @Path("schoolId") schoolId: Int
    ): Response<Unit>

    @POST("user/new/course/{idUser}/{idCourse}")
    suspend fun assignCourseToUser(
        @Path("idUser") idUser: Int,
        @Path("idCourse") idCourse: Int
    ): UserResponse

}

object RetrofitServiceFactory {
    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }
}