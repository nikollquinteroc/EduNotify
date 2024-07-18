package com.nocountry.edunotify.domain.mappers

import com.nocountry.edunotify.data.api.model.notifications.NotificationResponse
import com.nocountry.edunotify.domain.model.NotificationDomain

class NotificationMapper {

    fun mapNotificationResponseToNotificationDomain(notificationResponse: NotificationResponse): NotificationDomain {
        return NotificationDomain(
            messageId = notificationResponse.message_id,
            messageDate = notificationResponse.messageDate,
            author = notificationResponse.author,
            message = notificationResponse.message,
            title = notificationResponse.title,
            expiration = notificationResponse.expiration
        )
    }
}