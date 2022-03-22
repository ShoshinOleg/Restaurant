package com.shoshin.data.remote.sources

import com.shoshin.data.interfaces.services.remote.IOrderRemoteService
import com.shoshin.data.interfaces.sources.remote.IOrderRemoteSource
import com.shoshin.data.remote.main.NetworkHelper
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.Order
import com.shoshin.domain_abstract.entities.order.OrderMetadata
import javax.inject.Inject

class OrderRemoteSource @Inject constructor(
    private val orderRemoteService: IOrderRemoteService
): IOrderRemoteSource {
    override suspend fun updateOrder(order: Order): Reaction<OrderMetadata> =
        NetworkHelper.safeApiCall {
            orderRemoteService.updateOrder(order)
        }

    override suspend fun getOrdersMetadata(): Reaction<List<OrderMetadata>> =
        NetworkHelper.safeApiCall {
            orderRemoteService.getOrdersMetadata()
        }
}