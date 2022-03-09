package com.shoshin.restaurant.di

import com.shoshin.domain_abstract.repositories.IUserTokenRepository
import com.shoshin.restaurant.repositories.UserTokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UserTokenRepositoryModule {
    @Provides
    @Singleton
    fun provideUserTokenRepository(): IUserTokenRepository {
        return UserTokenRepository()
    }
}