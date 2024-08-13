package com.nocountry.edunotify.data.repository.course

import com.nocountry.edunotify.data.api.RetrofitService
import com.nocountry.edunotify.data.database.dao.UserDao
import com.nocountry.edunotify.data.database.entities.CourseEntity
import com.nocountry.edunotify.data.database.entities.NotificationEntity
import com.nocountry.edunotify.data.database.entities.UserEntity
import com.nocountry.edunotify.domain.mappers.CourseMapper
import com.nocountry.edunotify.domain.mappers.UserMapper
import com.nocountry.edunotify.domain.model.CourseDomain
import com.nocountry.edunotify.domain.model.UserDomain
import com.nocountry.edunotify.domain.repositories.CourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class CourseRepositoryImpl(
    private val service: RetrofitService,
    private val courseMapper: CourseMapper,
    private val userMapper: UserMapper,
    private val userDao: UserDao
) : CourseRepository {
    override suspend fun getCoursesBySchool(schoolId: Int): Flow<List<CourseDomain>> {
        return flow {
            val schoolInfoResponse = service.getSchoolInfo(schoolId)
            val courseDomainList = schoolInfoResponse.levels.flatMap { level ->
                level.years.flatMap { year ->
                    year.courses.map { courseInfo ->
                        courseMapper.mapCourseInfoToCourseDomain(courseInfo)
                    }
                }
            }
            emit(courseDomainList)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun assignCourseToUser(courseId: Int, userId: Int): Flow<UserDomain> {
        val remoteResultAssignCourseToUser =
            service.assignCourseToUser(idCourse = courseId, idUser = userId)
        val userDomain = userMapper.mapUserResponseToUserDomain(remoteResultAssignCourseToUser)

        val userEntity = UserEntity(
            id = userDomain.id,
            name = userDomain.name,
            lastName = userDomain.lastName,
            email = userDomain.email,
            phone = userDomain.phone,
            role = userDomain.role,
            school = userDomain.schoolId,
            courses = userDomain.courses?.map { courseDomain ->
                CourseEntity(
                    courseId = courseDomain.courseId,
                    course = courseDomain.course,
                    notifications = courseDomain.notifications.map { notificationDomain ->
                        NotificationEntity(
                            messageId = notificationDomain.messageId,
                            messageDate = notificationDomain.messageDate,
                            author = notificationDomain.author,
                            title = notificationDomain.title,
                            message = notificationDomain.message,
                            expiration = notificationDomain.expiration
                        )
                    })
            } ?: emptyList()
        )

        userDao.insert(userEntity)

        return flowOf(userDomain)
    }
}