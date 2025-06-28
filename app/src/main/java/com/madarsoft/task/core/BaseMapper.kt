package com.madarsoft.task.core

interface BaseMapper<T, R> {
    fun map(input: T): R
}