package com.nocountry.edunotify.data.api.model.notifications

data class NotificationResponse(
    val message_id: Int,
    val messageDate: String,
    val author: String,
    val title: String,
    val message: String,
    val expiration: Int
)
