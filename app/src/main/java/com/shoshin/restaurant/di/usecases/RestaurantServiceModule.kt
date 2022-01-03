package com.shoshin.restaurant.di.usecases

import com.shoshin.data.api.main.RestaurantService
import com.shoshin.data.api.repositories.CategoryRepository
import com.shoshin.data.api.repositories.DishRepository
import com.shoshin.domain_abstract.repositories.ICategoryRepository
import com.shoshin.domain_abstract.repositories.IDishRepository
import com.shoshin.domain_abstract.repositories.IUserTokenRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RestaurantServiceModule {
    @Provides
    @Singleton
    fun provideRestaurantService(userTokenRepository: IUserTokenRepository): RestaurantService {
        return RestaurantService.getInstance(userTokenRepository)
    }

    @Provides
    fun provideCategoryRepository(restaurantService: RestaurantService) : ICategoryRepository {
        return CategoryRepository(restaurantService)
    }

    @Provides
    fun provideDishesRepository(restaurantService: RestaurantService) : IDishRepository {
        return DishRepository(restaurantService)
    }
}