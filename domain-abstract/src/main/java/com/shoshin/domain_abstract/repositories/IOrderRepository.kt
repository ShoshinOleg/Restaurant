package com.shoshin.domain_abstract.repositories

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.Order
import com.shoshin.domain_abstract.entities.order.OrderMetadata

interface IOrderRepository {
    suspend fun updateOrder(order: Order): Reaction<OrderMetadata>
    suspend fun getOrdersMetadata(): Reaction<List<OrderMetadata>>
    suspend fun getOrder(orderId: String): Reaction<Order>
}