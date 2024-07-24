package com.nocountry.edunotify.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nocountry.edunotify.data.api.model.notifications.NotificationResponse
import com.nocountry.edunotify.data.database.entities.NotificationEntity

class NotificationResponseConverter {

    @TypeConverter
    fun fromNotificationResponseList(notifications: List<NotificationEntity>): String {
        return Gson().toJson(notifications)
    }

    @TypeConverter
    fun toNotificationResponseList(notificationsString: String): List<NotificationEntity> {
        val listType = object : TypeToken<List<NotificationEntity>>() {}.type
        return Gson().fromJson(notificationsString, listType)
    }
}