package com.shoshin.data.db.entities.cart

import com.shoshin.domain_abstract.entities.dish.Dish

data class CartItem(
    var id: String,
    var item: Dish
)