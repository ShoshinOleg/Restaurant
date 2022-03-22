package com.shoshin.restaurant.di.usecases

import com.shoshin.domain.usecases.schedule.GetDefaultScheduleUseCase
import com.shoshin.domain_abstract.usecases.schedule.IGetDefaultScheduleUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface ScheduleUseCaseModule {
    @Binds
    fun bindGetDefaultScheduleUseCase(useCase: GetDefaultScheduleUseCase): IGetDefaultScheduleUseCase
}