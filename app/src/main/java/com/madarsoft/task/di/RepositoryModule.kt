package com.madarsoft.task.di

import com.madarsoft.task.data.local.users.UsersDao
import com.madarsoft.task.data.repositories.UserRepositoryImp
import com.madarsoft.task.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        usersDao: UsersDao
    ): UserRepository = UserRepositoryImp(usersDao)

}
