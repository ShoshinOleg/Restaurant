package com.shoshin.data.repositories

import com.shoshin.data.interfaces.sources.local.ICartLocalSource
import com.shoshin.domain_abstract.entities.cart.CartItem1
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.domain_abstract.repositories.ICartRepository
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val localSource: ICartLocalSource
): ICartRepository {
    override fun getCartItemsFlow(): Flow<List<CartItem1>> = localSource.getCartItemsFlow()

    override suspend fun getCartItems(): List<CartItem1> = localSource.getCartItems()

    override suspend fun setDish(dish: Dish) {
        val cartItems = getCartItems()
        val itemIndex = cartItems.indexOfFirst { it.dish.isEqual(dish) }
        val cartItem: CartItem1
        if(itemIndex == -1) {
            cartItem = CartItem1(UUID.randomUUID().toString(), dish)
        } else {
            cartItem = cartItems[itemIndex]
            cartItem.dish = cartItem.dish.copy(count = cartItem.dish.count?.plus(1))
        }
        localSource.setCartItem(cartItem)
    }

    override suspend fun setCartItem(cartItem1: CartItem1) {
        localSource.setCartItem(cartItem1)
    }


    override suspend fun increaseCartItem(cartItem: CartItem1) {
        cartItem.dish = cartItem.dish.copy(count = cartItem.dish.count?.plus(1))
        localSource.setCartItem(cartItem)
    }

    override suspend fun decreaseCartItem(cartItem: CartItem1) {
        if(cartItem.dish.count == 1) {
            localSource.remove(cartItem)
        } else {
            cartItem.dish = cartItem.dish.copy(count = cartItem.dish.count?.minus(1))
            localSource.setCartItem(cartItem)
        }
    }

    override suspend fun removeCartItem(cartItem1: CartItem1) {
        localSource.remove(cartItem1)
    }

    override suspend fun clearCart() = localSource.clear()
}