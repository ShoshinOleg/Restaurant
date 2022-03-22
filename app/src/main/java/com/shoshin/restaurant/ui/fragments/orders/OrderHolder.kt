package com.shoshin.restaurant.ui.fragments.orders

import android.view.View
import com.shoshin.domain_abstract.entities.order.OrderMetadata
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.OrderHolderBinding
import com.shoshin.restaurant.ui.common.recycler.BaseViewHolder

class OrderHolder(
    itemView: View,
    private val onOrderClickListener: (orderMetadata: OrderMetadata) -> Unit = {}
): BaseViewHolder<OrderMetadata>(itemView) {
    val binding = OrderHolderBinding.bind(itemView)

    companion object {
        val mapStatusKeyToText = mapOf(
            "new" to "Проверяется",
            "accepted" to "Принят",
            "on_kitchen" to "На кухне",
            "on_delivery" to "На доставке",
            "delivered" to "Доставлен"
        )
    }

    init {
        binding.root.setOnClickListener {
            item?.let { onOrderClickListener(it) }
        }
    }

    override fun bind(meta: OrderMetadata) {
        super.bind(meta)
        binding.orderNumber.text = meta.id
        binding.orderStatus.text = mapStatusKeyToText[meta.status]
        binding.orderTime.text = meta.createdAt
        binding.orderPrice.text = itemView.context.getString(R.string.rubles_price, meta.totalPrice)
    }
}