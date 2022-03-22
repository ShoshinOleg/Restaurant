package com.shoshin.restaurant.di.data.cart

import com.shoshin.data.db.entities.cart1.CartDao1
import com.shoshin.data.db.entities.cart1.CartItemDbo1
import com.shoshin.data.db.entities.cart1.CartItemDomainDbMapper
import com.shoshin.data.db.main.AppDatabase
import com.shoshin.domain_abstract.common.Mapper
import com.shoshin.domain_abstract.entities.cart.CartItem1
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class CartModule1 {
    @Provides
    fun provideCartDao(appDatabase: AppDatabase): CartDao1 = appDatabase.cartDao1()
    @Provides
    fun provideDomainDbMapper(): Mapper<CartItem1, CartItemDbo1> = CartItemDomainDbMapper()
}

//@InstallIn(SingletonComponent::class)
//@Module
//class CategoryModule {
//    @Provides
//    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao = appDatabase.categoryDao()
//    @Provides
//    fun provideDataDbMapper(): Mapper<CategoryData, CategoryDbo> = CategoryDataDbMapper()
//    @Provides
//    fun provideDataRemoteMapper(): Mapper<CategoryData, CategoryRemote> = CategoryDataRemoteMapper()
//    @Provides
//    fun provideDomainDataMapper(): Mapper<Category, CategoryData> = CategoryDomainDataMapper()
//}