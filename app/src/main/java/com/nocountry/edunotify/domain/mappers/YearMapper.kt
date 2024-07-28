package com.nocountry.edunotify.domain.mappers

import com.nocountry.edunotify.data.api.model.schools.Year
import com.nocountry.edunotify.domain.model.YearDomain

class YearMapper(private val courseInfoMapper: CourseInfoMapper) {
    fun mapYearToYearDomain(year: Year): YearDomain {
        return YearDomain(
            year = year.year,
            courses = year.courses.map { courseInfo ->
                courseInfoMapper.mapCourseInfoToCourseInfoDomain(courseInfo)
            }
        )
    }
}