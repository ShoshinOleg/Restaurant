package com.shoshin.restaurant.ui.fragments.cart

import android.view.ViewGroup
import com.shoshin.domain_abstract.entities.cart.CartItem1
import com.shoshin.restaurant.R
import com.shoshin.restaurant.ui.common.recycler.mutable_recycler.BaseMutableAdapter

class CartAdapter(
    private var increaser: (cartItem: CartItem1) -> Unit = {},
    private var decreaser: (cartItem: CartItem1) -> Unit = {}
): BaseMutableAdapter<CartItem1, CartItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CartItemHolder(
            R.layout.cart_item_holder.makeView(parent), increaser, ::adapterDecreaser
        )

    override fun same(it1: CartItem1, it2: CartItem1): Boolean = it1.id == it2.id

    private fun adapterDecreaser(cartItem: CartItem1) {
        if(cartItem.dish.count == 0) {
            removeItem(cartItem)
        }
        decreaser(cartItem)
    }
}