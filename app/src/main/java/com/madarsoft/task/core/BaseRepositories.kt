package com.madarsoft.task.core

import kotlinx.coroutines.Dispatchers
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
    protected suspend fun <T> buildTask(task: suspend () -> T) = flow<BaseDataResponse<T>> {
        emit(BaseDataResponse.Success(data = task()))
    }
        .flowOn(defaultDispatcher)
        .onStart {
            emit(BaseDataResponse.Loading)
        }
        .catch { throwable ->
            emit(BaseDataResponse.Error(throwable = throwable))
        }


}