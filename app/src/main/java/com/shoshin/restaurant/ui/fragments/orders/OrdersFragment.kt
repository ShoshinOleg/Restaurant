package com.shoshin.restaurant.ui.fragments.orders

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.OrdersFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment: Fragment(R.layout.orders_fragment) {
    private val binding by viewBinding(OrdersFragmentBinding::bind)
    private val adapter by lazy { OrderAdapter() }
    private val ordersViewModel: OrdersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ordersRecycler.adapter = adapter
        ordersViewModel.orders.observe(viewLifecycleOwner) { event ->
            when(event) {
                is Reaction.Success -> {
                    Log.e("edatas", "edatas=${event.data}")
                    adapter.setupItems(event.data)
                }
            }
        }
        ordersViewModel.getOrders()
    }
}