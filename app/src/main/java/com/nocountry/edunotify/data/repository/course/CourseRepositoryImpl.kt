package com.nocountry.edunotify.data.repository.course

import com.nocountry.edunotify.data.api.RetrofitService
import com.nocountry.edunotify.domain.mappers.CourseMapper
import com.nocountry.edunotify.domain.mappers.UserMapper
import com.nocountry.edunotify.domain.model.CourseDomain
import com.nocountry.edunotify.domain.model.NotificationDomain
import com.nocountry.edunotify.domain.model.UserDomain
import com.nocountry.edunotify.domain.repositories.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CourseRepositoryImpl(
    private val service: RetrofitService,
    private val courseMapper: CourseMapper,
    private val userMapper: UserMapper
) : CourseRepository {

    override suspend fun assignCourseToUser(courseId: Int, userId: Int): Flow<UserDomain> {
        val remoteResultAssignCourseToUser = service.assignCourseToUser(idCourse = courseId, idUser = userId)
        val userDomain = userMapper.mapUserResponseToUserDomain(remoteResultAssignCourseToUser)
        return flowOf(userDomain)
    }
}