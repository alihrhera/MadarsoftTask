package com.madarsoft.task.core

import com.madarsoft.task.core.BaseDataResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface BaseUseCase<in Param, out Result> {
    suspend fun execute(params: Param): Result

    suspend fun <T, R> mapResponseToStateFlow(
        response: Flow<BaseDataResponse<T>>,
        mapper: BaseMapper<T, R>
    ): MutableStateFlow<BaseDataResponse<R>> {

        val state = MutableStateFlow<BaseDataResponse<R>>(BaseDataResponse.Loading)

        response.collect { result ->
            val mapped = when (result) {
                is BaseDataResponse.Success -> {
                    BaseDataResponse.Success(mapper.map(input = result.data))
                }

                is BaseDataResponse.Error -> {
                    BaseDataResponse.Error(result.throwable)
                }

                is BaseDataResponse.Loading -> {
                    BaseDataResponse.Loading
                }
            }
            state.value = mapped
        }

        return state
    }
}