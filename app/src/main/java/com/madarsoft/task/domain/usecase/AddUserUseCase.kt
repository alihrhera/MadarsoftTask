package com.madarsoft.task.domain.usecase

import com.madarsoft.task.core.BaseDataResponse
import com.madarsoft.task.core.BaseMapper
import com.madarsoft.task.core.BaseUseCase
import com.madarsoft.task.core.BaseUseCaseWithPrams
import com.madarsoft.task.core.BaseValidator
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.domain.model.User
import com.madarsoft.task.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val mapper: BaseMapper<UserEntity, User>,
    private val validator: BaseValidator<UserEntity>
) : BaseUseCaseWithPrams<UserEntity, MutableStateFlow<BaseDataResponse<User>>> {
    suspend operator fun invoke(user: UserEntity):
            MutableStateFlow<BaseDataResponse<User>> {
        return try {
            validator.validate(user)
            execute(user)
        } catch (e: Throwable) {
            MutableStateFlow(BaseDataResponse.Error(throwable = e))
        }
    }

    private suspend fun mapResponse(response: Flow<BaseDataResponse<UserEntity>>) = mapResponseToStateFlow(response, mapper)

    override suspend fun execute(params: UserEntity): MutableStateFlow<BaseDataResponse<User>> {
        return mapResponse(repository.addUser(params))
    }
}