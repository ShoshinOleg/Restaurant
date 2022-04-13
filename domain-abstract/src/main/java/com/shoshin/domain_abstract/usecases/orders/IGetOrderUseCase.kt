package com.shoshin.domain_abstract.usecases.orders

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.Order

interface IGetOrderUseCase {
    suspend fun execute(orderId: String): Reaction<Order>
}