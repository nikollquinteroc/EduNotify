package com.nocountry.edunotify.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nocountry.edunotify.data.database.dao.UserDao
import com.nocountry.edunotify.data.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class EduNotifyRoomDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: EduNotifyRoomDatabase? = null


    }
}