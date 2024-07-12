package com.nocountry.edunotify.domain.mappers

import com.nocountry.edunotify.data.api.model.user.UserResponse
import com.nocountry.edunotify.domain.model.UserDomain

class UserMapper(private val courseMapper: CourseMapper) {

    fun mapUserResponseToUserDomain(userResponse: UserResponse): UserDomain {
        return UserDomain(
            id = userResponse.id,
            name = userResponse.name,
            lastName = userResponse.lastName,
            email = userResponse.email,
            phone = userResponse.phone,
            role = userResponse.role,
            schoolId = userResponse.school,
            courses = userResponse.courses?.map { courseResponse ->
                courseMapper.mapCourseResponseToCourseDomain(
                    courseResponse
                )
            }
        )
    }
}