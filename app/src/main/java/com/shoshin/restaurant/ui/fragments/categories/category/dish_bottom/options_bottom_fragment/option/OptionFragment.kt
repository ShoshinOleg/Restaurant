package com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_bottom_fragment.option

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shoshin.domain_abstract.entities.dish.DishOption
import com.shoshin.domain_abstract.entities.dish.DishOptionVariant
import com.shoshin.restaurant.R
import com.shoshin.restaurant.common.argument
import com.shoshin.restaurant.databinding.OptionFragmentBinding
import com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_bottom_fragment.option.variantRV.VariantAdapter
import com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_bottom_fragment.option.variantRV.VariantHolder

class OptionFragment:
    Fragment(R.layout.option_fragment) ,
    VariantHolder.OnVariantCheck
{
    private val binding by viewBinding(OptionFragmentBinding::bind)
    private var option: DishOption by argument()
    private var adapter: VariantAdapter? = null

    companion object {
        val TAG = OptionFragment::class.java.simpleName
        fun newInstance(itemOption: DishOption): OptionFragment {
            return OptionFragment().apply {
                option = itemOption
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = VariantAdapter(option.isMultiCheck)
        binding.recyclerView.adapter = adapter
        option.variants?.let {
            val list = mutableListOf<DishOptionVariant>()
            for(key in it.keys.sorted()) {
                list.add(it[key]!!)
            }
            adapter?.setVariants(list)
        }
    }

    override fun onVariantCheck(variant: DishOptionVariant) {

    }
}