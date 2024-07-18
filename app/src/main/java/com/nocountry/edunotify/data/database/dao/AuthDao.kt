package com.nocountry.edunotify.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nocountry.edunotify.data.database.entities.AuthEntity
import com.nocountry.edunotify.data.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthDao {
    @Query("SELECT * FROM ${Constants.AUTH_TABLE_NAME} WHERE id = :id")
    fun getAuth(id: Int): Flow<AuthEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(authEntity: AuthEntity)

    @Delete
    fun delete(authEntity: AuthEntity)

    @Update
    fun update(authEntity: AuthEntity)
}