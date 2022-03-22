package com.shoshin.domain_abstract.entities.order

import java.io.Serializable

data class OrderMetadata(
    var id: String? = null,
    var customerId: String? = null,
    var date: String? = null,
    var status: String? = null,
    var createdAt: String? = null,
    var totalPrice: Int? = null
): Serializable