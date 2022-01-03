package com.shoshin.restaurant.ui.fragments.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoshin.domain_abstract.entities.category.MenuCategory
import com.shoshin.restaurant.R

class MenuCategoryAdapter(
    private val onCategoryClickListener: MenuCategoryHolder.OnCategoryClickListener
): RecyclerView.Adapter<MenuCategoryHolder>() {
    private var categories: List<MenuCategory?>? = null

    fun setupCategories(categories: List<MenuCategory?>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuCategoryHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.menu_category_holder, parent, false)
        return MenuCategoryHolder(view, onCategoryClickListener)
    }

    override fun onBindViewHolder(holder: MenuCategoryHolder, position: Int) {
        if(categories != null) {
            holder.bind(categories!![position]!!)
        }
    }

    override fun getItemCount(): Int {
        return categories?.size ?: 0
    }
}