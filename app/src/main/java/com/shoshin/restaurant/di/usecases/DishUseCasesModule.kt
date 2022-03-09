package com.shoshin.restaurant.di.usecases

import com.shoshin.domain.usecases.dish.GetDishesByCategoryUseCase
import com.shoshin.domain_abstract.repositories.IDishRepository
import com.shoshin.domain_abstract.usecases.dish.IGetDishesByCategoryUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface DishUseCasesModule {
    @Binds
    fun bindGetDishesByCategoryUseCase(useCase: GetDishesByCategoryUseCase)
        : IGetDishesByCategoryUseCase
}