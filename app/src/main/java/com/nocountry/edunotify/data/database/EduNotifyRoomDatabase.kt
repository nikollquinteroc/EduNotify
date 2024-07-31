package com.nocountry.edunotify.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nocountry.edunotify.data.database.dao.AuthDao
import com.nocountry.edunotify.data.database.dao.UserDao
import com.nocountry.edunotify.data.database.entities.AuthEntity
import com.nocountry.edunotify.data.database.entities.CourseEntity
import com.nocountry.edunotify.data.database.entities.NotificationEntity
import com.nocountry.edunotify.data.database.entities.UserEntity

@Database(
    entities = [AuthEntity::class, UserEntity::class, CourseEntity::class, NotificationEntity::class],
    version = 1
)
@TypeConverters(UserTypeConverter::class, CourseResponseConverter::class, NotificationResponseConverter::class, ListTypeConverter::class)
abstract class EduNotifyRoomDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getAuthDao(): AuthDao

    companion object {
        @Volatile
        private var INSTANCE: EduNotifyRoomDatabase? = null

        fun getDatabase(context: Context): EduNotifyRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EduNotifyRoomDatabase::class.java,
                    "edu_notify_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}