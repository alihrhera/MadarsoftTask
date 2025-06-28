package com.madarsoft.task.presentation.userslist.compos

import android.util.Log
import com.madarsoft.task.domain.model.User

data class UsersScreenUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
) {
    val isEmpty: Boolean
        get() {
            return users.isEmpty()
        }
}