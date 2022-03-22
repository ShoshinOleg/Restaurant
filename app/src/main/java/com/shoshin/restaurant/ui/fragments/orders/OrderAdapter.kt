package com.shoshin.restaurant.ui.fragments.orders

import android.view.ViewGroup
import com.shoshin.domain_abstract.entities.order.OrderMetadata
import com.shoshin.restaurant.R
import com.shoshin.restaurant.ui.common.recycler.recycler.BaseAdapter

class OrderAdapter(
    private val onOrderClickListener: (orderMetadata: OrderMetadata) -> Unit = {}
): BaseAdapter<OrderMetadata, OrderHolder>() {
    override fun same(it1: OrderMetadata, it2: OrderMetadata): Boolean = it1.id == it2.id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrderHolder(R.layout.order_holder.makeView(parent), onOrderClickListener)
}