package com.madarsoft.task.core

sealed class BaseDataResponse<out T> {
    object Loading : BaseDataResponse<Nothing>()
    data class Error(val throwable: Throwable) : BaseDataResponse<Nothing>()
    data class Success<T>(val data: T) : BaseDataResponse<T>()
}