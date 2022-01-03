package com.shoshin.restaurant.ui.fragments.categories.category.dishes_recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.DishHolderBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class DishHolder(
    view: View,
    private val onClickListener: OnClickListener? = null,
    private val onPriceClickListener: OnPriceClickListener? = null
) : RecyclerView.ViewHolder(view)
{
    private val binding by viewBinding(DishHolderBinding::bind)
    private var dish: Dish? = null

    init {
        view.setOnClickListener {
            dish?.let {
                onClickListener?.onDishClick(it)
            }
        }
        binding.priceCard.setOnClickListener {
            dish?.let {
                onPriceClickListener?.onDishPriceClick(it)
            }
        }
    }

    interface OnClickListener {
        fun onDishClick(dish: Dish)
    }

    interface OnPriceClickListener {
        fun onDishPriceClick(dish: Dish)
    }

    fun bind(newDish: Dish) {
        dish = newDish
        val transformation = RoundedCornersTransformation(
            50,
            0,
            RoundedCornersTransformation.CornerType.TOP
        )
        Picasso.get()
            .load(dish?.imageURL)
            .placeholder(R.drawable.ic_menu)
//            .transform(transformation)
            .into(binding.image)

        binding.name.text = dish?.name
        binding.weight.text = "${dish?.weight} гр"
        binding.price.text = "${dish?.price} ₽"
    }
}