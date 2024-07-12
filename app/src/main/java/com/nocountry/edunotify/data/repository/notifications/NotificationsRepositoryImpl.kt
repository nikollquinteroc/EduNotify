package com.nocountry.edunotify.data.repository.notifications

import com.nocountry.edunotify.data.api.RetrofitService
import com.nocountry.edunotify.data.api.body.CreateNewMessageBody
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
        val createNewMessageBody = CreateNewMessageBody(author, title, message, expiration)

        service.createNewMessageCourse(
            createNewMessageBody = createNewMessageBody,
            cursoId = courseId
        )

        return flowOf("Notification message for course was sent successfully!")
    }

    override suspend fun createNotificationMessageForSchool(
        author: String,
        title: String,
        message: String,
        expiration: Int,
        schoolId: Int
    ): Flow<String> {
        val createNewMessageBody = CreateNewMessageBody(author, title, message, expiration)
        service.createNewMessageSchool(
            createNewMessageBody = createNewMessageBody,
            schoolId = schoolId
        )
        return flowOf("Notification message for school was sent successfully!")
    }

}