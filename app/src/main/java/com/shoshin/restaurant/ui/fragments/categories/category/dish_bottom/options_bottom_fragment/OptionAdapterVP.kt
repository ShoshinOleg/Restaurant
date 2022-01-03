package com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_bottom_fragment

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.shoshin.domain_abstract.entities.dish.DishOption
import com.shoshin.restaurant.ui.fragments.categories.category.dish_bottom.options_bottom_fragment.option.OptionFragment

class OptionAdapterVP(
    fm: FragmentManager,
    behavior: Int,
    private val mContext: Context
) : FragmentStatePagerAdapter(fm, behavior)
{
    var options: List<DishOption>? = null

    fun setupOptions(items: List<DishOption>) {
        options = items
        notifyDataSetChanged()
        Log.e("size=", "${options?.size}")
    }

    override fun getCount(): Int {
        return options?.size ?: 0
    }

    override fun getItem(position: Int): Fragment {
        return OptionFragment.newInstance(options!![position])
    }
}