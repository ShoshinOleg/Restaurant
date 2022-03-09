package com.shoshin.restaurant.ui.fragments.categories

import android.view.View
import com.shoshin.domain_abstract.entities.category.Category
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.MenuCategoryHolderBinding
import com.shoshin.restaurant.ui.common.BaseViewHolder
import com.squareup.picasso.Picasso

class MenuCategoryHolder (
    itemView: View,
    private val onClickListener: OnCategoryClickListener
): BaseViewHolder<Category>(itemView), View.OnClickListener{
    private var binding = MenuCategoryHolderBinding.bind(itemView)

    init {
        itemView.setOnClickListener(this)
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }

    override fun bind(item: Category) {
        super.bind(item)
        binding.name.text = item.name
        Picasso.get()
            .load(item.imageURL)
            .placeholder(R.drawable.ic_menu)
            .into(binding.image)
    }

    override fun onClick(v: View?) {
        item?.let { onClickListener.onCategoryClick(it) }
    }
}