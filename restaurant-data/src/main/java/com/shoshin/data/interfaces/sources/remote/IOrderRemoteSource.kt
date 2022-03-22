package com.shoshin.data.interfaces.sources.remote

import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.Order
import com.shoshin.domain_abstract.entities.order.OrderMetadata
import retrofit2.http.Body

interface IOrderRemoteSource {
    suspend fun updateOrder(@Body order: Order): Reaction<OrderMetadata>
    suspend fun getOrdersMetadata(): Reaction<List<OrderMetadata>>
}