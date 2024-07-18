package com.nocountry.edunotify.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nocountry.edunotify.data.api.model.notifications.NotificationResponse

class NotificationResponseConverter {

    @TypeConverter
    fun fromNotificationResponseList(notifications: List<NotificationResponse>): String {
        return Gson().toJson(notifications)
    }

    @TypeConverter
    fun toNotificationResponseList(notificationsString: String): List<NotificationResponse> {
        val listType = object : TypeToken<List<NotificationResponse>>() {}.type
        return Gson().fromJson(notificationsString, listType)
    }
}