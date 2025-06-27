package com.madarsoft.task.di

import com.madarsoft.task.core.BaseMapper
import com.madarsoft.task.core.BaseValidator
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.domain.mapper.UserMapper
import com.madarsoft.task.domain.model.User
import com.madarsoft.task.domain.validation.AddUserValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideUserMapper(): BaseMapper<UserEntity, User> = UserMapper()
}