package com.nocountry.edunotify.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nocountry.edunotify.data.database.entities.UserEntity
import com.nocountry.edunotify.data.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM ${Constants.USER_TABLE_NAME} WHERE id = :id")
    fun getUserById(id: Int): Flow<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity)
}