package com.shoshin.restaurant.ui.fragments.order_create.locations_recycler

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.shoshin.domain_abstract.common.Reaction
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.restaurant.databinding.OrderLocationCheckLayoutBinding

class LocationsCheckableView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: OrderLocationCheckLayoutBinding
    private var state: Reaction<List<Location>> = Reaction.Progress()
    var adapter: LocationCheckableAdapter? = null
        set(value) {
            binding.locationRecycler.adapter = value
            field = value
        }

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = OrderLocationCheckLayoutBinding.inflate(layoutInflater, this, false)
        addView(binding.root)
        updateView()
    }


    private fun updateView() {
        state.let { state ->
            when(state) {
                is Reaction.Success -> {
                    binding.progress.visibility = View.GONE
                    if(state.data.isNotEmpty()) {
                        adapter?.setupItems(state.data as MutableList)
                    } else {
                        binding.emptyInfoBlock.visibility = View.VISIBLE
                    }
                }
                is Reaction.Progress -> {
                    binding.progress.visibility = View.VISIBLE
                    state.data?.let { adapter?.setupItems(state.data as MutableList) }
                }
                is Reaction.Error -> {}
            }
        }
    }

    fun setState(state: Reaction<List<Location>>) {
        this.state = state
        updateView()
    }
}