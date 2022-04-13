package com.shoshin.restaurant.ui.fragments.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.Order
import com.shoshin.domain_abstract.usecases.orders.IGetOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrderUseCase: IGetOrderUseCase
): ViewModel() {
    private val mutableOrder = MutableLiveData<Reaction<Order>>()
    val order = mutableOrder as LiveData<Reaction<Order>>

    fun getOrder(orderId: String) {
        viewModelScope.launch {
            mutableOrder.value = Reaction.Progress()
            mutableOrder.value = getOrderUseCase.execute(orderId)
        }
    }
}