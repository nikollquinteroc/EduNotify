package com.nocountry.edunotify.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nocountry.edunotify.data.utils.Constants

@Entity(tableName = Constants.NOTIFICATION_TABLE_NAME)
data class NotificationEntity(
    @PrimaryKey val messageId: Int,
    @ColumnInfo(name = "messageDate") val messageDate: List<Int>,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "expiration") val expiration: Int
)
