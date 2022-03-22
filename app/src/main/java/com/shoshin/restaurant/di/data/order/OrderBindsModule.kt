package com.shoshin.restaurant.di.data.order

import com.shoshin.data.interfaces.services.remote.IOrderRemoteService
import com.shoshin.data.interfaces.sources.remote.IOrderRemoteSource
import com.shoshin.data.remote.main.RestaurantService
import com.shoshin.data.remote.sources.OrderRemoteSource
import com.shoshin.data.repositories.OrderRepository
import com.shoshin.domain_abstract.repositories.IOrderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface OrderBindsModule {
    @Binds
    fun bindRepository(repository: OrderRepository): IOrderRepository
    @Binds
    fun bindService(restaurantService: RestaurantService): IOrderRemoteService
    @Binds
    fun bindRemoteService(source: OrderRemoteSource): IOrderRemoteSource
}