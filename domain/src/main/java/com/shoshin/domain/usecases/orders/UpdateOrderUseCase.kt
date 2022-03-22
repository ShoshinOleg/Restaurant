package com.shoshin.domain.usecases.orders

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.Order
import com.shoshin.domain_abstract.entities.order.OrderMetadata
import com.shoshin.domain_abstract.repositories.IOrderRepository
import com.shoshin.domain_abstract.usecases.orders.IUpdateOrderUseCase
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(
    private val orderRepository: IOrderRepository
): IUpdateOrderUseCase {
    override suspend fun execute(order: Order): Reaction<OrderMetadata> =
        orderRepository.updateOrder(order)
}