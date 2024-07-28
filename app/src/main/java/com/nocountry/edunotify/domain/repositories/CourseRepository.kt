package com.nocountry.edunotify.domain.repositories

import com.nocountry.edunotify.domain.model.CourseDomain
import com.nocountry.edunotify.domain.model.UserDomain
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun assignCourseToUser(courseId: Int, userId: Int): Flow<UserDomain>
}