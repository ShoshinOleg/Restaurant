package com.shoshin.restaurant.ui.fragments.order

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.Order
import com.shoshin.restaurant.R
import com.shoshin.restaurant.common.argument
import com.shoshin.restaurant.databinding.OrderFragmentBinding
import com.shoshin.restaurant.ui.fragments.order.dishes_recycler.OrderDishAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment: Fragment(R.layout.order_fragment) {
    private val binding by viewBinding(OrderFragmentBinding::bind)
    private val orderId: String by argument()
    private val adapter by lazy { OrderDishAdapter() }
    private val orderViewModel: OrderViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.orderItemsRecycler.adapter = adapter
        orderViewModel.order.observe(viewLifecycleOwner) { event ->
            when(event) {
                is Reaction.Success -> {
                    onSuccess(event)
                    binding.progress.isVisible = false
                    binding.mainLayout.isVisible = true
                }
                is Reaction.Progress -> {
                    binding.progress.isVisible = true
                    binding.mainLayout.isVisible = false
                }
            }
        }
        orderViewModel.getOrder(orderId)
    }

    private fun onSuccess(event: Reaction.Success<Order>) {
        val dateTimeSplit = event.data.createdAt?.split(" ")
        val date = dateTimeSplit!![0].replace(":", ".")
        val datetime = "$date ${dateTimeSplit[1]}"
        binding.dateTimeText.text = datetime
        binding.orderTotalBlock.address.text = event.data.location?.fullName()

        binding.orderTotalBlock.deliveryPrice.text =
            context?.getString(R.string.rubles_price, event.data.deliveryPrice)
        binding.orderTotalBlock.paymentMethod.text = event.data.paymentMethod
        binding.orderTotalBlock.totalOrderPrice.text =
            context?.getString(R.string.rubles_price, event.data.orderPrice)
        binding.orderTotalBlock.totalPrice.text =
            context?.getString(R.string.rubles_price, event.data.getTotalPrice())
        event.data.items?.let { adapter.setupItems(it) }
    }
}