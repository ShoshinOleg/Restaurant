package com.shoshin.restaurant.ui.fragments.categories.category.dishes_recycler

import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.restaurant.R
import com.shoshin.restaurant.common.images.interfaces.ImageLoader
import com.shoshin.restaurant.databinding.DishHolderBinding
import com.shoshin.restaurant.ui.common.recycler.BaseViewHolder
import com.shoshin.restaurant.ui.fragments.cart.CartItemHolder
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class DishHolder(
    view: View,
    private val onClickListener: OnClickListener? = null,
    private val onPriceClickListener: OnPriceClickListener? = null
): BaseViewHolder<Dish>(view) {
    private val binding by viewBinding(DishHolderBinding::bind)

    private val imageLoader: ImageLoader

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface SentenceViewHolderEntryPoint {
        fun imageLoader(): ImageLoader
    }

    init {
        view.setOnClickListener {
            item?.let {
                onClickListener?.onDishClick(it)
            }
        }
        binding.priceCard.setOnClickListener {
            item?.let {
                onPriceClickListener?.onDishPriceClick(it)
            }
        }
        val entryPoint = EntryPointAccessors.fromApplication(
            itemView.context.applicationContext,
            CartItemHolder.SentenceViewHolderEntryPoint::class.java
        )
        imageLoader = entryPoint.imageLoader()
        binding.image.clipToOutline = true
    }

    interface OnClickListener {
        fun onDishClick(dish: Dish)
    }

    interface OnPriceClickListener {
        fun onDishPriceClick(dish: Dish)
    }

    override fun bind(item: Dish) {
        super.bind(item)
        imageLoader.load(binding.image, item.imageUrl)
        binding.name.text = item.name
        binding.weight.text = itemView.context.getString(R.string.gram_weight, item.weight)
        binding.price.text = itemView.context.getString(R.string.rubles_price, item.price)
    }
}