package com.shoshin.restaurant.entities

import com.shoshin.domain_abstract.entities.dish.Dish

data class CartItem(
    var id: String,
    var item: Dish
)