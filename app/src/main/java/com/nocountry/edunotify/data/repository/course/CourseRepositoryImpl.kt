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

    private val fakeUserResponse = UserDomain(
        id = 1,
        name = "Nikoll",
        lastName = "Quintero",
        email = "nikollquinteroc@gmail.com",
        phone = "3057878326",
        role = "parent",
        school = 1,
        courses = listOf(
            CourseDomain(
                course = "Primero de primaria",
                courseId = 1,
                notifications = listOf(
                    NotificationDomain(
                        messageId = 1,
                        messageDate = "12 de Julio 2024",
                        author = "Carlos Morales",
                        title = "Anuncio importante",
                        message = "Mañana no hay clases",
                        expiration = 2
                    )
                )
            )
        )
    )

    private val fakeCoursesResponse = listOf(
        CourseDomain(
            course = "Sala Roja",
            courseId = 1,
            notifications = listOf(
                NotificationDomain(
                    messageId = 1,
                    messageDate = "12 de Julio 2024",
                    author = "Carlos Morales",
                    title = "Anuncio importante",
                    message = "Mañana no hay clases",
                    expiration = 2
                )
            )
        )
    )

    override suspend fun getCoursesBySchool(schoolId: Int): Flow<List<CourseDomain>> {
        return flowOf(fakeCoursesResponse)
    }

    override suspend fun assignCourseToUser(courseId: Int, userId: Int): Flow<UserDomain> {
        //val remoteResultAssignCourseToUser = service.assignCourseToUser(idCourse = courseId, idUser = userId)
        //val userDomain = userMapper.mapUserResponseToUserDomain(remoteResultAssignCourseToUser)
        //return flowOf(userDomain)
        return flowOf(fakeUserResponse)
    }

}