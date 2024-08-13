package com.nocountry.edunotify.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nocountry.edunotify.data.database.CourseResponseConverter
import com.nocountry.edunotify.data.database.NotificationResponseConverter
import com.nocountry.edunotify.data.utils.Constants

@Entity(tableName = Constants.USER_TABLE_NAME)
@TypeConverters(CourseResponseConverter::class, NotificationResponseConverter::class)
data class UserEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "role") val role: String,
    @ColumnInfo(name = "school") val school: Int,
    @ColumnInfo(name = "courses") val courses: List<CourseEntity>
)
