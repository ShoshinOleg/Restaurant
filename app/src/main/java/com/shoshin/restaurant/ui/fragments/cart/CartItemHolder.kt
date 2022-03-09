package com.shoshin.restaurant.ui.fragments.cart

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.CartItemHolderBinding
import com.shoshin.data.db.entities.cart.CartItem
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class CartItemHolder(
    itemView: View,
    private var onCartItemRemove: OnRemoveCartItem,
    private var onCartItemChangeCount: OnCartItemChangeCount
) : RecyclerView.ViewHolder(itemView)
{
    private val binding = CartItemHolderBinding.bind(itemView)
    private var cartItem: CartItem? = null

    interface OnRemoveCartItem {
        fun onItemRemove(cartItem: CartItem)
    }

    interface OnCartItemChangeCount {
        fun onChangeCount(cartItem: CartItem, count: Int)
    }

    init {
        binding.countBar.addCard.setOnClickListener {
            onCartItemChangeCount.onChangeCount(cartItem!!, cartItem?.item?.count!! + 1)
            showPrice()
            showCount()
        }
        binding.countBar.removeCard.setOnClickListener {
            if(cartItem?.item?.count != null) {
                if(cartItem?.item!!.count!! == 1) {
                    onCartItemRemove.onItemRemove(cartItem!!)
                    showCount()
                } else {
                    onCartItemChangeCount.onChangeCount(cartItem!!, cartItem?.item?.count!! - 1)
                    showPrice()
                    showCount()
                }
            }
        }
    }

    fun bind(newCartItem: CartItem) {
        cartItem = newCartItem
        binding.name.text = cartItem?.item?.name
        showPrice()
        showCount()
        fillImage()
        fillOptionsField()

    }

    private fun showCount() {
        binding.countBar.count.text = cartItem?.item?.count.toString()
    }

    private fun showPrice() {
        cartItem?.item?.let {
            binding.price.text = itemView.context.getString(R.string.rubles_price, it.getTotalPrice())
        }
    }

    private fun fillImage() {
        val transformation = RoundedCornersTransformation(
            50,
            0,
            RoundedCornersTransformation.CornerType.ALL
        )
        Picasso.get()
            .load(cartItem?.item?.imageURL)
            .placeholder(R.drawable.ic_menu)
            .transform(transformation)
            .into(binding.image)
    }

    private fun fillOptionsField() {
        val listNames = cartItem?.item?.getSelectedVariantsList()
        listNames?.let { names ->
            if(names.isNotEmpty()) {
                val builder = StringBuilder("")
                for(name in names) {
                    builder.append(name).append("·")
                }
                var string = builder.toString()
                string = string.substring(0..string.length-2)
                binding.optionsName.text = string
                binding.optionsName.visibility = View.VISIBLE
            } else {
                binding.optionsName.visibility = View.GONE
            }
        }
    }
}