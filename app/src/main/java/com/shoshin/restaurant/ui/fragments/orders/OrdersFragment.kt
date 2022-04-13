package com.shoshin.restaurant.ui.fragments.orders

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.Order
import com.shoshin.domain_abstract.entities.order.OrderMetadata
import com.shoshin.restaurant.R
import com.shoshin.restaurant.common.argumentNullable
import com.shoshin.restaurant.databinding.OrdersFragmentBinding
import com.shoshin.restaurant.ui.fragments.login.LoginEnterNumberPhoneFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment: Fragment(R.layout.orders_fragment) {
    private val binding by viewBinding(OrdersFragmentBinding::bind)
    private val adapter by lazy { OrderAdapter(::onOrderClickListener) }
    private val ordersViewModel: OrdersViewModel by viewModels()
    private val userId: String? by argumentNullable()
    private val orderId: String? by argumentNullable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController()

        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(LoginEnterNumberPhoneFragment.LOGIN_SUCCESSFUL)
            .observe(currentBackStackEntry) { success ->
                Log.e("orders", "success=$success")
                if(!success) {
                    val startDestination = navController.graph.startDestinationId
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(startDestination, true)
                        .build()
                    navController.navigate(startDestination, null, navOptions)
                }
            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(Firebase.auth.currentUser == null) {
            findNavController().navigate(R.id.loginEnterPhone)
        } else {
            if(userId != null && orderId != null) {

            } else {
                initUi()
            }
        }
    }

    private fun initUi() {
        binding.ordersRecycler.adapter = adapter
        ordersViewModel.orders.observe(viewLifecycleOwner) { event ->
            when(event) {
                is Reaction.Success -> {
                    if(event.data.isNotEmpty()) toDataMode(event.data) else toEmptyListMode()
                }
                is Reaction.Progress -> toProgressMode()
            }
        }
        binding.toMenu.setOnClickListener {
            val directions = OrdersFragmentDirections.toMenu()
            findNavController().navigate(directions)
        }
        ordersViewModel.getOrders()
    }

    private fun toDataMode(items: List<OrderMetadata>) {
        adapter.setupItems(items)
        binding.progress.isVisible = false
        binding.emptyConstraint.isVisible = false
        binding.mainLayout.isVisible = true
    }

    private fun toEmptyListMode() {
        binding.mainLayout.isVisible = false
        binding.progress.isVisible = false
        binding.emptyConstraint.isVisible = true
    }

    private fun toProgressMode() {
        binding.progress.isVisible = true
        binding.emptyConstraint.isVisible = false
        binding.mainLayout.isVisible = false
    }

    private fun onOrderClickListener(orderMetadata: OrderMetadata) {
        orderMetadata.id?.let { id ->
            val directions = OrdersFragmentDirections.toOrder(id)
            findNavController().navigate(directions)
        }
    }
}