package com.shoshin.restaurant.ui.fragments.order.dishes_recycler

import android.view.View
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.restaurant.R
import com.shoshin.restaurant.common.images.interfaces.ImageLoader
import com.shoshin.restaurant.databinding.OrderDishHolderBinding
import com.shoshin.restaurant.ui.common.recycler.BaseViewHolder
import com.shoshin.restaurant.ui.fragments.cart.CartItemHolder
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class OrderDishHolder(
    itemView: View,
    private val onDishClickListener: (dish: Dish) -> Unit = {}
): BaseViewHolder<Dish>(itemView) {
    private val binding = OrderDishHolderBinding.bind(itemView)

    private val imageLoader: ImageLoader

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface SentenceViewHolderEntryPoint {
        fun imageLoader(): ImageLoader
    }

    init {
        binding.root.setOnClickListener {
            item?.let { onDishClickListener(it) }
        }
        val entryPoint = EntryPointAccessors.fromApplication(
            itemView.context.applicationContext,
            CartItemHolder.SentenceViewHolderEntryPoint::class.java
        )
        imageLoader = entryPoint.imageLoader()
        binding.image.clipToOutline = true
    }

    override fun bind(item: Dish) {
        super.bind(item)
        this.item = item
        binding.name.text = item.name
        imageLoader.load(binding.image, item.imageUrl)
        binding.count.text = itemView.context.getString(R.string.dishes_count, item.count)
        binding.price.text = itemView.context.getString(R.string.rubles_price, item.price)
    }
}