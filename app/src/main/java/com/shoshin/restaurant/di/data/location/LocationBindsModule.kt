package com.shoshin.restaurant.di.data.location

import com.shoshin.data.db.source.LocationLocalSource
import com.shoshin.data.interfaces.services.remote.ILocationRemoteService
import com.shoshin.data.interfaces.sources.local.ILocationLocalSource
import com.shoshin.data.interfaces.sources.remote.ILocationRemoteSource
import com.shoshin.data.remote.main.RestaurantService
import com.shoshin.data.remote.sources.LocationRemoteSource
import com.shoshin.data.repositories.LocationRepository
import com.shoshin.domain_abstract.repositories.ILocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface LocationBindsModule {
    @Binds
    fun bindRepository(repository: LocationRepository): ILocationRepository
    @Binds
    fun bindService(restaurantService: RestaurantService): ILocationRemoteService
    @Binds
    fun bindLocalSource(source: LocationLocalSource): ILocationLocalSource
    @Binds
    fun bindRemoteSource(source: LocationRemoteSource): ILocationRemoteSource
}