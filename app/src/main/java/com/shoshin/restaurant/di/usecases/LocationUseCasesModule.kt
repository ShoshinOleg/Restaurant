package com.shoshin.restaurant.di.usecases

import com.shoshin.domain.usecases.locations.GetLocationsUseCase
import com.shoshin.domain.usecases.locations.SetLocationUseCase
import com.shoshin.domain_abstract.usecases.locations.IGetLocationsUseCase
import com.shoshin.domain_abstract.usecases.locations.ISetLocationUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface LocationUseCasesModule {
    @Binds
    fun bindGetLocationUseCase(useCase: GetLocationsUseCase): IGetLocationsUseCase
    @Binds
    fun setLocationUseCase(useCase: SetLocationUseCase): ISetLocationUseCase
}