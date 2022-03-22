package com.shoshin.domain_abstract.repositories

import com.shoshin.domain_abstract.entities.cart.CartItem1
import com.shoshin.domain_abstract.entities.dish.Dish
import kotlinx.coroutines.flow.Flow

interface ICartRepository {
    fun getCartItemsFlow(): Flow<List<CartItem1>>
    suspend fun getCartItems(): List<CartItem1>
    suspend fun setDish(dish: Dish)
    suspend fun setCartItem(cartItem1: CartItem1)
    suspend fun increaseCartItem(cartItem: CartItem1)
    suspend fun decreaseCartItem(cartItem: CartItem1)
    suspend fun removeCartItem(cartItem1: CartItem1)
    suspend fun clearCart()
}