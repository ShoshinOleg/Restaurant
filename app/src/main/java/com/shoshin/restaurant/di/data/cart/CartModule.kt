package com.shoshin.restaurant.di.data.cart

import com.shoshin.data.db.entities.cart.CartDao
import com.shoshin.data.db.main.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class CartModule {
    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): CartDao = appDatabase.cartDao()
}