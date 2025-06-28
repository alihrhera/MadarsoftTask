package com.madarsoft.task.domain.validation

import com.madarsoft.task.core.BaseValidator
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.utils.errors.UserInputScreenErrors
import javax.inject.Inject

class AddUserValidator @Inject constructor(): BaseValidator<UserEntity> {

    @Throws(UserInputScreenErrors::class)
    override fun validate(value: UserEntity) {
        val errors = mutableMapOf<String, String>()

        if (value.name.isBlank()) {
            errors["name"] = "Name is required"
        }
        if (value.age !in 1..150) {
            errors["age"] = "Age must be between 1 and 150"
        }
        if (value.gender.isBlank()) {
            errors["gender"] = "Gender is required"
        }
        if (value.jobTitle.isBlank()) {
            errors["jobTitle"] = "Job title is required"
        }

        if (errors.isNotEmpty()) {
            throw UserInputScreenErrors(errors)
        }
    }
}
