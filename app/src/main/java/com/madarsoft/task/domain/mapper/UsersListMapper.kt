package com.madarsoft.task.domain.mapper

import com.madarsoft.task.core.BaseMapper
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.domain.model.User
import javax.inject.Inject

class UsersListMapper @Inject constructor(): BaseMapper<List<UserEntity>, List<User>> {
    override fun map(input: List<UserEntity>): List<User> {
        return  input.map {
            User(
                id = it.id,
                name = it.name,
                age = it.age,
                gender = it.gender,
                jobTitle = it.jobTitle
            )
        }
    }
}