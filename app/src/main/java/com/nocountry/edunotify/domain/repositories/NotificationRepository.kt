package com.nocountry.edunotify.domain.repositories

import com.nocountry.edunotify.domain.model.CourseDomain
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun createNotificationMessageForCourse(
        author: String,
        title: String,
        message: String,
        expiration: Int,
        courseId: Int,
    ): Flow<String>

    suspend fun createNotificationMessageForSchool(
        author: String,
        title: String,
        message: String,
        expiration: Int,
        schoolId: Int,
    ): Flow<String>
}