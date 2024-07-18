package com.nocountry.edunotify.domain.repositories

import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun createNotificationMessageForCourse(
        author: String,
        title: String,
        message: String,
        expiration: Int,
        courseId: Int,
    ): Flow<String>
}