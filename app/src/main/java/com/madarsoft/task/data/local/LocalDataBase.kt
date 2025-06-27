package com.madarsoft.task.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.data.local.users.UsersDao

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun usersDAO(): UsersDao
}
