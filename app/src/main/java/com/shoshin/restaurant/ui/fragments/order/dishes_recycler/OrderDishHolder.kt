package com.shoshin.restaurant.ui.fragments.order.dishes_recycler

import android.view.View
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.OrderDishHolderBinding
import com.shoshin.restaurant.ui.common.recycler.BaseViewHolder
import com.squareup.picasso.Picasso

class OrderDishHolder(
    itemView: View,
    private val onDishClickListener: (dish: Dish) -> Unit = {}
): BaseViewHolder<Dish>(itemView) {
    private val binding = OrderDishHolderBinding.bind(itemView)

    init {
        binding.root.setOnClickListener {
            item?.let { onDishClickListener(it) }
        }
    }

    override fun bind(item: Dish) {
        super.bind(item)
        this.item = item
        binding.name.text = item.name
        Picasso.get()
            .load(item.imageUrl)
//            .placeholder(R.drawable.ic_menu)
            .into(binding.image)
        itemView.context.getString(R.string.dishes_count, item.count)
        binding.count.text = itemView.context.getString(R.string.dishes_count, item.count)
        binding.price.text = itemView.context.getString(R.string.rubles_price, item.price)
//        binding.addToCart.
    }
}