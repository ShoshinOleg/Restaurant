package com.shoshin.restaurant.ui.fragments.cart

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.CartFragmentBinding
import com.shoshin.data.db.entities.cart.CartItem

class CartFragment:
    Fragment(R.layout.cart_fragment),
    CartItemHolder.OnCartItemChangeCount,
    CartItemHolder.OnRemoveCartItem
{
    private val adapter = CartAdapter(this, this)
    private val binding by viewBinding(CartFragmentBinding::bind)
    private var isEmpty = true
    private var cartViewModel: CartViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupDoOrderClickListener()
        setupToMenuClickListener()
        initRecycler()
        subscribeCart()
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
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        Log.i("CartFragment", "onResume")
        super.onResume()
        adapter.notifyDataSetChanged()
        checkCart()
    }
    private fun checkCart() {
        cartViewModel?.subscribeCartItemsCount()?.observe(viewLifecycleOwner, { count ->
            Log.e("count=", "$count")
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
        menu.findItem(R.id.more).icon.setTint(
            ContextCompat.getColor(context?.applicationContext!!, R.color.black)
        )
    }

    private fun subscribeCart() {
        cartViewModel?.subscribeCart()?.observe(viewLifecycleOwner, {
            adapter.setupItems(it)
        })
    }

    private fun subscribeTotalPrice() {
        cartViewModel?.subscribeTotalPrice()?.observe(viewLifecycleOwner, {
            binding.price.text = context?.getString(R.string.rubles_price, it)
        })
    }

    private fun subscribeCartItemsCount() {
        cartViewModel?.subscribeCartItemsCount()?.observe(viewLifecycleOwner, { count ->
            Log.e("count=", "$count")
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

    private fun switchToNormalCart() {
        binding.emptyCartBlock.visibility = View.INVISIBLE
        binding.normalCartBlock.visibility = View.VISIBLE
    }

    private fun switchToEmptyCart() {
        binding.normalCartBlock.visibility = View.INVISIBLE
        binding.emptyCartBlock.visibility = View.VISIBLE
    }

    override fun onItemRemove(cartItem: CartItem) {
        cartViewModel?.removeItem(cartItem)
    }

    override fun onChangeCount(cartItem: CartItem, count: Int) {
        cartViewModel?.changeCount(cartItem, count)
    }
}