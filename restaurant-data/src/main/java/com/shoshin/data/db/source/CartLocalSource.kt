package com.shoshin.data.db.source

import com.shoshin.data.db.entities.cart1.CartDao1
import com.shoshin.data.db.entities.cart1.CartItemDbo1
import com.shoshin.data.interfaces.sources.local.ICartLocalSource
import com.shoshin.domain_abstract.common.Mapper
import com.shoshin.domain_abstract.entities.cart.CartItem1
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CartLocalSource @Inject constructor(
    private val cartDao: CartDao1,
    private val cartItemDomainDbMapper: Mapper<CartItem1, CartItemDbo1>
): ICartLocalSource {

    override fun getCartItemsFlow(): Flow<List<CartItem1>> =
        cartDao.getAllFlow().map { cartItemDomainDbMapper.mapFrom(it) }

    override suspend fun getCartItems(): List<CartItem1> =
        cartDao.getAll().map(cartItemDomainDbMapper::mapFrom)

    override suspend fun setCartItem(cartItem: CartItem1) {
        val cartItemDb = cartItemDomainDbMapper.mapTo(cartItem)
        cartDao.insert(cartItemDb)
    }

    override suspend fun remove(cartItem: CartItem1) {
        val cartItemDb = cartItemDomainDbMapper.mapTo(cartItem)
        cartDao.remove(cartItemDb)
    }

    override suspend fun clear() = cartDao.clear()
}