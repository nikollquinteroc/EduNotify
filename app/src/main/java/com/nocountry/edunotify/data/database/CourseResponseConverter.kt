package com.nocountry.edunotify.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nocountry.edunotify.data.api.model.courses.CourseResponse
import com.nocountry.edunotify.data.database.entities.CourseEntity

class CourseResponseConverter {
    @TypeConverter
    fun fromCourseResponseList(courses: List<CourseEntity>): String {
        return Gson().toJson(courses)
    }

    @TypeConverter
    fun toCourseResponseList(coursesString: String): List<CourseEntity> {
        val listType = object : TypeToken<List<CourseEntity>>() {}.type
        return Gson().fromJson(coursesString, listType)
    }
}