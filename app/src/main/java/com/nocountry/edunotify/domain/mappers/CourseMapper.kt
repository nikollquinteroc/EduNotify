package com.nocountry.edunotify.domain.mappers

import com.nocountry.edunotify.data.api.model.courses.CourseResponse
import com.nocountry.edunotify.data.api.model.schools.CourseInfo
import com.nocountry.edunotify.domain.model.CourseDomain

class CourseMapper(private val notificationMapper: NotificationMapper) {
    fun mapCourseResponseToCourseDomain(courseResponse: CourseResponse): CourseDomain {
        return CourseDomain(
            course = courseResponse.course,
            courseId = courseResponse.courseId,
            notifications = courseResponse.messages.map { notificationResponse ->
                notificationMapper.mapNotificationResponseToNotificationDomain(
                    notificationResponse
                )
            }
        )
    }

    fun mapCourseInfoToCourseDomain(courseInfo: CourseInfo): CourseDomain {
        return CourseDomain(
            course = courseInfo.course,
            courseId = courseInfo.courseId,
            notifications = emptyList() // Assuming CourseInfo doesn't have notifications, or adapt as needed
        )
    }
}