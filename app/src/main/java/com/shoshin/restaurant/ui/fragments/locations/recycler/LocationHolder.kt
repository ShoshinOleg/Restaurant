package com.shoshin.restaurant.ui.fragments.locations.recycler

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.restaurant.R
import com.shoshin.restaurant.databinding.LocationItemHolderBinding
import com.shoshin.restaurant.ui.common.state_recycler.BaseStateViewHolder

class LocationHolder(
    itemView: View,
    private val editor: (location: Location) -> Unit = {},
    private val deleter: (location: Location) -> Unit = {}
): BaseStateViewHolder<Location, LocationHolder.LocationState>(itemView) {
    private var binding = LocationItemHolderBinding.bind(itemView)

    sealed interface LocationState: State {
        object Normal: LocationState
        object Progress: LocationState
        object Error: LocationState
    }

    init {
        binding.editImage.setOnClickListener { item?.let(editor) }
        binding.removeImage.setOnClickListener { item?.let(deleter) }
    }

    override fun bind(item: Location, state: LocationState) {
        super.bind(item, state)
        binding.name.text = item.fullName()

        when(state) {
            is LocationState.Normal -> {
                binding.removeImage.visibility
                binding.error.visibility = View.GONE
                binding.progress.isVisible = false
            }
            is LocationState.Progress -> {
                binding.error.visibility = View.GONE
                binding.progress.isVisible = true
            }
            is LocationState.Error -> {
                binding.error.visibility = View.VISIBLE
                binding.progress.isVisible = false
            }
        }
    }
}