package com.shoshin.restaurant.di.usecases

import com.shoshin.domain.usecases.orders.GetOrdersMetadataUseCase
import com.shoshin.domain.usecases.orders.UpdateOrderUseCase
import com.shoshin.domain_abstract.usecases.orders.IGetOrdersMetadataUseCase
import com.shoshin.domain_abstract.usecases.orders.IUpdateOrderUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface OrderUseCasesModule {
    @Binds
    fun bindUpdateOrderUseCase(useCase: UpdateOrderUseCase): IUpdateOrderUseCase
    @Binds
    fun bindGetOrdersMetadataUseCase(useCase: GetOrdersMetadataUseCase): IGetOrdersMetadataUseCase
}