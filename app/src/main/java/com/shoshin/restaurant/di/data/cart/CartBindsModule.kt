package com.shoshin.restaurant.di.data.cart

import com.shoshin.data.db.source.CartLocalSource
import com.shoshin.data.interfaces.sources.local.ICartLocalSource
import com.shoshin.data.repositories.CartRepository
import com.shoshin.domain_abstract.repositories.ICartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface CartBindsModule {
    @Binds
    fun bindRepository(repository: CartRepository): ICartRepository
    @Binds
    fun bindLocalSource(source: CartLocalSource): ICartLocalSource
}

//@InstallIn(SingletonComponent::class)
//@Module
//interface CategoryBindsModule {
//    @Binds
//    fun bindRepository(repository: CategoryRepository): ICategoryRepository
//    @Binds
//    fun bindService(restaurantService: RestaurantService): ICategoryRemoteService
//    @Binds
//    fun bindLocalSource(source: CategoryLocalSource): ICategoryLocalSource
//    @Binds
//    fun bindRemoteSource(source: CategoryRemoteSource): ICategoryRemoteSource
//}