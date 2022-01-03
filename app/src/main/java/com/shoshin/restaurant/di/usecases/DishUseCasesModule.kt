package com.shoshin.restaurant.di.usecases

import com.shoshin.domain.usecases.dish.GetDishesByCategoryUseCase
import com.shoshin.domain_abstract.repositories.IDishRepository
import com.shoshin.domain_abstract.usecases.dish.IGetDishesByCategoryUseCase
import dagger.Module
import dagger.Provides

@Module
class DishUseCasesModule {
    @Provides
    fun provideGetDishesByCategoryUseCase(dishRepo: IDishRepository)
        : IGetDishesByCategoryUseCase
    {
        return GetDishesByCategoryUseCase(dishRepo)
    }
}