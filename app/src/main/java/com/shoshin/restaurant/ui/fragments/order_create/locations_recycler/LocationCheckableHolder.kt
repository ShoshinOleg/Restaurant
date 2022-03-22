package com.shoshin.restaurant.ui.fragments.order_create.locations_recycler

import android.view.View
import androidx.core.view.isVisible
import com.shoshin.domain_abstract.entities.locations.Location
import com.shoshin.restaurant.databinding.LocationCheckedHolderBinding
import com.shoshin.restaurant.ui.common.state_recycler.BaseStateViewHolder

class LocationCheckableHolder(
    itemView: View,
    private val editor: (location: Location) -> Unit = {},
    private val deleter: (location: Location) -> Unit = {},
    private val checker: (location: Location) -> Unit = {}
): BaseStateViewHolder<Location, LocationCheckableHolder.CheckedState>(itemView) {
    private val binding = LocationCheckedHolderBinding.bind(itemView)

    data class CheckedState(
        val isChecked: Boolean,
        val bodyState: BodyState
    ): State

    sealed interface BodyState {
        object Normal: BodyState
        object Progress: BodyState
        object Error: BodyState
    }

    init {
        binding.locationRow.editImage.setOnClickListener { item?.let(editor) }
        binding.locationRow.removeImage.setOnClickListener { item?.let(deleter) }
        binding.locationRow.name.setOnClickListener { item?.let(checker) }
    }

    override fun bind(item: Location, state: CheckedState) {
        super.bind(item, state)
        this.item = item
        binding.locationRow.name.text = item.fullName()

        when(state.bodyState) {
            is BodyState.Normal -> {
                binding.locationRow.removeImage.visibility
                binding.locationRow.error.visibility = View.GONE
                binding.locationRow.progress.isVisible = false
            }
            is BodyState.Progress -> {
                binding.locationRow.error.visibility = View.GONE
                binding.locationRow.progress.isVisible = true
            }
            is BodyState.Error -> {
                binding.locationRow.error.visibility = View.VISIBLE
                binding.locationRow.progress.isVisible = false
            }
        }

        binding.checkLocation.setOnClickListener(null)
        binding.checkLocation.isChecked = state.isChecked
        binding.checkLocation.setOnClickListener { this.item?.let(checker) }
    }
}