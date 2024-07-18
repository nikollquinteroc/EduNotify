package com.nocountry.edunotify.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nocountry.edunotify.data.utils.Constants

@Entity(tableName = Constants.AUTH_TABLE_NAME)
data class AuthEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "jwt") val jwt: String,
    @ColumnInfo(name = "user") val user: UserEntity,
)
