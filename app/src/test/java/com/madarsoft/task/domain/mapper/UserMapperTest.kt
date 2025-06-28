package com.madarsoft.task.domain.mapper

import com.madarsoft.task.data.local.users.UserEntity
import org.junit.Assert.*
import org.junit.Test

class UserMapperTest {

    private val mapper = UserMapper()

    @Test
    fun `test map UserEntity to User`() {
        val userEntity = UserEntity(id = 1, name = "John", age = 30, gender = "Male", jobTitle = "Developer")

        val user = mapper.map(userEntity)

        assertEquals(userEntity.id, user.id)
        assertEquals(userEntity.name, user.name)
        assertEquals(userEntity.age, user.age)
        assertEquals(userEntity.gender, user.gender)
        assertEquals(userEntity.jobTitle, user.jobTitle)
    }

    @Test
    fun `test map empty UserEntity`() {
        val userEntity = UserEntity(id = 0, name = "", age = 0, gender = "", jobTitle = "")

        val user = mapper.map(userEntity)

        assertEquals(userEntity.id, user.id)
        assertEquals(userEntity.name, user.name)
        assertEquals(userEntity.age, user.age)
        assertEquals(userEntity.gender, user.gender)
        assertEquals(userEntity.jobTitle, user.jobTitle)
    }
}
