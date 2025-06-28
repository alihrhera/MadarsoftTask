package com.madarsoft.task.domain.validation

import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.utils.errors.UserInputScreenErrors
import org.junit.Assert.*

import org.junit.Test

class AddUserValidatorTest {

    private val validator = AddUserValidator()

    @Test
    fun `test valid user`() {
        val validUser = UserEntity(name = "John", age = 30, gender = "Male", jobTitle = "Developer")
        try {
            validator.validate(validUser)
        } catch (e: Exception) {
            fail("Validation failed unexpectedly: ${e.message}")
        }
    }

    @Test
    fun `test invalid user - blank name`() {
        val invalidUser = UserEntity(name = "", age = 30, gender = "Male", jobTitle = "Developer")
        try {
            validator.validate(invalidUser)
            fail("Expected UserInputScreenErrors to be thrown")
        } catch (e: UserInputScreenErrors) {
            assertTrue(e.errors.containsKey("name"))
            assertEquals("Name is required", e.errors["name"])
        }
    }

    @Test
    fun `test invalid user - age out of range`() {
        val invalidUser = UserEntity(name = "John", age = 200, gender = "Male", jobTitle = "Developer")
        try {
            validator.validate(invalidUser)
            fail("Expected UserInputScreenErrors to be thrown")
        } catch (e: UserInputScreenErrors) {
            assertTrue(e.errors.containsKey("age"))
            assertEquals("Age must be between 1 and 150", e.errors["age"])
        }
    }

    @Test
    fun `test invalid user - blank gender`() {
        val invalidUser = UserEntity(name = "John", age = 30, gender = "", jobTitle = "Developer")
        try {
            validator.validate(invalidUser)
            fail("Expected UserInputScreenErrors to be thrown")
        } catch (e: UserInputScreenErrors) {
            assertTrue(e.errors.containsKey("gender"))
            assertEquals("Gender is required", e.errors["gender"])
        }
    }

    @Test
    fun `test invalid user - blank jobTitle`() {
        val invalidUser = UserEntity(name = "John", age = 30, gender = "Male", jobTitle = "")
        try {
            validator.validate(invalidUser)
            fail("Expected UserInputScreenErrors to be thrown")
        } catch (e: UserInputScreenErrors) {
            assertTrue(e.errors.containsKey("jobTitle"))
            assertEquals("Job title is required", e.errors["jobTitle"])
        }
    }
}
