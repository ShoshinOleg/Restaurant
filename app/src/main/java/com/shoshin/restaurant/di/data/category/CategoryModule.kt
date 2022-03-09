package com.shoshin.restaurant.di.data.category

import com.shoshin.data.db.entities.categories.CategoryDao
import com.shoshin.data.db.entities.categories.CategoryDataDbMapper
import com.shoshin.data.db.entities.categories.CategoryDbo
import com.shoshin.data.db.main.AppDatabase
import com.shoshin.data.entities.categories.CategoryData
import com.shoshin.data.entities.categories.CategoryDomainDataMapper
import com.shoshin.data.remote.entities.categories.CategoryDataRemoteMapper
import com.shoshin.data.remote.entities.categories.CategoryRemote
import com.shoshin.domain_abstract.common.Mapper
import com.shoshin.domain_abstract.entities.category.Category
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class CategoryModule {
    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao = appDatabase.categoryDao()
    @Provides
    fun provideDataDbMapper(): Mapper<CategoryData, CategoryDbo> = CategoryDataDbMapper()
    @Provides
    fun provideDataRemoteMapper(): Mapper<CategoryData, CategoryRemote> = CategoryDataRemoteMapper()
    @Provides
    fun provideDomainDataMapper(): Mapper<Category, CategoryData> = CategoryDomainDataMapper()
}