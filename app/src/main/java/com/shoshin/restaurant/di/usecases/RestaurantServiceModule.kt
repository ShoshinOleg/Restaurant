package com.shoshin.restaurant.di.usecases

import com.shoshin.data.remote.main.RestaurantService
import com.shoshin.data.repositories.DishRepository
import com.shoshin.domain_abstract.repositories.IDishRepository
import com.shoshin.domain_abstract.repositories.IUserTokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RestaurantServiceModule {
    @Provides
    @Singleton
    fun provideRestaurantService(userTokenRepository: IUserTokenRepository): RestaurantService {
        return RestaurantService.getInstance(userTokenRepository)
    }

    @Provides
    fun provideDishesRepository(restaurantService: RestaurantService) : IDishRepository {
        return DishRepository(restaurantService)
    }
}