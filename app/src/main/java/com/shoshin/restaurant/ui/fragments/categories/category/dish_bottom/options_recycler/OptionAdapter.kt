package com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoshin.domain_abstract.entities.dish.DishOption
import com.shoshin.restaurant.R

class OptionAdapter(
    private val optionClickListener: OptionHolder.OnOptionClick
): RecyclerView.Adapter<OptionHolder>() {
    var options: List<DishOption>? = null

    fun setupItems(itemOptions: List<DishOption>) {
        options = itemOptions
        notifyDataSetChanged()
    }

    fun setListOptionChecked(listOk: List<Boolean>) {
//        for(index in listOk.indices) {
//
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.dish_option_holder, parent, false)
        return OptionHolder(view, optionClickListener)
    }

    override fun onBindViewHolder(holder: OptionHolder, position: Int) {
        holder.bind(options!![position])
    }

    override fun getItemCount(): Int {
        return options?.size ?: 0
    }
}