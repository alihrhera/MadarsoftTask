package com.madarsoft.task.data.local.users

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val name: String,
    val age: Int,
    val jobTitle: String,
    val gender: String
) {

    companion object{
        fun emptyUser() = UserEntity(
            name = "",
            age = 0,
            jobTitle = "",
            gender = ""
        )
    }
}
