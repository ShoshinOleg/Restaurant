package com.shoshin.domain_abstract.usecases.orders

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.Order
import com.shoshin.domain_abstract.entities.order.OrderMetadata

interface IUpdateOrderUseCase {
    suspend fun execute(order: Order): Reaction<OrderMetadata>
}