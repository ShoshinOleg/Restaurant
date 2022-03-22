package com.shoshin.data.db.entities.cart1

import com.shoshin.domain_abstract.common.Mapper
import com.shoshin.domain_abstract.entities.cart.CartItem1

class MenuItemToCartItemMapper1: Mapper<CartItem1, CartItemDbo1>() {
    override fun mapTo(from: CartItem1): CartItemDbo1 {
        return CartItemDbo1(
            id = from.id,
            item = from.dish
        )
    }

    override fun mapFrom(from: CartItemDbo1): CartItem1 {
        return CartItem1(
            id = from.id,
            dish = from.item
        )
    }
}