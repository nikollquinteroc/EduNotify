package com.nocountry.edunotify.data.model.user

import com.nocountry.edunotify.data.model.courses.CourseResponse

data class User(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val role: String,
    val school: Int,
    val courses: List<CourseResponse>
)
