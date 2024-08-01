package com.nocountry.edunotify.data.repository.notifications

import com.nocountry.edunotify.data.api.RetrofitService
import com.nocountry.edunotify.domain.model.CourseDomain
import com.nocountry.edunotify.domain.repositories.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NotificationsRepositoryImpl(private val service: RetrofitService) : NotificationRepository {

    override suspend fun createNotificationMessageForCourse(
        author: String,
        title: String,
        message: String,
        expiration: Int,
        courseId: Int
    ): Flow<String> {
        return try {
            val response = service.createNewMessageCourse(
                author = author,
                title = title,
                message = message,
                expiration = expiration,
                cursoId = courseId
            )

            if (response.isSuccessful) {
                flowOf("Notification message for course was sent successfully!")
            } else {
                flowOf("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            flowOf("Exception: ${e.message}")
        }


        //return flowOf("Notification message for course was sent successfully!")
    }

    override suspend fun createNotificationMessageForSchool(
        author: String,
        title: String,
        message: String,
        expiration: Int,
        schoolId: Int
    ): Flow<String> {
        service.createNewMessageSchool(
            author = author,
            title = title,
            message = message,
            expiration = expiration,
            schoolId = schoolId
        )
        return flowOf("Notification message for school was sent successfully!")
    }

}