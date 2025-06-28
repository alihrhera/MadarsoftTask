package com.madarsoft.task.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.madarsoft.task.core.BaseDataResponse
import com.madarsoft.task.core.BaseMapper
import com.madarsoft.task.core.BaseValidator
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.domain.model.User
import com.madarsoft.task.domain.repository.UserRepository
import com.madarsoft.task.domain.usecase.AddUserUseCase
import com.madarsoft.task.utils.errors.UserInputScreenErrors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class AddUserUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockRepository: UserRepository

    @Mock
    private lateinit var mockMapper: BaseMapper<UserEntity, User>

    @Mock
    private lateinit var mockValidator: BaseValidator<UserEntity>

    private lateinit var addUserUseCase: AddUserUseCase

    private val userEntity = UserEntity(name = "John", age = 25, gender = "Male", jobTitle = "Developer")
    private val invalidUserEntity = UserEntity.emptyUser()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        addUserUseCase = AddUserUseCase(mockRepository, mockMapper, mockValidator)
    }

    @Test
    fun `test invoke success`() = runTest {
        val mappedUser = User(id = 1, name = "John", age = 25, gender = "Male", jobTitle = "Developer")
        doNothing().`when`(mockValidator).validate(userEntity)
        `when`(mockRepository.addUser(userEntity)).thenReturn(flowOf(BaseDataResponse.Success(userEntity)))
        `when`(mockMapper.map(userEntity)).thenReturn(mappedUser)
        val result = addUserUseCase(userEntity)
        assertTrue(result.value is BaseDataResponse.Success)
        val user = (result.value as BaseDataResponse.Success).data
        assertEquals(user.id, 1)
        assertEquals(user.name, "John")
    }

    @Test
    fun `test invoke validation failure`() = runTest {
        val validationError = UserInputScreenErrors(mapOf("name" to "Name is required"))
        doAnswer { throw validationError }.`when`(mockValidator).validate(invalidUserEntity)
        val result = addUserUseCase(invalidUserEntity)
        assertTrue(result.value is BaseDataResponse.Error)
        val error = (result.value as BaseDataResponse.Error).throwable
        assertFailsWith<UserInputScreenErrors> { throw error }
    }

}
