package com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_recycler

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shoshin.domain_abstract.entities.dish.DishOption
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.DishOptionHolderBinding

class OptionHolder(
    itemView: View,
    private val onClickListener: OnOptionClick
):  RecyclerView.ViewHolder(itemView)

{
    interface OnOptionClick {
        fun onOptionClick(option: DishOption)
    }

    private val binding = DishOptionHolderBinding.bind(itemView)
    private var option: DishOption? = null

    init {
        itemView.setOnClickListener {
            onClickListener.onOptionClick(option!!)
        }
    }


    fun bind(itemOption: DishOption) {
        option = itemOption
        binding.name.text = option?.name
        option?.let {
            option?.isChecked?.let {
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