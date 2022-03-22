package com.shoshin.restaurant.ui.fragments.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoshin.domain.usecases.orders.GetOrdersMetadataUseCase
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.order.OrderMetadata
import com.shoshin.domain_abstract.usecases.orders.IGetOrdersMetadataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getOrdersMetadataUseCase: IGetOrdersMetadataUseCase
): ViewModel() {
    private val mutableOrders = MutableLiveData<Reaction<List<OrderMetadata>>>()
    val orders = mutableOrders as LiveData<Reaction<List<OrderMetadata>>>

    fun getOrders() {
        viewModelScope.launch {
            mutableOrders.value = Reaction.Progress()
            mutableOrders.value = getOrdersMetadataUseCase.execute()
        }
    }
}