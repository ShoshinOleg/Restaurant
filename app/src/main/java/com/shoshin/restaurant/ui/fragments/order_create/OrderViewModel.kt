package com.shoshin.restaurant.ui.fragments.order_create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.Order
import com.shoshin.domain_abstract.entities.order.OrderMetadata
import com.shoshin.domain_abstract.usecases.orders.IUpdateOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val updateOrderUseCase: IUpdateOrderUseCase
): ViewModel() {

    private val mutableUpdatedOrder = MutableLiveData<Reaction<OrderMetadata>>()
    val updatedOrderMetadata = mutableUpdatedOrder as LiveData<Reaction<OrderMetadata>>

    fun updateOrder(order: Order) {
        viewModelScope.launch {
            mutableUpdatedOrder.value = Reaction.Progress()
            mutableUpdatedOrder.value = updateOrderUseCase.execute(order)
        }
    }
}