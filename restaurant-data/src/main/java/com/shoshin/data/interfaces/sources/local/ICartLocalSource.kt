package com.shoshin.data.interfaces.sources.local

import com.shoshin.domain_abstract.entities.cart.CartItem1
import kotlinx.coroutines.flow.Flow

interface ICartLocalSource {
    fun getCartItemsFlow(): Flow<List<CartItem1>>
    suspend fun getCartItems(): List<CartItem1>
    suspend fun setCartItem(cartItem: CartItem1)
    suspend fun remove(cartItem: CartItem1)
    suspend fun clear()
}