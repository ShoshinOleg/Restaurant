package com.shoshin.restaurant.di.data.category

import com.shoshin.data.db.source.CategoryLocalSource
import com.shoshin.data.interfaces.services.remote.ICategoryRemoteService
import com.shoshin.data.interfaces.sources.local.ICategoryLocalSource
import com.shoshin.data.interfaces.sources.remote.ICategoryRemoteSource
import com.shoshin.data.remote.main.RestaurantService
import com.shoshin.data.remote.sources.CategoryRemoteSource
import com.shoshin.data.repositories.CategoryRepository
import com.shoshin.domain_abstract.repositories.ICategoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface CategoryBindsModule {
    @Binds
    fun bindRepository(repository: CategoryRepository): ICategoryRepository
    @Binds
    fun bindService(restaurantService: RestaurantService): ICategoryRemoteService
    @Binds
    fun bindLocalSource(source: CategoryLocalSource): ICategoryLocalSource
    @Binds
    fun bindRemoteSource(source: CategoryRemoteSource): ICategoryRemoteSource
}