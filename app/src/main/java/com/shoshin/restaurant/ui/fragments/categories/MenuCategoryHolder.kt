package com.shoshin.restaurant.ui.fragments.categories

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shoshin.domain_abstract.entities.category.MenuCategory
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.MenuCategoryHolderBinding
import com.squareup.picasso.Picasso

class MenuCategoryHolder (
    itemView: View,
    private val onClickListener: OnCategoryClickListener
) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
    private var category: MenuCategory? = null
    private var binding = MenuCategoryHolderBinding.bind(itemView)

    init {
        itemView.setOnClickListener(this)
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(category: MenuCategory)
    }

    fun bind(category: MenuCategory) {
        this.category = category
        binding.name.text = category.name
        Picasso.get()
            .load(category.imageURL)
            .placeholder(R.drawable.ic_menu)
            .into(binding.image)
    }

    override fun onClick(v: View?) {
        category?.let { onClickListener.onCategoryClick(it) }
    }

}