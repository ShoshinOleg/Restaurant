package com.shoshin.data.repositories

import com.shoshin.data.interfaces.sources.remote.IOrderRemoteSource
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.Order
import com.shoshin.domain_abstract.entities.order.OrderMetadata
import com.shoshin.domain_abstract.repositories.IOrderRepository
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderRemoteSource: IOrderRemoteSource
): IOrderRepository {
    override suspend fun updateOrder(order: Order): Reaction<OrderMetadata> =
        orderRemoteSource.updateOrder(order)

    override suspend fun getOrdersMetadata(): Reaction<List<OrderMetadata>> =
        orderRemoteSource.getOrdersMetadata()
}