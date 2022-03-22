package com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_recycler

import android.view.ViewGroup
import com.shoshin.domain_abstract.entities.dish.DishOption
import com.shoshin.restaurant.R
import com.shoshin.restaurant.ui.common.recycler.recycler.BaseAdapter

class OptionAdapter(
    private val onOptionClick: OptionHolder.OnOptionClick
): BaseAdapter<DishOption, OptionHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionHolder =
        OptionHolder(R.layout.dish_option_holder.makeView(parent), onOptionClick)

    override fun same(it1: DishOption, it2: DishOption): Boolean = it1.id == it2.id
}