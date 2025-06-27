package com.madarsoft.task.domain.mapper

import com.madarsoft.task.core.BaseMapper
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.domain.model.User

class UserMapper : BaseMapper<UserEntity, User> {
    override fun map(input: UserEntity): User {
        return  User(
            id = input.id,
            name = input.name,
            age = input.age,
            gender = input.gender,
            jobTitle = input.jobTitle
        )
    }
}