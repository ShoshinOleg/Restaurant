package com.shoshin.restaurant.ui.fragments.categories.category.dishes_recycler

import android.view.ViewGroup
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.restaurant.R
import com.shoshin.restaurant.ui.common.BaseAdapter

class DishAdapter(
    private val onItemClick: DishHolder.OnClickListener,
    private val onDishPriceClick: DishHolder.OnPriceClickListener
) : BaseAdapter<Dish, DishHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishHolder =
        DishHolder(R.layout.dish_holder.makeView(parent), onItemClick, onDishPriceClick)

    override fun same(item1: Dish, item2: Dish): Boolean = item1.id == item2.id
}