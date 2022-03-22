package com.shoshin.restaurant.di.usecases

import com.shoshin.domain.usecases.category.GetCategoriesUseCase
import com.shoshin.domain_abstract.usecases.category.IGetCategoriesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface CategoryUseCasesModule {
    @Binds
    fun bindGetCategoriesUseCase(useCase: GetCategoriesUseCase) : IGetCategoriesUseCase
}