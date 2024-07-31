package com.nocountry.edunotify.data.database.mappers

import com.nocountry.edunotify.data.database.entities.AuthEntity
import com.nocountry.edunotify.data.database.entities.CourseEntity
import com.nocountry.edunotify.data.database.entities.NotificationEntity
import com.nocountry.edunotify.data.database.entities.UserEntity
import com.nocountry.edunotify.domain.model.AuthDomain
import com.nocountry.edunotify.domain.model.CourseDomain
import com.nocountry.edunotify.domain.model.NotificationDomain
import com.nocountry.edunotify.domain.model.UserDomain

class AuthMapperDb {
    fun mapAuthDomainToAuthEntity(authDomain: AuthDomain): AuthEntity {
        return AuthEntity(
            id = 0,
            jwt = authDomain.jwt,
            user = authDomain.user?.let { mapUserDomainToUserEntity(it) } ?: UserEntity(
                id = 0,
                name = "",
                lastName = "",
                email = "",
                phone = "",
                role = "",
                school = 0,
                courses = emptyList()
            )
        )
    }

    fun mapUserEntityToUserDomain(userEntity: UserEntity): UserDomain {
        return UserDomain(
            id = userEntity.id,
            name = userEntity.name,
            lastName = userEntity.lastName,
            email = userEntity.email,
            phone = userEntity.phone,
            role = userEntity.role,
            schoolId = userEntity.school,
            courses = userEntity.courses.map { courseEntity ->
                mapCourseEntityToCourseDomain(courseEntity)
            }
        )
    }

    private fun mapCourseEntityToCourseDomain(courseEntity: CourseEntity): CourseDomain {
        return CourseDomain(
            course = courseEntity.course,
            courseId = courseEntity.courseId,
            notifications = courseEntity.notifications.map { notificationEntity ->
                mapNotificationEntityToNotificationDomain(notificationEntity)

            }
        )
    }

    private fun mapNotificationEntityToNotificationDomain(notificationEntity: NotificationEntity): NotificationDomain {
        return NotificationDomain(
            messageId = notificationEntity.messageId,
            messageDate = notificationEntity.messageDate,
            author = notificationEntity.author,
            title = notificationEntity.title,
            message = notificationEntity.message,
            expiration = notificationEntity.expiration
        )
    }

    private fun mapUserDomainToUserEntity(userDomain: UserDomain): UserEntity {
        return UserEntity(
            id = userDomain.id,
            name = userDomain.name,
            lastName = userDomain.lastName,
            email = userDomain.email,
            phone = userDomain.phone,
            role = userDomain.role,
            school = userDomain.schoolId,
            courses = userDomain.courses?.map { mapCourseDomainToCourseEntity(it) } ?: emptyList()
        )
    }

    private fun mapCourseDomainToCourseEntity(courseDomain: CourseDomain): CourseEntity {
        return CourseEntity(
            course = courseDomain.course,
            courseId = courseDomain.courseId,
            notifications = courseDomain.notifications.map {
                mapNotificationDomainToNotificationEntity(
                    it
                )
            }
        )
    }

    private fun mapNotificationDomainToNotificationEntity(notificationDomain: NotificationDomain): NotificationEntity {
        return NotificationEntity(
            messageId = notificationDomain.messageId,
            messageDate = notificationDomain.messageDate,
            author = notificationDomain.author,
            title = notificationDomain.title,
            message = notificationDomain.message,
            expiration = notificationDomain.expiration
        )
    }

}