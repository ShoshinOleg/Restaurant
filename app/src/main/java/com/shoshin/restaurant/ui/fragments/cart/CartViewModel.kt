package com.shoshin.restaurant.ui.fragments.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.restaurant.entities.CartItem
import com.shoshin.restaurant.local_db.mappers.MenuItemToCartItemMapper
import com.shoshin.restaurant.main.app.App

class CartViewModel: ViewModel() {
    private var mutableCart: MutableLiveData<List<CartItem?>> = MutableLiveData()
    private var mutableTotalPrice: MutableLiveData<Int> = MutableLiveData()
    private var mutableCartItemsCount: MutableLiveData<Int> = MutableLiveData()
    private val cartDao = App.instance.getDatabase().cartDao()
    private val mapper = MenuItemToCartItemMapper()



    init {
        initCart()
    }

    private fun initCart() {
        val items = mapper.mapFrom(cartDao.getAll())
        mutableCart.value = items as MutableList
        calcTotalCartPrice()
        updateCartItemsCount()
    }

    private fun calcTotalCartPrice() {
        mutableTotalPrice.value = mutableCart.value?.sumBy { it?.item?.getTotalPrice()?: 0 }
    }

    fun subscribeCart(): LiveData<List<CartItem?>> {
        if(mutableCart.value == null) {
            initCart()
        }
        return mutableCart
    }

    fun getCartItems() : List<CartItem?> {
        return mutableCart.value!!
    }

    fun getTotalPrice(): Int {
        return mutableTotalPrice.value ?: 0
    }

    fun getCartItemsCount() : Int{
        return mutableCartItemsCount.value!!
    }

    fun addItem(menuItem: Dish) {
        if(mutableCart.value == null) {
            initCart()
        }
        val items = mutableCart.value as MutableList
        val itemIndex = findIndexItem(items, menuItem)
        if(itemIndex != null) {
            Log.e("itemIndex != null", "itemIndex != null")
            items[itemIndex]?.item?.count = items[itemIndex]?.item?.count!! + menuItem.count!!
            cartDao.update(mapper.mapTo(items[itemIndex]!!))
//            cartDao.update(mapper.mapTo(items[itemIndex]!!))
        } else {
            Log.e("itemIndex == null", "itemIndex == null")
            Log.e("menItem", "$menuItem")
            val newCartItem = CartItem(
                id = Firebase.database.reference.push().key.toString(),
                menuItem
            )
            items.add(newCartItem)
            cartDao.insert(mapper.mapTo(newCartItem))
        }
        Log.e("items", "${items.size}")
        mutableCart.value = items
        calcTotalCartPrice()
        updateCartItemsCount()
    }

    fun removeItem(cartItem: CartItem) {
        if(mutableCart.value == null) {
            initCart()
            return
        }
        mutableCart.value?.let { cartItems ->
            val items = cartItems as MutableList
            val index = items.indexOfFirst { it?.id == cartItem.id }
            if(index != -1) {
                items.removeAt(index)
                //БД
                cartDao.remove(mapper.mapTo(cartItem))
                mutableCart.value = items
                calcTotalCartPrice()
                updateCartItemsCount()
                //посчитать стоимость
            }
        }
//        mutableCart.value?.let { items ->
//            val itemIndex = findIndexItem(items, menuItem)
////            if(itemIndex != )
//
//        }
//        mutableCart.value?.let {
//            val items = mutableCart.value as MutableList
//            val index = items.indexOfFirst { it?.id == menuItem.id }
//            if(index != -1) {
//                items.removeAt(index)
////                cartDao.remove(mapper.mapTo(menuItem))
//            }
//            mutableCart.value = items
//            calcTotalCartPrice()
//            updateCartItemsCount()
//        }
    }

    fun changeCount(cartItem: CartItem, count: Int) {
        if(mutableCart.value == null) {
            initCart()
        }
        mutableCart.value?.let { cartItems ->
            val items = cartItems as MutableList
            val index = items.indexOfFirst { it?.id == cartItem.id }
            if(index != -1) {
                items[index]?.item?.count = count
                //БД
                cartDao.update(mapper.mapTo(items[index]!!))
                mutableCart.value = items
                calcTotalCartPrice()
                updateCartItemsCount()
                //посчитать стоимость
            }
        }
//        mutableCart.value?.let {
//            val items = mutableCart.value as MutableList
//            val index = items.indexOfFirst { it?.id == menuItem.id }
//            if(index != -1) {
//                items[index]?.count = count
////                cartDao.update(mapper.mapTo(menuItem))
//            }
//            mutableCart.value = items
//            calcTotalCartPrice()
//            updateCartItemsCount()
//        }
    }

    fun subscribeTotalPrice(): MutableLiveData<Int> {
        if(mutableTotalPrice.value == null) {
            mutableTotalPrice.value = 0
        }
        return mutableTotalPrice
    }

    fun subscribeCartItemsCount(): MutableLiveData<Int> {
        if(mutableCartItemsCount.value == null) {
            mutableCartItemsCount.value = 0
        }
        return mutableCartItemsCount
    }

    private fun updateCartItemsCount() {
        mutableCartItemsCount.value = mutableCart.value?.sumBy { it?.item?.count!!}
    }

    fun clearCart() {
        cartDao.clear()
        mutableCart.value = mutableListOf()
        calcTotalCartPrice()
        updateCartItemsCount()
    }

    private fun findIndexItem(cartItems: List<CartItem?>, menuItem: Dish): Int? {
        cartItems.let { items ->
            for(cartItemIndex in items.indices) {
                items[cartItemIndex]?.let { cartItem ->
                    if(cartItem.item.isEqual(menuItem)) {
                        return cartItemIndex
                    }
                }
            }
        }
        return null
    }

    fun getMenuItems() : List<Dish?>? {
        return mutableCart.value?.map { it?.item }
    }


}