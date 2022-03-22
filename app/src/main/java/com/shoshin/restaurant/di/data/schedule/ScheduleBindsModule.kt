package com.shoshin.restaurant.di.data.schedule

import com.shoshin.data.interfaces.services.remote.IScheduleRemoteService
import com.shoshin.data.interfaces.sources.remote.IScheduleRemoteSource
import com.shoshin.data.remote.main.RestaurantService
import com.shoshin.data.remote.sources.ScheduleRemoteSource
import com.shoshin.data.repositories.ScheduleRepository
import com.shoshin.domain_abstract.repositories.IScheduleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface ScheduleBindsModule {
    @Binds
    fun bindRepository(repository: ScheduleRepository): IScheduleRepository
    @Binds
    fun bindService(restaurantService: RestaurantService): IScheduleRemoteService
    @Binds
    fun bindRemoteSource(source: ScheduleRemoteSource): IScheduleRemoteSource
}