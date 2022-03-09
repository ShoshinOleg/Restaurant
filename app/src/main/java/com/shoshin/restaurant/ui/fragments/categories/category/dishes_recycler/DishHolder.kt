package com.shoshin.restaurant.ui.fragments.categories.category.dishes_recycler

import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.DishHolderBinding
import com.shoshin.restaurant.ui.common.BaseViewHolder
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class DishHolder(
    view: View,
    private val onClickListener: OnClickListener? = null,
    private val onPriceClickListener: OnPriceClickListener? = null
): BaseViewHolder<Dish>(view) {
    private val binding by viewBinding(DishHolderBinding::bind)

    init {
        view.setOnClickListener {
            item?.let {
                onClickListener?.onDishClick(it)
            }
        }
        binding.priceCard.setOnClickListener {
            item?.let {
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

    override fun bind(item: Dish) {
        super.bind(item)
        val transformation = RoundedCornersTransformation(
            50,
            0,
            RoundedCornersTransformation.CornerType.TOP
        )
        Picasso.get()
            .load(item.imageURL)
            .placeholder(R.drawable.ic_menu)
//            .transform(transformation)
            .into(binding.image)

        binding.name.text = item.name
        binding.weight.text = itemView.context.getString(R.string.gram_weight, item.weight)
        binding.price.text = itemView.context.getString(R.string.rubles_price, item.price)
    }
}