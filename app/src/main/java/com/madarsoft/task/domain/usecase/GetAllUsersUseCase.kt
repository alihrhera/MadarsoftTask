package com.madarsoft.task.domain.usecase

import com.madarsoft.task.core.BaseDataResponse
import com.madarsoft.task.core.BaseMapper
import com.madarsoft.task.core.BaseUseCase
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.domain.model.User
import com.madarsoft.task.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val repository: UserRepository,
    private val mapper: BaseMapper<List<UserEntity>, List<User>>,
) : BaseUseCase<MutableStateFlow<BaseDataResponse<List<User>>>> {
    suspend operator fun invoke():
            MutableStateFlow<BaseDataResponse<List<User>>> {
        return execute()
    }

    private suspend fun mapResponse(response: Flow<BaseDataResponse<List<UserEntity>>>) = mapResponseToStateFlow(response, mapper)

    override suspend fun execute(): MutableStateFlow<BaseDataResponse<List<User>>> {
        return mapResponse(repository.getLocalUser())
    }
}