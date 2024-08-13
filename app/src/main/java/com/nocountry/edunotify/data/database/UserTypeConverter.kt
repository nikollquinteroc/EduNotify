package com.nocountry.edunotify.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nocountry.edunotify.data.database.entities.UserEntity

class UserTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromUserEntity(userEntity: UserEntity?): String? {
        return userEntity?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toUserEntity(userEntityJson: String?): UserEntity? {
        return userEntityJson?.let {
            val type = object : TypeToken<UserEntity>() {}.type
            gson.fromJson(it, type)
        }
    }
}