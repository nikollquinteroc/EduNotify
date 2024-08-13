package com.nocountry.edunotify.data.api.model.courses

import com.nocountry.edunotify.data.api.model.notifications.NotificationResponse

data class CourseResponse(
    val course: String,
    val courseId: Int,
    val messages: List<NotificationResponse>
)
