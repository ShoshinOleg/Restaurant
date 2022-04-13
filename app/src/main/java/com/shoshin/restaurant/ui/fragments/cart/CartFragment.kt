package com.shoshin.restaurant.ui.fragments.cart

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shoshin.domain_abstract.entities.cart.CartItem1
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.CartFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment: Fragment(R.layout.cart_fragment) {
    private val adapter by lazy { CartAdapter(::onCartItemIncrease, ::onCartItemDecrease) }
    private val binding by viewBinding(CartFragmentBinding::bind)
    private var isEmpty = true
    private val cartViewModel: CartViewModel1 by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDoOrderClickListener()
        setupToMenuClickListener()
        initRecycler()
        subscribeTotalPrice()
        subscribeCartItemsCount()
    }

    private fun setupToMenuClickListener() {
        binding.toMenu.setOnClickListener {
            val directions = CartFragmentDirections.toMenu()
            findNavController().navigate(directions)
        }
    }

    private fun initRecycler() {
        binding.recyclerView.adapter = adapter
        cartViewModel.cartItems.observe(viewLifecycleOwner, {
            Log.e("cartItems", "cartItems=$it")
            adapter.setupItems(it as MutableList)
        })
        cartViewModel.getItems()
    }

    private fun subscribeCartItemsCount() {
        cartViewModel.cartItemsCount.observe(viewLifecycleOwner, { count ->
            if(count == 0) {
                switchToEmptyCart()
                isEmpty = true
            } else {
                if(isEmpty) {
                    switchToNormalCart()
                    isEmpty = false
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.cart_menu, menu)
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
    //    getMenuInflater().inflate(R.menu.menu_main, menu);
    //
    //    for(int i = 0; i < menu.size(); i++){
    //        Drawable drawable = menu.getItem(i).getIcon();
    //        if(drawable != null) {
    //            drawable.mutate();
    //            drawable.setColorFilter(getResources().getColor(R.color.textColorPrimary), PorterDuff.Mode.SRC_ATOP);
    //        }
    //    }
    //
    //    return true;
    //}

    private fun subscribeTotalPrice() {
        cartViewModel.cartPrice.observe(viewLifecycleOwner, {
            binding.price.text = context?.getString(R.string.rubles_price, it)
        })
    }

    private fun switchToNormalCart() {
        binding.emptyCartBlock.visibility = View.INVISIBLE
        binding.normalCartBlock.visibility = View.VISIBLE
    }

    private fun switchToEmptyCart() {
        binding.normalCartBlock.visibility = View.INVISIBLE
        binding.emptyCartBlock.visibility = View.VISIBLE
    }

    private fun onCartItemIncrease(cartItem: CartItem1) {
        cartViewModel.setCartItem(cartItem)
//        cartViewModel.increaseCartItem(cartItem)
//        cartViewModel.addDish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.clearCart -> {
                cartViewModel.clearCart()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun onCartItemDecrease(cartItem: CartItem1) {
        if(cartItem.dish.count != 0) {
            cartViewModel.setCartItem(cartItem)
        } else {
            cartViewModel.removeCartItem(cartItem)
        }
    }

    private fun setupDoOrderClickListener() {
        binding.doOrder.setOnClickListener {
            val directions = CartFragmentDirections.toCreateOrder()
            findNavController().navigate(directions)
        }
    }
}