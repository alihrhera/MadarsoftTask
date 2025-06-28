package com.madarsoft.task.presentation.adduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.madarsoft.task.core.BaseDataResponse
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.domain.usecase.AddUserUseCase
import com.madarsoft.task.presentation.adduser.compos.AddUserUiState
import com.madarsoft.task.utils.errors.UserInputScreenErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel
@Inject constructor(
    private val addNewUserUseCase: AddUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddUserUiState())
    val state: StateFlow<AddUserUiState> = _state

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> get() = _toastMessage

    fun updateUiState(newState: AddUserUiState) {
        _state.value = newState
    }

    val nameError = state.map { it.errors["name"] }.asLiveData()
    val ageError = state.map { it.errors["age"] }.asLiveData()
    val genderError = state.map { it.errors["gender"] }.asLiveData()
    val jobTitleError = state.map { it.errors["jobTitle"] }.asLiveData()
    val isLoading = state.map { it.isLoading }.asLiveData()


    fun onNameChanged(newName: String) {
        updateUiState(
            _state.value.copy(
                user = _state.value.user.copy(name = newName),
                errors = _state.value.errors - "name",
            )
        )
    }

    fun onAgeChanged(age: String) {
        val newAge = age.toIntOrNull() ?: 0
        updateUiState(
            _state.value.copy(
                user = _state.value.user.copy(age = newAge),
                errors = _state.value.errors - "age",
            )
        )
    }

    fun onGenderChanged(gender: String) {
        updateUiState(
            _state.value.copy(
                user = _state.value.user.copy(gender = gender),
                errors = _state.value.errors - "gender",
            )
        )
    }

    fun onJobTitleChanged(newJobTitle: String) {
        updateUiState(
            _state.value.copy(
                user = _state.value.user.copy(jobTitle = newJobTitle),
                errors = _state.value.errors - "jobTitle",
            )
        )
    }

    fun submit() {
        viewModelScope.launch {
            addUser(state.value.user)
        }
    }

    suspend fun addUser(user: UserEntity) {
        addNewUserUseCase(user)
            .collectLatest { response ->
                updateUiState(_state.value.copy(isLoading = response is BaseDataResponse.Loading))
                when (response) {
                    is BaseDataResponse.Error -> {
                        if (response.throwable is UserInputScreenErrors) {
                            updateUiState(_state.value.copy(errors = response.throwable.errors))
                        } else {
                            _toastMessage.value = response.throwable.message
                        }
                    }

                    is BaseDataResponse.Success -> {
                        _state.value = AddUserUiState()
                        updateUiState(AddUserUiState())
                        _toastMessage.value = "User added successfully"
                    }

                    else -> {}
                }
            }
    }
}