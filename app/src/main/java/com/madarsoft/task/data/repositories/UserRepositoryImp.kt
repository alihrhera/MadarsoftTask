package com.madarsoft.task.data.repositories

import com.madarsoft.task.core.BaseDataResponse
import com.madarsoft.task.core.BaseRepositories
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.data.local.users.UsersDao
import com.madarsoft.task.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val localDao: UsersDao
) : BaseRepositories(), UserRepository {
    override suspend fun addUser(user: UserEntity): Flow<BaseDataResponse<UserEntity>> {
        return buildTask {
            val userId = localDao.addRow(user)
            user.apply {
                id = userId.toInt()
            }
        }
    }

    override suspend fun getLocalUser(): Flow<BaseDataResponse<List<UserEntity>>> {
        return buildTask {
            localDao.getAllRows()
        }
    }
}