package com.shoshin.data.db.entities.cart

import com.shoshin.domain_abstract.common.Mapper

class MenuItemToCartItemMapper: Mapper<CartItem, CartItemDbo>() {
    override fun mapTo(from: CartItem): CartItemDbo {
        return CartItemDbo(
            id = from.id,
            item = from.item
        )
    }

    override fun mapFrom(from: CartItemDbo): CartItem {
        return CartItem(
            id = from.id,
            item = from.item
        )
    }
}