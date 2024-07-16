package com.nocountry.edunotify.data.repository.notifications

import com.nocountry.edunotify.data.RetrofitService
import com.nocountry.edunotify.data.model.notifications.NotificationResponse
import com.nocountry.edunotify.domain.notifications.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NotificationsRepositoryImpl(private val service: RetrofitService) : NotificationRepository {
    override suspend fun getNotification(
        author: String,
        title: String,
        message: String,
        expiration: Int
    ): Flow<NotificationResponse> {
        val remoteResultNotifications = service.createNewMessageSchool(
            author = author,
            title = title,
            message = message,
            expiration = expiration
        )
        return flowOf(remoteResultNotifications)
    }

}