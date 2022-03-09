package com.shoshin.restaurant.di.data.user

import com.shoshin.data.interfaces.services.remote.IUserRemoteService
import com.shoshin.data.remote.main.RestaurantService
import com.shoshin.data.repositories.UserRepository
import com.shoshin.domain_abstract.repositories.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface UserBindsModule {
    @Binds
    fun bindRepository(repository: UserRepository): IUserRepository
    @Binds
    fun bindService(service: RestaurantService): IUserRemoteService
}