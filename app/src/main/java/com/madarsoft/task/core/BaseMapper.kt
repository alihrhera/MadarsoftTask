package com.madarsoft.task.core

interface BaseMapper<in T, out R> {
    fun map(input: T): R
}