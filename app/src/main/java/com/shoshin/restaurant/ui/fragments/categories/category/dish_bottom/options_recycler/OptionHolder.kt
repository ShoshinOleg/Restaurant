package com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_recycler

import android.view.View
import androidx.core.content.ContextCompat
import com.shoshin.domain_abstract.entities.dish.DishOption
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.DishOptionHolderBinding
import com.shoshin.restaurant.ui.common.BaseViewHolder

class OptionHolder(
    itemView: View,
    private val onClickListener: OnOptionClick
):  BaseViewHolder<DishOption>(itemView) {
    interface OnOptionClick {
        fun onOptionClick(option: DishOption)
    }

    private val binding = DishOptionHolderBinding.bind(itemView)

    init {
        itemView.setOnClickListener {
            onClickListener.onOptionClick(item!!)
        }
    }


    override fun bind(item: DishOption) {
        super.bind(item)
        binding.name.text = item.name
        item.let {
            item.isChecked?.let {
                if(!it) {
                    binding.name.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.red
                        )
                    )
                } else {
                    binding.name.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.black
                        )
                    )
                }
            }
        }
    }


}