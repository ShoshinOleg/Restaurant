package com.shoshin.restaurant.ui.fragments.order.dishes_recycler

import android.view.ViewGroup
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.restaurant.R
import com.shoshin.restaurant.ui.common.recycler.recycler.BaseAdapter

class OrderDishAdapter(
    private val onDishClickHolder: (dish: Dish) -> Unit = {}
): BaseAdapter<Dish, OrderDishHolder>() {
    override fun same(it1: Dish, it2: Dish): Boolean = it1.id == it2.id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrderDishHolder(R.layout.order_dish_holder.makeView(parent), onDishClickHolder)
}