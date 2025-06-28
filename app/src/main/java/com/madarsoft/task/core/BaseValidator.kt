package com.madarsoft.task.core

interface BaseValidator< T> {
    /**
     * Validate the given value.
     * @throws Exception if the value is invalid.
     */
    fun validate(value: T)
}
