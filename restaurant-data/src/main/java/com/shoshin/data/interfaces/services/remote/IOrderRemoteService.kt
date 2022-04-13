package com.shoshin.data.interfaces.services.remote

import com.shoshin.data.remote.main.Constants
import com.shoshin.domain_abstract.entities.order.Order
import com.shoshin.domain_abstract.entities.order.OrderMetadata
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IOrderRemoteService {
    @POST(Constants.ORDERS_URL)
    suspend fun updateOrder(@Body order: Order): OrderMetadata
    @GET(Constants.ORDERS_METADATA_URL)
    suspend fun getOrdersMetadata(): List<OrderMetadata>
    @GET("${Constants.ORDERS_URL}/{order_id}")
    suspend fun getOrder(
        @Path(value = "order_id", encoded = true) orderId: String
    ): Order
}