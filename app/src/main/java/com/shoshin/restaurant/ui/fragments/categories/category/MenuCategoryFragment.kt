package com.shoshin.restaurant.ui.fragments.categories.category

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.Category
import com.shoshin.domain_abstract.entities.dish.Dish
import com.shoshin.restaurant.R
import com.shoshin.restaurant.common.argument
import com.shoshin.restaurant.databinding.MenuCategoryFragmentBinding
import com.shoshin.restaurant.ui.fragments.cart.CartViewModel
import com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.DishBottomDialogFragment
import com.shoshin.restaurant.ui.fragments.categories.category.dishes_recycler.DishAdapter
import com.shoshin.restaurant.ui.fragments.categories.category.dishes_recycler.DishHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuCategoryFragment:
    Fragment(R.layout.menu_category_fragment),
    DishHolder.OnClickListener,
    DishHolder.OnPriceClickListener
{
    private val binding by viewBinding(MenuCategoryFragmentBinding::bind)
    private var category: Category by argument()
    private val viewModel: MenuCategoryViewModel by viewModels()
    private lateinit var adapter: DishAdapter
    private var cartViewModel: CartViewModel?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        subscribeDishes()
        viewModel.getDishes(category)
    }

    private fun initRecycler() {
        val numberOfColumns = 2
        binding.recyclerView.layoutManager = GridLayoutManager(this.context, numberOfColumns)
        adapter = DishAdapter(this, this)
        binding.recyclerView.adapter = adapter
    }

    private fun subscribeDishes() {
        viewModel.dishes.observe(viewLifecycleOwner, { event ->
            when(event) {
                is Reaction.Success -> showSuccess(event.data)
                is Reaction.Progress -> showDownload()
                else -> {}
            }
        })
    }

    private fun toDataMode() {
        binding.errorLayout.isVisible = false
        binding.downloadLayout.isVisible = false
        binding.mainLayout.isVisible = true
    }

    private fun toDownloadMode() {
        binding.errorLayout.isVisible = false
        binding.mainLayout.isVisible = false
        binding.downloadLayout.isVisible = true
    }


    private fun showSuccess(dishes :List<Dish>) {
        adapter.setupItems(dishes)
        toDataMode()
    }

    private fun showDownload() {
        toDownloadMode()
    }

    override fun onDishClick(dish: Dish) {
        openBottomDialogFragment(dish)
    }

    private fun openBottomDialogFragment(item: Dish) {
        DishBottomDialogFragment.newInstance(item).show(
            childFragmentManager,
            DishBottomDialogFragment.TAG
        )
    }

    override fun onDishPriceClick(dish: Dish) {
        if(dish.options != null && dish.options!!.size > 0) {
            openBottomDialogFragment(dish)
        } else {
            cartViewModel?.addItem(dish.clone())
            Toast.makeText(context?.applicationContext, "Блюдо добавлено в корзину", Toast.LENGTH_SHORT).show()
        }
    }
}