package com.shoshin.data.db.entities.cart

import com.shoshin.domain_abstract.common.Mapper

class MenuItemToCartItemMapper: Mapper<CartItem, CartItem1Dbo>() {
    override fun mapTo(from: CartItem): CartItem1Dbo {
        return CartItem1Dbo(
            id = from.id,
            item = from.item
        )
    }

    override fun mapFrom(from: CartItem1Dbo): CartItem {
        return CartItem(
            id = from.id,
            item = from.item
        )
    }
}