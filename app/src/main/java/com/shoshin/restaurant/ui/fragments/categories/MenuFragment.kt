package com.shoshin.restaurant.ui.fragments.categories

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.category.Category
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.MenuFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                is Reaction.Progress -> toDownloadMode()
                is Reaction.Error -> showError(event)
                is Reaction.Success -> showSuccessData(event.data)
            }
        })
    }

    private fun showSuccessData(categories: List<Category>) {
        menuCategoryAdapter.setupItems(categories)
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

    private fun showError(event: Reaction.Error<List<Category>>) {
        toErrorMode()
    }

    override fun onCategoryClick(category: Category) {
        findNavController().navigate(
            MenuFragmentDirections.toCategory(category)
        )
    }

}