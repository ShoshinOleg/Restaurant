package com.shoshin.restaurant.ui.fragments.cart

import androidx.lifecycle.*
import com.shoshin.domain_abstract.entities.cart.CartItem1
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.domain_abstract.repositories.ICartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel1 @Inject constructor(
    private val cartRepository: ICartRepository
): ViewModel() {
    private val cartItemsFlow = cartRepository.getCartItemsFlow().asLiveData()
    val cartItemsCount = cartItemsFlow.map { it.size }
    val cartPrice = cartItemsFlow.map {
        it.fold(0) { acc, cartItem1 -> acc + cartItem1.dish.getTotalPrice() }
    }

    private val mutableCartItems = MutableLiveData<List<CartItem1>>()
    val cartItems: LiveData<List<CartItem1>> = mutableCartItems

    fun getItems() {
        viewModelScope.launch {
            mutableCartItems.value = cartRepository.getCartItems()
        }
    }

    fun addDish(dish: Dish) = viewModelScope.launch { cartRepository.setDish(dish) }

    fun setCartItem(cartItem1: CartItem1) =
        viewModelScope.launch { cartRepository.setCartItem(cartItem1) }

    fun removeCartItem(cartItem1: CartItem1) =
        viewModelScope.launch { cartRepository.removeCartItem(cartItem1) }

    fun clearCart() =
        viewModelScope.launch { cartRepository.clearCart() }
}