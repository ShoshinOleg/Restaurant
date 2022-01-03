package com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_bottom_fragment.option.variantRV

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shoshin.domain_abstract.entities.dish.DishOptionVariant
import com.shoshin.restaurant.databinding.DishOptionVariantHolderBinding

class VariantHolder(
    itemView: View,
    private val onItemCheck: OnVariantCheck
): RecyclerView.ViewHolder(itemView) {
    private val binding = DishOptionVariantHolderBinding.bind(itemView)
    private var variant: DishOptionVariant? = null

    interface OnVariantCheck {
        fun onVariantCheck(variant: DishOptionVariant)
    }

    init {
        itemView.setOnClickListener {
            setChecked(!binding.checkBox.isChecked)
        }
    }

    fun bind(optionVariant: DishOptionVariant) {
        variant = optionVariant
        binding.checkBox.setOnClickListener(null)
        binding.checkBox.isChecked = optionVariant.isChecked
        binding.name.text = optionVariant.name
        binding.price.text = "${optionVariant.price} ₽"
        binding.checkBox.setOnClickListener {
            variant?.isChecked = !variant?.isChecked!!
            onItemCheck.onVariantCheck(variant!!)
        }
    }

    fun setChecked(isChecked: Boolean) {
        Log.e("setChecked", "setChecked")
        binding.checkBox.isChecked = isChecked
        variant?.isChecked = isChecked
        onItemCheck.onVariantCheck(variant!!)
    }
}