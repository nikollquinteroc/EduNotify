package com.nocountry.edunotify.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nocountry.edunotify.data.api.model.courses.CourseResponse

class CourseResponseConverter {
    @TypeConverter
    fun fromCourseResponseList(courses: List<CourseResponse>): String {
        return Gson().toJson(courses)
    }

    @TypeConverter
    fun toCourseResponseList(coursesString: String): List<CourseResponse> {
        val listType = object : TypeToken<List<CourseResponse>>() {}.type
        return Gson().fromJson(coursesString, listType)
    }
}