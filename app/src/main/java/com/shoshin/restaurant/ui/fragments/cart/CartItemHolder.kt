package com.shoshin.restaurant.ui.fragments.cart

import android.view.View
import com.shoshin.domain_abstract.entities.cart.CartItem1
import com.shoshin.restaurant.R
import com.shoshin.restaurant.common.images.interfaces.ImageLoader
import com.shoshin.restaurant.databinding.CartItemHolderBinding
import com.shoshin.restaurant.ui.common.recycler.BaseViewHolder
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class CartItemHolder(
    itemView: View,
    private var increaser: (cartItem: CartItem1) -> Unit = {},
    private var decreaser: (cartItem: CartItem1) -> Unit = {}
) : BaseViewHolder<CartItem1>(itemView) {
    private val binding = CartItemHolderBinding.bind(itemView)

    private val imageLoader: ImageLoader

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface SentenceViewHolderEntryPoint {
        fun imageLoader(): ImageLoader
    }

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
        val entryPoint = EntryPointAccessors.fromApplication(
            itemView.context.applicationContext,
            SentenceViewHolderEntryPoint::class.java
        )
        imageLoader = entryPoint.imageLoader()
        binding.image.clipToOutline = true
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

    private fun fillImage() = imageLoader.load(binding.image, item?.dish?.imageUrl)

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