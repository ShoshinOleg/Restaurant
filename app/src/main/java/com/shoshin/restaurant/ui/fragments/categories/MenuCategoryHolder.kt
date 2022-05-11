package com.shoshin.restaurant.ui.fragments.categories

import android.view.View
import com.shoshin.domain_abstract.entities.category.Category
import com.shoshin.restaurant.common.images.interfaces.ImageLoader
import com.shoshin.restaurant.databinding.MenuCategoryHolderBinding
import com.shoshin.restaurant.ui.common.recycler.BaseViewHolder
import com.shoshin.restaurant.ui.fragments.cart.CartItemHolder
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class MenuCategoryHolder (
    itemView: View,
    private val onClickListener: OnCategoryClickListener
): BaseViewHolder<Category>(itemView), View.OnClickListener{
    private var binding = MenuCategoryHolderBinding.bind(itemView)

    private val imageLoader: ImageLoader

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface SentenceViewHolderEntryPoint {
        fun imageLoader(): ImageLoader
    }

    init {
        itemView.setOnClickListener(this)
        val entryPoint = EntryPointAccessors.fromApplication(
            itemView.context.applicationContext,
            CartItemHolder.SentenceViewHolderEntryPoint::class.java
        )
        imageLoader = entryPoint.imageLoader()
        binding.image.clipToOutline = true
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }

    override fun bind(item: Category) {
        super.bind(item)
        binding.name.text = item.name
        imageLoader.load(binding.image, item.imageURL)
    }

    override fun onClick(v: View?) {
        item?.let { onClickListener.onCategoryClick(it) }
    }
}