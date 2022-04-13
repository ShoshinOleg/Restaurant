package com.shoshin.domain.usecases.orders

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.Order
import com.shoshin.domain_abstract.repositories.IOrderRepository
import com.shoshin.domain_abstract.usecases.orders.IGetOrderUseCase
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(
    private val orderRepository: IOrderRepository
): IGetOrderUseCase {
    override suspend fun execute(orderId: String): Reaction<Order> =
        orderRepository.getOrder(orderId)
}