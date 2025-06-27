package com.madarsoft.task.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

open class BaseRepositories {
    private val defaultDispatcher = Dispatchers.IO


    /**
     * Builds a suspend function that returns a [BaseDataResponse].
     *
     * @param task the suspend function to build.
     * @return a flow that emits a [BaseDataResponse].
     */
    protected suspend fun <T> buildTask(
        dispatcher: CoroutineDispatcher = defaultDispatcher,
        task: suspend () -> T
    ): Flow<BaseDataResponse<T>> = flow<BaseDataResponse<T>> {
        emit(BaseDataResponse.Success(data = task()))
    }
        .flowOn(dispatcher)
        .onStart {
            emit(BaseDataResponse.Loading)
        }
        .catch { throwable ->
            emit(BaseDataResponse.Error(throwable = throwable))
        }


}