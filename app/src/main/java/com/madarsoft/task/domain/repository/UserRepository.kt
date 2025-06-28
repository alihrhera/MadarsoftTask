package com.madarsoft.task.domain.repository

import com.madarsoft.task.core.BaseDataResponse
import com.madarsoft.task.data.local.users.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun addUser(user: UserEntity): Flow<BaseDataResponse<UserEntity>>
}