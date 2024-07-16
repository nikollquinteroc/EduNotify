package com.nocountry.edunotify.domain.notifications

import com.nocountry.edunotify.data.model.notifications.NotificationResponse
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun getNotification(
        author: String,
        title: String,
        message: String,
        expiration: Int
    ): Flow<NotificationResponse>
}