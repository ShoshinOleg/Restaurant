package com.shoshin.domain_abstract.entities.order

data class Order(val number: String, val contents: List<OrderItem>)