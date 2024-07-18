package com.nocountry.edunotify.data.api.model.user

import com.nocountry.edunotify.data.api.model.courses.CourseResponse

data class UserResponse(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val role: String,
    val school: Int,
    val courses: List<CourseResponse>?
)
