package com.madarsoft.task.presentation.adduser.compos

import com.madarsoft.task.data.local.users.UserEntity

data class AddUserUiState(
    val isLoading: Boolean = false,
    val errors:Map<String,String> = emptyMap(),
    val user: UserEntity = UserEntity.Companion.emptyUser(),
)