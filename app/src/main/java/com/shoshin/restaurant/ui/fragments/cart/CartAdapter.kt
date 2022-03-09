package com.shoshin.restaurant.ui.fragments.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoshin.restaurant.R
import com.shoshin.data.db.entities.cart.CartItem

class CartAdapter(
    private val onChangeCartItemCount: CartItemHolder.OnCartItemChangeCount,
    private val onRemoveCartItem: CartItemHolder.OnRemoveCartItem
):
    RecyclerView.Adapter<CartItemHolder>()
{
    private var items: List<CartItem?>? = null

    fun setupItems(cartItems: List<CartItem?>) {
        items = cartItems
        notifyItemRangeInserted(0, cartItems.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.cart_item_holder, parent, false)
        return CartItemHolder(view, onRemoveCartItem, onChangeCartItemCount)
    }

    override fun onBindViewHolder(holder: CartItemHolder, position: Int) {
        items!![position]?.let {
            holder.bind(items!![position]!!)
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }
}