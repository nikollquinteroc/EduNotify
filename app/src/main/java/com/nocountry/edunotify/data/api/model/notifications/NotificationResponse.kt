package com.nocountry.edunotify.data.api.model.notifications

import java.time.LocalDateTime

data class NotificationResponse(
    val message_id: Int,
    val messageDate: List<Int>,
    val author: String,
    val title: String,
    val message: String,
    val expiration: Int
)
