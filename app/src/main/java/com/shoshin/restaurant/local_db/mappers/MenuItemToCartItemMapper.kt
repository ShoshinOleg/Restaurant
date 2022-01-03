package com.shoshin.restaurant.local_db.mappers

import com.shoshin.restaurant.entities.CartItem
import com.shoshin.restaurant.local_db.models.CartItem1Dbo

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