package com.shoshin.restaurant.ui.fragments.categories

import android.view.ViewGroup
import com.shoshin.domain_abstract.entities.category.Category
import com.shoshin.restaurant.R
import com.shoshin.restaurant.ui.common.BaseAdapter

class MenuCategoryAdapter(
    private val onCategoryClickListener: MenuCategoryHolder.OnCategoryClickListener
): BaseAdapter<Category, MenuCategoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuCategoryHolder =
        MenuCategoryHolder(R.layout.menu_category_holder.makeView(parent), onCategoryClickListener)

    override fun same(item1: Category, item2: Category): Boolean = item1.id == item2.id
}