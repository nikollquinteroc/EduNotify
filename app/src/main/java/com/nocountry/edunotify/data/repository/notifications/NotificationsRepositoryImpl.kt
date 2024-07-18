package com.nocountry.edunotify.data.repository.notifications

import com.nocountry.edunotify.data.api.RetrofitService
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
        service.createNewMessageCourse(
            author = author,
            title = title,
            message = message,
            expiration = expiration,
            cursoId = courseId
        )
        return flowOf("Notification message was sent successfully!")
    }

}