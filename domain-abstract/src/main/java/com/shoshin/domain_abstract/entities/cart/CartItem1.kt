package com.shoshin.domain_abstract.entities.cart

import com.shoshin.domain_abstract.entities.dish.Dish

data class CartItem1(
    var id: String,
    var dish: Dish
)