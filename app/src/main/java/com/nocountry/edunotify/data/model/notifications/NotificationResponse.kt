package com.nocountry.edunotify.data.model.notifications

import java.time.LocalDateTime

data class NotificationResponse(
    val message_id: Int,
    val messageDate: LocalDateTime,
    val author: String,
    val title: String,
    val message: String,
    val expiration: Int
)
