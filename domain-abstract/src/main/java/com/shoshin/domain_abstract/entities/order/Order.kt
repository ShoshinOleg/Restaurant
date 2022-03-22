package com.shoshin.domain_abstract.entities.order

import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.domain_abstract.entities.locations.Location
import java.io.Serializable

data class Order(
    var id: String? = null,
    var isDelivery: Boolean = true,
    var items: MutableList<Dish>? = null,
    var location: Location? = null,
    var status: String? = "new",
    var paymentMethod: String? = null,
    var customerId: String? = null,
    var phoneNumber: String? = null,
    var createdAt: String? = null,
    var isFastest: Boolean = true,
    var orderTime: String? = null,
    var orderDate: String? = null,
    var deliveryPrice: Int? = null,
    var orderPrice: Int? = null,
): Serializable {
    fun getTotalPrice() : Int {
        var sum = 0
        deliveryPrice?.let { sum += it}
        orderPrice?.let { sum += it}
        return sum
    }

    fun getOrderMetaData(): OrderMetadata {
        return OrderMetadata(
            id,
            customerId,
            orderDate,
            status,
            createdAt,
            getTotalPrice()
        )
    }
}