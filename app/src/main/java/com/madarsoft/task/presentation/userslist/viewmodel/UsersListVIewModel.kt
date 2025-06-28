package com.madarsoft.task.presentation.userslist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.madarsoft.task.core.BaseDataResponse
import com.madarsoft.task.domain.usecase.GetAllUsersUseCase
import com.madarsoft.task.presentation.userslist.compos.UsersScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListVIewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UsersScreenUiState())
    val state: StateFlow<UsersScreenUiState> = _state


    val isLoading = state.map { it.isLoading }.asLiveData()
    val isEmpty = state.map { it. isEmpty}.asLiveData()
    val users = state.map { it. users}.asLiveData()

    private val _toastMessage = MutableStateFlow<String?>("")
    val toastMessage: StateFlow<String?> = _toastMessage

    fun updateUiState(newState: UsersScreenUiState) {
        _state.value = newState
    }

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        Log.e("getAllUsers", "getAllUsers: ", )
        updateUiState(UsersScreenUiState(isLoading = true))
        viewModelScope.launch {
            getAllUsersUseCase().collectLatest {
                updateUiState(UsersScreenUiState(isLoading = it is BaseDataResponse.Loading))
                when (it) {
                    is BaseDataResponse.Error -> _toastMessage.value = it.throwable.message ?: ""
                    is BaseDataResponse.Success -> {
                        updateUiState(UsersScreenUiState(users = it.data))
                    }
                    else -> {}
                }
            }
        }
    }

}