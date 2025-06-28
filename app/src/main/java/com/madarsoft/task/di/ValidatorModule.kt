package com.madarsoft.task.di

import com.madarsoft.task.core.BaseValidator
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.domain.validation.AddUserValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ValidatorModule {

    @Provides
    @Singleton
    fun provideUserValidator(): BaseValidator<UserEntity> = AddUserValidator()
} 