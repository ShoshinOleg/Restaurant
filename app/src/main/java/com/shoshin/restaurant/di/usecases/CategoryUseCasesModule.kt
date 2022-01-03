package com.shoshin.restaurant.di.usecases

import com.shoshin.domain.usecases.category.GetCategoriesUseCase
import com.shoshin.domain.usecases.category.GetCategoryUseCase
import com.shoshin.domain_abstract.repositories.ICategoryRepository
import com.shoshin.domain_abstract.usecases.category.IGetCategoriesUseCase
import com.shoshin.domain_abstract.usecases.category.IGetCategoryUseCase
import dagger.Module
import dagger.Provides

@Module
class CategoryUseCasesModule {
    @Provides
    fun provideGetCategoriesUseCase(categoryRepository: ICategoryRepository) : IGetCategoriesUseCase {
        return GetCategoriesUseCase(categoryRepository)
    }
    @Provides
    fun provideGetCategoryUseCase(categoryRepository: ICategoryRepository): IGetCategoryUseCase {
        return GetCategoryUseCase(categoryRepository)
    }
}