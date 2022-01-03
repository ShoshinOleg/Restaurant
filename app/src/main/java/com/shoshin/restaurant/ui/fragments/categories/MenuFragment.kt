package com.shoshin.restaurant.ui.fragments.categories

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.domain_abstract.entities.category.MenuCategory
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.MenuFragmentBinding
import com.shoshin.restaurant.ui.common.ViewModelEvent

class MenuFragment :
    Fragment(R.layout.menu_fragment),
    MenuCategoryHolder.OnCategoryClickListener
{
    private var menuCategoryAdapter = MenuCategoryAdapter(this)
    private val binding by viewBinding(MenuFragmentBinding::bind)
    private val categoriesViewModel: CategoriesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        subscribeCategories()
        categoriesViewModel.getCategories()
    }

    private fun initRecyclerView() {
        val numberOfColumns = 2
        binding.recyclerView.layoutManager = GridLayoutManager(this.context, numberOfColumns)
        binding.recyclerView.adapter = menuCategoryAdapter
    }

    private fun subscribeCategories() {
        categoriesViewModel.categories.observe(viewLifecycleOwner, { event ->
            when(event) {
                is ViewModelEvent.Download -> toDownloadMode()
                is ViewModelEvent.Error -> showError(event)
                is ViewModelEvent.Success -> showSuccessData(event.data)
            }
        })
    }

    private fun showSuccessData(categories: List<MenuCategory>) {
        menuCategoryAdapter.setupCategories(categories)
        toDataMode()
    }

    private fun toDownloadMode() {
        binding.mainLayout.isVisible = false
        binding.errorLayout.isVisible = false
        binding.downloadLayout.isVisible = true
    }

    private fun toDataMode() {
        binding.downloadLayout.isVisible = false
        binding.errorLayout.isVisible = false
        binding.mainLayout.isVisible = true
    }

    private fun toErrorMode() {
        binding.mainLayout.isVisible = false
        binding.downloadLayout.isVisible = false
        binding.errorLayout.isVisible = true
    }

    private fun showError(event: ViewModelEvent.Error<List<MenuCategory>>) {
        toErrorMode()
    }

    override fun onCategoryClick(category: MenuCategory) {
        findNavController().navigate(
            MenuFragmentDirections.toCategory(category)
        )
    }

}