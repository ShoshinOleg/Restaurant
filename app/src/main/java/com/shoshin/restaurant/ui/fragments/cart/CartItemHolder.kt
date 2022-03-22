package com.shoshin.restaurant.ui.fragments.cart

import android.view.View
import com.shoshin.domain_abstract.entities.cart.CartItem1
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.CartItemHolderBinding
import com.shoshin.restaurant.ui.common.recycler.BaseViewHolder
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class CartItemHolder(
    itemView: View,
    private var increaser: (cartItem: CartItem1) -> Unit = {},
    private var decreaser: (cartItem: CartItem1) -> Unit = {}
) : BaseViewHolder<CartItem1>(itemView) {
    private val binding = CartItemHolderBinding.bind(itemView)

    init {
        binding.countBar.addCard.setOnClickListener {
            item?.dish?.count = item?.dish?.count?.plus(1)
            increaser(item!!)
            showCount()
            showPrice()
        }
        binding.countBar.removeCard.setOnClickListener {
            item?.dish?.count = item?.dish?.count?.minus(1)
            decreaser(item!!)
            showCount()
            showPrice()
        }
    }

    override fun bind(newCartItem: CartItem1) {
        super.bind(newCartItem)
        binding.name.text = item?.dish?.name
        showPrice()
        showCount()
        fillImage()
        fillOptionsField()
    }

    private fun showCount() {
        binding.countBar.count.text = item?.dish?.count.toString()
    }

    private fun showPrice() {
        item?.dish?.let {
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
            .load(item?.dish?.imageURL)
            .placeholder(R.drawable.ic_menu)
            .transform(transformation)
            .into(binding.image)
    }

    private fun fillOptionsField() {
        val listNames = item?.dish?.getSelectedVariantsList()
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