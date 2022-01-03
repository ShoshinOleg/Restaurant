package com.shoshin.restaurant.di

import com.shoshin.restaurant.di.usecases.CategoryUseCasesModule
import com.shoshin.restaurant.di.usecases.DishUseCasesModule
import com.shoshin.restaurant.di.usecases.RestaurantServiceModule
import com.shoshin.restaurant.ui.fragments.categories.CategoriesViewModel
import com.shoshin.restaurant.ui.fragments.categories.category.MenuCategoryViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules =
    [
        ContextModule::class,
        UserTokenRepositoryModule::class,
        RestaurantServiceModule::class,
        CategoryUseCasesModule::class,
        DishUseCasesModule::class
    ]
)
interface AppComponent {
    fun inject(viewModel: CategoriesViewModel)
    fun inject(viewModel: MenuCategoryViewModel)
}