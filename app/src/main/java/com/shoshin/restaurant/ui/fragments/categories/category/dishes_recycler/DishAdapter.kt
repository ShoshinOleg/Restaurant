package com.shoshin.restaurant.ui.fragments.categories.category.dishes_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.restaurant.R

class DishAdapter(
    private val onItemClickListener: DishHolder.OnClickListener,
    private val onDishPriceClickListener: DishHolder.OnPriceClickListener
) : RecyclerView.Adapter<DishHolder>() {
    private var dishes: MutableList<Dish> = mutableListOf()

    fun setupDishes(newDishes: List<Dish>) {
        dishes = newDishes as MutableList<Dish>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.dish_holder, parent, false)
        return DishHolder(view, onItemClickListener, onDishPriceClickListener)
    }

    override fun onBindViewHolder(holder: DishHolder, position: Int) {
        holder.bind(dishes[position])
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

}