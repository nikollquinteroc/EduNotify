package com.nocountry.edunotify.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nocountry.edunotify.data.database.NotificationResponseConverter
import com.nocountry.edunotify.data.utils.Constants

@Entity(tableName = Constants.COURSE_TABLE_NAME)
@TypeConverters(NotificationResponseConverter::class)
data class CourseEntity(
    @PrimaryKey val courseId: Int,
    @ColumnInfo(name = "course") val course: String,
    @ColumnInfo(name = "notifications") val notifications: List<NotificationEntity>
)
