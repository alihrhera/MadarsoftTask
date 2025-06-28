package com.madarsoft.task.data.repositories

import com.madarsoft.task.core.BaseDataResponse
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.data.local.users.UsersDao
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class UserRepositoryImpTest {

    private lateinit var userRepository: UserRepositoryImp
    private val mockUsersDao = mockk<UsersDao>()

    @Before
    fun setUp() {
        userRepository = UserRepositoryImp(mockUsersDao)
    }

    @Test
    fun `test addUser success`() = runTest {
        val userEntity = UserEntity(name = "John", age = 25, gender = "Male", jobTitle = "Developer")
        val mockUserId = 1L
        coEvery { mockUsersDao.addRow(any()) } returns mockUserId
        val result = userRepository.addUser(userEntity)
        val user = result.last()
        assertTrue(user is BaseDataResponse.Success)
        assertEquals((user as BaseDataResponse.Success).data.id, mockUserId.toInt())
    }
}
