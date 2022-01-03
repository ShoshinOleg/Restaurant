package com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_bottom_fragment.option.variantRV

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shoshin.domain_abstract.entities.dish.DishOptionVariant
import com.shoshin.restaurant.R

class VariantAdapter(
    private val isMultiCheck: Boolean
): RecyclerView.Adapter<VariantHolder>(),
    VariantHolder.OnVariantCheck
{
    private var variants: List<DishOptionVariant>? = null
    private var checkedPosition: Int? = null

    fun setVariants(items: List<DishOptionVariant>) {
        variants = items
        if(!isMultiCheck) {
            val index = variants?.indexOfFirst { it.isChecked }
            if(index != -1) {
                checkedPosition = index
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariantHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.dish_option_variant_holder, parent, false)
        return VariantHolder(view, this)
    }

    override fun onBindViewHolder(holder: VariantHolder, position: Int) {
        holder.bind(variants!![position])
    }

    override fun getItemCount(): Int {
        return variants?.size ?: 0
    }

    override fun onVariantCheck(variant: DishOptionVariant) {
        val index = variants?.indexOfFirst { it.id == variant.id }
        if(index != -1 ) {
            if(!isMultiCheck) {
                when (checkedPosition) {
                    null -> {
                        Log.e("tut1", "tut1")
                        checkedPosition = index
                    }
                    index -> {
                        Log.e("tut2", "tut2")
                        checkedPosition = null
                    }
                    else -> {
                        Log.e("tut3", "tut3")
                        variants!![checkedPosition!!].isChecked = false
                        notifyItemChanged(checkedPosition!!)
                        checkedPosition = index
                    }
                }
            }
        }
    }
}