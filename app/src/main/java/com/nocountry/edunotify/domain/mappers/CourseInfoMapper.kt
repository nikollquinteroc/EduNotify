package com.nocountry.edunotify.domain.mappers

import com.nocountry.edunotify.data.api.model.schools.CourseInfo
import com.nocountry.edunotify.domain.model.CourseInfoDomain

class CourseInfoMapper {
    fun mapCourseInfoToCourseInfoDomain(courseInfo: CourseInfo): CourseInfoDomain {
        return CourseInfoDomain(courseId = courseInfo.courseId, course = courseInfo.course)
    }
}