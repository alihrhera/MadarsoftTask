package com.madarsoft.task.presentation.adduser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.madarsoft.task.core.BaseDataResponse
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.domain.model.User
import com.madarsoft.task.domain.usecase.AddUserUseCase
import com.madarsoft.task.utils.errors.UserInputScreenErrors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class AddUserViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var addUserUseCase: AddUserUseCase
    private lateinit var viewModel: AddUserViewModel

    private val mockUserEntity = UserEntity(1, "John Doe", 30,"Developer","Male")
    val  emptyUser = UserEntity.emptyUser()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        addUserUseCase = mock(AddUserUseCase::class.java)
        viewModel = AddUserViewModel(addUserUseCase)
    }

    @Test
    fun `test onNameChanged updates state correctly`() = runTest {
        val nameObserver = mock(Observer::class.java) as Observer<String>
        val job = launch {
            viewModel.state.collect {
                nameObserver.onChanged(it.user.name)
            }
        }
        viewModel.onNameChanged("John")
        assertEquals(viewModel.state.value.user.name, "John")
        advanceUntilIdle()
        verify(nameObserver).onChanged("John")
        job.cancel()
    }
    @Test
    fun `test onAgeChanged updates state correctly`() = runTest {
        val ageObserver = mock(Observer::class.java) as Observer<Int>
        val job = launch {
            viewModel.state.collect {
                ageObserver.onChanged(it.user.age)
            }
        }
        viewModel.onAgeChanged("25")
        assertEquals(viewModel.state.value.user.age, 25)
        advanceUntilIdle()
        verify(ageObserver).onChanged(25)
        job.cancel()
    }

    @Test
    fun `test onAgeChanged with invalid value does not crash`() = runTest {
        val ageObserver = mock(Observer::class.java) as Observer<Int>
        val job = launch {
            viewModel.state.collect {
                ageObserver.onChanged(it.user.age)
            }
        }
        viewModel.onAgeChanged("invalid")
        assertEquals(viewModel.state.value.user.age, 0)
        advanceUntilIdle()
        verify(ageObserver).onChanged(0)
        job.cancel()
    }

    @Test
    fun `test onGenderChanged updates state correctly`() = runTest {
        val genderObserver = mock(Observer::class.java) as Observer<String>
        val job = launch {
            viewModel.state.collect {
                genderObserver.onChanged(it.user.gender)
            }
        }
        viewModel.onGenderChanged("female")
        assertEquals(viewModel.state.value.user.gender, "female")
        advanceUntilIdle()
        verify(genderObserver).onChanged("female")

        job.cancel()
    }

    @Test
    fun `test onJobTitleChanged updates state correctly`() = runTest {
        val jobTitleObserver = mock(Observer::class.java) as Observer<String>
        val job = launch {
            viewModel.state.collect {
                jobTitleObserver.onChanged(it.user.jobTitle)
            }
        }
        viewModel.onJobTitleChanged("Manager")
        assertEquals(viewModel.state.value.user.jobTitle, "Manager")
        advanceUntilIdle()
        verify(jobTitleObserver).onChanged("Manager")
        job.cancel()
    }

    @Test
    fun `test submit successfully calls AddUserUseCase`() = runTest {
        val mockResponse = MutableStateFlow<BaseDataResponse<User>>(BaseDataResponse.Loading)
        `when`(addUserUseCase.invoke(mockUserEntity)).thenReturn(mockResponse)
        viewModel.updateUiState( viewModel.state.value.copy(user = mockUserEntity))
        viewModel.submit()
        advanceUntilIdle()
        verify(addUserUseCase).invoke(mockUserEntity)
    }

    @Test
    fun `test submit handles errors from AddUserUseCase`()=runTest {
        val mockErrorResponse = MutableStateFlow<BaseDataResponse<User>>(BaseDataResponse.Loading)
        `when`(addUserUseCase(emptyUser)).thenAnswer {
            mockErrorResponse.value = BaseDataResponse.Error(UserInputScreenErrors(mapOf("name" to "Name is required")))
            mockErrorResponse
        }
        `when`(addUserUseCase(emptyUser)).thenReturn(mockErrorResponse)

        viewModel.updateUiState( viewModel.state.value.copy(user =emptyUser))
        viewModel.submit()
        advanceUntilIdle()
        assertTrue(viewModel.state.value.errors.containsKey("name"))
    }

    @Test
    fun `test submit handles loading state from AddUserUseCase`() = runTest {
        val loadingResponse = MutableStateFlow<BaseDataResponse<User>>(BaseDataResponse.Loading)
        `when`(addUserUseCase(mockUserEntity)).thenReturn(loadingResponse)
        viewModel.updateUiState( viewModel.state.value.copy(user = mockUserEntity))
        viewModel.submit()
        advanceUntilIdle()
        assertTrue(viewModel.state.value.isLoading)
    }


    @Test
    fun `test submit handles unexpected errors from AddUserUseCase`() = runTest {
        val mockErrorResponse = MutableStateFlow<BaseDataResponse<User>>(BaseDataResponse.Loading)
        `when`(addUserUseCase.invoke(mockUserEntity)).thenAnswer {
            mockErrorResponse.value = BaseDataResponse.Error(Throwable("Unexpected Error"))
            mockErrorResponse
        }
        viewModel.updateUiState(viewModel.state.value.copy(user = mockUserEntity))
        viewModel.submit()
        advanceUntilIdle()
        assertTrue(viewModel.state.value.errors.isEmpty())
    }

    @Test
    fun `test general error displays toast message`() = runTest {
        val mockErrorResponse = MutableStateFlow<BaseDataResponse<User>>(BaseDataResponse.Loading)
        `when`(addUserUseCase.invoke(mockUserEntity)).thenAnswer {
            mockErrorResponse.value = BaseDataResponse.Error(Throwable("An unexpected error occurred"))
            mockErrorResponse
        }
        viewModel.updateUiState(viewModel.state.value.copy(user = mockUserEntity))
        viewModel.submit()
        advanceUntilIdle()
        assertEquals(viewModel.toastMessage.value, "An unexpected error occurred")
    }

}
