package com.madarsoft.task.data.local.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun addRows(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun addRow(user: UserEntity): Long


    @Query("SELECT * FROM users")
    suspend fun getAllRows(): List<UserEntity>

}