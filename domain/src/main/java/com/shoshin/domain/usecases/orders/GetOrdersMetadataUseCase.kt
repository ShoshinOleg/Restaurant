package com.shoshin.domain.usecases.orders

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.OrderMetadata
import com.shoshin.domain_abstract.repositories.IOrderRepository
import com.shoshin.domain_abstract.usecases.orders.IGetOrdersMetadataUseCase
import javax.inject.Inject

class GetOrdersMetadataUseCase @Inject constructor(
    private val orderRepository: IOrderRepository
): IGetOrdersMetadataUseCase {
    override suspend fun execute(): Reaction<List<OrderMetadata>> = orderRepository.getOrdersMetadata()
}